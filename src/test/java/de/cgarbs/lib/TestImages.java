package de.cgarbs.lib;

import java.io.File;

public class TestImages
{
	private static final String GRADLE_RESOURCEPATH = createPathFrom(
			"src", "test", "resources"
			);

	private static final String PACKAGE_PATH = createPathFrom(
			"de", "cgarbs", "lib"
			);

	public static final String IMG_WHITE_PNG = createPathFrom(
			GRADLE_RESOURCEPATH,
			PACKAGE_PATH,
			"white.png"
			);

	public static final String IMG_BLACK_PNG = createPathFrom(
			GRADLE_RESOURCEPATH,
			PACKAGE_PATH,
			"black.png"
			);

	private static String createPathFrom(String... elements )
	{
		StringBuilder path = new StringBuilder();
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

}
