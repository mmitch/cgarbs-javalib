/*
 * Copyright 2015 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.json.type;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class JSONTypeTestBase
{
	JSONType object_underTest;

	Map<String, Object> givenJSONMap()
	{
		Map<String, Object> jsonMap = new LinkedHashMap<String, Object>();
		jsonMap.put(JSONType.CLASS_FIELD,     object_underTest.getJSONClassName());
		jsonMap.put(JSONType.VERSION_FIELD,   object_underTest.getJSONClassVersion());
		jsonMap.put(JSONType.ATTRIBUTE_FIELD, givenAttributesMap());
		return jsonMap;
	}

	String givenJSONString()
	{
		return String.format(
				"{\"class\":\"%s\",\"version\":\"%s\",\"attributes\":%s}",
				JSONColor.CLASS_NAME,
				JSONColor.CLASS_VERSION,
				givenAttributesString()
				);
	}

	abstract String givenAttributesString();

	abstract Map<String, Object> givenAttributesMap();
}
