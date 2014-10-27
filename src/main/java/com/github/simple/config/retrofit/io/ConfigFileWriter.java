package com.github.simple.config.retrofit.io;

import com.github.simple.config.retrofit.exception.WriteException;
import org.apache.log4j.Logger;
import org.ini4j.Ini;

import java.io.File;
import java.io.IOException;


public class ConfigFileWriter implements ConfigWriter {
	private static final Logger LOGGER = Logger.getLogger(ConfigFileWriter.class);

	private String path;

	public ConfigFileWriter(String path) {
		this.path = path;
	}

	@Override public void write(Ini config) throws WriteException {
		try {
			config.store(new File(path));
		} catch (IOException e) {
			LOGGER.debug("Cannot write configuration data due to error " + e.getMessage(), e);
			throw new WriteException("Cannot write configuration data", e);
		}
	}
}
