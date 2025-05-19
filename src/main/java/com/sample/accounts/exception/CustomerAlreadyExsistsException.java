package com.sample.accounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CustomerAlreadyExsistsException extends RuntimeException {

	public CustomerAlreadyExsistsException(String message) {
		super(message);
	}
}
