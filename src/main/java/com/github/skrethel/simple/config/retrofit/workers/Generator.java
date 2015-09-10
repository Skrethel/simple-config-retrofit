package com.github.skrethel.simple.config.retrofit.workers;

import com.github.skrethel.simple.config.retrofit.exception.GeneratorException;
import com.github.skrethel.simple.config.retrofit.exception.GetException;
import com.github.skrethel.simple.config.retrofit.exception.InvalidDescriptionException;
import com.github.skrethel.simple.config.retrofit.exception.WriteException;
import com.github.skrethel.simple.config.retrofit.io.ConfigSource;
import com.github.skrethel.simple.config.retrofit.io.ConfigWriter;
import com.github.skrethel.simple.config.retrofit.io.SchemaSource;
import com.github.skrethel.simple.config.retrofit.io.SchemaWriter;
import com.github.skrethel.simple.config.retrofit.schema.ConfigSchema;
import com.github.skrethel.simple.config.retrofit.schema.SchemaItem;
import org.apache.log4j.Logger;
import org.ini4j.Ini;
import org.ini4j.Profile;

import java.util.List;
import java.util.Map;


public class Generator {

	private static final Logger LOGGER = Logger.getLogger(Generator.class);

	public void generate(SchemaSource source, ConfigWriter output) throws GeneratorException {
		try {
			ConfigSchema schema = source.getSchema();
			if (schema.isEmpty()) {
				throw new GeneratorException("Cannot generate config file from empty schema file");
			}
			Ini newIni = new Ini();
			for (SchemaItem item : schema) {
				if (item.getDefaultValue() instanceof List) {
					for (Object val : (List) item.getDefaultValue()) {
						newIni.add(item.getGroup(), item.getName(), val);
					}
				} else {
					newIni.put(item.getGroup(), item.getName(), item.getDefaultValue());
				}
				if (item.getDescription() != null) {
					newIni.get(item.getGroup()).putComment(item.getName(), item.getDescription());
				}
			}
			output.write(newIni);
		} catch (GetException | InvalidDescriptionException e) {
			throw new GeneratorException("Cannot generate config file from schema: " + e.getMessage(), e);
		} catch (WriteException e) {
			throw new GeneratorException("Cannot write config file generated from schema: " + e.getMessage(), e);
		}
	}

	public void generate(ConfigSource source, SchemaWriter output) throws GeneratorException {
		try {
			Ini config = source.getConfig();
			if (config.isEmpty()) {
				throw new GeneratorException("Cannot generate schema file from empty config");
			}
			ConfigSchema newSchema = new ConfigSchema();
			for (Map.Entry<String, Profile.Section> entry : config.entrySet()) {
				String groupName = entry.getKey();
				Profile.Section section = entry.getValue();
				for (String name : section.keySet()) {
					SchemaItem item = new SchemaItem();
					item.setGroup(groupName);
					item.setName(name);
					item.setDescription(section.getComment(name));
					List<String> values = section.getAll(name);
					if (values.size() == 1) {
						item.setDefaultValue(values.get(0));
					} else {
						item.setDefaultValue(values);
					}
					newSchema.add(item);
				}
			}
			output.write(newSchema);
		} catch (GetException e) {
			throw new GeneratorException("Cannot generate schema from config file: " + e.getMessage(), e);
		} catch (WriteException e) {
			throw new GeneratorException("Cannot write schema generate from config file: " + e.getMessage(), e);
		}
	}
}
