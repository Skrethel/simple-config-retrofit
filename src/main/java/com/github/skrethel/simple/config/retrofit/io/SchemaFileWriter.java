package com.github.skrethel.simple.config.retrofit.io;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.skrethel.simple.config.retrofit.exception.WriteException;
import com.github.skrethel.simple.config.retrofit.schema.ConfigSchema;
import org.apache.log4j.Logger;

import java.io.File;


public class SchemaFileWriter implements SchemaWriter {
	private static final Logger LOGGER = Logger.getLogger(SchemaFileWriter.class);
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	private final String schemaFile;

	static  {
		OBJECT_MAPPER.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
	}

	public SchemaFileWriter(String schemaFile) {
		this.schemaFile = schemaFile;
	}

	public void write(ConfigSchema schema) throws WriteException {
		try {
			OBJECT_MAPPER.writeValue(new File(schemaFile), schema);
		} catch (java.io.IOException e) {
			LOGGER.debug("Cannot write schema data due to error " + e.getMessage(), e);
			throw new WriteException("Cannot write schema data", e);
		}
	}
}
