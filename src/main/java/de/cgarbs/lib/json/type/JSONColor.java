/*
 * Copyright 2015 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.json.type;

import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONValue;

import de.cgarbs.lib.data.TypeConverter;

/**
 * JSON wrapper for the {@link Color} class.
 *
 * @author Christian Garbs <mitch@cgarbs.de>
 * @since 0.3.0
 */
public class JSONColor extends JSONType
{
	public static final String CLASS_NAME    = "JSONColor";
	public static final String CLASS_VERSION = "1";

	Color color;

	/**
	 * Creates a JSONColor object based on the given Color.
	 * @param color the color to wrap for JSON conversion
	 */
	public JSONColor(Color color)
	{
		this.color = color;
	}

	/**
	 * Creates a JSONColor object based on the given decoded JSON Map.
	 * @param attributesMap the decoded JSON Map
	 */
	public JSONColor(Map<String, Object> attributesMap)
	{
		this.color = new Color(
				TypeConverter.parseAsInt(attributesMap.get("R")),
				TypeConverter.parseAsInt(attributesMap.get("G")),
				TypeConverter.parseAsInt(attributesMap.get("B")),
				TypeConverter.parseAsInt(attributesMap.get("A"))
				);
	}

	@Override
	String getJSONAttributes()
	{
		Map<String, Integer> data = new LinkedHashMap<String, Integer>();
		data.put("R", color.getRed());
		data.put("G", color.getGreen());
		data.put("B", color.getBlue());
		data.put("A", color.getAlpha());

		return JSONValue.toJSONString(data);
	}

	@Override
	String getJSONClassName()
	{
		return CLASS_NAME;
	}

	@Override
	String getJSONClassVersion()
	{
		return CLASS_VERSION;
	}

	@Override
	public Object toJavaValue()
	{
		return color;
	}
}
