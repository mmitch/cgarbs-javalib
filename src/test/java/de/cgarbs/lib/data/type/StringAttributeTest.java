package de.cgarbs.lib.data.type;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import de.cgarbs.lib.data.DataAttribute;
import de.cgarbs.lib.data.DataModel;
import de.cgarbs.lib.exception.DataException;
import de.cgarbs.lib.exception.ValidationError;
import de.cgarbs.lib.i18n.Resource;

public class StringAttributeTest
{
	DataModel model;
	DataAttribute attribute_1_underTest;
	DataAttribute attribute_2_underTest;

	final String GIVEN_STRING = "foo";
	final Integer GIVEN_INTEGER = 42;
	final Float GIVEN_FLOAT = 13.7f;

	final int GIVEN_MIN_LENGTH = 1;
	final int GIVEN_MAX_LENGTH = 5;
	final String GIVEN_STRING_TOO_SHORT = "";
	final String GIVEN_STRING_TOO_LONG  = "xxxxxxx";

	@Before
	public void setUp() throws Exception
	{
		model = new StringAttributeTestDataModel(new Resource(BaseTestDataModel.class));
		attribute_1_underTest = model.getAttribute(StringAttributeTestDataModel.TEST_ATTRIBUTE_1);
		attribute_2_underTest = model.getAttribute(StringAttributeTestDataModel.TEST_ATTRIBUTE_2);
	}

	@Test
	public void checkSetNull() throws Exception
	{
		attribute_1_underTest.setValue(null);
		assertNull(attribute_1_underTest.getValue());
	}

	@Test
	public void checkSetValues() throws Exception
	{
		attribute_1_underTest.setValue(GIVEN_STRING);
		assertEquals(GIVEN_STRING, attribute_1_underTest.getValue());

		attribute_1_underTest.setValue(GIVEN_INTEGER);
		assertEquals(GIVEN_INTEGER.toString(), attribute_1_underTest.getValue());

		attribute_1_underTest.setValue(GIVEN_FLOAT);
		assertEquals(GIVEN_FLOAT.toString(), attribute_1_underTest.getValue());
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
			assertEquals(ValidationError.ERROR.NULL_NOT_ALLOWED, e.getError());
		}

		try
		{
			attribute_1_underTest.validate(GIVEN_STRING_TOO_LONG);
			fail("String too long - no exception thrown");
		}
		catch (ValidationError e)
		{
			assertEquals(ValidationError.ERROR.STRING_TOO_LONG, e.getError());
		}
		attribute_2_underTest.validate(GIVEN_STRING_TOO_LONG);

		try
		{
			attribute_1_underTest.validate(GIVEN_STRING_TOO_SHORT);
			fail("String too short - no exception thrown");
		}
		catch (ValidationError e)
		{
			assertEquals(ValidationError.ERROR.STRING_TOO_SHORT, e.getError());
		}
		attribute_2_underTest.validate(GIVEN_STRING_TOO_SHORT);
	}

	class StringAttributeTestDataModel extends BaseTestDataModel
	{
		private static final long serialVersionUID = 1L;

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