package com.fullstack.redditclone.application.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.fullstack.redditclone.application.exception.SpringRedditException;
import com.fullstack.redditclone.application.service.MailService;
import com.fullstack.redditclone.application.shared.EmailBody;

@Service
public class MailServiceImpl implements MailService {
	
	//will contain the email message that we want to send as an input
	
	@Autowired
	TemplateEngine templateEngine;
	
	@Autowired
	JavaMailSender javaMailSender;

	@Override
	public String build(String message) {
		
		Context context = new Context();
		context.setVariable("message", message);
		
		return templateEngine.process("emailTemplate", context);
	}

	
	//we will create an instance of MimeMessageHelper
	
	@Override
	public void sendMail(EmailBody emailBody) {
		
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom("springreddit@email.com");
			messageHelper.setTo(emailBody.getRecipient());
			messageHelper.setSubject(emailBody.getSubject());
			messageHelper.setText(build(emailBody.getBody()));
			
		};
		
		try {
			
			javaMailSender.send(messagePreparator);
			System.out.println("Mail SENT!!!!!!!!");
			
		}catch(MailException e) {
			
			throw new SpringRedditException("Exception Occured while sending mail to -> "+emailBody.getRecipient());
		}
		
	}

}
