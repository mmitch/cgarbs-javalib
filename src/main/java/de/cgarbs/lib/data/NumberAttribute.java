/*
 * Copyright 2014 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.data;

import java.text.NumberFormat;
import java.text.ParseException;

import de.cgarbs.lib.exception.DataException;
import de.cgarbs.lib.exception.ValidationError;

public abstract class NumberAttribute extends DataAttribute
{
	protected Number value;

	private final Number minValue;
	private final Number maxValue;

	protected NumberFormat numberFormat;

	// Builder pattern start
	public abstract static class Builder<N extends Number> extends DataAttribute.Builder<Builder<N>>
	{
		public Builder<N> setMinValue(final N minValue)
		{
			this.minValue = minValue;
			return getThis();
		}
		public Builder<N> setMaxValue(final N maxValue)
		{
			this.maxValue = maxValue;
			return getThis();
		}

		private Number minValue;
		private Number maxValue;
	}

	protected NumberAttribute(final Builder<?> builder)
	{
		super(builder);
		minValue = builder.minValue;
		maxValue = builder.maxValue;
		numberFormat = NumberFormat.getInstance();
	}
	// Builder pattern end

	public void validate(final Object value) throws ValidationError
	{
		final Number n = (Number) convertType(value);

		super.validate(n);

		if (n != null)
		{
			double dvalue = n.doubleValue();

			if (minValue != null && dvalue < minValue.doubleValue())
			{
				throw new ValidationError(
						this,
						"value too small: " + value + " < " + minValue,
						ValidationError.ERROR.NUMBER_TOO_SMALL,
						String.valueOf(value), String.valueOf(minValue)
						);
			}

			if (maxValue != null && dvalue > maxValue.doubleValue())
			{
				throw new ValidationError(
						this,
						"value too big: " + value + " > " + maxValue,
						ValidationError.ERROR.NUMBER_TOO_LARGE,
						String.valueOf(value), String.valueOf(minValue)
						);
			}
		}
	}

	public String getFormattedValue()
	{
		if (getValue() == null)
		{
			return null; // FIXME or ""?
		}
		else
		{
			return numberFormat.format(getValue());
		}
	}

	public Number getMinValue()
	{
		return this.minValue;
	}

	public Number getMaxValue()
	{
		return this.maxValue;
	}

	@Override
	protected void setValueInternal(final Object newValue) throws DataException
	{
		value = (Number) newValue;
	}

	@Override
	protected Object convertType(final Object newValue) throws ValidationError
	{
		try
		{
			return TypeConverter.parseNumber(newValue);
		}
		catch (ParseException e)
		{
			throw new ValidationError(
					this,
					"can't parse " + newValue.getClass() + " as number: " + newValue.toString(),
					ValidationError.ERROR.NUMBER_FORMAT
					);
		}
	}
}
