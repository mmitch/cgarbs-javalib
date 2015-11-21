/*
 * Copyright 2014 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import de.cgarbs.lib.glue.Binding;

public class Group
{
	private final List<Element> elements = new ArrayList<Element>();
	private final String title;

	public Group(final String title)
	{
		this.title = title;
	}

	public void addBinding(final Binding binding)
	{
		elements.add(new BindingElement(binding));
	}

	public void addComponent(final JComponent component)
	{
		elements.add(new JComponentElement(component));
	}

	public String getTitle()
	{
		return this.title;
	}

	public List<Element> getElements()
	{
		return elements;
	}
}
