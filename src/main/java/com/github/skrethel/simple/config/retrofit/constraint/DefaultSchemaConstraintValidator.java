package com.github.skrethel.simple.config.retrofit.constraint;

import com.github.skrethel.simple.config.retrofit.exception.ValidationException;
import com.github.skrethel.simple.config.retrofit.schema.ConfigSchema;
import com.github.skrethel.simple.config.retrofit.schema.Constraint;
import com.github.skrethel.simple.config.retrofit.schema.SchemaItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ini4j.Ini;
import org.ini4j.Profile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;


public class DefaultSchemaConstraintValidator implements SchemaConstraintValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultSchemaConstraintValidator.class);

	@Override public void validate(ConfigSchema schema, Ini config) throws ValidationException {
		Map<String, ConstraintValidator> validators = collectValidators();
		for (Map.Entry<String, Profile.Section> entry : config.entrySet()) {
			String groupName = entry.getKey();
			Profile.Section section = entry.getValue();
			for (String name : section.keySet()) {
				SchemaItem item = schema.get(groupName, name);
				if (item == null) {
					// should not happen
					LOGGER.warn("Found configuration item [" + groupName + "] " + name + " without corresponding schema item");
					continue;
				}
				validateItemValues(validators, item, section.getAll(name));
			}
		}
	}

	private List<ConstraintValidator> validateItemValues(Map<String, ConstraintValidator> allValidators,
		SchemaItem item, List<String> values) throws ValidationException {
		if (item.getConstraints() == null) {
			return Collections.emptyList();
		}

		ArrayList<ConstraintValidator> list = new ArrayList<>();
		for (Constraint constraint : item.getConstraints()) {
			ConstraintValidator itemValidator = allValidators.get(constraint.getType());
			if (itemValidator == null) {
				throw new ValidationException("Unable to find validator with type: " + constraint.getType());
			}
			for (String value : values) {
				try {
					itemValidator.validate(constraint.getParams(), value);
				} catch (ValidationException e) {
					throw new ValidationException("Validation failed for item with group " + item.getGroup() +
						" and name " + item.getName() + ", validator: " + constraint.getType() + ", error: " + e.getMessage(), e);
				}
			}
		}
		return list;
	}

	private Map<String, ConstraintValidator> collectValidators() {
		ServiceLoader<ConstraintValidator> loader = ServiceLoader.load(ConstraintValidator.class);
		Map<String, ConstraintValidator> validators = new HashMap<>();

		for (ConstraintValidator validator : loader) {
			LOGGER.debug("Found validator with type " + validator.getType());
			validators.put(validator.getType(), validator);
		}
		return validators;
	}
}
