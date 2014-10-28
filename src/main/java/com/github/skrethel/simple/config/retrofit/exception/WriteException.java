package com.github.skrethel.simple.config.retrofit.exception;

public class WriteException extends Exception {

	public WriteException() {
	}

	public WriteException(String message) {
		super(message);
	}

	public WriteException(String message, Throwable cause) {
		super(message, cause);
	}

	public WriteException(Throwable cause) {
		super(cause);
	}
}
