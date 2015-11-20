/*
 * Copyright 2015 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.json.type;

import java.awt.Color;
import java.util.Map;

import de.cgarbs.lib.exception.DataException;

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
	 * @throws DataException when no conversion exists for the given Object
	 */
	public static JSONType wrapJavaObject(Object o) throws DataException
	{
		if (o instanceof Color)
		{
			return new JSONColor((Color) o);
		}

		// not yet supported
		throw new DataException(
				DataException.ERROR.JSON_CONVERSION_ERROR,
				"unknown class " + o.getClass() + " in JSONTypeFactory"
				);
	}

	/**
	 * Gets an instance of the correct {@link JSONType} subclass for the given
	 * decoded JSON object {@link Map}
	 * @param map the Map to be converted to a {@link JSONType}
	 * @return the JSON object wrapped as a {@link JSONType}
	 * @throws DataException when no conversion exists for the given Object
	 */
	public static Object unwrapJSONObject(Object map) throws DataException
	{
		if (! (map instanceof Map))
		{
			throwJSONError("root element is no map");
		}
		Map<String, Object> jsonMap = (Map<String, Object>) map;

		Object identifier = jsonMap.get(JSONType.CLASS_FIELD);
		Object version    = jsonMap.get(JSONType.VERSION_FIELD);
		Object attributes = jsonMap.get(JSONType.ATTRIBUTE_FIELD);

		// check null
		if (identifier == null)
		{
			throwJSONError("class ["+JSONType.CLASS_FIELD+"] is missing");
		}
		if (version == null)
		{
			throwJSONError("version ["+JSONType.VERSION_FIELD+"] is missing");
		}
		if (attributes == null)
		{
			throwJSONError("attributes ["+JSONType.ATTRIBUTE_FIELD+"] are missing");
		}
		if (! (attributes instanceof Map))
		{
			throwJSONError("wrong attributes ["+JSONType.ATTRIBUTE_FIELD+"] for "+identifier+"#"+version+": expected a <Map> but got a <"+attributes.getClass().toString()+">");
		}

		Map<String, Object> attributesMap = (Map<String, Object>) attributes;

		// ignore version for now
		if (JSONColor.CLASS_NAME.equals(identifier))
		{
			return new JSONColor(attributesMap);
		}

		// not yet supported
		throw new DataException(
				DataException.ERROR.JSON_CONVERSION_ERROR,
				"unknown class <" + identifier + "> version <" + version + "> in JSONTypeFactory"
				);
	}

	/**
	 * Throws a DataException with {@link DataException.ERROR#JSON_CONVERSION_ERROR}
	 * and a given error text.
	 * @param errorText the error text
	 * @throws DataException the freshly constructed DataException
	 */
	private static void throwJSONError(String errorText) throws DataException
	{
		throw new DataException(
				DataException.ERROR.JSON_CONVERSION_ERROR,
				"error during JSON conversion: " + errorText
				);
	}
}
