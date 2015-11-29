/*
 * Copyright 2015 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.json.type;

import java.awt.Color;
import java.util.Map;

import de.cgarbs.lib.exception.JSONException;

/**
 * A factory that creates {@link JSONType}s based on either
 * a Java object or a decoded JSON representation ({@link Map}).
 *
 * @author Christian Garbs <mitch@cgarbs.de>
 * @since 0.3.0
 */
public abstract class JSONTypeFactory
{

	/**
	 * Gets an instance of the correct {@link JSONType} subclass for the given
	 * Java object.
	 * @param o the Object to be converted to JSON
	 * @return the Object wrapped in a {@link JSONType}
	 * @throws JSONException when no conversion exists for the given Object
	 */
	public static JSONType wrapJavaObject(final Object o) throws JSONException
	{
		if (o instanceof Color)
		{
			return new JSONColor((Color) o);
		}

		// not yet supported
		throw new JSONException(
				JSONException.ERROR.JAVA_TO_JSON,
				"unknown class " + o.getClass() + " in JSONTypeFactory"
				);
	}

	/**
	 * Gets an instance of the correct {@link JSONType} subclass for the given
	 * decoded JSON object {@link Map}
	 * @param map the Map to be converted to a {@link JSONType}
	 * @return the JSON object wrapped as a {@link JSONType}
	 * @throws JSONException when no conversion exists for the given Object
	 */
	public static JSONType unwrapJSONObject(final Object map) throws JSONException
	{
		if (! (map instanceof Map))
		{
			throw newJSONToJavaError("root element is no map");
		}
		final Map<String, Object> jsonMap = (Map<String, Object>) map;

		final Object identifier = jsonMap.get(JSONType.CLASS_FIELD);
		final Object version    = jsonMap.get(JSONType.VERSION_FIELD);
		final Object attributes = jsonMap.get(JSONType.ATTRIBUTE_FIELD);

		// check null
		if (identifier == null)
		{
			throw newJSONToJavaError("class ["+JSONType.CLASS_FIELD+"] is missing");
		}
		if (version == null)
		{
			throw newJSONToJavaError("version ["+JSONType.VERSION_FIELD+"] is missing");
		}
		if (attributes == null)
		{
			throw newJSONToJavaError("attributes ["+JSONType.ATTRIBUTE_FIELD+"] are missing");
		}
		if (! (attributes instanceof Map))
		{
			throw newJSONToJavaError("wrong attributes ["+JSONType.ATTRIBUTE_FIELD+"] for "+identifier+"#"+version+": expected a <Map> but got a <"+attributes.getClass().toString()+">");
		}

		final Map<String, Object> attributesMap = (Map<String, Object>) attributes;

		// ignore version for now
		if (JSONColor.CLASS_NAME.equals(identifier))
		{
			return new JSONColor(attributesMap);
		}

		// not yet supported
		throw newJSONToJavaError("unknown class <" + identifier + "> version <" + version + "> in JSONTypeFactory");
	}

	/**
	 * Generate a new JSONException with {@link JSONException.ERROR#JSON_TO_JAVA}
	 * and a given error text.
	 * @param errorText the error text
	 * @return the freshly constructed JSONException
	 */
	private static JSONException newJSONToJavaError(final String errorText)
	{
		return new JSONException(
				JSONException.ERROR.JSON_TO_JAVA,
				errorText
				);
	}
}
