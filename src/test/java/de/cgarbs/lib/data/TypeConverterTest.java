/*
 * Copyright 2015 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.data;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.text.NumberFormat;
import java.text.ParseException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TypeConverterTest
{
	NumberFormat nf;

	@Rule
	public ExpectedException thrown= ExpectedException.none();

	@Before
	public void setUp()
	{
		nf = NumberFormat.getInstance();
	}

	@Test
	public void checkParseNumber() throws ParseException
	{
		assertThat(TypeConverter.parseNumber(null), 	is(nullValue()));
		assertThat(TypeConverter.parseNumber(33), 		is(equalTo((Number) Integer.valueOf(33))));
		assertThat(TypeConverter.parseNumber(-12l), 	is(equalTo((Number) Long.valueOf(-12))));
		assertThat(TypeConverter.parseNumber(12.7d), 	is(equalTo((Number) Double.valueOf(12.7d))));

		// String always become Long?!?!
		assertThat(TypeConverter.parseNumber(""), 				is(nullValue()));
		assertThat(TypeConverter.parseNumber("-77000"), 		is(equalTo((Number) Long.valueOf(-77000))));
		assertThat(TypeConverter.parseNumber("5 Hello"), 		is(equalTo((Number) Long.valueOf(5))));
		assertThat(TypeConverter.parseNumber(nf.format(11.1d)), is(equalTo((Number) Double.valueOf("11.1"))));
	}

	@Test
	public void checkParseNumber_ParseException() throws ParseException
	{
		thrown.expect(ParseException.class);
		TypeConverter.parseNumber("hello");
	}

	@Test
	public void checkParseAsInteger() throws ParseException
	{
		assertThat(TypeConverter.parseAsInteger(null), 	is(nullValue()));
		assertThat(TypeConverter.parseAsInteger(33), 	is(equalTo(Integer.valueOf(33))));
		assertThat(TypeConverter.parseAsInteger(-12l), 	is(equalTo(Integer.valueOf(-12))));
		assertThat(TypeConverter.parseAsInteger(12.7d), is(equalTo(Integer.valueOf(12))));

		assertThat(TypeConverter.parseAsInteger(""), 				is(nullValue()));
		assertThat(TypeConverter.parseAsInteger("-77000"), 			is(equalTo((Number) Integer.valueOf(-77000))));
		assertThat(TypeConverter.parseAsInteger("5 Hello"), 		is(equalTo((Number) Integer.valueOf(5))));
		assertThat(TypeConverter.parseAsInteger(nf.format(-7.1d)),	is(equalTo((Number) Integer.valueOf(-7))));

		assertThat(TypeConverter.parseAsInteger("hello"), is(nullValue()));
	}

	@Test
	public void checkParseAsInt() throws ParseException
	{
		assertThat(TypeConverter.parseAsInt(null), 	is(equalTo(0)));
		assertThat(TypeConverter.parseAsInt(33), 	is(equalTo(33)));
		assertThat(TypeConverter.parseAsInt(-12l), 	is(equalTo(-12)));
		assertThat(TypeConverter.parseAsInt(12.7d), is(equalTo(12)));

		assertThat(TypeConverter.parseAsInt(""), 				is(equalTo(0)));
		assertThat(TypeConverter.parseAsInt("-77000"), 			is(equalTo(-77000)));
		assertThat(TypeConverter.parseAsInt("5 Hello"), 		is(equalTo(5)));
		assertThat(TypeConverter.parseAsInt(nf.format(-7.1d)), 	is(equalTo(-7)));

		assertThat(TypeConverter.parseAsInt("hello"), is(equalTo(0)));
	}
}
