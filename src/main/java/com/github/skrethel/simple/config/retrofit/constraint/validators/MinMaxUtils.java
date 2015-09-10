package com.github.skrethel.simple.config.retrofit.constraint.validators;

import com.github.skrethel.simple.config.retrofit.exception.ValidationException;


public class MinMaxUtils {

	public static void validateMax(long value, long max) throws ValidationException {
		if (value > max) {
			throw new ValidationException("Value " + value + " is lower than allowed maximum " + max);
		}
	}

	public static void validateMin(long value, long min) throws ValidationException {
		if (value < min) {
			throw new ValidationException("Value " + value + " is lower than allowed minimum " + min);
		}
	}
}
