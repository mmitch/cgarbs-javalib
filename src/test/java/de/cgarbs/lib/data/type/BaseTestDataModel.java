/*
 * Copyright 2015 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.data.type;

import de.cgarbs.lib.data.DataModel;
import de.cgarbs.lib.i18n.Resource;

public abstract class BaseTestDataModel extends DataModel
{
	private static final long serialVersionUID = 1L;

	public static final String TEST_ATTRIBUTE_1 = "TEST_ATTRIBUTE_1";
	public static final String TEST_ATTRIBUTE_2 = "TEST_ATTRIBUTE_2";

	public BaseTestDataModel(Resource resource)
	{
		super(resource);
	}
}
