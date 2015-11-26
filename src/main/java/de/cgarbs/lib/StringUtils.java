/*
 * Copyright 2015 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib;

/**
 * String-related utility functions.
 *
 * @author Christian Garbs <mitch@cgarbs.de>
 * @since 0.3.0
 *
 */
public class StringUtils
{
	/**
	 * Concatenates multiple strings using a common delimiter.
	 * @param delimiter The delimiter used to separate the given strings.
	 *                  Both <code>null</code> or an empty string result in
	 *                  no delimiter between adjacent strings.
	 * @param parts The strings to concatenate. Null values will be skipped
	 *              together with their delimiter.
	 * @return
	 */
	public static String join(String delimiter, final String... strings)
	{
		final StringBuilder result = new StringBuilder();
		boolean resultEmpty = true;

		if (delimiter == null)
		{
			delimiter = "";
		}

		if (strings == null)
		{
			return "";
		}

		for (final String currentString: strings)
		{
			if (currentString != null)
			{
				if (! resultEmpty)
				{
					result.append(delimiter);
				}
				result.append(currentString);
				resultEmpty = false;
			}
		}

		return result.toString();
	}
}
