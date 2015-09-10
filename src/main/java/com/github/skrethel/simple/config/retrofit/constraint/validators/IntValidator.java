package com.github.skrethel.simple.config.retrofit.constraint.validators;

import com.github.skrethel.simple.config.retrofit.constraint.ConstraintValidator;
import com.github.skrethel.simple.config.retrofit.exception.ValidationException;

import java.util.Map;


public class IntValidator implements ConstraintValidator {

	@Override public void validate(Map<String, String> inputParams, String value) throws ValidationException {
		int intValue;
		try {
			intValue = Integer.valueOf(value);
		} catch (NumberFormatException e) {
			throw new ValidationException("Invalid integer value " + value);
		}

		validateMax(inputParams, intValue);
		validateMin(inputParams, intValue);
	}

	private void validateMax(Map<String, String> inputParams, int intValue) throws ValidationException {
		String max = inputParams.get("max");
		if (max == null) {
			return;
		}
		int maxValue;
		try {
			maxValue = Integer.valueOf(max);
		} catch (NumberFormatException e) {
			throw new ValidationException("Invalid specification of maximum value " + max);
		}
		MinMaxUtils.validateMax(intValue, maxValue);
	}

	private void validateMin(Map<String, String> inputParams, int intValue) throws ValidationException {
		String min = inputParams.get("min");
		if (min == null) {
			return;
		}
		int minValue;
		try {
			minValue = Integer.valueOf(min);
		} catch (NumberFormatException e) {
			throw new ValidationException("Invalid specification of minimum value " + min);
		}
		MinMaxUtils.validateMin(intValue, minValue);
	}

	@Override public String getType() {
		return "int";
	}
}
