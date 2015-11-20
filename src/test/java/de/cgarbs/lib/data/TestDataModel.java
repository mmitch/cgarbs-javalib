/*
 * Copyright 2015 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.data;

import de.cgarbs.lib.data.type.IntegerAttribute;
import de.cgarbs.lib.data.type.StringAttribute;
import de.cgarbs.lib.exception.DataException;
import de.cgarbs.lib.i18n.Resource;

/**
 * This is the {@link DataModel} for the {@link DataModelTest} test class.
 * It needs to be a separate file because the serialization ({@link #writeToFile(java.io.File)},
 * {@link #readFromFile(java.io.File)}) does not work with inner classes.
 */
class TestDataModel extends DataModel
{
	private static final long serialVersionUID = 1L;
	static final String STRING_ATTRIBUTE  = "StringAttribute";
	static final String INTEGER_ATTRIBUTE = "IntegerAttribute";

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
	}

	@Override
	public String getModelName()
	{
		return "TestModel";
	}
}