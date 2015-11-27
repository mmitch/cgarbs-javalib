/*
 * Copyright 2015 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.json;

import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import de.cgarbs.lib.data.DataModel;
import de.cgarbs.lib.data.TestDataModel;
import de.cgarbs.lib.exception.DataException;
import de.cgarbs.lib.exception.JSONException;

public class JSONDataModelTest
{
	DataModel dataModel;

	@Before
	public void setUp() throws DataException
	{
		dataModel = new TestDataModel();
	}

	@Test
	public void checkReadFromJSON_Exceptions() throws DataException
	{
		String noMap = "";

		checkWithException(noMap, "root element");

		Map<String, Object> jsonMap = new LinkedHashMap<String, Object>();
		checkWithException(jsonMap, "identifier [");

		jsonMap.put(JSONDataModel.IDENTIFIER_FIELD, "x");
		checkWithException(jsonMap, "version [");

		jsonMap.put(JSONDataModel.VERSION_FIELD, "x");
		checkWithException(jsonMap, "attributes [");

		jsonMap.put(JSONDataModel.ATTRIBUTE_FIELD, "x");
		checkWithException(jsonMap, "wrong identifier [");

		jsonMap.put(JSONDataModel.IDENTIFIER_FIELD, JSONDataModel.IDENTIFIER);
		checkWithException(jsonMap, "wrong version [");

		jsonMap.put(JSONDataModel.VERSION_FIELD, JSONDataModel.VERSION);
		checkWithException(jsonMap, "wrong attributes [");
	}

	@Test
	public void check() throws JSONException, DataException
	{
		Map<String, Object> attributesMap = new LinkedHashMap<String, Object>();
		attributesMap.put("*UNKNOWN*ATTRIBUTE*", "foo");

		Map<String, Object> jsonMap = new LinkedHashMap<String, Object>();
		jsonMap.put(JSONDataModel.IDENTIFIER_FIELD, JSONDataModel.IDENTIFIER);
		jsonMap.put(JSONDataModel.VERSION_FIELD,    JSONDataModel.VERSION);
		jsonMap.put(JSONDataModel.ATTRIBUTE_FIELD,  attributesMap);

		JSONDataModel.readFromJSON(dataModel, jsonMap);
	}

	private void checkWithException(Object json, String exceptionStart) throws DataException
	{
		try
		{
			JSONDataModel.readFromJSON(dataModel, json);
			fail("no exception thrown");
		}
		catch (JSONException e)
		{
			assertThat(e.getMessageOnly(), startsWith(exceptionStart));
		}
	}
}
