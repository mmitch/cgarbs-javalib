package de.cgarbs.lib.data;

import de.cgarbs.lib.exception.DataException;

public abstract class NumberAttribute extends DataAttribute
{
	protected Number value;

	private final Number minValue;
	private final Number maxValue;
	
	// Builder pattern start
	public abstract static class Builder<N extends Number> extends DataAttribute.Builder<Builder<N>>
	{
		public Builder<N> setMinValue(N minValue)
		{
			this.minValue = minValue;
			return this;
		}
		public Builder<N> setMaxValue(N maxValue)
		{
			this.maxValue = maxValue;
			return this;
		}
		
		private Number minValue;
		private Number maxValue;
	}
	
	protected NumberAttribute(Builder<?> builder)
	{
		super(builder);
		minValue = builder.minValue;
		maxValue = builder.maxValue;
	}
	
	// Builder pattern end

	@Override
	public Object getValue() throws DataException
	{
		return value;
	}

	@Override
	protected void validate() throws DataException
	{
		super.validate(value);
		
		double dvalue = value.doubleValue();
		
		if (minValue != null && dvalue < minValue.doubleValue())
		{
			throw new DataException(
					DataException.ERROR.INVALID_VALUE,
					"value too small: " + value + " < " + minValue
					);
		}
		if (maxValue != null && dvalue > maxValue.doubleValue())
		{
			throw new DataException(
					DataException.ERROR.INVALID_VALUE,
					"value too big: " + value + " > " + maxValue
					);
		}
	}

}
