/*
 * Copyright 2015 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.json.type;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONAware;
import org.json.simple.JSONValue;

/**
 * Base class for JSON type conversion.
 * This mainly adds some metadata for later
 * retrieval of data (from JSON to Java Objects).
 *
 * @author Christian Garbs <mitch@cgarbs.de>
 * @since 0.3.0
 */
public abstract class JSONType implements JSONAware
{
	static final String CLASS_FIELD = "class";
	static final String VERSION_FIELD = "version";
	static final String ATTRIBUTE_FIELD = "attributes";

	@Override
	public String toJSONString()
	{
		final Map<String, String> json = new LinkedHashMap<String, String>();
		json.put(CLASS_FIELD, getJSONClassName());
		json.put(VERSION_FIELD, getJSONClassVersion());
		json.put(ATTRIBUTE_FIELD, getJSONAttributes());
		return JSONValue.toJSONString(json);
	}

	/**
	 * returns the value as the specific Java class
	 * @return the value as a specific Java class
	 */
	public abstract Object toJavaValue();

	/**
	 * Returns a JSON string containing the wrapped Object.
	 *
	 * @return JSON representation of the Object data
	 */
	abstract String getJSONAttributes();

	/**
	 * Returns the class name of the actual wrapper class.
	 * Must be unique to that the class can be identified
	 * when transforming from JSON back to the original Object.
	 *
	 * @return class name of the actual wrapper class
	 */
	abstract String getJSONClassName();

	/**
	 * Returns the version of the actual wrapper class.
	 * Might come in handy when there are incompatible
	 * changes in the wrapper classes.
	 *
	 * @return version of the actual wrapper class
	 */
	abstract String getJSONClassVersion();
}
