package com.github.skrethel.simple.config.retrofit.exception;

public class RetrofitException extends Exception {

	public RetrofitException() {
	}

	public RetrofitException(String message) {
		super(message);
	}

	public RetrofitException(String message, Throwable cause) {
		super(message, cause);
	}

	public RetrofitException(Throwable cause) {
		super(cause);
	}
}
