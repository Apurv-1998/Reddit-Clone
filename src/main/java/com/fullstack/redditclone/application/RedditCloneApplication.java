package com.fullstack.redditclone.application;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RedditCloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedditCloneApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		return mapper;
	}
	
}
