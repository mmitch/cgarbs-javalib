package de.cgarbs.lib.data.type;

import de.cgarbs.lib.data.DataModel;
import de.cgarbs.lib.i18n.Resource;

public abstract class BaseTestDataModel extends DataModel
{
	public static final String TEST_ATTRIBUTE_NAME = "TEST_ATTRIBUTE_NAME";

	public BaseTestDataModel(Resource resource)
	{
		super(resource);
	}
}
