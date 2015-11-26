/*
 * Copyright 2015 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class StringUtilsTest
{
	@Test
	public void checkJoin_EmptyResults()
	{
		assertThat(
				StringUtils.join(null),
				is(equalTo(""))
				);
		assertThat(
				StringUtils.join("qwertz"),
				is(equalTo(""))
				);
		assertThat(
				StringUtils.join(null, (String) null),
				is(equalTo(""))
				);
		assertThat(
				StringUtils.join(null, (String[]) null),
				is(equalTo(""))
				);
		assertThat(
				StringUtils.join(null, null, null, null),
				is(equalTo(""))
				);
		assertThat(
				StringUtils.join("ABC", null, null, null),
				is(equalTo(""))
				);
		assertThat(
				StringUtils.join(null, null, "", null),
				is(equalTo(""))
				);
		assertThat(
				StringUtils.join("", null, null, null),
				is(equalTo(""))
				);
		assertThat(
				StringUtils.join("", null, "", ""),
				is(equalTo(""))
				);
	}

	@Test
	public void checkJoin()
	{
		assertThat(
				StringUtils.join(", ", "dog", "cat", "mice"),
				is(equalTo("dog, cat, mice"))
				);
		assertThat(
				StringUtils.join(null, "dig", "cot", "mace"),
				is(equalTo("digcotmace"))
				);
		assertThat(
				StringUtils.join("*", new String[]{ "foo", "bar", "baz" } ),
				is(equalTo("foo*bar*baz"))
				);
		assertThat(
				StringUtils.join(" ABCDE ", "ABCDE", "ABCDE", "ABCDE"),
				is(equalTo("ABCDE ABCDE ABCDE ABCDE ABCDE"))
				);
		assertThat(
				StringUtils.join("|", null, "foo", "bar"),
				is(equalTo("foo|bar"))
				);
		assertThat(
				StringUtils.join("§", "", "", "", ""),
				is(equalTo("§§§"))
				);
		assertThat(
				StringUtils.join("§", "1", "2", "3", "4"),
				is(equalTo("1§2§3§4"))
				);
		assertThat(
				StringUtils.join("§", "", null, null, ""),
				is(equalTo("§"))
				);
		assertThat(
				StringUtils.join("%", "", null, null, "bar"),
				is(equalTo("%bar"))
				);
	}

	@Test
	public void checkTruncate()
	{
		assertThat(
				StringUtils.truncate(null, -3),
				is(nullValue())
				);
		assertThat(
				StringUtils.truncate(null, 0),
				is(nullValue())
				);
		assertThat(
				StringUtils.truncate(null, 7),
				is(nullValue())
				);

		assertThat(
				StringUtils.truncate("califabulatoric", -5),
				is(equalTo(""))
				);
		assertThat(
				StringUtils.truncate("califabulatoric", 0),
				is(equalTo(""))
				);
		assertThat(
				StringUtils.truncate("califabulatoric", 1),
				is(equalTo("c"))
				);
		assertThat(
				StringUtils.truncate("califabulatoric", 5),
				is(equalTo("calif"))
				);
		assertThat(
				StringUtils.truncate("califabulatoric", 500),
				is(equalTo("califabulatoric"))
				);
	}
}
