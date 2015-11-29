/*
 * Copyright 2015 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib;

import java.io.File;
import java.util.regex.Pattern;

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
	 * @see #splitPath(String) splitPath() for the reverse operation
	 */
	public static String createPathFrom(final String... elements)
	{
		return StringUtils.join(File.separator, elements);
	}

	/**
	 * Creates a new {@link File} from the concatination
	 * of several path elements and an optional
	 * final filename.
	 *
	 * @param elements The path elements to concatenate.  Null values are skipped.
	 * @return a new {@link File} pointing to the concatenated path
	 * @see #splitFile(File) splitFile()for the reverse operation
	 */
	public static File createFileFrom(final String... elements)
	{
		return new File(createPathFrom(elements));
	}

	/**
	 * Splits a String containing a path and an optional final filename
	 * separated by {@link File#separatorChar} into individual path elements.
	 *
	 *
	 * @param path The path to split. A null results in an empty array.
	 * @return an array consisting of the individual elements of the path
	 * @see #createPathFrom(String...) createPathFrom() for the reverse operation
	 */
	public static String[] splitPath(final String path)
	{
		if (path == null)
		{
			return null;
		}
		return path.split(Pattern.quote(File.separator), -1);
	}

	/**
	 * Splits the a path and an optional final filename of a File
	 * into individual path elements.
	 *
	 *
	 * @param file The File whose path should be split. A null results in an empty array.
	 * @return an array consisting of the individual elements of the File's path
	 * @see #createFileFrom(String...) createFileFrom() for the reverse operation
	 */
	public static String[] splitFile(final File file)
	{
		if (file == null)
		{
			return null;
		}
		return splitPath(file.getPath());
	}
}
