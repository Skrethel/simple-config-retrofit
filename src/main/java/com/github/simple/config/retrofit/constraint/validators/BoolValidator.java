package com.github.simple.config.retrofit.constraint.validators;

import com.github.simple.config.retrofit.constraint.ConstraintValidator;
import com.github.simple.config.retrofit.exception.ValidationException;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;


public class BoolValidator implements ConstraintValidator {

	@Override public void validate(Map<String, String> inputParams, String value) throws ValidationException {
		if (!(StringUtils.equals(value, "true") || StringUtils.equals(value, "false"))) {
			throw new ValidationException("Invalid bool value " + value);
		}
	}

	@Override public String getType() {
		return "bool";
	}
}
