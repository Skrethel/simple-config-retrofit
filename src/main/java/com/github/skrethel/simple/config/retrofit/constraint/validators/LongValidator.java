package com.github.skrethel.simple.config.retrofit.constraint.validators;

import com.github.skrethel.simple.config.retrofit.constraint.ConstraintValidator;
import com.github.skrethel.simple.config.retrofit.exception.ValidationException;

import java.util.Map;


public class LongValidator implements ConstraintValidator {

	@Override public void validate(Map<String, String> inputParams, String value) throws ValidationException {
		try {
			Long.valueOf(value);
		} catch (NumberFormatException e) {
			throw new ValidationException("Invalid long value " + value);
		}
	}

	@Override public String getType() {
		return "long";
	}
}
