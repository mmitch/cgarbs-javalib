/*
 * Copyright 2015 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Test;

public class FileUtilsTest
{
	@Test
	public void checkCreatePathFrom()
	{
		final char sep = File.separatorChar;

		assertThat(
				FileUtils.createPathFrom(),
				is(equalTo(""))
				);

		assertThat(
				FileUtils.createPathFrom("foo"),
				is(equalTo("foo"))
				);

		assertThat(
				FileUtils.createPathFrom("foo", "bar", "baz"),
				is(equalTo("foo"+sep+"bar"+sep+"baz"))
				);
	}

	@Test
	public void checkCreateFileFrom()
	{
		final char sep = File.separatorChar;

		assertThat(
				FileUtils.createFileFrom(),
				is(equalTo(new File("")))
				);

		assertThat(
				FileUtils.createFileFrom("foo"),
				is(equalTo(new File("foo")))
				);

		assertThat(
				FileUtils.createFileFrom("foo", "bar", "baz"),
				is(equalTo(new File("foo"+sep+"bar"+sep+"baz")))
				);
	}
}
