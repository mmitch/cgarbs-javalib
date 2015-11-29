/*
 * Copyright 2015 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.data;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.awt.Color;
import java.io.File;

import org.junit.Before;
import org.junit.Test;

import de.cgarbs.lib.FileUtils;
import de.cgarbs.lib.data.type.ColorAttribute;
import de.cgarbs.lib.data.type.FileAttribute;
import de.cgarbs.lib.data.type.IntegerAttribute;
import de.cgarbs.lib.data.type.StringAttribute;
import de.cgarbs.lib.exception.ValidationErrorList;

public class DataModelTest
{
	DataModel model;
	StringAttribute  stringAttribute_underTest;
	IntegerAttribute integerAttribute_underTest;
	ColorAttribute   colorAttribute_underTest;
	FileAttribute    fileAttribute_underTest;

	static final File TEMPFILE = new File("model.tmp");

	static final Integer GIVEN_MAX_LENGTH = 3;

	static final String GIVEN_STRING_VALID   = "abc";
	static final String GIVEN_STRING_INVALID = "abcdefg";

	static final Integer GIVEN_INTEGER_VALID   = Integer.valueOf(0);
	static final Integer GIVEN_INTEGER_INVALID = null;

	static final Color GIVEN_COLOR_VALID   = Color.MAGENTA;
	static final Color GIVEN_COLOR_INVALID = null;

	static final File GIVEN_FILE_VALID   = FileUtils.createFileFrom("foo", "bar", "baz", "");
	static final File GIVEN_FILE_INVALID = null;


	@Before
	public void setUp() throws Exception
	{
		model = new TestDataModel();
		stringAttribute_underTest  = (StringAttribute)  model.getAttribute(TestDataModel.STRING_ATTRIBUTE);
		integerAttribute_underTest = (IntegerAttribute) model.getAttribute(TestDataModel.INTEGER_ATTRIBUTE);
		colorAttribute_underTest   = (ColorAttribute)   model.getAttribute(TestDataModel.COLOR_ATTRIBUTE);
		fileAttribute_underTest    = (FileAttribute)    model.getAttribute(TestDataModel.FILE_ATTRIBUTE);
	}

	@Test
	public void checkValidateValid() throws Exception
	{
		stringAttribute_underTest.setValue(GIVEN_STRING_VALID);
		integerAttribute_underTest.setValue(GIVEN_INTEGER_VALID);
		colorAttribute_underTest.setValue(GIVEN_COLOR_VALID);
		fileAttribute_underTest.setValue(GIVEN_FILE_VALID);
		model.validate();
	}

	@Test
	public void checkValidateInvalid() throws Exception
	{
		stringAttribute_underTest.setValue(GIVEN_STRING_INVALID);
		integerAttribute_underTest.setValue(GIVEN_INTEGER_INVALID);
		colorAttribute_underTest.setValue(GIVEN_COLOR_INVALID);
		fileAttribute_underTest.setValue(GIVEN_FILE_INVALID);
		try
		{
			model.validate();
			fail("no exception thrown");
		}
		catch (ValidationErrorList vel)
		{
			assertThat(vel.getValidationErrors().size(), is(equalTo( 4 )));
		}
	}

	@Test
	public void checkPersistenceOtherInstance() throws Exception
	{
		stringAttribute_underTest.setValue(GIVEN_STRING_VALID);
		integerAttribute_underTest.setValue(GIVEN_INTEGER_VALID);
		colorAttribute_underTest.setValue(GIVEN_COLOR_VALID);
		fileAttribute_underTest.setValue(GIVEN_FILE_VALID);

		model.writeToFile(TEMPFILE);

		TestDataModel otherModel = new TestDataModel();
		otherModel.readFromFile(TEMPFILE);

		assertThat(otherModel.getValue(TestDataModel.STRING_ATTRIBUTE),  is(equalTo(model.getValue(TestDataModel.STRING_ATTRIBUTE))));
		assertThat(otherModel.getValue(TestDataModel.INTEGER_ATTRIBUTE), is(equalTo(model.getValue(TestDataModel.INTEGER_ATTRIBUTE))));
		assertThat(otherModel.getValue(TestDataModel.COLOR_ATTRIBUTE),   is(equalTo(model.getValue(TestDataModel.COLOR_ATTRIBUTE))));
		assertThat(otherModel.getValue(TestDataModel.FILE_ATTRIBUTE),    is(equalTo(model.getValue(TestDataModel.FILE_ATTRIBUTE))));

		TEMPFILE.delete();
	}

	@Test
	public void checkPersistenceValid() throws Exception
	{
		stringAttribute_underTest.setValue(GIVEN_STRING_VALID);
		integerAttribute_underTest.setValue(GIVEN_INTEGER_VALID);
		colorAttribute_underTest.setValue(GIVEN_COLOR_VALID);
		fileAttribute_underTest.setValue(GIVEN_FILE_VALID);

		model.writeToFile(TEMPFILE);

		assertThat((String)  stringAttribute_underTest.getValue(),  is(equalTo(GIVEN_STRING_VALID)));
		assertThat((Integer) integerAttribute_underTest.getValue(), is(equalTo(GIVEN_INTEGER_VALID)));
		assertThat((Color)   colorAttribute_underTest.getValue(),   is(equalTo(GIVEN_COLOR_VALID)));
		assertThat((File)    fileAttribute_underTest.getValue(),    is(equalTo(GIVEN_FILE_VALID)));

		stringAttribute_underTest.setValue(GIVEN_STRING_INVALID);
		integerAttribute_underTest.setValue(GIVEN_INTEGER_INVALID);
		colorAttribute_underTest.setValue(GIVEN_COLOR_INVALID);
		fileAttribute_underTest.setValue(GIVEN_FILE_INVALID);

		model.readFromFile(TEMPFILE);

		assertThat((String)  stringAttribute_underTest.getValue(),  is(equalTo(GIVEN_STRING_VALID)));
		assertThat((Integer) integerAttribute_underTest.getValue(), is(equalTo(GIVEN_INTEGER_VALID)));
		assertThat((Color)   colorAttribute_underTest.getValue(),   is(equalTo(GIVEN_COLOR_VALID)));
		assertThat((File)    fileAttribute_underTest.getValue(),    is(equalTo(GIVEN_FILE_VALID)));

		TEMPFILE.delete();
	}

	@Test
	public void checkPersistenceInvalid() throws Exception
	{
		stringAttribute_underTest.setValue(GIVEN_STRING_INVALID);
		integerAttribute_underTest.setValue(GIVEN_INTEGER_INVALID);
		colorAttribute_underTest.setValue(GIVEN_COLOR_INVALID);
		fileAttribute_underTest.setValue(GIVEN_FILE_INVALID);

		model.writeToFile(TEMPFILE);

		assertThat((String)  stringAttribute_underTest.getValue(),  is(equalTo(GIVEN_STRING_INVALID)));
		assertThat((Integer) integerAttribute_underTest.getValue(), is(equalTo(GIVEN_INTEGER_INVALID)));
		assertThat((Color)   colorAttribute_underTest.getValue(),   is(equalTo(GIVEN_COLOR_INVALID)));
		assertThat((File)    fileAttribute_underTest.getValue(),    is(equalTo(GIVEN_FILE_INVALID)));

		stringAttribute_underTest.setValue(GIVEN_STRING_VALID);
		integerAttribute_underTest.setValue(GIVEN_INTEGER_VALID);
		colorAttribute_underTest.setValue(GIVEN_COLOR_VALID);
		fileAttribute_underTest.setValue(GIVEN_FILE_VALID);

		model.readFromFile(TEMPFILE);

		assertThat((String)  stringAttribute_underTest.getValue(),  is(equalTo(GIVEN_STRING_INVALID)));
		assertThat((Integer) integerAttribute_underTest.getValue(), is(equalTo(GIVEN_INTEGER_INVALID)));
		assertThat((Color)   colorAttribute_underTest.getValue(),   is(equalTo(GIVEN_COLOR_INVALID)));
		assertThat((File)    fileAttribute_underTest.getValue(),    is(equalTo(GIVEN_FILE_INVALID)));

		TEMPFILE.delete();
	}

	@Test
	public void checkIsDirty() throws Exception
	{
		assertThat(model.isDirty(), is(false));

		stringAttribute_underTest.setValue(GIVEN_STRING_VALID);
		integerAttribute_underTest.setValue(GIVEN_INTEGER_VALID);
		colorAttribute_underTest.setValue(GIVEN_COLOR_VALID);
		fileAttribute_underTest.setValue(GIVEN_FILE_VALID);

		assertThat(model.isDirty(), is(true));

		model.writeToFile(TEMPFILE);

		// FIXME writing does not reset the flag, this must be done manually!
		assertThat(model.isDirty(), is(true));

		model.resetDirty();

		assertThat(model.isDirty(), is(false));

		stringAttribute_underTest.setValue(GIVEN_STRING_INVALID);
		integerAttribute_underTest.setValue(GIVEN_INTEGER_INVALID);
		colorAttribute_underTest.setValue(GIVEN_COLOR_INVALID);
		fileAttribute_underTest.setValue(GIVEN_FILE_INVALID);

		assertThat(model.isDirty(), is(true));

		model.readFromFile(TEMPFILE);

		assertThat(model.isDirty(), is(false));

		TEMPFILE.delete();
	}

}
