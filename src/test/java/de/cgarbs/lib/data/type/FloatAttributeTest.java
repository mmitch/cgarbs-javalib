package de.cgarbs.lib.data.type;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;

import java.text.NumberFormat;

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
		assertNull(attribute_1_underTest.getValue());
		assertNull(attribute_1_underTest.getFormattedValue());
	}

	@Test
	public void checkSetValues() throws Exception
	{
		attribute_1_underTest.setValue(GIVEN_STRING);
		assertEquals(Float.valueOf(GIVEN_STRING), attribute_1_underTest.getValue());

		attribute_1_underTest.setValue(GIVEN_INTEGER);
		assertEquals(Float.valueOf(GIVEN_INTEGER), attribute_1_underTest.getValue());

		attribute_1_underTest.setValue(GIVEN_FLOAT);
		assertEquals(GIVEN_FLOAT, attribute_1_underTest.getValue());
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

			assertEquals(raw, formatted);
		}

		{
			attribute.setValue(GIVEN_VALUE_TOO_FEW_DECIMALS);
			final String raw = nf.format((Float) attribute.getValue());
			final String formatted = attribute.getFormattedValue();

			assertNotEquals(raw, formatted);
			assertTrue(raw.length() < formatted.length());
			assertTrue(formatted.startsWith(raw));
		}

		{
			attribute.setValue(GIVEN_VALUE_TOO_MANY_DECIMALS);
			final String raw = nf.format((Float) attribute.getValue());
			final String formatted = attribute.getFormattedValue();

			assertNotEquals(raw, formatted);
			assertTrue(raw.length() > formatted.length());
			assertTrue(raw.startsWith(formatted));
		}
	}

	@Test
	public void checkSetup()
	{
		assertEquals(GIVEN_MIN_VALUE, attribute_1_underTest.getMinValue());
		assertEquals(GIVEN_MAX_VALUE, attribute_1_underTest.getMaxValue());
		assertEquals(GIVEN_MIN_DECIMALS, attribute_1_underTest.getMinDecimals());
		assertEquals(GIVEN_MAX_DECIMALS, attribute_1_underTest.getMaxDecimals());
		assertTrue(attribute_1_underTest.isNullable());

		assertNull(attribute_2_underTest.getMinValue());
		assertNull(attribute_2_underTest.getMaxValue());
		assertEquals(GIVEN_DECIMALS, attribute_2_underTest.getMinDecimals());
		assertEquals(GIVEN_DECIMALS, attribute_2_underTest.getMaxDecimals());
		assertFalse(attribute_2_underTest.isNullable());
	}

	class FloatAttributeTestDataModel extends BaseTestDataModel
	{
		private static final long serialVersionUID = 1L;

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
