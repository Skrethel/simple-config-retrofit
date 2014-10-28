package com.github.skrethel.simple.config.retrofit.constraint;

import com.github.skrethel.simple.config.retrofit.exception.ValidationException;
import com.github.skrethel.simple.config.retrofit.schema.ConfigSchema;
import org.ini4j.Ini;


public interface SchemaConstraintValidator {

	void validate(ConfigSchema schema, Ini config) throws ValidationException;
}
