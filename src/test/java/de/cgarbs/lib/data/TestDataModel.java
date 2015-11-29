/*
 * Copyright 2015 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.data;

import de.cgarbs.lib.data.type.ColorAttribute;
import de.cgarbs.lib.data.type.FileAttribute;
import de.cgarbs.lib.data.type.IntegerAttribute;
import de.cgarbs.lib.data.type.StringAttribute;
import de.cgarbs.lib.exception.DataException;

/**
 * This is the {@link DataModel} for the {@link DataModelTest} test class.
 * It needs to be a separate file because the serialization ({@link #writeToFile(java.io.File)},
 * {@link #readFromFile(java.io.File)}) does not work with inner classes.
 */
public class TestDataModel extends DataModel
{
	static final String STRING_ATTRIBUTE  = "StringAttribute";
	static final String INTEGER_ATTRIBUTE = "IntegerAttribute";
	static final String COLOR_ATTRIBUTE = "ColorAttribute";
	static final String FILE_ATTRIBUTE = null;

	public TestDataModel() throws DataException
	{
		super();

		addAttribute(
				STRING_ATTRIBUTE,
				StringAttribute.builder()
					.setMaxLength(DataModelTest.GIVEN_MAX_LENGTH)
					.setNullable(true)
					.build()
				);

		addAttribute(
				INTEGER_ATTRIBUTE,
				IntegerAttribute.builder()
					.setNullable(false)
					.build()
				);

		addAttribute(
				COLOR_ATTRIBUTE,
				ColorAttribute.builder()
					.setNullable(false)
					.build()
				);

		addAttribute(
				FILE_ATTRIBUTE,
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