package com.project.blogging.exception;

public class InvalidUserNameOrPasswordException extends RuntimeException {

	private String password;
	private String message;

	public InvalidUserNameOrPasswordException(String message, String password) {

		super(String.format("%s not found with:  %s", message, password));// this is messege will get from super exception
																		// class

		this.message = message;
		this.password = password;

	}

}
