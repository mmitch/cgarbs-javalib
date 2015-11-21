/*
 * Copyright 2015 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.glue.type;

import de.cgarbs.lib.data.DataAttribute;
import de.cgarbs.lib.data.type.IntegerAttribute;
import de.cgarbs.lib.glue.TextFieldBinding;
import de.cgarbs.lib.i18n.Resource;

/**
 *
 * @author Christian Garbs <mitch@cgarbs.de>
 * @since 0.2.0
 */
public class IntegerBinding extends TextFieldBinding
{
	public IntegerBinding(final DataAttribute attribute, final Resource resource, final String label)
	{
		super(attribute, resource, label);
	}

	@Override
	public void syncToView()
	{
		setViewValue(((IntegerAttribute)attribute).getFormattedValue());
	}
}
