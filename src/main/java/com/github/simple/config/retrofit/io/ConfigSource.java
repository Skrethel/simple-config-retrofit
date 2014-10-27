package com.github.simple.config.retrofit.io;

import com.github.simple.config.retrofit.exception.GetException;
import org.ini4j.Ini;


public interface ConfigSource {

	Ini getConfig() throws GetException;

}
