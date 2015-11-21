/*
 * Copyright 2015 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.glue;

import de.cgarbs.lib.data.DataModel;
import de.cgarbs.lib.data.type.BooleanAttribute;
import de.cgarbs.lib.data.type.ColorAttribute;
import de.cgarbs.lib.data.type.FileAttribute;
import de.cgarbs.lib.data.type.FloatAttribute;
import de.cgarbs.lib.data.type.IntegerAttribute;
import de.cgarbs.lib.data.type.StringAttribute;
import de.cgarbs.lib.exception.DataException;
import de.cgarbs.lib.i18n.Resource;

public class GlueTestDataModel extends DataModel
{
	public static final String STRING_ATTRIBUTE = "StringAttribute";

	public static final String BOOLEAN_ATTRIBUTE = "BooleanAttribute";

	public static final String INTEGER_ATTRIBUTE = "IntegerAttribute";

	public static final String FLOAT_ATTRIBUTE = "FloatAttribute";
	public static final int FLOAT_DECIMALS = 2;

	public static final String COLOR_ATTRIBUTE = "ColorAttribute";

	public static final String IMAGE_ATTRIBUTE = "ImageAttribute";

	public static final String PROGRESSBAR_INT_ATTRIBUTE = "ProgressBarIntAttribute";
	public static final int PROGRESSBAR_MIN_VALUE = 5;
	public static final int PROGRESSBAR_MAX_VALUE = 15;

	public static final String PROGRESSBAR_FLOAT_ATTRIBUTE = "ProgressBarFloatAttribute";

	public GlueTestDataModel(Resource resource) throws DataException
	{
		super(resource);

		addAttribute(
				STRING_ATTRIBUTE,
				StringAttribute.builder()
					.build()
				);

		addAttribute(
				BOOLEAN_ATTRIBUTE,
				BooleanAttribute.builder()
					.build()
				);

		addAttribute(
				INTEGER_ATTRIBUTE,
				IntegerAttribute.builder()
					.build()
				);

		addAttribute(
				FLOAT_ATTRIBUTE,
				FloatAttribute.builder()
					.setDecimals(FLOAT_DECIMALS)
					.build()
				);

		addAttribute(
				COLOR_ATTRIBUTE,
				ColorAttribute.builder()
					.build()
				);

		addAttribute(
				IMAGE_ATTRIBUTE,
				FileAttribute.builder()
					.build()
				);

		addAttribute(
				PROGRESSBAR_INT_ATTRIBUTE,
				IntegerAttribute.builder()
					.setMinValue(PROGRESSBAR_MIN_VALUE)
					.setMaxValue(PROGRESSBAR_MAX_VALUE)
					.build()
				);

		addAttribute(
				PROGRESSBAR_FLOAT_ATTRIBUTE,
				FloatAttribute.builder()
					.setMinValue((float) PROGRESSBAR_MIN_VALUE)
					.setMaxValue((float) PROGRESSBAR_MAX_VALUE)
					.build()
				);
	}

	@Override
	public String getModelName()
	{
		return "TestModel";
	}

}
