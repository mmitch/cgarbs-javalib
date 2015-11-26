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

	/**
	 * Truncates a string to a given length if it exceeds the length.
	 *
	 * @param originalString The string to be truncated.  A null value is returned unchanged..
	 * @param maxChars The number of characters to keep.  Input strings shorter than this
	 *                 length are returned unchanged.  Values lower than <code>1</code> result
	 *                 in an empty string.
	 * @return The string truncated to the given length.
	 */
	public static String truncate(final String originalString, final int maxChars)
	{
		if (originalString == null)
		{
			return null;
		}

		if (maxChars < 1)
		{
			return "";
		}

		if (maxChars >= originalString.length())
		{
			return originalString;
		}

		return originalString.substring(0, maxChars);
	}
}
