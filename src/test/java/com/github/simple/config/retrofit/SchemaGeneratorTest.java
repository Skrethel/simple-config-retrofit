package com.github.simple.config.retrofit;

import com.github.simple.config.retrofit.exception.GeneratorException;
import com.github.simple.config.retrofit.exception.GetException;
import com.github.simple.config.retrofit.exception.WriteException;
import com.github.simple.config.retrofit.io.ConfigSource;
import com.github.simple.config.retrofit.io.SchemaWriter;
import com.github.simple.config.retrofit.schema.ConfigSchema;
import com.github.simple.config.retrofit.workers.Generator;
import org.ini4j.Ini;
import org.junit.Test;

import static com.github.simple.config.retrofit.TestUtils.*;
import static org.junit.Assert.*;


public class SchemaGeneratorTest implements ConfigSource, SchemaWriter {

	private Ini inputConfig;
	private ConfigSchema outputSchema;

	@Test(expected = GeneratorException.class)
	public void testEmptySchema() throws Exception {
		inputConfig = new Ini();

		Generator generator = new Generator();

		generator.generate(this, this);
	}

	@Test
	public void testSchemaGeneration() throws Exception {
		inputConfig = new Ini();

		String group1 = "group1";
		String name1 = "name1";
		String value1 = "value1";
		String comment1 = "comment1";
		addIniItem(inputConfig, group1, name1, value1, comment1);
		String group2 = "group2";
		String name2 = "name2";
		String value2 = "value2";
		String comment2 = "comment2";
		addIniItem(inputConfig, group2, name2, value2, comment2);
		String group3 = "group3";
		String name3 = "name3";
		String value3 = "value3";
		addIniItem(inputConfig, group3, name3, value3, null);

		Generator generator = new Generator();
		generator.generate(this, this);

		assertTrue(outputSchema.contains(group1, name1));
		assertTrue(outputSchema.contains(group2, name2));
		assertTrue(outputSchema.contains(group3, name3));

		assertEquals(comment1, outputSchema.get(group1, name1).getDescription());
		assertEquals(comment2, outputSchema.get(group2, name2).getDescription());
		assertNull(outputSchema.get(group3, name3).getDescription());
	}

	@Override public Ini getConfig() throws GetException {
		return inputConfig;
	}

	@Override public void write(ConfigSchema configSchema) throws WriteException {
		outputSchema = configSchema;
	}
}