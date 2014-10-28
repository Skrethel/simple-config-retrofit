package com.github.skrethel.simple.config.retrofit.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class SchemaItem {

	@JsonProperty(value = "group", required = true)
	@NotNull
	private String group;

	@JsonProperty(value = "name", required = true)
	@NotNull
	private String name;

	@JsonProperty(value = "default", required = true)
	@NotNull
	private Object defaultValue;

	@JsonProperty(value = "description", required = true)
	private String description;

	@JsonProperty(value = "constraints", required = true)
	private List<Constraint> constraints;

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Constraint> getConstraints() {
		return constraints;
	}

	public void setConstraints(List<Constraint> constraints) {
		this.constraints = constraints;
	}

	public Object getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}
}
