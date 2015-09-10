package com.github.skrethel.simple.config.retrofit.constraint.validators;

import com.github.skrethel.simple.config.retrofit.constraint.ConstraintValidator;
import com.github.skrethel.simple.config.retrofit.exception.ValidationException;

import java.util.Map;


public class LongValidator implements ConstraintValidator {

	@Override public void validate(Map<String, String> inputParams, String value) throws ValidationException {
		long longValue;
		try {
			longValue = Long.valueOf(value);
		} catch (NumberFormatException e) {
			throw new ValidationException("Invalid long value " + value);
		}
		validateMax(inputParams, longValue);
		validateMin(inputParams, longValue);
	}

	private void validateMax(Map<String, String> inputParams, long longValue) throws ValidationException {
		String max = inputParams.get("max");
		if (max == null) {
			return;
		}
		long maxValue;
		try {
			maxValue = Long.valueOf(max);
		} catch (NumberFormatException e) {
			throw new ValidationException("Invalid specification of maximum value " + max);
		}
		MinMaxUtils.validateMax(longValue, maxValue);
	}

	private void validateMin(Map<String, String> inputParams, long longValue) throws ValidationException {
		String min = inputParams.get("min");
		if (min == null) {
			return;
		}
		long minValue;
		try {
			minValue = Long.valueOf(min);
		} catch (NumberFormatException e) {
			throw new ValidationException("Invalid specification of minimum value " + min);
		}
		MinMaxUtils.validateMin(longValue, minValue);
	}

	@Override public String getType() {
		return "long";
	}
}
