package com.fullstack.redditclone.application.service;

import com.fullstack.redditclone.application.shared.EmailBody;

public interface MailService {
	
	String build(String message);
	
	void sendMail(EmailBody emailBody);

}
