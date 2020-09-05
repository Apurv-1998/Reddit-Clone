package com.fullstack.redditclone.application.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Override
	public void addCorsMappings(CorsRegistry registry){
		
		registry
		.addMapping("/**")
		.allowedMethods("*")
		.allowedOrigins("*");
		
	}
	
	//After Adding Cors we have to handle the jars for swagger
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		registry.addResourceHandler("swagger-ui.html")
			    .addResourceLocations("classpath:/META-INF/resources/");
		
		registry.addResourceHandler("/webjars/**")
		        .addResourceLocations("classpath:/META-INF/resources/webjars/");
		
	}

}
