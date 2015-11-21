/*
 * Copyright 2014 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.i18n;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Resource
{
	// FIXME: implement cacheing per class?

	private ResourceBundle rb;
	private Pattern pattern = Pattern.compile("(\\$?)\\$(\\d*+)");

	/**
	 * For usage without a class/property file: every key is mapped to itself
	 *
	 * @since 0.2.0
	 */
	public Resource()
	{
		this(Resource.class);
	}

	public Resource(final Class<?> c)
	{
		this(c.getName());
	}

	public Resource(final String baseName)
	{
		rb = ResourceBundle.getBundle(baseName);
	}

	/**
	 * parameter markers are $0, $1, $2...
	 * $$ escapes a $
	 *
	 * missing key results in no exception, key is given back (with expanded parameters, where possible)
	 *
	 * @param key
	 * @param values
	 * @return
	 */
	public String _(final String key, final String... values)
	{
		if (key == null)
		{
			return null;
		}

		String text;

		try
		{
			text = rb.getString(key);
		}
		catch (MissingResourceException e)
		{
			text = key;
		}

		final StringBuffer sb = new StringBuffer();
		final Matcher matcher = pattern.matcher(text);
		while (matcher.find())
		{
			String repl;
			if (matcher.group(1).isEmpty() && matcher.group(2).isEmpty())
			{
				// single "$" -> don't do anything, replace by $
				repl = "\\$"; // quote this!
			}
			else if (matcher.group(1).isEmpty())
			{
				// replacement of variable $n
				int i = Integer.valueOf(matcher.group(2));
				if (i < values.length)
				{
					repl = values[i];
				}
				else
				{
					repl = "{PARAMETER "+i+" NOT SET}";
					// FIXME warn via exception?
				}
			}
			else
			{
				// duplicate $$ -> escaped $
				// change $$ to $, don't change anything else
				repl = "\\$$2"; // quoted $ (\$) plus group 2 ($2)
			}
			matcher.appendReplacement(sb, repl);
		}
		matcher.appendTail(sb);

		return sb.toString();
	}
}
