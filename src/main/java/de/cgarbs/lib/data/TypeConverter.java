/*
 * Copyright 2015 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.data;

import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Various type converters.
 *
 * @author Christian Garbs <mitch@cgarbs.de>
 * @since 0.3.0
 */
public abstract class TypeConverter
{
	/**
	 * try to parse value as a Number
	 *
	 * @param value the value to parse
	 * @return the parsed Number
	 * @throws ParseException when a String can't be parsed
	 */
	public static Number parseNumber(final Object value) throws ParseException
	{
		if (value == null)
		{
			return null;
		}
		else if (value instanceof Number)
		{
			return (Number) value;
		}
		else
		{
			final String s = String.valueOf(value);
			if (s.length() == 0)
			{
				return null;
			}
			else
			{
				return NumberFormat.getInstance().parse(s);
			}
		}
	}

	/**
	 * Tries to parse a value as an Integer
	 * @param value the value to parse
	 * @return the Integer or <code>null</code> if the conversion fails
	 */
	public static Integer parseAsInteger(final Object value)
	{
		try
		{
			final Number n = TypeConverter.parseNumber(value);
			if (n == null)
			{
				return null;
			}
			return n.intValue();
		}
		catch (ParseException e)
		{
			return null;
		}
	}

	/**
	 * Tries to parse a value as an int.
	 * @param value the value to parse
	 * @return the int value or 0 if the conversion fails or a null value is given
	 */
	public static int parseAsInt(final Object value)
	{
		final Integer i = parseAsInteger(value);
		if (i == null)
		{
			return 0;
		}
		return i.intValue();
	}
}
