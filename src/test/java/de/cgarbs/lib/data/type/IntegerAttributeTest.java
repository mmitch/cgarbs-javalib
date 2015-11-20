/*
 * Copyright 2015 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.data.type;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import de.cgarbs.lib.data.DataModel;
import de.cgarbs.lib.exception.DataException;
import de.cgarbs.lib.exception.ValidationError;
import de.cgarbs.lib.i18n.Resource;

public class IntegerAttributeTest
{
	DataModel model;
	IntegerAttribute attribute_1_underTest;
	IntegerAttribute attribute_2_underTest;

	static final String GIVEN_STRING = "13";
	static final Integer GIVEN_INTEGER = -42;
	static final Float GIVEN_FLOAT = 13.7f;

	static final Integer GIVEN_MIN_VALUE = 1;
	static final Integer GIVEN_MAX_VALUE = 5;
	static final Integer GIVEN_VALUE_TOO_SMALL = 0;
	static final Integer GIVEN_VALUE_TOO_BIG   = 17;

	@Before
	public void setUp() throws Exception
	{
		model = new IntAttributeTestDataModel(new Resource(BaseTestDataModel.class));
		attribute_1_underTest = (IntegerAttribute) model.getAttribute(IntAttributeTestDataModel.TEST_ATTRIBUTE_1);
		attribute_2_underTest = (IntegerAttribute) model.getAttribute(IntAttributeTestDataModel.TEST_ATTRIBUTE_2);
	}

	@Test
	public void checkSetNull() throws Exception
	{
		attribute_1_underTest.setValue(null);
		assertNull(attribute_1_underTest.getValue());
		assertNull(attribute_1_underTest.getFormattedValue());
	}

	@Test
	public void checkSetValues() throws Exception
	{
		attribute_1_underTest.setValue(GIVEN_STRING);
		assertEquals(Integer.valueOf(GIVEN_STRING), attribute_1_underTest.getValue());

		attribute_1_underTest.setValue(GIVEN_INTEGER);
		assertEquals(GIVEN_INTEGER, attribute_1_underTest.getValue());

		attribute_1_underTest.setValue(GIVEN_FLOAT);
		assertEquals(Integer.valueOf(GIVEN_FLOAT.intValue()), attribute_1_underTest.getValue());
	}

	@Test
	public void checkSetInvalid() throws Exception
	{
		// these should throw no error unless validate() is called!
		attribute_1_underTest.setValue(GIVEN_VALUE_TOO_BIG);
		attribute_1_underTest.setValue(GIVEN_VALUE_TOO_SMALL);
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
			assertEquals(ValidationError.ERROR.NULL_NOT_ALLOWED, e.getError());
		}

		try
		{
			attribute_1_underTest.validate(GIVEN_VALUE_TOO_BIG);
			fail("no exception thrown");
		}
		catch (ValidationError e)
		{
			assertEquals(ValidationError.ERROR.NUMBER_TOO_LARGE, e.getError());
		}
		attribute_2_underTest.validate(GIVEN_VALUE_TOO_BIG);

		try
		{
			attribute_1_underTest.validate(GIVEN_VALUE_TOO_SMALL);
			fail("no exception thrown");
		}
		catch (ValidationError e)
		{
			assertEquals(ValidationError.ERROR.NUMBER_TOO_SMALL, e.getError());
		}
		attribute_2_underTest.validate(GIVEN_VALUE_TOO_SMALL);
	}

	@Test
	public void checkSetup()
	{
		assertEquals(GIVEN_MIN_VALUE, attribute_1_underTest.getMinValue());
		assertEquals(GIVEN_MAX_VALUE, attribute_1_underTest.getMaxValue());
		assertTrue(attribute_1_underTest.isNullable());

		assertNull(attribute_2_underTest.getMinValue());
		assertNull(attribute_2_underTest.getMaxValue());
		assertFalse(attribute_2_underTest.isNullable());
	}

	class IntAttributeTestDataModel extends BaseTestDataModel
	{
		private static final long serialVersionUID = 1L;

		public IntAttributeTestDataModel(Resource resource) throws DataException
		{
			super(resource);

			addAttribute(
					TEST_ATTRIBUTE_1,
					IntegerAttribute.builder()
						.setMinValue(GIVEN_MIN_VALUE)
						.setMaxValue(GIVEN_MAX_VALUE)
						.setNullable(true)
						.build()
					);

			addAttribute(
					TEST_ATTRIBUTE_2,
					IntegerAttribute.builder()
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
