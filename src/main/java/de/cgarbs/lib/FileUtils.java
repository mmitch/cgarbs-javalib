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
	 * @param elements the path elements to concatenate
	 * @return the concatenated path
	 */
	public static String createPathFrom(final String... elements )
	{
		final StringBuilder path = new StringBuilder();
		if (elements.length > 0)
		{
			path.append(elements[0]);
		}
		for (int i=1; i<elements.length; i++)
		{
			path.append(File.separatorChar);
			path.append(elements[i]);
		}
		return path.toString();
	}

	/**
	 * Creates a new {@link File} from the concatination
	 * Cof several path elements and an optional
	 * final filename.
	 *
	 * @param elements the path elements to concatenate
	 * @return a new {@link File} pointing to the concatenated path
	 */
	public static File createFileFrom(final String... elements )
	{
		return new File(createPathFrom(elements));
	}

}
