/*
 * Copyright 2015, 2020 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.data.type;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.text.NumberFormat;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import de.cgarbs.lib.data.DataModel;
import de.cgarbs.lib.data.NumberAttribute;
import de.cgarbs.lib.exception.DataException;
import de.cgarbs.lib.exception.ValidationError;
import de.cgarbs.lib.i18n.Resource;

// FIXME add check for correct locale handling in DecimalFormatter?

public class FloatAttributeTest
{
	DataModel model;
	FloatAttribute attribute_1_underTest;
	FloatAttribute attribute_2_underTest;

	static final String GIVEN_STRING = "13";
	static final Integer GIVEN_INTEGER = -42;
	static final Float GIVEN_FLOAT = 13.7f;

	static final float GIVEN_MIN_VALUE = -1.2f;
	static final float GIVEN_MAX_VALUE = 5;
	static final float GIVEN_VALUE_TOO_SMALL = -1.3f;
	static final float GIVEN_VALUE_TOO_BIG   = 5.000001f;

	static final int GIVEN_DECIMALS = 3;
	static final int GIVEN_MIN_DECIMALS = 2;
	static final int GIVEN_MAX_DECIMALS = 4;
	static final float GIVEN_VALUE_TOO_FEW_DECIMALS = 2.1f;
	static final float GIVEN_VALUE_TOO_MANY_DECIMALS = 2.12341f;
	static final float GIVEN_VALUE_MATCHING_DECIMALS = 3.414f;

	@Before
	public void setUp() throws Exception
	{
		model = new FloatAttributeTestDataModel(new Resource(BaseTestDataModel.class));
		attribute_1_underTest = (FloatAttribute) model.getAttribute(FloatAttributeTestDataModel.TEST_ATTRIBUTE_1);
		attribute_2_underTest = (FloatAttribute) model.getAttribute(FloatAttributeTestDataModel.TEST_ATTRIBUTE_2);
	}

	@Test
	public void checkSetNull() throws Exception
	{
		attribute_1_underTest.setValue(null);
		assertThat(attribute_1_underTest.getValue(), is(nullValue()));
		assertThat(attribute_1_underTest.getFormattedValue(), is(nullValue()));
	}

	@Test
	public void checkSetValues() throws Exception
	{
		attribute_1_underTest.setValue(GIVEN_STRING);
		assertThat(attribute_1_underTest.getValue(), is(Float.valueOf(GIVEN_STRING)));

		attribute_1_underTest.setValue(GIVEN_INTEGER);
		assertThat(attribute_1_underTest.getValue(), is(Float.valueOf(GIVEN_INTEGER)));

		attribute_1_underTest.setValue(GIVEN_FLOAT);
		assertThat(attribute_1_underTest.getValue(), is(GIVEN_FLOAT));
	}

	@Test
	public void checkSetInvalid() throws Exception
	{
		// these should throw no error unless validate() is called!
		attribute_1_underTest.setValue(GIVEN_VALUE_TOO_BIG);
		attribute_1_underTest.setValue(GIVEN_VALUE_TOO_SMALL);
		attribute_1_underTest.setValue(GIVEN_VALUE_TOO_FEW_DECIMALS);
		attribute_1_underTest.setValue(GIVEN_VALUE_TOO_MANY_DECIMALS);
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
			attribute_1_underTest.validate(GIVEN_VALUE_TOO_BIG);
			fail("no exception thrown");
		}
		catch (ValidationError e)
		{
			assertThat(e.getError(), is(ValidationError.ERROR.NUMBER_TOO_LARGE));
		}
		attribute_2_underTest.validate(GIVEN_VALUE_TOO_BIG);

		try
		{
			attribute_1_underTest.validate(GIVEN_VALUE_TOO_SMALL);
			fail("no exception thrown");
		}
		catch (ValidationError e)
		{
			assertThat(e.getError(), is(ValidationError.ERROR.NUMBER_TOO_SMALL));
		}
		attribute_2_underTest.validate(GIVEN_VALUE_TOO_SMALL);

		// these should never throw errors
		attribute_1_underTest.validate(GIVEN_VALUE_TOO_FEW_DECIMALS);
		attribute_2_underTest.validate(GIVEN_VALUE_TOO_FEW_DECIMALS);
		attribute_1_underTest.validate(GIVEN_VALUE_TOO_MANY_DECIMALS);
		attribute_2_underTest.validate(GIVEN_VALUE_TOO_MANY_DECIMALS);
	}

	@Test
	public void checkDecimalFormatting() throws DataException
	{
		checkDecimalFormatting(attribute_1_underTest);
		checkDecimalFormatting(attribute_2_underTest);
	}

	public void checkDecimalFormatting(NumberAttribute attribute) throws DataException
	{
		// look out for locales!
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(0);
		nf.setMaximumFractionDigits(6);

		{
			attribute.setValue(GIVEN_VALUE_MATCHING_DECIMALS);
			final String raw = nf.format((Float) attribute.getValue());
			final String formatted = attribute.getFormattedValue();

			assertThat(formatted, is(raw));
		}

		{
			attribute.setValue(GIVEN_VALUE_TOO_FEW_DECIMALS);
			final String raw = nf.format((Float) attribute.getValue());
			final String formatted = attribute.getFormattedValue();

			assertThat(formatted, is(not(raw)));
			assertThat(formatted.length(), is(greaterThan(raw.length())));
			assertThat(formatted.startsWith(raw), is(true));
		}

		{
			attribute.setValue(GIVEN_VALUE_TOO_MANY_DECIMALS);
			final String raw = nf.format((Float) attribute.getValue());
			final String formatted = attribute.getFormattedValue(); 

			assertThat(raw, is(not(formatted)));
			assertThat(formatted.length(), is(lessThan(raw.length())));
			assertThat(raw, Matchers.startsWith(formatted));
		}
	}

	@Test
	public void checkSetup()
	{
		assertThat(attribute_1_underTest.getMinValue(), is(GIVEN_MIN_VALUE));
		assertThat(attribute_1_underTest.getMaxValue(), is(GIVEN_MAX_VALUE));
		assertThat(attribute_1_underTest.getMinDecimals(), is(GIVEN_MIN_DECIMALS));
		assertThat(attribute_1_underTest.getMaxDecimals(), is(GIVEN_MAX_DECIMALS));
		assertThat(attribute_1_underTest.isNullable(), is(true));

		assertThat(attribute_2_underTest.getMinValue(), is(nullValue()));
		assertThat(attribute_2_underTest.getMaxValue(), is(nullValue()));
		assertThat(attribute_2_underTest.getMinDecimals(), is(GIVEN_DECIMALS));
		assertThat(attribute_2_underTest.getMaxDecimals(), is(GIVEN_DECIMALS));
		assertThat(attribute_2_underTest.isNullable(), is(false));
	}

	class FloatAttributeTestDataModel extends BaseTestDataModel
	{
		public FloatAttributeTestDataModel(Resource resource) throws DataException
		{
			super(resource);

			addAttribute(
					TEST_ATTRIBUTE_1,
					FloatAttribute.builder()
						.setMinDecimals(GIVEN_MIN_DECIMALS)
						.setMaxDecimals(GIVEN_MAX_DECIMALS)
						.setMinValue(GIVEN_MIN_VALUE)
						.setMaxValue(GIVEN_MAX_VALUE)
						.setNullable(true)
						.build()
					);

			addAttribute(
					TEST_ATTRIBUTE_2,
					FloatAttribute.builder()
						.setDecimals(GIVEN_DECIMALS)
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
