/*
 * Copyright 2014 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.data;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import de.cgarbs.lib.exception.DataException;
import de.cgarbs.lib.exception.ValidationError;
import de.cgarbs.lib.exception.ValidationErrorList;
import de.cgarbs.lib.i18n.Resource;
import de.cgarbs.lib.json.JSONDataModel;

// FIXME: user Builder pattern, not abstract getModelName() and Resource constructor...
abstract public class DataModel
{
	private Map<String, DataAttribute> attributes = new LinkedHashMap<String, DataAttribute>();

	private transient final Resource resource;

	public DataModel(Resource resource)
	{
		this.resource = resource;
	}

	/**
	 * Creates a DataModel without a {@link Resource} file.
	 * Every key is mapped to itself, see {@link Resource#Resource()}.
	 *
	 * @since 0.3.0
	 */
	public DataModel()
	{
		this.resource = new Resource();
	}

	public Resource getResource()
	{
		return this.resource;
	}

	public void addAttribute(String key, DataAttribute attribute) throws DataException
	{
		if (attributes.containsKey(key))
		{
			throw new DataException(
					DataException.ERROR.DUPLICATE_ATTRIBUTE,
					key
					);
		}

		if (attribute.getModel() != null)
		{
			throw new DataException(
					DataException.ERROR.DUPLICATE_USAGE,
					getModelName()+"."+key + ", " + attribute.getAttributeName()
					);
		}
		attribute.setKey(key);
		attribute.setModel(this);
		attributes.put(key, attribute);
	}

	public void setValue(String key, Object value) throws DataException
	{
		checkForAttribute(key);
		try
		{
			attributes.get(key).setValue(value);
		}
		catch (DataException dex)
		{
			throw dex.prependMessage("attribute " + key);
		}
	}

	/**
	 * FIXME return Object -> not type safe!
	 */
	public Object getValue(String key) throws DataException
	{
		return getAttribute(key).getValue();
	}

	public DataAttribute getAttribute(String key) throws DataException
	{
		checkForAttribute(key);
		return attributes.get(key);
	}

	private void checkForAttribute(String key) throws DataException
	{
		if (!attributes.containsKey(key))
		{
			throw new DataException(
					DataException.ERROR.UNKNOWN_ATTRIBUTE,
					key
					);
		}
	}

	abstract public String getModelName();

	// FIXME -> toString() bauen!

	/**
	 * Writes a JSON representation of the current model state to a file.
	 * The file is overwritten if it already exists.
	 *
	 * @since 0.3.0
	 *
	 * @param file the file to write to
	 * @throws DataException either IO errors or errors during the JSON conversion
	 */
	public void writeToFile(File file) throws DataException
	{
		JSONDataModel.writeToFile(this, file);
	}

	public void readFromFile(File file) throws DataException
	{
		JSONDataModel.readFromFile(this, file);
	}

	public void validate() throws ValidationErrorList // FIXME: unneeded? remove?
	{
		ValidationErrorList ex = new ValidationErrorList();
		for (DataAttribute attribute: attributes.values())
		{
			try
			{
				attribute.validate();
			}
			catch (ValidationError e)
			{
				ex.addValidationError(attribute, e);
			}
		}

		try
		{
			additionalValidation();
		}
		catch (ValidationError e)
		{
			ex.addValidationError(this, e);
		}

		if (! ex.isEmpty())
		{
			throw ex;
		}
	}

	public void additionalValidation() throws ValidationError
	{
		// NOP, to be overwritten in anonymous subclasses
	}

	public Set<String> getAttributeKeys()
	{
		return attributes.keySet();
	}

	// FIXME retain dirty-flag in model or in view?
	// in model is easier for now, but needs a syncToModel() before evaluation
	// in view is more flexible - but is it worth the extra effort?
	public void resetDirty()
	{
		for (DataAttribute a: attributes.values())
		{
			a.resetDirty();
		}
	}

	public boolean isDirty()
	{
		for (DataAttribute a: attributes.values())
		{
			if (a.isDirty())
			{
				return true;
			}
		}
		return false;
	}
}
