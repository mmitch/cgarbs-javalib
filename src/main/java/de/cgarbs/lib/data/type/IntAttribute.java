package de.cgarbs.lib.data.type;

import java.text.NumberFormat;

/**
 *
 * @author Christian Garbs <mitch@cgarbs.de>
 * @since 0.1.0
 * @deprecated use {@link IntegerAttribute} instead
 *
 */
public class IntAttribute extends IntegerAttribute
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	// Builder pattern start
	public static class Builder extends IntegerAttribute.Builder
	{
		public IntAttribute build()
		{
			return new IntAttribute(this);
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

	protected IntAttribute(Builder builder)
	{
		super(builder);
		numberFormat = NumberFormat.getIntegerInstance();
	}
	// Builder pattern end

}
