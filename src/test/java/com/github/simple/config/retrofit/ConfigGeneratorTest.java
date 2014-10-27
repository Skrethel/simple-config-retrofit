package com.github.simple.config.retrofit;

import com.github.simple.config.retrofit.exception.GeneratorException;
import com.github.simple.config.retrofit.exception.GetException;
import com.github.simple.config.retrofit.exception.WriteException;
import com.github.simple.config.retrofit.io.ConfigWriter;
import com.github.simple.config.retrofit.io.SchemaSource;
import com.github.simple.config.retrofit.schema.ConfigSchema;
import com.github.simple.config.retrofit.workers.Generator;
import org.ini4j.Ini;
import org.junit.Test;

import static com.github.simple.config.retrofit.TestUtils.*;
import static org.junit.Assert.*;


public class ConfigGeneratorTest implements SchemaSource, ConfigWriter {

	private Ini outputConfig;
	private ConfigSchema schema;

	@Test(expected = GeneratorException.class)
	public void testEmptySchema() throws Exception {
		schema = new ConfigSchema();

		Generator generator = new Generator();

		generator.generate(this, this);
	}

	@Test
	public void testGeneratorFromSchema() throws Exception {
		schema = new ConfigSchema();

		String groupName1 = "group1";
		String name1 = "name1";
		Integer value1 = 1;
		String description1 = "description1";
		addSchemaItem(schema, groupName1, name1, value1, description1);

		String groupName2 = "group2";
		String name2 = "name2";
		Integer value2 = 2;
		addSchemaItem(schema, groupName2, name2, value2, null);

		String groupName3 = "group3";
		String name3 = "name3";
		Integer value3 = 3;
		String description3 = "description3";
		addSchemaItem(schema, groupName3, name3, value3, description3);

		Generator generator = new Generator();
		generator.generate(this, this);

		String expectedContent = "[" + groupName1 + "]\n"
			+ "#" + description1 +"\n"
			+ name1 + " = 1\n"
			+ "\n"
			+ "[" + groupName2 + "]\n"
			+ name2 + " = 2\n"
			+ "\n"
			+ "[" + groupName3 + "]\n"
			+ "#" + description3 + "\n"
			+ name3 + " = 3\n\n";
		assertEquals(expectedContent, getConfigAsString(outputConfig));

	}

	@Override public void write(Ini config) throws WriteException {
		outputConfig = config;
	}

	@Override public ConfigSchema getSchema() throws GetException {
		return schema;
	}

}