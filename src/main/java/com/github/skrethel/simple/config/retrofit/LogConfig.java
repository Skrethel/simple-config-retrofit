package com.github.skrethel.simple.config.retrofit;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;


public class LogConfig {

	public static void defaultConfig() {
		Configurator.setRootLevel(Level.INFO);
	}

	public static void verboseConfig() {
		Configurator.setRootLevel(Level.DEBUG);
	}
}
