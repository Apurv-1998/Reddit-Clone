package com.fullstack.redditclone.application.dto;

import java.io.Serializable;

public class CreateUserDto implements Serializable {

	private static final long serialVersionUID = 4456278192220657592L;

	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String registeredPassword;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRegisteredPassword() {
		return registeredPassword;
	}

	public void setRegisteredPassword(String registeredPassword) {
		this.registeredPassword = registeredPassword;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
