package com.github.skrethel.simple.config.retrofit.exception;

public class ValidatorException extends Exception {

	public ValidatorException() {
	}

	public ValidatorException(String message) {
		super(message);
	}

	public ValidatorException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValidatorException(Throwable cause) {
		super(cause);
	}
}
