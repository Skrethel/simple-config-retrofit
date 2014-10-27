package com.github.simple.config.retrofit.io;

import com.github.simple.config.retrofit.exception.GetException;
import com.github.simple.config.retrofit.schema.ConfigSchema;


public interface SchemaSource {

	ConfigSchema getSchema() throws GetException;

}
