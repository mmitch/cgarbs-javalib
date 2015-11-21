/*
 * Copyright 2015 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.ui;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JComponent;

import org.junit.Before;
import org.junit.Test;

import de.cgarbs.lib.exception.DataException;
import de.cgarbs.lib.exception.GlueException;
import de.cgarbs.lib.ui.layout.SimpleTabbedLayout;
import de.cgarbs.lib.ui.layout.SimpleVerticalLayout;

public class AutoLayoutTest extends UiTestBase
{
	@Before
	public void setUp() throws DataException, GlueException
	{
		super.setUp();

		container = SimpleTabbedLayout.builder()
				.startNextGroup(GROUP_1)
				.addAttribute(binding3)
				.addAttribute(binding4)
				.startNextGroup(GROUP_2)
				.addAttribute(binding5)
				.startNextGroup(GROUP_3)
				.addAttribute(binding6)
				.build();
	}

	@Test
	public void showComponent() throws GlueException
	{
		AutoLayout.showComponent(binding3.getJData());
		AutoLayout.showComponent(binding3.getJLabel());

		AutoLayout.showComponent(binding4.getJData());
		AutoLayout.showComponent(binding4.getJLabel());

		AutoLayout.showComponent(binding5.getJData());
		AutoLayout.showComponent(binding5.getJLabel());

		AutoLayout.showComponent(binding6.getJData());
		AutoLayout.showComponent(binding6.getJLabel());

		container = SimpleVerticalLayout.builder()
				.addAttribute(binding1)
				.addAttribute(binding2)
				.build();

		AutoLayout.showComponent(binding1.getJData());
		AutoLayout.showComponent(binding1.getJLabel());

		AutoLayout.showComponent(binding2.getJData());
		AutoLayout.showComponent(binding2.getJLabel());
	}

	@Test
	public void showComponentRecursive() throws GlueException
	{
		showComponentRecursive(container);
	}

	private void showComponentRecursive(final Container container)
	{
		for (final Component c: container.getComponents())
		{
			if (c instanceof JComponent)
			{
				AutoLayout.showComponent((JComponent) c);
			}
			if (c instanceof Container)
			{
				showComponentRecursive((Container) c);
			}
		}
	}

}
