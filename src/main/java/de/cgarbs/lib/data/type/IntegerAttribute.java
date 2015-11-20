/*
 * Copyright 2014 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.data.type;

import java.text.NumberFormat;

import de.cgarbs.lib.data.NumberAttribute;

/**
 *
 * @author Christian Garbs <mitch@cgarbs.de>
 * @since 0.2.0
 */
public class IntegerAttribute extends NumberAttribute
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	// Builder pattern start
	public static class Builder extends NumberAttribute.Builder<Integer>
	{
		public IntegerAttribute build()
		{
			return new IntegerAttribute(this);
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

	protected IntegerAttribute(Builder builder)
	{
		super(builder);
		numberFormat = NumberFormat.getIntegerInstance();
	}
	// Builder pattern end

	@Override
	public Object getValue()
	{
		if (value == null)
		{
			return null;
		}
		return value.intValue();
	}
}
