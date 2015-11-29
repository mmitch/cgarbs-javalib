/*
 * Copyright 2015 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.json.type;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import de.cgarbs.lib.FileUtils;

/**
 * JSON wrapper for the {@link File} class.
 *
 * @author Christian Garbs <mitch@cgarbs.de>
 * @since 0.3.0
 */
public class JSONFile extends JSONType
{
	public static final String CLASS_NAME    = "JSONFile";
	public static final String CLASS_VERSION = "1";

	static final String ATTRIBUTE_PATH_ELEMENTS = "pathElements";

	File file;

	/**
	 * Creates a JSONFile object based on the given File.
	 * @param file the File to wrap for JSON conversion
	 */
	public JSONFile(final File file)
	{
		this.file = file;
	}

	/**
	 * Creates a JSONColor object based on the given decoded JSON Map.
	 * @param attributesMap the decoded JSON Map
	 */
	public JSONFile(final Map<String, Object> attributesMap)
	{
		// FIXME throw JSON error on parsing problems
		final List<String> pathElements = (List<String>) attributesMap.get(ATTRIBUTE_PATH_ELEMENTS);

		this.file = FileUtils.createFileFrom(pathElements.toArray(new String[0]));
	}

	@Override
	Map<String, Object> getJSONAttributes()
	{
		final Map<String, Object> data = new LinkedHashMap<String, Object>();
		data.put(ATTRIBUTE_PATH_ELEMENTS, Arrays.asList( FileUtils.splitFile(file) ));
		return data;
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
		return file;
	}
}
