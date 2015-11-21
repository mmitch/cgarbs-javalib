/*
 * Copyright 2015 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.json;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONAware;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;

import de.cgarbs.lib.exception.JSONException;
import de.cgarbs.lib.json.type.JSONType;
import de.cgarbs.lib.json.type.JSONTypeFactory;

/**
 * JSON adapter for various classes.
 * Allows conversion from and to JSON.
 *
 * @author Christian Garbs <mitch@cgarbs.de>
 * @since 0.3.0
 */
public abstract class JSONAdapter
{
	private static ContainerFactory orderedContainerFactory;
	private static JSONParser jsonParser;

	/**
	 * Prepares a given Object for JSON conversion.
	 * <br>
	 * Native data types like {@link String}, {@link Number} and {@link Boolean}
	 * as well as the <code>null</code> value are returned unchanged as they can
	 * be directly converted to JSON.
	 * <br>
	 * Other data types are converted to special wrapper objects
	 * (subclasses of {@link JSONType} implementing {@link JSONAware} to
	 * allow JSON conversion.
	 *
	 * @param o the Object to be converted
	 * @return either the unchanged Object or a wrapped version ready for JSON conversion
	 * @throws JSONException when no conversion exists for the given Object
	 */
	protected static Object prepareForJSON(Object o) throws JSONException
	{
		// no wrapping needed
		if (o instanceof String
				|| o instanceof Number
				|| o instanceof Boolean
				|| o == null
				)
		{
			return o;
		}

		return JSONTypeFactory.wrapJavaObject(o);
	}

	/**
	 * Prepares a given decoded JSON Object for usage in the Java world.
	 * <br>
	 * Native data types like {@link String}, {@link Number} and {@link Boolean}
	 * as well as the <code>null</code> value are already mapped correctly and can
	 * simply be returned for further usage as Java values.
	 * <br>
	 * {@link Maps} are considered to be special wrapper objects
	 * (subclasses of {@link JSONType} and are  decoded into a proper Java class.
	 *
	 * @param o the Object to be converted
	 * @return either the unchanged Object or a wrapped Java type
	 * @throws JSONException when no conversion exists for the given Object
	 */
	protected static Object prepareForJava(Object o) throws JSONException
	{
		// no wrapping needed
		if (o instanceof String
				|| o instanceof Number
				|| o instanceof Boolean
				|| o == null
				)
		{
			return o;
		}

		return JSONTypeFactory.unwrapJSONObject(o);
	}

	/**
	 * Returns a cached instance of a {@link JSONParser}.
	 *
	 * @return a JSONParser ready for use
	 */
	protected static JSONParser getJSONParser()
	{
		if (jsonParser == null)
		{
			jsonParser = new JSONParser();
		}
		return jsonParser;
	}

	/**
	 * Returns a JSON {@link ContainerFactors} that keeps the ordering
	 * from the JSON String for Maps and Lists by using {@link LinkedHashMap}
	 * and {@link LinkedList}.
	 *
	 * @return a ContainerFactory providing ordered results
	 */
	@SuppressWarnings("rawtypes")
	protected static ContainerFactory getOrderedContainerFactory()
	{
		if (orderedContainerFactory == null)
		{
			orderedContainerFactory = new ContainerFactory() {

				@Override
				public Map createObjectContainer()
				{
					return new LinkedHashMap();
				}

				@Override
				public List creatArrayContainer()
				{
					return new LinkedList();
				}
			};
		}
		return orderedContainerFactory;

	}
}
