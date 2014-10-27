package com.github.simple.config.retrofit;

import com.github.simple.config.retrofit.constraint.DefaultSchemaConstraintValidator;
import com.github.simple.config.retrofit.exception.ValidationException;
import com.github.simple.config.retrofit.schema.ConfigSchema;
import com.github.simple.config.retrofit.schema.Constraint;
import org.ini4j.Ini;
import org.junit.Test;

import java.util.Arrays;

import static com.github.simple.config.retrofit.TestUtils.*;


public class DefaultSchemaConstraintValidatorTest {

	@Test
	public void testValidBool() throws Exception {
		ConfigSchema schema = new ConfigSchema();
		Constraint constraint = new Constraint();
		constraint.setType("bool");
		String group = "group";
		String name = "name";
		addSchemaItem(schema, group, name, true, "desc", Arrays.asList(constraint));

		Ini ini = new Ini();
		ini.put(group, name, "true");

		DefaultSchemaConstraintValidator validator = new DefaultSchemaConstraintValidator();
		validator.validate(schema, ini);
	}

	@Test(expected = ValidationException.class)
	public void testInvalidBool() throws Exception {
		ConfigSchema schema = new ConfigSchema();
		Constraint constraint = new Constraint();
		constraint.setType("bool");
		String group = "group";
		String name = "name";
		addSchemaItem(schema, group, name, true, "desc", Arrays.asList(constraint));

		Ini ini = new Ini();
		ini.put(group, name, "asdf");

		DefaultSchemaConstraintValidator validator = new DefaultSchemaConstraintValidator();
		validator.validate(schema, ini);
	}

	@Test
	public void testValidInt() throws Exception {
		ConfigSchema schema = new ConfigSchema();
		Constraint constraint = new Constraint();
		constraint.setType("int");
		String group = "group";
		String name = "name";
		addSchemaItem(schema, group, name, 3, "desc", Arrays.asList(constraint));

		Ini ini = new Ini();
		ini.put(group, name, "3");

		DefaultSchemaConstraintValidator validator = new DefaultSchemaConstraintValidator();
		validator.validate(schema, ini);
	}

	@Test(expected = ValidationException.class)
	public void testInvalidInt() throws Exception {
		ConfigSchema schema = new ConfigSchema();
		Constraint constraint = new Constraint();
		constraint.setType("int");
		String group = "group";
		String name = "name";
		addSchemaItem(schema, group, name, true, "desc", Arrays.asList(constraint));

		Ini ini = new Ini();
		ini.put(group, name, "asdf");

		DefaultSchemaConstraintValidator validator = new DefaultSchemaConstraintValidator();
		validator.validate(schema, ini);
	}

	@Test
	public void testValidMin() throws Exception {
		ConfigSchema schema = new ConfigSchema();
		Constraint constraint = new Constraint();
		constraint.setType("min");
		constraint.addParam("min", "3");
		String group = "group";
		String name = "name";
		addSchemaItem(schema, group, name, 3, "desc", Arrays.asList(constraint));

		Ini ini = new Ini();
		ini.put(group, name, "3");

		DefaultSchemaConstraintValidator validator = new DefaultSchemaConstraintValidator();
		validator.validate(schema, ini);
	}

	@Test(expected = ValidationException.class)
	public void testInvalidMin() throws Exception {
		ConfigSchema schema = new ConfigSchema();
		Constraint constraint = new Constraint();
		constraint.setType("min");
		constraint.addParam("min", "3");
		String group = "group";
		String name = "name";
		addSchemaItem(schema, group, name, true, "desc", Arrays.asList(constraint));

		Ini ini = new Ini();
		ini.put(group, name, "2");

		DefaultSchemaConstraintValidator validator = new DefaultSchemaConstraintValidator();
		validator.validate(schema, ini);
	}
	@Test
	public void testValidMax() throws Exception {
		ConfigSchema schema = new ConfigSchema();
		Constraint constraint = new Constraint();
		constraint.setType("max");
		constraint.addParam("max", "3");
		String group = "group";
		String name = "name";
		addSchemaItem(schema, group, name, 3, "desc", Arrays.asList(constraint));

		Ini ini = new Ini();
		ini.put(group, name, "3");

		DefaultSchemaConstraintValidator validator = new DefaultSchemaConstraintValidator();
		validator.validate(schema, ini);
	}

	@Test(expected = ValidationException.class)
	public void testInvalidMax() throws Exception {
		ConfigSchema schema = new ConfigSchema();
		Constraint constraint = new Constraint();
		constraint.setType("max");
		constraint.addParam("max", "3");
		String group = "group";
		String name = "name";
		addSchemaItem(schema, group, name, true, "desc", Arrays.asList(constraint));

		Ini ini = new Ini();
		ini.put(group, name, "5");

		DefaultSchemaConstraintValidator validator = new DefaultSchemaConstraintValidator();
		validator.validate(schema, ini);
	}

	@Test(expected = ValidationException.class)
	public void testValidateMultiValuedMax() throws Exception {
		ConfigSchema schema = new ConfigSchema();
		Constraint constraint = new Constraint();
		constraint.setType("max");
		constraint.addParam("max", "3");
		String group = "group";
		String name = "name";
		addSchemaItem(schema, group, name, true, "desc", Arrays.asList(constraint));

		Ini ini = new Ini();
		ini.add(group, name, "3");
		ini.add(group, name, "5");

		DefaultSchemaConstraintValidator validator = new DefaultSchemaConstraintValidator();
		validator.validate(schema, ini);
	}
}