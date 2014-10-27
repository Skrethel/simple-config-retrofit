package com.github.simple.config.retrofit;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;


public class LogConfig {

	public static void defaultConfig() {
		Logger logger = Logger.getRootLogger();
		logger.setLevel(Level.INFO);
	}

	public static void verboseConfig() {
		Logger logger = Logger.getRootLogger();
		logger.setLevel(Level.DEBUG);
	}
}
