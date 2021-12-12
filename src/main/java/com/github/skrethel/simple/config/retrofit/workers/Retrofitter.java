package com.github.skrethel.simple.config.retrofit.workers;

import com.github.skrethel.simple.config.retrofit.constraint.SchemaConstraintValidator;
import com.github.skrethel.simple.config.retrofit.exception.GetException;
import com.github.skrethel.simple.config.retrofit.exception.InvalidDescriptionException;
import com.github.skrethel.simple.config.retrofit.exception.RetrofitException;
import com.github.skrethel.simple.config.retrofit.exception.ValidationException;
import com.github.skrethel.simple.config.retrofit.exception.WriteException;
import com.github.skrethel.simple.config.retrofit.io.ConfigSource;
import com.github.skrethel.simple.config.retrofit.io.ConfigWriter;
import com.github.skrethel.simple.config.retrofit.io.SchemaSource;
import com.github.skrethel.simple.config.retrofit.schema.ConfigSchema;
import com.github.skrethel.simple.config.retrofit.schema.SchemaItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ini4j.Ini;
import org.ini4j.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Retrofitter {
	private static final Logger LOGGER = LoggerFactory.getLogger(Retrofitter.class);

	public void retrofit(ConfigSource configSource, SchemaSource schemaSource,
		ConfigWriter configWriter, SchemaConstraintValidator validator) throws RetrofitException {
		try {
			Ini config = configSource.getConfig();
			ConfigSchema schema = schemaSource.getSchema();

			for (SchemaItem item : schema) {
				String val = config.get(item.getGroup(), item.getName());
				if (val == null) {
					LOGGER.debug("Adding missing option group: " + item.getGroup() + " name: " + item.getName());
					Object defaultValue = item.getDefaultValue();
					if (defaultValue instanceof List) {
						List defaultValues = (List) defaultValue;
						for (Object defValue : defaultValues) {
							config.add(item.getGroup(), item.getName(), defValue);
						}
					} else {
						config.put(item.getGroup(), item.getName(), item.getDefaultValue());
					}
					if (item.getDescription() != null) {
						config.get(item.getGroup()).putComment(item.getName(), item.getDescription());
					}
				}
			}

			List<GroupNamePair> toDelete = new ArrayList<>();
			for (Map.Entry<String, Profile.Section> entry : config.entrySet()) {
				String group = entry.getKey();
				for (String name : entry.getValue().keySet()) {
					if (!schema.contains(group, name)) {
						toDelete.add(new GroupNamePair(group, name));
					}
				}
			}
			for (GroupNamePair entry : toDelete) {
				String section = entry.group;
				String name = entry.name;
				LOGGER.debug("Removing option group: " + section + " name: " + name + " because it no longer exists in schema");
				config.remove(section, name);
			}

			validator.validate(schema, config);

			configWriter.write(config);

		} catch (ValidationException | GetException | WriteException | InvalidDescriptionException e) {
			throw new RetrofitException("Cannot execute retrofit: " + e.getMessage(), e);
		}
	}

	private class GroupNamePair {

		private final String group;
		private final String name;

		public GroupNamePair(String group, String name) {
			this.group = group;
			this.name = name;
		}
	}
}
