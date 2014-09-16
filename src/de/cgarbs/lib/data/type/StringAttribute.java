package de.cgarbs.lib.data.type;

import de.cgarbs.lib.data.DataAttribute;
import de.cgarbs.lib.exception.DataException;
import de.cgarbs.lib.exception.ValidationError;

public class StringAttribute extends DataAttribute
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String value;

	private transient final Integer minLength;
	private transient final Integer maxLength;

	// Builder pattern start
	public static class Builder extends DataAttribute.Builder<Builder>
	{
		public StringAttribute build()
		{
			return new StringAttribute(this);
		}
		public Builder setMinLength(Integer minLength)
		{
			this.minLength = minLength;
			return this;
		}
		public Builder setMaxLength(Integer maxLength)
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

	private StringAttribute(Builder builder)
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

	public void validate(Object value) throws ValidationError
	{
		String s = (String) convertType(value);

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
	protected void setValueInternal(Object newValue) throws DataException
	{
		value = (String) newValue;
	}

	@Override
	protected Object convertType(Object newValue) throws ValidationError
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

}
