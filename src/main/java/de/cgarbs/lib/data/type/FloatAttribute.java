/*
 * Copyright 2014 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.data.type;

import de.cgarbs.lib.data.NumberAttribute;

public class FloatAttribute extends NumberAttribute
{
	private final int minDecimals;
	private final int maxDecimals;

	// Builder pattern start
	public static class Builder extends NumberAttribute.Builder<Float>
	{
		@Override
		protected Builder getThis()
		{
			return this;
		}

		public FloatAttribute build()
		{
			return new FloatAttribute(this);
		}

		public Builder setMinDecimals(int minDecimals)
		{
			this.minDecimals = minDecimals;
			return this;
		}

		public Builder setMaxDecimals(int maxDecimals)
		{
			this.maxDecimals = maxDecimals;
			return this;
		}

		public Builder setDecimals(int decimals)
		{
			return setMinDecimals(decimals).setMaxDecimals(decimals);
		}

		private int minDecimals;
		private int maxDecimals;
	}

	public static Builder builder()
	{
		return new Builder();
	}

	private FloatAttribute(final Builder builder)
	{
		super(builder);
		minDecimals = builder.minDecimals;
		maxDecimals = builder.maxDecimals;
		numberFormat.setMinimumFractionDigits(minDecimals);
		numberFormat.setMaximumFractionDigits(maxDecimals);
	}
	// Builder pattern end


	@Override
	public Object getValue()
	{
		if (value == null)
		{
			return null;
		}
		return value.floatValue();
	}

	/**
	 * @since 0.2.0
	 */
	public int getMinDecimals()
	{
		return minDecimals;
	}

	/**
	 * @since 0.2.0
	 */
	public int getMaxDecimals()
	{
		return maxDecimals;
	}
}
