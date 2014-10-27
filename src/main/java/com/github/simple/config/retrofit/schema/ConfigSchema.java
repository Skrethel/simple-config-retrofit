package com.github.simple.config.retrofit.schema;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;


public class ConfigSchema extends ArrayList<SchemaItem> {

	public boolean contains(String group, String name) {
		for (SchemaItem item : this) {
			if (StringUtils.equals(item.getGroup(), group) && StringUtils.equals(item.getName(), name)) {
				return true;
			}
		}
		return false;
	}

	public SchemaItem get(String group, String name) {
		for (SchemaItem item : this) {
			if (StringUtils.equals(item.getGroup(), group) && StringUtils.equals(item.getName(), name)) {
				return item;
			}
		}
		return null;
	}
}
