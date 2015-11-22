/*
 * Copyright 2015 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib;

import static de.cgarbs.lib.FileUtils.createPathFrom;

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
}
