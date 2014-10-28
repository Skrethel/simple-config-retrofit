package com.github.skrethel.simple.config.retrofit.workers;

import com.github.skrethel.simple.config.retrofit.constraint.SchemaConstraintValidator;
import com.github.skrethel.simple.config.retrofit.exception.GetException;
import com.github.skrethel.simple.config.retrofit.exception.ValidationException;
import com.github.skrethel.simple.config.retrofit.exception.ValidatorException;
import com.github.skrethel.simple.config.retrofit.io.ConfigSource;
import com.github.skrethel.simple.config.retrofit.io.SchemaSource;
import com.github.skrethel.simple.config.retrofit.schema.ConfigSchema;
import org.apache.log4j.Logger;
import org.ini4j.Ini;


public class Validator {

	private static final Logger LOGGER = Logger.getLogger(Validator.class);

	public void retrofit(ConfigSource configSource, SchemaSource schemaSource,
		SchemaConstraintValidator validator) throws ValidatorException {
		try {
			Ini config = configSource.getConfig();
			ConfigSchema schema = schemaSource.getSchema();
			validator.validate(schema, config);
		} catch (GetException e) {
			throw new ValidatorException("Cannot generate config file from schema: " + e.getMessage(), e);
		} catch (ValidationException e) {
			throw new ValidatorException("Cannot write config file generated from schema: " + e.getMessage(), e);
		}
	}
}
