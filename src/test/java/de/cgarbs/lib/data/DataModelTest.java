package de.cgarbs.lib.data;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import de.cgarbs.lib.data.type.IntAttribute;
import de.cgarbs.lib.data.type.StringAttribute;
import de.cgarbs.lib.exception.ValidationErrorList;
import de.cgarbs.lib.i18n.Resource;

public class DataModelTest
{
	DataModel model;
	StringAttribute stringAttribute_underTest;
	IntAttribute    intAttribute_underTest;

	static final File TEMPFILE = new File("model.tmp");

	static final Integer GIVEN_MAX_LENGTH = 3;

	static final String GIVEN_STRING_VALID   = "abc";
	static final String GIVEN_STRING_INVALID = "abcdefg";

	static final Integer GIVEN_INTEGER_VALID   = Integer.valueOf(0);
	static final Integer GIVEN_INTEGER_INVALID = null;

	@Before
	public void setUp() throws Exception
	{
		model = new TestDataModel(new Resource());
		stringAttribute_underTest = (StringAttribute) model.getAttribute(TestDataModel.STRING_ATTRIBUTE);
		intAttribute_underTest = (IntAttribute) model.getAttribute(TestDataModel.INT_ATTRIBUTE);
	}

	@Test
	public void checkValidateValid() throws Exception
	{
		stringAttribute_underTest.setValue(GIVEN_STRING_VALID);
		intAttribute_underTest.setValue(GIVEN_INTEGER_VALID);
		model.validate();
	}

	@Test
	public void checkValidateInvalid() throws Exception
	{
		stringAttribute_underTest.setValue(GIVEN_STRING_INVALID);
		intAttribute_underTest.setValue(GIVEN_INTEGER_INVALID);
		try
		{
			model.validate();
			fail("no exception thrown");
		}
		catch (ValidationErrorList vel)
		{
			assertThat(vel.getValidationErrors().size(), is(equalTo(2)));
		}
	}

	@Test
	public void checkPersistenceValid() throws Exception
	{
		stringAttribute_underTest.setValue(GIVEN_STRING_VALID);
		intAttribute_underTest.setValue(GIVEN_INTEGER_VALID);

		model.writeToFile(TEMPFILE);

		assertThat((String) stringAttribute_underTest.getValue(), is(equalTo(GIVEN_STRING_VALID)));
		assertThat((Integer) intAttribute_underTest.getValue(), is(equalTo(GIVEN_INTEGER_VALID)));

		stringAttribute_underTest.setValue(GIVEN_STRING_INVALID);
		intAttribute_underTest.setValue(GIVEN_INTEGER_INVALID);

		model.readFromFile(TEMPFILE);

		assertThat((String) stringAttribute_underTest.getValue(), is(equalTo(GIVEN_STRING_VALID)));
		assertThat((Integer) intAttribute_underTest.getValue(), is(equalTo(GIVEN_INTEGER_VALID)));

		TEMPFILE.delete();
	}

	@Test
	public void checkPersistenceInvalid() throws Exception
	{
		stringAttribute_underTest.setValue(GIVEN_STRING_INVALID);
		intAttribute_underTest.setValue(GIVEN_INTEGER_INVALID);

		model.writeToFile(TEMPFILE);

		assertThat((String) stringAttribute_underTest.getValue(), is(equalTo(GIVEN_STRING_INVALID)));
		assertThat((Integer) intAttribute_underTest.getValue(), is(equalTo(GIVEN_INTEGER_INVALID)));

		stringAttribute_underTest.setValue(GIVEN_STRING_VALID);
		intAttribute_underTest.setValue(GIVEN_INTEGER_VALID);

		model.readFromFile(TEMPFILE);

		assertThat((String) stringAttribute_underTest.getValue(), is(equalTo(GIVEN_STRING_INVALID)));
		assertThat((Integer) intAttribute_underTest.getValue(), is(equalTo(GIVEN_INTEGER_INVALID)));

		TEMPFILE.delete();
	}

	@Test
	public void checkIsDirty() throws Exception
	{
		assertThat(model.isDirty(), is(false));

		stringAttribute_underTest.setValue(GIVEN_STRING_VALID);
		intAttribute_underTest.setValue(GIVEN_INTEGER_VALID);

		assertThat(model.isDirty(), is(true));

		model.writeToFile(TEMPFILE);

		// FIXME writing does not reset the flag, this must be done manually!
		assertThat(model.isDirty(), is(true));

		model.resetDirty();

		assertThat(model.isDirty(), is(false));

		stringAttribute_underTest.setValue(GIVEN_STRING_INVALID);
		intAttribute_underTest.setValue(GIVEN_INTEGER_INVALID);

		assertThat(model.isDirty(), is(true));

		model.readFromFile(TEMPFILE);

		assertThat(model.isDirty(), is(false));

		TEMPFILE.delete();
	}

}
