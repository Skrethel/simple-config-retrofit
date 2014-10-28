package com.github.skrethel.simple.config.retrofit.io;

import com.github.skrethel.simple.config.retrofit.exception.GetException;
import org.apache.log4j.Logger;
import org.ini4j.Ini;

import java.io.File;
import java.io.IOException;


public class ConfigFileSource implements ConfigSource {
	private static final Logger LOGGER = Logger.getLogger(ConfigFileSource.class);

	private String path;

	public ConfigFileSource(String path) {
		this.path = path;
	}

	@Override public Ini getConfig() throws GetException {
		try {
			return new Ini(new File(path));
		} catch (IOException e) {
			LOGGER.debug("Cannot read configuration data due to error: " + e.getMessage(), e);
			throw new GetException("Cannot read configuration data due to error: " + e.getMessage(), e);
		}
	}
}
