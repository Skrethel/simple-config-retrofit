package com.github.skrethel.simple.config.retrofit.constraint.validators;

import com.github.skrethel.simple.config.retrofit.constraint.ConstraintValidator;
import com.github.skrethel.simple.config.retrofit.exception.ValidationException;

import java.util.Map;


public class MinValidator implements ConstraintValidator {

	@Override public void validate(Map<String, String> inputParams, String value) throws ValidationException {
		long longValue;
		try {
			longValue = Long.valueOf(value);
		} catch (NumberFormatException e) {
			throw new ValidationException("Invalid integer value " + value);
		}
		String min = inputParams.get("min");
		long minValue;
		try {
			minValue = Long.valueOf(min);
		} catch (NumberFormatException e) {
			throw new ValidationException("Invalid specification of minimum value " + min);
		}
		if (longValue < minValue) {
			throw new ValidationException("Value " + value + " is lower than allowed minimum " + min);
		}
	}

	@Override public String getType() {
		return "min";
	}
}
