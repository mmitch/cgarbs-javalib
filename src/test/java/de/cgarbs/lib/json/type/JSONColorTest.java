/*
 * Copyright 2015 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.json.type;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import de.cgarbs.lib.exception.JSONException;


public class JSONColorTest extends JSONTypeTestBase
{
	static Color GIVEN_COLOR = Color.CYAN;

	@Before
	public void setUp()
	{
		object_underTest = new JSONColor(GIVEN_COLOR);
	}

	@Test
	public void checkConstructor()
	{
		assertThat((Color) object_underTest.toJavaValue(), is(equalTo(GIVEN_COLOR)));
	}

	@Test
	public void checkConstants()
	{
		assertThat(object_underTest.getJSONClassName(),    is(equalTo(JSONColor.CLASS_NAME)));
		assertThat(object_underTest.getJSONClassVersion(), is(equalTo(JSONColor.CLASS_VERSION)));
	}

	@Test
	public void checkJSONConversion()
	{
		assertThat(
				object_underTest.getJSONAttributes(),
				is(equalTo(givenAttributesMap()))
				);

		assertThat(
				object_underTest.toJSONString(),
				is(equalTo(givenJSONString()))
				);
	}

	@Test
	public void checkJavaConversion() throws JSONException
	{
		Object converted = JSONTypeFactory.unwrapJSONObject(givenJSONMap());
		assertThat(converted, is(instanceOf(JSONColor.class)));

		object_underTest = (JSONColor) converted;
		assertThat((Color) object_underTest.toJavaValue(), is(equalTo(GIVEN_COLOR)));
	}

	String givenAttributesString()
	{
		return String.format("{\"R\":%d,\"G\":%d,\"B\":%d,\"A\":%d}",
				GIVEN_COLOR.getRed(),
				GIVEN_COLOR.getGreen(),
				GIVEN_COLOR.getBlue(),
				GIVEN_COLOR.getAlpha()
				);
	}

	Map<String, Object> givenAttributesMap()
	{
		Map<String, Object> attributeMap = new LinkedHashMap<String, Object>();
		attributeMap.put(JSONColor.ATTRIBUTE_RED,   GIVEN_COLOR.getRed());
		attributeMap.put(JSONColor.ATTRIBUTE_GREEN, GIVEN_COLOR.getGreen());
		attributeMap.put(JSONColor.ATTRIBUTE_BLUE,  GIVEN_COLOR.getBlue());
		attributeMap.put(JSONColor.ATTRIBUTE_ALPHA, GIVEN_COLOR.getAlpha());
		return attributeMap;
	}
}
