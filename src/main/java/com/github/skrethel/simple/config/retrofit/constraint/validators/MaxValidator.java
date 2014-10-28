package com.github.skrethel.simple.config.retrofit.constraint.validators;

import com.github.skrethel.simple.config.retrofit.constraint.ConstraintValidator;
import com.github.skrethel.simple.config.retrofit.exception.ValidationException;

import java.util.Map;


public class MaxValidator implements ConstraintValidator {

	@Override public void validate(Map<String, String> inputParams, String value) throws ValidationException {
		long longValue;
		try {
			longValue = Long.valueOf(value);
		} catch (NumberFormatException e) {
			throw new ValidationException("Invalid integer value " + value);
		}
		String max = inputParams.get("max");
		long maxValue;
		try {
			maxValue = Long.valueOf(max);
		} catch (NumberFormatException e) {
			throw new ValidationException("Invalid specification of maximum value " + max);
		}
		if (longValue > maxValue) {
			throw new ValidationException("Value " + value + " is lower than allowed maximum " + max);
		}
	}

	@Override public String getType() {
		return "max";
	}
}
