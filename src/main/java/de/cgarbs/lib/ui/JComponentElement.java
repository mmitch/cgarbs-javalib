/*
 * Copyright 2014 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.ui;

import javax.swing.JComponent;

public class JComponentElement extends Element
{
	protected JComponent component;

	public JComponentElement(final JComponent component)
	{
		this.component = component;
	}

	@Override
	public void addToComponent(final JComponent component, int x, int y)
	{
		component.add(this.component, pos_value(x, y, 2, 1));
	}
}
