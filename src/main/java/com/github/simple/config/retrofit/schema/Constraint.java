package com.github.simple.config.retrofit.schema;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;

import java.util.HashMap;
import java.util.Map;


public class Constraint {
	@JsonProperty(value = "type")
	private String type;

	@JsonIgnore
	private Map<String, String> params = new HashMap<>();

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	@JsonAnyGetter
	private Map<String, JsonNode> getExtraProperty() {
		HashMap<String, JsonNode> toReturn = new HashMap<>();

		for (Map.Entry<String, String> entry : params.entrySet()) {
			toReturn.put(entry.getKey(), new TextNode(entry.getValue()));
		}
		return toReturn;
	}

	@JsonAnySetter
	private void setUnknownProperty(String key, JsonNode value) {
		params.put(key, value.textValue());
	}

	public void addParam(String key, String value) {
		params.put(key, value);
	}
}
