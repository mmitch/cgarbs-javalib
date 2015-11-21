/*
 * Copyright 2014 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.ui.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import de.cgarbs.lib.exception.GlueException;
import de.cgarbs.lib.ui.Element;
import de.cgarbs.lib.ui.Group;

public abstract class DualColumnTabbedLayout extends SimpleTabbedLayout
{
	// Builder pattern start
	public static class Builder extends SimpleTabbedLayout.Builder
	{

		@Override
		public Container build() throws GlueException
		{
			final JTabbedPane component = new JTabbedPane();

			for (final Group group: groups)
			{
				final JPanel tab = new JPanel();
				tab.setLayout(new GridBagLayout());
				int line = 0;
				int col = 0;

				for (final Element element: group.getElements())
				{
					element.addToComponent(tab,  col,  line);

					col+=2;
					if (col > 2)
					{
						col = 0;
						line++;
					}
				}

				final Component remainder = Box.createGlue();
				final GridBagConstraints gbc = new GridBagConstraints();
				gbc.gridx = 0;
				gbc.gridy = line;
				gbc.gridwidth = 2;
				gbc.gridheight = 1;
				gbc.weightx = 1;
				gbc.weighty = 1;
				gbc.fill = GridBagConstraints.VERTICAL;
				tab.add(remainder, gbc);

				component.add(group.getTitle(), wrapInScrollPane(tab));
			}

			return component;
		}
	}

	public static Builder builder()
	{
		return new Builder();
	}

	// Builder pattern end

}
