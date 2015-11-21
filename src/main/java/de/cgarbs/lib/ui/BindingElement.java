/*
 * Copyright 2014 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.ui;

import javax.swing.JComponent;

import de.cgarbs.lib.glue.Binding;

public class BindingElement extends Element
{
	protected final Binding binding;

	public BindingElement(final Binding binding)
	{
		this.binding = binding;
	}

	@Override
	public void addToComponent(final JComponent component, int x, int y)
	{
		component.add(binding.getJLabel(), pos_label(x,   y, 1, 1));
		component.add(binding.getJData(),  pos_value(x+1, y, 1, 1));
	}
}
