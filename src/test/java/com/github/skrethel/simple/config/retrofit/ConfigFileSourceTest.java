package com.github.skrethel.simple.config.retrofit;

import com.github.skrethel.simple.config.retrofit.exception.GetException;
import com.github.skrethel.simple.config.retrofit.io.ConfigFileSource;
import org.ini4j.Ini;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;

import static org.junit.Assert.*;


public class ConfigFileSourceTest {

	@Test(expected = GetException.class)
	public void testFailed() throws Exception {
		// Create and delete file to get non existent pseudo random file
		ConfigFileSource source = new ConfigFileSource("/this/cannot/exist/for/real");
		source.getConfig();
	}

	@Test
	public void testExisting() throws Exception {
		File tmpFile = File.createTempFile("testGet", ".json");
		tmpFile.deleteOnExit();

		String groupName = "some_group";
		String name = "some_name";
		String value = "some_value";
		try (FileWriter writer = new FileWriter(tmpFile)) {
			writer.write("[" + groupName + "]\n"
				+  name + ": " + value);
		}
		ConfigFileSource source = new ConfigFileSource(tmpFile.getPath());
		Ini ini = source.getConfig();
		assertEquals(value, ini.get(groupName, name));
	}
}