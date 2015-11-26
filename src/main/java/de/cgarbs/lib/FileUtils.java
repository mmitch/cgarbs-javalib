/*
 * Copyright 2015 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib;

import java.io.File;

/**
 * File-related utility functions.
 *
 * @author Christian Garbs <mitch@cgarbs.de>
 * @since 0.3.0
 */
public abstract class FileUtils
{
	/**
	 * Concatenates several path elements and an optional
	 * final filename to a relative path separated by
	 * {@link File#separatorChar}.
	 *
	 * @param elements The path elements to concatenate.  Null values are skipped.
	 * @return the concatenated path
	 */
	public static String createPathFrom(final String... elements )
	{
		return StringUtils.join(File.separator, elements);
	}

	/**
	 * Creates a new {@link File} from the concatination
	 * Cof several path elements and an optional
	 * final filename.
	 *
	 * @param elements The path elements to concatenate.  Null values are skipped.
	 * @return a new {@link File} pointing to the concatenated path
	 */
	public static File createFileFrom(final String... elements )
	{
		return new File(createPathFrom(elements));
	}

}
