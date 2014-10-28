package com.github.skrethel.simple.config.retrofit.io;

import com.github.skrethel.simple.config.retrofit.exception.WriteException;
import org.ini4j.Ini;

public interface ConfigWriter {

	void write(Ini config) throws WriteException;

}
