/*
 * Copyright 2015 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
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
				FileUtils.createPathFrom(null),
				is(equalTo(""))
				);

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

		// check empty Path components
		assertThat(
				FileUtils.createPathFrom("", "foo"),
				is(equalTo(sep+"foo"))
				);

		assertThat(
				FileUtils.createPathFrom("foo", "", "", "", "bar"),
				is(equalTo("foo"+sep+sep+sep+sep+"bar"))
				);

		assertThat(
				FileUtils.createPathFrom("foo", ""),
				is(equalTo("foo"+sep))
				);

		assertThat(
				FileUtils.createPathFrom("","foo", ""),
				is(equalTo(sep+"foo"+sep))
				);
	}

	@Test
	public void checkSplitPath()
	{
		final char sep = File.separatorChar;

		assertThat(
				FileUtils.splitPath(null),
				is(nullValue())
				);

		assertThat(
				FileUtils.splitPath(""),
				is(equalTo(new String[]{""}))
				);

		assertThat(
				FileUtils.splitPath("foo"),
				is(equalTo(new String[]{"foo"}))
				);

		assertThat(
				FileUtils.splitPath("foo"+sep+"bar"+sep+"baz"),
				is(equalTo(new String[]{"foo", "bar", "baz"}))
				);

		// check empty Path components
		assertThat(
				FileUtils.splitPath(sep+"foo"),
				is(equalTo(new String[]{"", "foo"}))
				);

		assertThat(
				FileUtils.splitPath("foo"+sep+sep+sep+sep+"bar"),
				is(equalTo(new String[]{"foo", "", "", "", "bar"}))
				);

		assertThat(
				FileUtils.splitPath("foo"+sep),
				is(equalTo(new String[]{"foo", ""}))
				);

		assertThat(
				FileUtils.splitPath(sep+"foo"+sep),
				is(equalTo(new String[]{"","foo", ""}))
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

		// check empty Path components
		assertThat(
				FileUtils.createFileFrom("", "foo"),
				is(equalTo(new File(sep+"foo")))
				);

		assertThat(
				FileUtils.createFileFrom("foo", "", "", "", "bar"),
				is(equalTo(new File("foo"+sep+sep+sep+sep+"bar")))
				);

		assertThat(
				FileUtils.createFileFrom("foo", ""),
				is(equalTo(new File("foo"+sep)))
				);

		assertThat(
				FileUtils.createFileFrom("","foo", ""),
				is(equalTo(new File(sep+"foo"+sep)))
				);
	}

	@Test
	public void checkSplitFile()
	{
		final char sep = File.separatorChar;

		assertThat(
				FileUtils.splitFile(null),
				is(nullValue())
				);

		assertThat(
				FileUtils.splitFile(new File("")),
				is(equalTo(new String[]{""}))
				);

		assertThat(
				FileUtils.splitFile(new File("foo")),
				is(equalTo(new String[]{"foo"}))
				);

		assertThat(
				FileUtils.splitFile(new File("foo"+sep+"bar"+sep+"baz")),
				is(equalTo(new String[]{"foo", "bar", "baz"}))
				);

		// check empty Path components
		assertThat(
				FileUtils.splitFile(new File(sep+"foo")),
				is(equalTo(new String[]{"", "foo"}))
				);

		assertThat(
				FileUtils.splitFile(new File("foo"+sep+sep+sep+sep+"bar")),
//				is(equalTo(new String[]{"foo", "", "", "", "bar"}))
				is(equalTo(new String[]{"foo", "bar"}))  // note: File shortens away multiple separators
				);

		assertThat(
				FileUtils.splitFile(new File("foo"+sep)),
//				is(equalTo(new String[]{"foo", ""}))
				is(equalTo(new String[]{"foo"})) // WTF: no "trailing slash" possible with File?
				);

		assertThat(
				FileUtils.splitFile(new File(sep+"foo"+sep)),
//				is(equalTo(new String[]{"","foo", ""}))
				is(equalTo(new String[]{"","foo"})) // WTF: no "trailing slash" possible with File?
				);
	}
}
