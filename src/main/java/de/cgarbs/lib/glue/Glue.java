/*
 * Copyright 2014 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.glue;

import java.util.ArrayList;
import java.util.List;

import de.cgarbs.lib.data.DataAttribute;
import de.cgarbs.lib.data.DataModel;
import de.cgarbs.lib.data.type.BooleanAttribute;
import de.cgarbs.lib.data.type.ColorAttribute;
import de.cgarbs.lib.data.type.FileAttribute;
import de.cgarbs.lib.data.type.FloatAttribute;
import de.cgarbs.lib.data.type.IntegerAttribute;
import de.cgarbs.lib.data.type.StringAttribute;
import de.cgarbs.lib.exception.DataException;
import de.cgarbs.lib.exception.GlueException;
import de.cgarbs.lib.exception.ValidationError;
import de.cgarbs.lib.exception.ValidationErrorList;
import de.cgarbs.lib.glue.type.BooleanBinding;
import de.cgarbs.lib.glue.type.ColorBinding;
import de.cgarbs.lib.glue.type.FileBinding;
import de.cgarbs.lib.glue.type.FloatBinding;
import de.cgarbs.lib.glue.type.ImageBinding;
import de.cgarbs.lib.glue.type.IntegerBinding;
import de.cgarbs.lib.glue.type.ProgressBarBinding;
import de.cgarbs.lib.glue.type.StringBinding;
import de.cgarbs.lib.i18n.Resource;
import de.cgarbs.lib.ui.AutoLayout;

public class Glue<T extends DataModel>
{
	final List<Binding> bindings = new ArrayList<Binding>();
	final T model;

	public Glue(final T model)
	{
		this.model = model;
	}

	public Binding addBinding(final String key) throws GlueException, DataException
	{
		return addBinding(key, (String)null);
	}

	public Binding addBinding(final String key, final String label) throws GlueException, DataException
	{
		// FIXME catch duplicate bindings (same key)
		final DataAttribute attribute = model.getAttribute(key);
		if (attribute instanceof StringAttribute)
		{
			return addBinding(key, StringBinding.class, label);
		}
		else if (attribute instanceof IntegerAttribute)
		{
			return addBinding(key, IntegerBinding.class, label);
		}
		else if (attribute instanceof FloatAttribute)
		{
			return addBinding(key, FloatBinding.class, label);
		}
		else if (attribute instanceof BooleanAttribute)
		{
			return addBinding(key, BooleanBinding.class, label);
		}
		else if (attribute instanceof FileAttribute)
		{
			return addBinding(key, FileBinding.class, label);
		}
		else if (attribute instanceof ColorAttribute)
		{
			return addBinding(key, ColorBinding.class, label);
		}
		else
		{
			throw new GlueException(
					GlueException.ERROR.BINDING_NOT_IMPLEMENTED,
					"no binding exists for " + attribute.getClass()
					);
		}
	}

	// FIXME restrict clazz to subtype of Binding?
	public Binding addBinding(final String key, final Class<? extends Binding> clazz) throws GlueException, DataException
	{
		return addBinding(key, clazz, null);
	}

	// FIXME restrict clazz to subtype of Binding?
	public Binding addBinding(final String key, final Class<? extends Binding> clazz, final String label) throws GlueException, DataException
	{
		// FIXME catch duplicate bindings (same key)
		final Binding binding;
		final DataAttribute attribute = model.getAttribute(key);
		final Resource resource = model.getResource();
		// FIXME catch incompatible attribute classes!
		if (StringBinding.class.equals(clazz))
		{
			binding = new StringBinding(attribute, resource, label);
		}
		else if (IntegerBinding.class.equals(clazz))
		{
			binding = new IntegerBinding(attribute, resource, label);
		}
		else if (FloatBinding.class.equals(clazz))
		{
			binding = new FloatBinding(attribute, resource, label);
		}
		else if (BooleanBinding.class.equals(clazz))
		{
			binding = new BooleanBinding(attribute, resource, label);
		}
		else if (FileBinding.class.equals(clazz))
		{
			binding = new FileBinding(attribute, resource, label);
		}
		else if (ColorBinding.class.equals(clazz))
		{
			binding = new ColorBinding(attribute, resource, label);
		}
		else if (ImageBinding.class.equals(clazz))
		{
			binding = new ImageBinding(attribute, resource, label);
		}
		else if (ProgressBarBinding.class.equals(clazz))
		{
			binding = new ProgressBarBinding(attribute, resource, label);
		}
		else
		{
			throw new GlueException(
					GlueException.ERROR.BINDING_NOT_IMPLEMENTED,
					"no binding defined for " + clazz
					);
		}
		bindings.add(binding);
		return binding;
	}

	public void syncToView()
	{
		for (final Binding binding: bindings)
		{
			binding.syncToView();
		}
	}

	public void syncToModel() throws DataException
	{
		for (final Binding binding: bindings)
		{
			binding.syncToModel();
		}
	}

	// FIXME check if syncToModel()+getModel() should be combined

	public T getModel()
	{
		return model;
	}

	public void validate() throws ValidationErrorList
	{
		// FIXME make focus-to-error optional via boolean?

		// only scroll to first error
		boolean scrolled = false;

		final ValidationErrorList ex = new ValidationErrorList();
		for (final Binding binding: bindings)
		{
			try
			{
				binding.validate();
			}
			catch (ValidationError e)
			{
				if (! scrolled)
				{
					AutoLayout.showComponent(binding.getJData());
					scrolled = true;
				}
				ex.addValidationError(binding, e);
			}
		}

		try
		{
			model.additionalValidation();
		}
		catch (ValidationError e)
		{
			// FIXME errors not shown on GUI
			ex.addValidationError(model, e);
		}

		if (! ex.isEmpty())
		{
			throw ex;
		}
	}

	public Binding addListener(final Binding base_binding, final Class<? extends Binding> clazz, final String label) throws GlueException, DataException
	{
		final Binding new_binding = addBinding(base_binding.attribute.getKey(), clazz, label);
		base_binding.addListeningBinding(new_binding);
		return new_binding;
	}

	public boolean isDirty()
	{
		return model.isDirty();
	}

	public void resetDirty()
	{
		model.resetDirty();
	}
}
