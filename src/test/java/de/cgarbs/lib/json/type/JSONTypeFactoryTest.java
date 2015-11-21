/*
 * Copyright 2015 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.json.type;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.core.StringContains.containsString;
import static org.hamcrest.core.StringStartsWith.startsWith;

import static org.hamcrest.core.IsInstanceOf.instanceOf;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.awt.Color;
import java.text.NumberFormat;
import java.text.ParseException;

import org.json.simple.JSONValue;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.cgarbs.lib.exception.JSONException;
import de.cgarbs.lib.exception.JSONException.ERROR;

public class JSONTypeFactoryTest
{
	@Test
	public void checkWrapJavaObject() throws JSONException
	{
		assertThat(
				JSONTypeFactory.wrapJavaObject(new Color(0)),
				is(instanceOf(JSONColor.class))
				);
	}

	@Test
	public void checkWrapJavaObject_Exception() throws JSONException
	{
		try
		{
			JSONTypeFactory.wrapJavaObject(new String());
			fail("no exception thrown");
		}
		catch (JSONException e)
		{
			checkJSONException(e, ERROR.JAVA_TO_JSON, "unknown class", "java.lang.String");
		}
	}

	@Test
	public void checkUnwrapJSONObject() throws JSONException
	{
		assertThat(
				JSONTypeFactory.unwrapJSONObject(JSONValue.parse(
						"{\"class\":\"JSONColor\",\"version\":\"1\",\"attributes\":{}}"
						)),
				is(instanceOf(JSONColor.class))
				);
	}

	@Test
	public void checkUnwrapJSONObject_Exception()
	{
		try
		{
			JSONTypeFactory.unwrapJSONObject("foo");
			fail("no exception thrown");
		}
		catch (JSONException e)
		{
			checkJSONException(e, ERROR.JSON_TO_JAVA, "root element");
		}

		try
		{
			JSONTypeFactory.unwrapJSONObject(JSONValue.parse(
					"{\"version\":\"1\",\"attributes\":{}}"
				));
			fail("no exception thrown");
		}
		catch (JSONException e)
		{
			checkJSONException(e, ERROR.JSON_TO_JAVA, "class [");
		}

		try
		{
			JSONTypeFactory.unwrapJSONObject(JSONValue.parse(
					"{\"class\":\"JSONColor\",\"attributes\":{}}"
				));
			fail("no exception thrown");
		}
		catch (JSONException e)
		{
			checkJSONException(e, ERROR.JSON_TO_JAVA, "version [");
		}

		try
		{
			JSONTypeFactory.unwrapJSONObject(JSONValue.parse(
					"{\"class\":\"JSONColor\",\"version\":\"1\"}"
				));
			fail("no exception thrown");
		}
		catch (JSONException e)
		{
			checkJSONException(e, ERROR.JSON_TO_JAVA, "attributes [");
		}

		try
		{
			JSONTypeFactory.unwrapJSONObject(JSONValue.parse(
					"{\"class\":\"JSONColor\",\"version\":\"1\",\"attributes\":[]}"
				));
			fail("no exception thrown");
		}
		catch (JSONException e)
		{
			checkJSONException(e, ERROR.JSON_TO_JAVA, "wrong attributes [", "JSONArray");
		}

		try
		{
			JSONTypeFactory.unwrapJSONObject(JSONValue.parse(
					"{\"class\":\"ENOEXIST\",\"version\":\"12345\",\"attributes\":{}}"
				));
			fail("no exception thrown");
		}
		catch (JSONException e)
		{
			checkJSONException(e, ERROR.JSON_TO_JAVA, "unknown class <", "ENOEXIST", "12345");
		}
	}

	private static void checkJSONException(JSONException e, ERROR error, String beginsWith, String... contains)
	{
		assertThat(e.getError(), is(equalTo(error)));
		assertThat(e.getMessageOnly(), startsWith(beginsWith));
		for (String contained: contains)
		{
			assertThat(e.getMessageOnly(), containsString(contained));
		}
	}
}
