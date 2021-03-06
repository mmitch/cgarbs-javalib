/*
 * Copyright 2014 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.exception;

import java.util.ArrayList;
import java.util.List;

import de.cgarbs.lib.data.DataAttribute;
import de.cgarbs.lib.data.DataModel;
import de.cgarbs.lib.glue.Binding;

public class ValidationErrorList extends Exception
{
	// FIXME keep more internal state than just a list of String?
	// what about the original exceptions? convert to String only in getErrorList()?
	// add other, more detailled getters?

	private static final long serialVersionUID = 1L;

	private List<String> validationErrors = new ArrayList<String>();

	public void addValidationError(final String text)
	{
		validationErrors.add(text);
	}

	public void addValidationError(final String text, final ValidationError e)
	{
		addValidationError(text + ": " + e.getLocalizedMessage());
	}

	public void addValidationError(final DataModel model, final ValidationError e)
	{
		addValidationError(model.getModelName(), e);
	}

	public void addValidationError(final DataAttribute attribute, final ValidationError e)
	{
		addValidationError(attribute.getAttributeName(), e);
	}

	public void addValidationError(final Binding binding, final ValidationError e)
	{
		addValidationError(binding.getTxtLabel(), e);
	}

	public void addValidationError(final Binding binding, final DataException e)
	{
		addValidationError(binding.getTxtLabel(), new ValidationError(binding.getAttribute(), e));
	}

	public boolean isEmpty()
	{
		return validationErrors.isEmpty();
	}

	public String getMessage()
	{
		return validationErrors.size() + " validation errors";
	}

	public String getErrorList()
	{
		final StringBuilder sb = new StringBuilder();

		for (String s: validationErrors)
		{
			sb.append(s).append('\n');
		}

		return sb.toString();
	}

	/**
	 * @since 0.2.0
	 * @deprecated FIXME: interface still unfinished - beware, this method might change or vanish
	 */
	public List<String> getValidationErrors()
	{
		return validationErrors; // FIXME immetable? clone()?
	}
}
