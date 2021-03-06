/*
 * Copyright 2014 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.glue.type;

import javax.swing.JCheckBox;
import javax.swing.JComponent;

import de.cgarbs.lib.data.DataAttribute;
import de.cgarbs.lib.glue.Binding;
import de.cgarbs.lib.i18n.Resource;

public class BooleanBinding extends Binding
{
	protected JCheckBox jCheckBox;

	public BooleanBinding(final DataAttribute attribute, final Resource resource, final String label)
	{
		super(attribute, resource, label);
	}

	@Override
	public Object getViewValue()
	{
		return jCheckBox.isSelected();
	}

	@Override
	protected JComponent createDataEntryComponent()
	{
		jCheckBox = new JCheckBox();
		return jCheckBox;
	}

	@Override
	public void setViewValue(final Object value)
	{
		super.setViewValue(value);

		final Boolean b = (Boolean) value;
		if (b == null)
		{
			jCheckBox.setSelected(false);
		}
		else
		{
			jCheckBox.setSelected(b);
		}
	}
}
