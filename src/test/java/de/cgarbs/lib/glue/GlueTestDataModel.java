package de.cgarbs.lib.glue;

import de.cgarbs.lib.data.DataModel;
import de.cgarbs.lib.data.type.BooleanAttribute;
import de.cgarbs.lib.data.type.ColorAttribute;
import de.cgarbs.lib.data.type.FloatAttribute;
import de.cgarbs.lib.data.type.IntAttribute;
import de.cgarbs.lib.data.type.StringAttribute;
import de.cgarbs.lib.exception.DataException;
import de.cgarbs.lib.i18n.Resource;

public class GlueTestDataModel extends DataModel
{
	private static final long serialVersionUID = 1L;

	public static final String STRING_ATTRIBUTE = "StringAttribute";

	public static final String BOOLEAN_ATTRIBUTE = "BooleanAttribute";

	public static final String INTEGER_ATTRIBUTE = "IntegerAttribute";

	public static final String FLOAT_ATTRIBUTE = "FloatAttribute";
	public static final int FLOAT_DECIMALS = 2;

	public static final String COLOR_ATTRIBUTE = "ColorAttribute";

	public static final String IMAGE_ATTRIBUTE = "Image Attribute";

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
				IntAttribute.builder()
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
				StringAttribute.builder()
					.build()
				);
	}

	@Override
	public String getModelName()
	{
		return "TestModel";
	}

}
