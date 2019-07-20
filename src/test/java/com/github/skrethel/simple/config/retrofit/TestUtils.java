package com.github.skrethel.simple.config.retrofit;

import com.github.skrethel.simple.config.retrofit.exception.WriteException;
import com.github.skrethel.simple.config.retrofit.io.ConfigFileWriter;
import com.github.skrethel.simple.config.retrofit.schema.ConfigSchema;
import com.github.skrethel.simple.config.retrofit.schema.Constraint;
import com.github.skrethel.simple.config.retrofit.schema.SchemaItem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.ini4j.Ini;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class TestUtils {

	static String getConfigAsString(Ini ini) throws IOException, WriteException {
		File tmpFile = File.createTempFile("config", "ini");
		tmpFile.deleteOnExit();
		ConfigFileWriter writer = new ConfigFileWriter(tmpFile.getAbsolutePath());
		writer.write(ini);

		return StringUtils.replace(FileUtils.readFileToString(tmpFile), "\r\n", "\n");
	}

	static void addIniItem(Ini ini, String group, String name, String value, String comment) {
		ini.put(group, name, value);
		if (comment != null) {
			ini.get(group).putComment(name, comment);
		}
	}

	static void addSchemaItem(ConfigSchema schema, String groupName, String name,
		Object value, String description) {
		addSchemaItem(schema, groupName, name, value, description, null);
	}

	@SuppressWarnings("unchecked")
	static void addSchemaItem(ConfigSchema schema, String groupName, String name,
		Object value, String description, List<Constraint> constraints) {
		SchemaItem item = new SchemaItem();
		item.setGroup(groupName);
		item.setName(name);
		item.setDefaultValue(value);
		item.setDescription(description);
		item.setConstraints(constraints);
		schema.add(item);
	}
}
