package com.github.skrethel.simple.config.retrofit.constraint.validators;

import com.github.skrethel.simple.config.retrofit.constraint.ConstraintValidator;
import com.github.skrethel.simple.config.retrofit.exception.ValidationException;

import java.util.Map;


public class MinValidator implements ConstraintValidator {

	@Override public void validate(Map<String, String> inputParams, String value) throws ValidationException {
		int intValue;
		try {
			intValue = Integer.valueOf(value);
		} catch (NumberFormatException e) {
			throw new ValidationException("Invalid integer value " + value);
		}
		String min = inputParams.get("min");
		int minValue;
		try {
			minValue = Integer.valueOf(min);
		} catch (NumberFormatException e) {
			throw new ValidationException("Invalid specification of minimum value" + value);
		}
		if (intValue < minValue) {
			throw new ValidationException("Value " + value + " is lower than allowed minimum " + min);
		}
	}

	@Override public String getType() {
		return "min";
	}
}
