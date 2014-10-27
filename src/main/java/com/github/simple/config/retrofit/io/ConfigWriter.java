package com.github.simple.config.retrofit.io;

import com.github.simple.config.retrofit.exception.WriteException;
import org.ini4j.Ini;

public interface ConfigWriter {

	void write(Ini config) throws WriteException;

}
