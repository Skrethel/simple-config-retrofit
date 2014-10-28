package com.github.skrethel.simple.config.retrofit.constraint;

import com.github.skrethel.simple.config.retrofit.exception.ValidationException;

import java.util.Map;


public interface ConstraintValidator {

	void validate(Map<String, String> inputParams, String value) throws ValidationException;

	String getType();
}
