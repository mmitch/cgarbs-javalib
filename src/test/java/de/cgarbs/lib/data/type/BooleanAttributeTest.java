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

public class BooleanAttributeTest
{
	DataModel model;
	DataAttribute attribute_1_underTest;
	DataAttribute attribute_2_underTest;

	static final String GIVEN_STRING = "true";
	static final Boolean GIVEN_BOOLEAN = false;

	@Before
	public void setUp() throws Exception
	{
		model = new BooleanAttributeTestDataModel(new Resource(BaseTestDataModel.class));
		attribute_1_underTest = model.getAttribute(BooleanAttributeTestDataModel.TEST_ATTRIBUTE_1);
		attribute_2_underTest = model.getAttribute(BooleanAttributeTestDataModel.TEST_ATTRIBUTE_2);
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
		attribute_1_underTest.setValue(GIVEN_BOOLEAN);
		assertEquals(GIVEN_BOOLEAN, attribute_1_underTest.getValue());
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
			assertEquals(DataException.ERROR.INVALID_VALUE, e.getError());
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
			assertEquals(ValidationError.ERROR.NULL_NOT_ALLOWED, e.getError());
		}
	}

	class BooleanAttributeTestDataModel extends BaseTestDataModel
	{
		private static final long serialVersionUID = 1L;

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
					IntAttribute.builder()
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
