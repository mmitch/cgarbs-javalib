/*
 * Copyright 2014, 2020 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.glue.type;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JPanel;

import de.cgarbs.lib.data.DataAttribute;
import de.cgarbs.lib.glue.Binding;
import de.cgarbs.lib.i18n.Resource;

public class ColorBinding extends Binding
{
	protected JPanel jPanel;
	protected JButton jButton;

	public ColorBinding(final DataAttribute attribute, final Resource resource, final String label)
	{
		super(attribute, resource, label);
	}

	@Override
	public Object getViewValue()
	{
		return jPanel.getBackground();
	}

	@Override
	protected JComponent createDataEntryComponent()
	{
		jPanel = new JPanel();

		jButton = new JButton("...");
		jButton.setToolTipText(R.get("TIT_COLORCHOOSER", txtLabel));

		jButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				final Color newColor = JColorChooser.showDialog(
						jButton,
						R.get("TIT_COLORCHOOSER", txtLabel),
						jPanel.getBackground());
				if (newColor != null)
				{
					setViewValue(newColor);
				}
			}
		});

		final JPanel combiPanel = new JPanel();
		combiPanel.setLayout(new GridBagLayout());

		final GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(3, 3, 3, 3);
		gbc.weightx = 1;
		gbc.weighty = 1;
		combiPanel.add(jPanel, gbc);

		gbc.gridx = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = null;
		gbc.weightx = 0.2;
		combiPanel.add(jButton);
		return combiPanel;
	}

	@Override
	public void setViewValue(Object value)
	{
		super.setViewValue(value);

		final Color c = (Color) value;
		jPanel.setBackground(c);
	}
}
