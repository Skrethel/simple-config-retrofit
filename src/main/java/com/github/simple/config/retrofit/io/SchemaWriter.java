package com.github.simple.config.retrofit.io;

import com.github.simple.config.retrofit.exception.WriteException;
import com.github.simple.config.retrofit.schema.ConfigSchema;


public interface SchemaWriter {

	void write(ConfigSchema configSchema) throws WriteException;

}
