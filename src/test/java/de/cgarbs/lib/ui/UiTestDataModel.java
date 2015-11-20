/*
 * Copyright 2015 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.ui;

import de.cgarbs.lib.data.DataModel;
import de.cgarbs.lib.data.type.StringAttribute;
import de.cgarbs.lib.exception.DataException;
import de.cgarbs.lib.i18n.Resource;

public class UiTestDataModel extends DataModel
{
	private static final long serialVersionUID = 1L;

	public static final String STRING_1 = "StringAttribute1";

	public static final String STRING_2 = "StringAttribute2";

	public static final String STRING_3 = "StringAttribute3";

	public static final String STRING_4 = "StringAttribute4";

	public static final String STRING_5 = "StringAttribute5";

	public static final String STRING_6 = "StringAttribute6";

	public static final String MODEL_NAME = "UiTestDataModelName";

	public UiTestDataModel(Resource resource) throws DataException
	{
		super(resource);

		addAttribute(
				STRING_1,
				StringAttribute.builder()
					.build()
				);

		addAttribute(
				STRING_2,
				StringAttribute.builder()
					.build()
				);

		addAttribute(
				STRING_3,
				StringAttribute.builder()
					.build()
				);

		addAttribute(
				STRING_4,
				StringAttribute.builder()
					.build()
				);

		addAttribute(
				STRING_5,
				StringAttribute.builder()
					.build()
				);

		addAttribute(
				STRING_6,
				StringAttribute.builder()
					.build()
				);
	}

	@Override
	public String getModelName()
	{
		return MODEL_NAME;
	}

}
