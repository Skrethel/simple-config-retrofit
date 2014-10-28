package com.github.skrethel.simple.config.retrofit.io;

import com.github.skrethel.simple.config.retrofit.exception.GetException;
import org.ini4j.Ini;


public interface ConfigSource {

	Ini getConfig() throws GetException;

}
