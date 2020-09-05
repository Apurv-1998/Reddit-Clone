package com.fullstack.redditclone.application.exception;

public class SpringRedditException extends RuntimeException {


	private static final long serialVersionUID = -5718355368673529200L;
	
	public SpringRedditException(String exceptionMessage) {
		super(exceptionMessage);
	}

	public SpringRedditException(String string, Exception e) {
		super(string);
		System.out.println(e);
	}

}
