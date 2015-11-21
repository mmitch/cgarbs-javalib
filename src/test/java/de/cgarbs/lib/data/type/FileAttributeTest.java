/*
 * Copyright 2015 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.data.type;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static de.cgarbs.lib.hamcrest.File.sameFileAs;

import java.io.File;
import java.text.NumberFormat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import de.cgarbs.lib.data.DataModel;
import de.cgarbs.lib.data.NumberAttribute;
import de.cgarbs.lib.exception.DataException;
import de.cgarbs.lib.exception.ValidationError;
import de.cgarbs.lib.i18n.Resource;

public class FileAttributeTest
{
	@Rule
	public TemporaryFolder tmpFolder= new TemporaryFolder();

	DataModel model;
	FileAttribute attribute_1_underTest;
	FileAttribute attribute_2_underTest;

	File accessibleFile;
	File missingFile;
	File notWritableFile;
	File notReadableFile;

	@Before
	public void setUp() throws Exception
	{
		model = new FileAttributeTestDataModel(new Resource(BaseTestDataModel.class));
		attribute_1_underTest = (FileAttribute) model.getAttribute(FileAttributeTestDataModel.TEST_ATTRIBUTE_1);
		attribute_2_underTest = (FileAttribute) model.getAttribute(FileAttributeTestDataModel.TEST_ATTRIBUTE_2);

		accessibleFile = tmpFolder.newFile();

		notWritableFile = tmpFolder.newFile();
		notWritableFile.setWritable(false);

		notReadableFile = tmpFolder.newFile();
		notReadableFile.setReadable(false);

		missingFile = tmpFolder.newFile();
		missingFile.delete();
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
		attribute_1_underTest.setValue(accessibleFile);
		assertThat((File) attribute_1_underTest.getValue(), is(sameFileAs(accessibleFile)));

		attribute_1_underTest.setValue(accessibleFile.getPath());
		assertThat((File) attribute_1_underTest.getValue(), is(sameFileAs(accessibleFile)));
	}

	@Test
	public void checkSetInvalid() throws Exception
	{
		// these should throw no error unless validate() is called!
		attribute_1_underTest.setValue(null);
		attribute_1_underTest.setValue(notReadableFile);
		attribute_1_underTest.setValue(notWritableFile);
		attribute_1_underTest.setValue(missingFile);

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
			assertThat(e.getError(), is(equalTo(ValidationError.ERROR.NULL_NOT_ALLOWED)));
		}

		try
		{
			attribute_1_underTest.validate(missingFile);
			fail("no exception thrown");
		}
		catch (ValidationError e)
		{
			assertThat(e.getError(), is(equalTo(ValidationError.ERROR.FILE_NOT_EXISTING)));
		}
		attribute_2_underTest.validate(missingFile);

		try
		{
			attribute_1_underTest.validate(notReadableFile);
			fail("no exception thrown");
		}
		catch (ValidationError e)
		{
			assertThat(e.getError(), is(equalTo(ValidationError.ERROR.FILE_NOT_READABLE)));
		}
		attribute_2_underTest.validate(notReadableFile);

		try
		{
			attribute_1_underTest.validate(notWritableFile);
			fail("no exception thrown");
		}
		catch (ValidationError e)
		{
			assertThat(e.getError(), is(equalTo(ValidationError.ERROR.FILE_NOT_WRITABLE)));
		}
		attribute_2_underTest.validate(notWritableFile);
	}

	@Test
	public void checkSetup()
	{
		assertThat(attribute_1_underTest.getMustExist(), is(true));
		assertThat(attribute_1_underTest.getMustRead(),  is(true));
		assertThat(attribute_1_underTest.getMustWrite(), is(true));
		assertThat(attribute_1_underTest.isNullable(),   is(true));

		assertThat(attribute_2_underTest.getMustExist(), is(false));
		assertThat(attribute_2_underTest.getMustRead(),  is(false));
		assertThat(attribute_2_underTest.getMustWrite(), is(false));
		assertThat(attribute_2_underTest.isNullable(),   is(false));
	}

	class FileAttributeTestDataModel extends BaseTestDataModel
	{
		public FileAttributeTestDataModel(Resource resource) throws DataException
		{
			super(resource);

			addAttribute(
					TEST_ATTRIBUTE_1,
					FileAttribute.builder()
						.setMustExist(true)
						.setMustRead(true)
						.setMustWrite(true)
						.setNullable(true)
						.build()
					);

			addAttribute(
					TEST_ATTRIBUTE_2,
					FileAttribute.builder()
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
