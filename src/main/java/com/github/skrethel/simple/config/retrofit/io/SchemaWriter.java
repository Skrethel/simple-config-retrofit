package com.github.skrethel.simple.config.retrofit.io;

import com.github.skrethel.simple.config.retrofit.exception.WriteException;
import com.github.skrethel.simple.config.retrofit.schema.ConfigSchema;


public interface SchemaWriter {

	void write(ConfigSchema configSchema) throws WriteException;

}
