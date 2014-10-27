package com.github.simple.config.retrofit;

import com.github.simple.config.retrofit.io.SchemaFileWriter;
import com.github.simple.config.retrofit.schema.ConfigSchema;
import com.github.simple.config.retrofit.schema.Constraint;
import com.github.simple.config.retrofit.schema.SchemaItem;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.*;


public class SchemaFileWriterTest {

	@Test
	public void testGenericConstraintParams() throws Exception {

		SchemaItem item = new SchemaItem();
		item.setGroup("some");
		item.setName("some");
		item.setDefaultValue(1);
		Constraint c = new Constraint();
		c.setType("min");
		HashMap<String, String> params = new HashMap<>();
		params.put("min", "3");
		c.setParams(params);
		item.setConstraints(Arrays.asList(c));

		File tmpFile = File.createTempFile("schema", "json");
		tmpFile.deleteOnExit();

		SchemaFileWriter writer = new SchemaFileWriter(tmpFile.getAbsolutePath());

		ConfigSchema schema = new ConfigSchema();
		schema.add(item);
		writer.write(schema);

		String content = FileUtils.readFileToString(tmpFile);
		assertTrue(content.contains("\"min\":\"3\""));

	}
}