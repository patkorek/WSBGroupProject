package com.WSBGroupProject.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserAlreadyExistsException extends RuntimeException {
	public UserAlreadyExistsException(String username) {
		super("user '" + username + "' already exists.");
	}
}
