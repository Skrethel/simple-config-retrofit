package com.github.simple.config.retrofit;

import com.github.simple.config.retrofit.exception.GetException;
import com.github.simple.config.retrofit.io.SchemaFileSource;
import com.github.simple.config.retrofit.schema.ConfigSchema;
import com.github.simple.config.retrofit.schema.SchemaItem;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import static org.junit.Assert.*;


public class SchemaFileSourceTest {

	@Test
	public void testSingle() throws Exception {
		File tmpFile = File.createTempFile("testGet", ".json");
		tmpFile.deleteOnExit();

		String groupName = "some_group";
		String name = "some_name";
		Boolean defaultValue = true;
		try (FileWriter writer = new FileWriter(tmpFile)) {
			writer.write("[{"
				+ "\"group\": \"" + groupName + "\","
				+ "\"name\": \"" + name + "\","
				+ "\"default\": " + defaultValue +","
				+ "\"constraints\": [ { \"type\": \"bool\"}, {\"type\": \"custom_validator\"} ]\n"
				+ "}]");
		}

		SchemaFileSource source = new SchemaFileSource(tmpFile.getAbsolutePath());

		ConfigSchema schema = source.getSchema();

		assertEquals(1, schema.size());
		SchemaItem item = schema.get(0);
		assertEquals(groupName, item.getGroup());
		assertEquals(name, item.getName());
		assertNull(item.getDescription());
		assertNotNull(item.getConstraints());
		assertEquals(defaultValue, item.getDefaultValue());
	}

	@Test
	public void testMultiple() throws Exception {
		File tmpFile = File.createTempFile("testGet", ".json");
		tmpFile.deleteOnExit();

		String groupName = "some_group";
		String name = "some_name";
		Boolean defaultValue = true;
		try (FileWriter writer = new FileWriter(tmpFile)) {
			writer.write("[{"
				+ "\"group\": \"" + groupName + "\","
				+ "\"name\": \"" + name + "\","
				+ "\"default\": [ \"A\", \"B\"]"
				+ "}]");
		}

		SchemaFileSource source = new SchemaFileSource(tmpFile.getAbsolutePath());

		ConfigSchema schema = source.getSchema();

		assertEquals(1, schema.size());
		SchemaItem item = schema.get(0);
		assertEquals(groupName, item.getGroup());
		assertEquals(name, item.getName());
		assertNull(item.getDescription());
		assertNull(item.getConstraints());
		ArrayList<String> values = new ArrayList<>();
		values.add("A");
		values.add("B");
		assertEquals(values, item.getDefaultValue());
	}

	@Test
	public void test2Items() throws Exception {
		File tmpFile = File.createTempFile("testGet", ".json");
		tmpFile.deleteOnExit();

		String groupName = "some_group";
		String name1 = "some_name";
		String name2 = "some_name2";
		try (FileWriter writer = new FileWriter(tmpFile)) {
			writer.write("[{"
				+ "\"group\": \"" + groupName + "\","
				+ "\"name\": \"" + name1 + "\","
				+ "\"default\": [ \"A\", \"B\"]"
				+ "}, {"
				+ "\"group\": \"" + groupName + "\","
				+ "\"name\": \"" + name2 + "\","
				+ "\"default\": [ \"A\", \"B\"]"
				+ "}]");
		}

		SchemaFileSource source = new SchemaFileSource(tmpFile.getAbsolutePath());

		ConfigSchema schema = source.getSchema();

		assertEquals(2, schema.size());
		SchemaItem item1 = schema.get(0);

		assertEquals(2, schema.size());
		SchemaItem item2 = schema.get(1);

		assertEquals(name1, item1.getName());
		assertEquals(name2, item2.getName());

	}

	@Test(expected = GetException.class)
	public void testMissingName() throws Exception {
		File tmpFile = File.createTempFile("testGet", ".json");
		tmpFile.deleteOnExit();

		String groupName = "some_group";
		try (FileWriter writer = new FileWriter(tmpFile)) {
			writer.write("[{"
				+ "\"group\": \"" + groupName + "\","
				+ "\"default\": [ \"A\", \"B\"]"
				+ "}]");
		}

		SchemaFileSource source = new SchemaFileSource(tmpFile.getAbsolutePath());
		source.getSchema();

	}

	@Test(expected = GetException.class)
	public void testMissingGroup() throws Exception {
		File tmpFile = File.createTempFile("testGet", ".json");
		tmpFile.deleteOnExit();

		String name = "some_name";
		try (FileWriter writer = new FileWriter(tmpFile)) {
			writer.write("[{"
				+ "\"name\": \"" + name + "\","
				+ "\"default\": [ \"A\", \"B\"]"
				+ "}]");
		}

		SchemaFileSource source = new SchemaFileSource(tmpFile.getAbsolutePath());
		source.getSchema();
	}

	@Test(expected = GetException.class)
	public void testMissingValue() throws Exception {
		File tmpFile = File.createTempFile("testGet", ".json");
		tmpFile.deleteOnExit();

		String groupName = "some_group";
		String name = "some_name";
		try (FileWriter writer = new FileWriter(tmpFile)) {
			writer.write("[{"
				+ "\"group\": \"" + groupName + "\","
				+ "\"name\": \"" + name + "\""
				+ "}]");
		}

		SchemaFileSource source = new SchemaFileSource(tmpFile.getAbsolutePath());
		source.getSchema();
	}

	@Test
	public void testConstraints() throws Exception {
		File tmpFile = File.createTempFile("testGet", ".json");
		tmpFile.deleteOnExit();

		String groupName = "some_group";
		String name = "some_name";
		try (FileWriter writer = new FileWriter(tmpFile)) {
			writer.write("[{"
				+ "\"group\": \"abc\","
				+ "\"name\": \"option\","
				+ "\"description\": \"Some\","
				+ "\"default\": [1, 2],"
				+ "\"constraints\": [ { \"type\": \"int\"}, {\"type\": \"min\", \"min\": 1}, {\"type\": \"max\", \"max\": 5} ]"
				+ "}]");
		}

		SchemaFileSource source = new SchemaFileSource(tmpFile.getAbsolutePath());
		ConfigSchema schema = source.getSchema();
	}
}