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

public class BooleanAttributeTest
{
	DataModel model;
	BooleanAttribute attribute_1_underTest;
	BooleanAttribute attribute_2_underTest;

	static final String GIVEN_STRING = "true";
	static final Boolean GIVEN_BOOLEAN = false;

	@Before
	public void setUp() throws Exception
	{
		model = new BooleanAttributeTestDataModel(new Resource(BaseTestDataModel.class));
		attribute_1_underTest = (BooleanAttribute) model.getAttribute(BooleanAttributeTestDataModel.TEST_ATTRIBUTE_1);
		attribute_2_underTest = (BooleanAttribute) model.getAttribute(BooleanAttributeTestDataModel.TEST_ATTRIBUTE_2);
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
		attribute_1_underTest.setValue(GIVEN_BOOLEAN);
		assertThat(attribute_1_underTest.getValue(), is(GIVEN_BOOLEAN));
	}

	@Test
	public void checkSetInvalid() throws Exception
	{
		// these should throw no error unless validate() is called!
		attribute_2_underTest.setValue(null);

		// String is not allowed
		try
		{
			attribute_1_underTest.setValue(GIVEN_STRING);
			fail("no exception thrown");
		}
		catch (DataException e)
		{
			assertThat(e.getError(), is(DataException.ERROR.INVALID_VALUE));
		}
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
	}

	@Test
	public void checkSetup()
	{
		assertThat(attribute_1_underTest.isNullable(), is(true));

		assertThat(attribute_2_underTest.isNullable(), is(false));
	}

	class BooleanAttributeTestDataModel extends BaseTestDataModel
	{
		public BooleanAttributeTestDataModel(Resource resource) throws DataException
		{
			super(resource);

			addAttribute(
					TEST_ATTRIBUTE_1,
					BooleanAttribute.builder()
						.setNullable(true)
						.build()
					);

			addAttribute(
					TEST_ATTRIBUTE_2,
					BooleanAttribute.builder()
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
