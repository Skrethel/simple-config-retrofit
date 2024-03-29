package com.github.skrethel.simple.config.retrofit.workers;

import com.github.skrethel.simple.config.retrofit.constraint.SchemaConstraintValidator;
import com.github.skrethel.simple.config.retrofit.exception.GetException;
import com.github.skrethel.simple.config.retrofit.exception.ValidationException;
import com.github.skrethel.simple.config.retrofit.exception.ValidatorException;
import com.github.skrethel.simple.config.retrofit.io.ConfigSource;
import com.github.skrethel.simple.config.retrofit.io.SchemaSource;
import com.github.skrethel.simple.config.retrofit.schema.ConfigSchema;
import org.ini4j.Ini;


public class Validator {

	public void validate(ConfigSource configSource, SchemaSource schemaSource,
		SchemaConstraintValidator validator) throws ValidatorException {
		try {
			Ini config = configSource.getConfig();
			ConfigSchema schema = schemaSource.getSchema();
			validator.validate(schema, config);
		} catch (GetException e) {
			throw new ValidatorException("Cannot validate config file - input file read error. Reason : " + e.getMessage(), e);
		} catch (ValidationException e) {
			throw new ValidatorException("Validation failed. Error: " + e.getMessage(), e);
		}
	}
}
