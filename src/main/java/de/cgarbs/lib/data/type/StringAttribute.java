/*
 * Copyright 2014 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.data.type;

import de.cgarbs.lib.data.DataAttribute;
import de.cgarbs.lib.exception.DataException;
import de.cgarbs.lib.exception.ValidationError;

public class StringAttribute extends DataAttribute
{
	private String value;

	private final Integer minLength;
	private final Integer maxLength;

	// Builder pattern start
	public static class Builder extends DataAttribute.Builder<Builder>
	{
		public StringAttribute build()
		{
			return new StringAttribute(this);
		}

		@Override
		protected Builder getThis()
		{
			return this;
		}

		public Builder setMinLength(final Integer minLength)
		{
			this.minLength = minLength;
			return this;
		}

		public Builder setMaxLength(final Integer maxLength)
		{
			this.maxLength = maxLength;
			return this;
		}

		private Integer minLength;
		private Integer maxLength;
	}

	public static Builder builder()
	{
		return new Builder();
	}

	private StringAttribute(final Builder builder)
	{
		super(builder);
		minLength = builder.minLength;
		maxLength = builder.maxLength;
	}
	// Builder pattern end

	@Override
	public Object getValue()
	{
		return value;
	}

	public void validate(final Object value) throws ValidationError
	{
		final String s = (String) convertType(value);

		super.validate(s);

		if (s != null)
		{
			int length = s.length();

			if (minLength != null && length < minLength)
			{
				throw new ValidationError(
						this,
						"string too short: " + length + " < " + minLength,
						ValidationError.ERROR.STRING_TOO_SHORT,
						String.valueOf(length), String.valueOf(minLength)
						);
			}

			if (maxLength != null && length > maxLength)
			{
				throw new ValidationError(
						this,
						"string too long: " + length + " > " + maxLength,
						ValidationError.ERROR.STRING_TOO_LONG,
						String.valueOf(length), String.valueOf(maxLength)
						);
			}
		}
	}

	@Override
	protected void setValueInternal(final Object newValue) throws DataException
	{
		value = (String) newValue;
	}

	@Override
	protected Object convertType(final Object newValue) throws ValidationError
	{
		if (newValue == null)
		{
			return null;
		}
		else if (newValue instanceof String)
		{
			return (String)newValue; // Strings are immutable, this is OK
		}
		else
		{
			return String.valueOf(newValue);
		}
	}

	/**
	 * @since 0.2.0
	 */
	public Integer getMinLength()
	{
		return minLength;
	}

	/**
	 * @since 0.2.0
	 */
	public Integer getMaxLength()
	{
		return maxLength;
	}

}
