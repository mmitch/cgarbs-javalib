/*
 * Copyright 2014 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.glue.type;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.border.Border;

import de.cgarbs.lib.data.DataAttribute;
import de.cgarbs.lib.glue.Binding;
import de.cgarbs.lib.i18n.Resource;

public class ImageBinding extends Binding
{
	protected JLabel jLabel;
	protected File file;

	protected final static Border border = BorderFactory.createEmptyBorder(2, 2, 2, 2);

	public ImageBinding(final DataAttribute attribute, final Resource resource, final String label)
	{
		super(attribute, resource, label);
	}

	@Override
	public Object getViewValue()
	{
		return file;
	}

	@Override
	protected JComponent createDataEntryComponent()
	{
		jLabel = new JLabel();
		jLabel.setBorder(border);
		return jLabel;
	}

	@Override
	public void setViewValue(Object value)
	{
		super.setViewValue(value);

		file = (File) value;

		if (file == null)
		{
			jLabel.setIcon(null);
		}
		else
		{
			try
			{
				final BufferedImage img = ImageIO.read(file);
				if (img == null)
				{
					jLabel.setIcon(null);
				}
				else
				{
					jLabel.setIcon(new ImageIcon(img));
				}
			}
			catch (IOException e)
			{
				// FIXME empty try-catch-block!
			}
		}
	}

}
