package com.github.skrethel.simple.config.retrofit.io;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.skrethel.simple.config.retrofit.exception.GetException;
import com.github.skrethel.simple.config.retrofit.schema.ConfigSchema;
import com.github.skrethel.simple.config.retrofit.schema.SchemaItem;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Set;


public class SchemaFileSource implements SchemaSource {

	private static final Logger LOGGER = LoggerFactory.getLogger(SchemaFileSource.class);

	private ValidatorFactory validatorFactory = Validation.byDefaultProvider()
		.configure()
		.buildValidatorFactory();
	private Validator validator = validatorFactory.getValidator();

	private String path;
	private InputStream inputStream;
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	static  {
		OBJECT_MAPPER.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
	}

	public SchemaFileSource(String path) {
		this.path = path;
	}

	public SchemaFileSource(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override public ConfigSchema getSchema() throws GetException {
		try {
			ConfigSchema schema = parseSchema();
			validate(schema);
			return schema;
		} catch (IOException e) {
			LOGGER.debug("Cannot read schema data due to error " + e.getMessage(), e);
			throw new GetException("Cannot read schema", e);
		}
	}

	private ConfigSchema parseSchema() throws IOException, GetException {
		if (path != null) {
			File source = new File(path);
			return OBJECT_MAPPER.readValue(source, ConfigSchema.class);
		} else if (inputStream != null) {
			return OBJECT_MAPPER.readValue(inputStream, ConfigSchema.class);
		} else {
			throw new GetException("Invalid input: no path or stream specified");
		}
	}

	private void validate(ConfigSchema schema) throws GetException {

		ArrayList<String> violationMessages = new ArrayList<>();
		for (int i = 0; i < schema.size(); i++) {
			SchemaItem item = schema.get(i);
			Set<ConstraintViolation<SchemaItem>> violations = validator.validate(item);
			for (ConstraintViolation<SchemaItem> violation : violations) {
				violationMessages.add("Schema item " + (i + 1) +
					": " + violation.getPropertyPath() + ": " + violation.getMessage());
			}
		}
		if (violationMessages.isEmpty()) {
			return;
		}
		throw new GetException("Invalid input schema. Violations: " + StringUtils.join(violationMessages, ", "));
	}
}
