package com.github.skrethel.simple.config.retrofit;

import com.github.skrethel.simple.config.retrofit.constraint.DefaultSchemaConstraintValidator;
import com.github.skrethel.simple.config.retrofit.exception.GetException;
import com.github.skrethel.simple.config.retrofit.exception.RetrofitException;
import com.github.skrethel.simple.config.retrofit.exception.ValidationException;
import com.github.skrethel.simple.config.retrofit.io.ConfigSource;
import com.github.skrethel.simple.config.retrofit.io.ConfigWriter;
import com.github.skrethel.simple.config.retrofit.io.SchemaSource;
import com.github.skrethel.simple.config.retrofit.schema.ConfigSchema;
import com.github.skrethel.simple.config.retrofit.schema.Constraint;
import com.github.skrethel.simple.config.retrofit.workers.Retrofitter;
import org.ini4j.Ini;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class RetrofitterTest implements ConfigSource, SchemaSource, ConfigWriter {

	private Ini outputConfig;
	private ConfigSchema schema;
	private Ini inputConfig;

	@Test
	public void testAddSingleValuedOption() throws Exception {
		inputConfig = new Ini();
		schema = new ConfigSchema();
		String groupName = "group_a";
		String name = "option_b";
		Object singleValue = "A";
		TestUtils.addSchemaItem(schema, groupName, name, singleValue, null);

		Retrofitter engine = new Retrofitter();
		engine.retrofit(this, this, this, new DefaultSchemaConstraintValidator());

		assertEquals(1, outputConfig.entrySet().size());
		assertNotNull(outputConfig.get(groupName, name));
		assertEquals(singleValue, outputConfig.get(groupName, name));
		assertEquals(1, outputConfig.get(groupName).getAll(name).size());
	}

	@Test
	public void testAddMultiValuedOption() throws Exception {
		inputConfig = new Ini();
		schema = new ConfigSchema();
		String groupName = "group_a";
		String name = "option_b";
		Object valueList = Arrays.asList("A", "B");
		TestUtils.addSchemaItem(schema, groupName, name, valueList, null);

		Retrofitter engine = new Retrofitter();
		engine.retrofit(this, this, this, new DefaultSchemaConstraintValidator());

		assertEquals(1, outputConfig.entrySet().size());
		assertNotNull(outputConfig.get(groupName));
		assertEquals(valueList, outputConfig.get(groupName).getAll(name));
		assertEquals(2, outputConfig.get(groupName).getAll(name).size());
	}

	@Test
	public void testRemoveOption() throws Exception {
		schema = new ConfigSchema();
		String groupName = "group_a";
		String name = "option_b";
		String singleValue = "A";
		TestUtils.addSchemaItem(schema, groupName, name, singleValue, null);
		inputConfig = new Ini();
		String missingGroup = "missing_group";
		String missingName = "missing_name";
		TestUtils.addIniItem(inputConfig, missingGroup, missingName, "unimportant", null);
		TestUtils.addIniItem(inputConfig, groupName, name, singleValue, null);

		Retrofitter engine = new Retrofitter();
		engine.retrofit(this, this, this, new DefaultSchemaConstraintValidator());

		assertNull(outputConfig.get(missingGroup, missingName));
		assertNotNull(outputConfig.get(groupName, name));
		assertEquals(singleValue, outputConfig.get(groupName, name));
		assertEquals(1, outputConfig.get(groupName).getAll(name).size());
	}

	@Test
	public void testComments() throws Exception {
		schema = new ConfigSchema();
		String groupName1 = "group_a";
		String name1 = "option_a";
		String singleValue1 = "A";
		String description1 = " option_a comment";
		TestUtils.addSchemaItem(schema, groupName1, name1, singleValue1, description1);

		inputConfig = new Ini();
		String groupName2 = "group_a";
		String name2 = "option_b";
		String value2 = "B";
		String description2 = " option_b comment";
		TestUtils.addIniItem(inputConfig, groupName2, name2, value2, description2);
		TestUtils.addSchemaItem(schema, groupName2, name2, value2, description2);

		Retrofitter engine = new Retrofitter();
		engine.retrofit(this, this, this, new DefaultSchemaConstraintValidator());

		assertNotNull(outputConfig.get(groupName1, name1));
		assertEquals(singleValue1, outputConfig.get(groupName1, name1));
		assertNotNull(outputConfig.get(groupName1));
		assertEquals(1, outputConfig.get(groupName1).getAll(name1).size());
		assertEquals(description1, outputConfig.get(groupName1).getComment(name1));

		String content = TestUtils.getConfigAsString(outputConfig);
		String expectedContent = "[group_a]\n"
			+ "# option_b comment\n"
			+ "option_b = B\n"
			+ "# option_a comment\n"
			+ "option_a = A\n\n";

		assertEquals(expectedContent, content);
	}

	@Test
	public void testFailedValidation() throws Exception {
		schema = new ConfigSchema();
		String groupName1 = "group_a";
		String name1 = "option_a";
		String singleValue1 = "A";
		String description1 = " option_a comment";
		TestUtils.addSchemaItem(schema, groupName1, name1, singleValue1, description1);

		inputConfig = new Ini();
		String groupName2 = "group_a";
		String name2 = "option_b";
		String value2 = "23";
		String description2 = " option_b comment";
		Constraint constraint = new Constraint();
		constraint.setType("max");
		constraint.addParam("max", "3");
		TestUtils.addIniItem(inputConfig, groupName2, name2, value2, description2);
		TestUtils.addSchemaItem(schema, groupName2, name2, value2, description2, Arrays.asList(constraint));

		Retrofitter engine = new Retrofitter();
		try {
			engine.retrofit(this, this, this, new DefaultSchemaConstraintValidator());
		} catch (RetrofitException e) {
			assertEquals(ValidationException.class, e.getCause().getClass());
		}
	}

	@Override public Ini getConfig() throws GetException {
		return this.inputConfig;
	}

	@Override public ConfigSchema getSchema() throws GetException {
		return this.schema;
	}

	@Override public void write(Ini config) {
		this.outputConfig = config;

	}
}