/*
 * Copyright 2014 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.data;

import de.cgarbs.lib.exception.DataException;
import de.cgarbs.lib.exception.ValidationError;

abstract public class DataAttribute
{
	private final boolean nullable;
	private String key;
	private DataModel model;
	protected Object cleanValue;

	abstract public Object getValue();
	abstract protected void setValueInternal(Object newValue) throws DataException;
	abstract protected Object convertType(Object newValue) throws ValidationError;

	// Builder pattern start
	public abstract static class Builder<T extends Builder<?>>
	{
		public abstract DataAttribute build();

		public T setNullable(final Boolean nullable)
		{
			this.nullable = nullable;
			return getThis();
		}

		protected abstract T getThis();

		private boolean nullable = true;
	};

	protected DataAttribute(final Builder<?> builder)
	{
		this.nullable = builder.nullable;
	}
	// Builder pattern end

	public final void validate() throws ValidationError
	{
		validate(getValue());
	}

	public void validate(final Object value) throws ValidationError // FIXME rename parameter!
	{
		if (! nullable && value == null)
		{
			throw new ValidationError(
					this,
					"null not allowed",
					ValidationError.ERROR.NULL_NOT_ALLOWED
					);
		}
	}

	public String getAttributeName()
	{
		return getModel().getModelName() + "." + getKey();
	}

	void setKey(String key)
	{
		this.key = key;
	}

	public String getKey()
	{
		return key;
	}

	void setModel(final DataModel model)
	{
		this.model = model;
	}

	public DataModel getModel()
	{
		return model;
	}

	public void resetDirty()
	{
		cleanValue = getValue();
	}

	public boolean isDirty()
	{
		if (getValue() == null)
		{
			return cleanValue != null;
		}
		return ! getValue().equals(cleanValue);
	}

	public final void setValue(final Object newValue) throws DataException
	{
		try
		{
			setValueInternal(convertType(newValue));
		}
		catch (ValidationError e)
		{
			throw new DataException(
					DataException.ERROR.INVALID_VALUE,
					e
					);
		}
	}

	/**
	 * @since 0.2.0
	 */
	public boolean isNullable()
	{
		return nullable;
	}
}
