package com.github.skrethel.simple.config.retrofit.exception;

public class GetException extends Exception {

	public GetException() {
	}

	public GetException(String message) {
		super(message);
	}

	public GetException(String message, Throwable cause) {
		super(message, cause);
	}

	public GetException(Throwable cause) {
		super(cause);
	}
}
