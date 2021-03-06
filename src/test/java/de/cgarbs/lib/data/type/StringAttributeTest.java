/*
 * Copyright 2015, 2020 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.data.type;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import de.cgarbs.lib.data.DataModel;
import de.cgarbs.lib.exception.DataException;
import de.cgarbs.lib.exception.ValidationError;
import de.cgarbs.lib.i18n.Resource;

public class StringAttributeTest
{
	DataModel model;
	StringAttribute attribute_1_underTest;
	StringAttribute attribute_2_underTest;

	static final String GIVEN_STRING = "foo";
	static final Integer GIVEN_INTEGER = 42;
	static final Float GIVEN_FLOAT = 13.7f;

	static final Integer GIVEN_MIN_LENGTH = 1;
	static final Integer GIVEN_MAX_LENGTH = 5;
	static final String GIVEN_STRING_TOO_SHORT = "";
	static final String GIVEN_STRING_TOO_LONG  = "xxxxxxx";

	@Before
	public void setUp() throws Exception
	{
		model = new StringAttributeTestDataModel(new Resource(BaseTestDataModel.class));
		attribute_1_underTest = (StringAttribute) model.getAttribute(StringAttributeTestDataModel.TEST_ATTRIBUTE_1);
		attribute_2_underTest = (StringAttribute) model.getAttribute(StringAttributeTestDataModel.TEST_ATTRIBUTE_2);
	}

	@Test
	public void checkSetNull() throws Exception
	{
		attribute_1_underTest.setValue(null);
		assertThat(attribute_1_underTest.getValue(), is(nullValue()));
	}

	@Test
	public void checkSetValues() throws Exception
	{
		attribute_1_underTest.setValue(GIVEN_STRING);
		assertThat(attribute_1_underTest.getValue(), is(GIVEN_STRING));

		attribute_1_underTest.setValue(GIVEN_INTEGER);
		assertThat(attribute_1_underTest.getValue(), is(GIVEN_INTEGER.toString()));

		attribute_1_underTest.setValue(GIVEN_FLOAT);
		assertThat(attribute_1_underTest.getValue(), is(GIVEN_FLOAT.toString()));
	}

	@Test
	public void checkSetInvalid() throws Exception
	{
		// these should throw no error unless validate() is called!
		attribute_1_underTest.setValue(GIVEN_STRING_TOO_LONG);
		attribute_1_underTest.setValue(GIVEN_STRING_TOO_SHORT);
		attribute_2_underTest.setValue(null);
	}

	@Test
	public void checkValidation() throws Exception
	{
		attribute_1_underTest.validate(null);
		try
		{
			attribute_2_underTest.validate(null);
			fail("null value - no exception thrown");
		}
		catch (ValidationError e)
		{
			assertThat(e.getError(), is(ValidationError.ERROR.NULL_NOT_ALLOWED));
		}

		try
		{
			attribute_1_underTest.validate(GIVEN_STRING_TOO_LONG);
			fail("no exception thrown");
		}
		catch (ValidationError e)
		{
			assertThat(e.getError(), is(ValidationError.ERROR.STRING_TOO_LONG));
		}
		attribute_2_underTest.validate(GIVEN_STRING_TOO_LONG);

		try
		{
			attribute_1_underTest.validate(GIVEN_STRING_TOO_SHORT);
			fail("no exception thrown");
		}
		catch (ValidationError e)
		{
			assertThat(e.getError(), is(ValidationError.ERROR.STRING_TOO_SHORT));
		}
		attribute_2_underTest.validate(GIVEN_STRING_TOO_SHORT);
	}

	@Test
	public void checkSetup()
	{
		assertThat(attribute_1_underTest.getMinLength(), is(GIVEN_MIN_LENGTH));
		assertThat(attribute_1_underTest.getMaxLength(), is(GIVEN_MAX_LENGTH));
		assertThat(attribute_1_underTest.isNullable(), is(true));

		assertThat(attribute_2_underTest.getMinLength(), is(nullValue()));
		assertThat(attribute_2_underTest.getMaxLength(), is(nullValue()));
		assertThat(attribute_2_underTest.isNullable(), is(false));
	}

	class StringAttributeTestDataModel extends BaseTestDataModel
	{
		public StringAttributeTestDataModel(Resource resource) throws DataException
		{
			super(resource);

			addAttribute(
					TEST_ATTRIBUTE_1,
					StringAttribute.builder()
						.setMinLength(GIVEN_MIN_LENGTH)
						.setMaxLength(GIVEN_MAX_LENGTH)
						.setNullable(true)
						.build()
					);

			addAttribute(
					TEST_ATTRIBUTE_2,
					StringAttribute.builder()
						.setNullable(false)
						.build()
					);
		}

		@Override
		public String getModelName()
		{
			return "TestModel";
		}
	}
}
