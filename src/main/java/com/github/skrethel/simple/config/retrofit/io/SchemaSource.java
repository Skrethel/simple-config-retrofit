package com.github.skrethel.simple.config.retrofit.io;

import com.github.skrethel.simple.config.retrofit.exception.GetException;
import com.github.skrethel.simple.config.retrofit.schema.ConfigSchema;


public interface SchemaSource {

	ConfigSchema getSchema() throws GetException;

}
