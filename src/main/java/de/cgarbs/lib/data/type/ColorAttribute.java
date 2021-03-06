/*
 * Copyright 2014 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.data.type;

import java.awt.Color;

import de.cgarbs.lib.data.DataAttribute;
import de.cgarbs.lib.exception.DataException;
import de.cgarbs.lib.exception.ValidationError;

public class ColorAttribute extends DataAttribute
{
	private Color value;

	// Builder pattern start
	public static class Builder extends DataAttribute.Builder<Builder>
	{
		public ColorAttribute build()
		{
			return new ColorAttribute(this);
		}

		@Override
		protected Builder getThis()
		{
			return this;
		}
	}

	public static Builder builder()
	{
		return new Builder();
	}

	private ColorAttribute(final Builder builder)
	{
		super(builder);
	}
	// Builder pattern end

	@Override
	public Object getValue()
	{
		return value;
	}

	@Override
	protected void setValueInternal(final Object newValue) throws DataException
	{
		value = (Color) newValue;
	}

	@Override
	protected Object convertType(final Object newValue) throws ValidationError
	{
		if (newValue == null)
		{
			return null;
		}
		else if (newValue instanceof Color)
		{
			final Color color = (Color)newValue;
			return new Color(
					color.getRed(),
					color.getGreen(),
					color.getBlue(),
					color.getAlpha()
					);
		}
		else
		{
			throw new ValidationError(
					this,
					"wrong type: " + newValue.getClass() + " != " + Color.class,
					ValidationError.ERROR.NUMBER_FORMAT // FIXME this is not number format error!
					);
		}
	}
}
