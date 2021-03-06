/*
 * Copyright 2014 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.glue.type;

import javax.swing.JComponent;
import javax.swing.JProgressBar;

import de.cgarbs.lib.data.DataAttribute;
import de.cgarbs.lib.data.NumberAttribute;
import de.cgarbs.lib.glue.Binding;
import de.cgarbs.lib.i18n.Resource;

public class ProgressBarBinding extends Binding
{
	protected JProgressBar jProgressBar;

//	protected final static Border border = BorderFactory.createEmptyBorder(2, 2, 2, 2);

	public ProgressBarBinding(final DataAttribute attribute, final Resource resource, final String label)
	{
		super(attribute, resource, label);
	}

	@Override
	public Object getViewValue()
	{
		return jProgressBar.getValue();
	}

	@Override
	protected JComponent createDataEntryComponent()
	{
		final NumberAttribute i = (NumberAttribute)attribute;
		jProgressBar = new JProgressBar();
		jProgressBar.setMinimum(i.getMinValue().intValue());
		jProgressBar.setMaximum(i.getMaxValue().intValue());
		return jProgressBar;
	}

	@Override
	public void setViewValue(final Object value)
	{
		super.setViewValue(value);

		final Number i = (Number) value;

		if (i != null)
		{
			jProgressBar.setValue(i.intValue());
		}
	}
}
