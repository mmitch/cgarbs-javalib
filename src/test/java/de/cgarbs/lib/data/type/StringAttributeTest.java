package de.cgarbs.lib.data.type;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import de.cgarbs.lib.data.DataAttribute;
import de.cgarbs.lib.data.DataModel;
import de.cgarbs.lib.exception.DataException;
import de.cgarbs.lib.exception.ValidationError.ERROR;
import de.cgarbs.lib.i18n.Resource;

public class StringAttributeTest
{
	DataModel model;
	DataAttribute attribute_underTest;
	
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
		attribute_underTest = model.getAttribute(StringAttributeTestDataModel.TEST_ATTRIBUTE_NAME);
	}
	
	@Test
	public void checkSetNull() throws Exception
	{
		attribute_underTest.setValue(null);
		assertNull(attribute_underTest.getValue());
	}
	
	@Test
	public void checkSetValues() throws Exception
	{
		attribute_underTest.setValue(GIVEN_STRING);
		assertEquals(GIVEN_STRING, attribute_underTest.getValue());
		
		attribute_underTest.setValue(GIVEN_INTEGER);
		assertEquals(GIVEN_INTEGER.toString(), attribute_underTest.getValue());
		
		attribute_underTest.setValue(GIVEN_FLOAT);
		assertEquals(GIVEN_FLOAT.toString(), attribute_underTest.getValue());
	}
	
	@Test
	public void checkBoundaries() throws Exception
	{
		try
		{
			attribute_underTest.setValue(GIVEN_STRING_TOO_LONG);
			fail("String too long - no exception thrown");
		}
		catch (DataException e)
		{
			assertEquals(ERROR.STRING_TOO_LONG, e.getError());
		}

		try
		{
			attribute_underTest.setValue(GIVEN_STRING_TOO_SHORT);
			fail("String too short - no exception thrown");
		}
		catch (DataException e)
		{
			assertEquals(ERROR.STRING_TOO_SHORT, e.getError());
		}
	}
	
	class StringAttributeTestDataModel extends BaseTestDataModel
	{
		private static final long serialVersionUID = 1L;

		public StringAttributeTestDataModel(Resource resource) throws DataException
		{
			super(resource);
			
			addAttribute(
					TEST_ATTRIBUTE_NAME,
					StringAttribute.builder()
						.setMinLength(GIVEN_MIN_LENGTH)
						.setMaxLength(GIVEN_MAX_LENGTH)
						.setNullable(true)
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
