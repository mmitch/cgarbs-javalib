/*
 * Copyright 2014 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.glue;

import javax.swing.JComponent;
import javax.swing.JTextField;

import de.cgarbs.lib.data.DataAttribute;
import de.cgarbs.lib.i18n.Resource;

public abstract class TextFieldBinding extends Binding
{

	protected JTextField jTextField;

	public TextFieldBinding(final DataAttribute attribute, final Resource resource, final String label)
	{
		super(attribute, resource, label);
	}

	@Override
	protected JComponent createDataEntryComponent()
	{
		return jTextField = new JTextField();
	}

	@Override
	public void setViewValue(final Object value)
	{
		super.setViewValue(value);

		final String s = (String) value;

		if (s == null)
		{
			jTextField.setText("");
		}
		else
		{
			jTextField.setText(s);
		}
	}

	@Override
	public Object getViewValue()
	{
		return jTextField.getText();
	}
}