/*
 * Copyright 2014 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.glue;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JLabel;

import de.cgarbs.lib.data.DataAttribute;
import de.cgarbs.lib.exception.DataException;
import de.cgarbs.lib.exception.ValidationError;
import de.cgarbs.lib.i18n.Resource;
import de.cgarbs.lib.ui.AutoLayout;

abstract public class Binding
{
	protected static Color ERROR_COLOR = Color.RED;

	protected DataAttribute attribute;
	protected JLabel     jLabel;
	protected JComponent jData;
	protected String     txtLabel;

	private Set<Binding> listeningBindings = new HashSet<Binding>(); // FIXME or List? -> check!
	private Color originalColor;

	protected static Resource R = new Resource(Binding.class);

	public abstract Object getViewValue();

	public JComponent getJLabel()
	{
		return jLabel;
	}

	public JComponent getJData()
	{
		return jData;
	}

	public Binding(DataAttribute attribute, Resource resource, String label)
	{
		this.attribute = attribute;
		if (label == null)
		{
			// default label from attribute resource
			this.txtLabel = resource._("LBL_"+attribute.getAttributeName());
		}
		else
		{
			// overwrite default
			this.txtLabel = label;
		}
		this.jLabel = createJLabel(txtLabel);
		this.jData  = createDataEntryComponent();
		originalColor = jData.getBackground();
	}

	abstract protected JComponent createDataEntryComponent(); // FIXME NAME cdec <-> JData


	private JLabel createJLabel(String label)
	{
		return new JLabel(label);
	}

	public void addListeningBinding(Binding binding)
	{
		listeningBindings.add(binding);
	}

	public void setViewValue(Object newValue)
	{
		for (Binding listeningBinding: listeningBindings)
		{
			listeningBinding.setViewValue(newValue);
		}
	}

	public void syncToView()
	{
		setViewValue(attribute.getValue());
	}

	public final void syncToModel() throws DataException
	{
		attribute.setValue(getViewValue());
	}

	protected void setValidationError(String text)
	{
		jData.setToolTipText(text);
		if (text == null)
		{
			jData.setBackground(originalColor);
		}
		else
		{
			jData.setBackground(ERROR_COLOR);
		}
	}

	public String getTxtLabel()
	{
		return txtLabel;
	}

	public DataAttribute getAttribute()
	{
		return attribute;
	}

	public void validate() throws ValidationError
	{
		try
		{
			attribute.validate(getViewValue());
			setValidationError(null);
		}
		catch (ValidationError e)
		{
			setValidationError(e.getLocalizedMessage());
			throw e;
		}
	}

	/**
	 * ensure that a component is visible within an AutoLayout
	 * This method will work upwards through all parent objects and
	 * scroll as needed.
	 *
	 * @param component the component to show
	 */
	public void show()
	{
		AutoLayout.showComponent(getJData());
	}

}
