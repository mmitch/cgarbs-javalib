package de.cgarbs.lib.i18n;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Locale;
import java.util.MissingResourceException;

import org.junit.Test;

public class ResourceTest
{
	public static final String KEY_LANGUAGE = "language";
	public static final String EXPECTED_LANGUAGE_FRENCH  = "std";
	public static final String EXPECTED_LANGUAGE_GERMAN  = "de_DE";
	public static final String EXPECTED_LANGUAGE_ENGLISH = "en";

	public static final String KEY_HELLO = "hello";
	public static final String EXPECTED_HELLO_GERMAN  = "Hallo";
	public static final String EXPECTED_HELLO_ENGLISH = "hello";

	public static final String KEY_PARAMS = "params";
	public static final String PARAM_0 = "gazebo";
	public static final String PARAM_1 = "don't anger";
	public static final String PARAM_2 = "unused";
	public static final String PARAM_UNEXPANDED = " $ $ ";
	public static final String KEY_WITH_PARAMS = "$0 $1";

	public static final String KEY_MISSING = "missing";

	public static final String KEY_NULL = null;

	@Test
	public void checkLocales()
	{
		final Locale oldLocale = Locale.getDefault();

		{
			/* unknown locale - fallback to defaults */
			Locale.setDefault(Locale.FRENCH);
			final Resource r = new Resource(ResourceTest.class);

			assertEquals(EXPECTED_LANGUAGE_FRENCH, r._(KEY_LANGUAGE));
			assertEquals(EXPECTED_HELLO_ENGLISH, r._(KEY_HELLO));
		}

		{
			Locale.setDefault(Locale.ENGLISH);
			final Resource r = new Resource(ResourceTest.class);

			assertEquals(EXPECTED_LANGUAGE_ENGLISH, r._(KEY_LANGUAGE));
			assertEquals(EXPECTED_HELLO_ENGLISH, r._(KEY_HELLO));
		}

		{
			Locale.setDefault(Locale.GERMAN);
			final Resource r = new Resource(ResourceTest.class);

			assertEquals(EXPECTED_LANGUAGE_GERMAN, r._(KEY_LANGUAGE));
			assertEquals(EXPECTED_HELLO_GERMAN, r._(KEY_HELLO));
		}

		Locale.setDefault(oldLocale);
	}

	@Test
	public void checkParameterExpansion()
	{
		final Resource r = new Resource(ResourceTest.class);

		/* matching parameters */
		{
			final String msg = r._(KEY_PARAMS, PARAM_0, PARAM_1);

			/* parameter string is $1:$0, so PARAM_2 comes first! */
			assertTrue(msg.startsWith(PARAM_1));
			assertTrue(msg.endsWith(PARAM_0));
			assertFalse(msg.contains(PARAM_2));
			assertTrue(msg.contains(PARAM_UNEXPANDED));
		}

		/* too few parameters */
		// FIXME also check for the text {PARAMETER n NOT SET}
		{
			final String msg = r._(KEY_PARAMS, PARAM_0);

			assertTrue(msg.endsWith(PARAM_0));
			assertFalse(msg.contains(PARAM_1));
			assertFalse(msg.contains(PARAM_2));
			assertTrue(msg.contains(PARAM_UNEXPANDED));
		}
		{
			final String msg = r._(KEY_PARAMS);

			assertFalse(msg.contains(PARAM_0));
			assertFalse(msg.contains(PARAM_1));
			assertFalse(msg.contains(PARAM_2));
			assertTrue(msg.contains(PARAM_UNEXPANDED));
		}

		/* too many parameters */
		{
			final String msg = r._(KEY_PARAMS, PARAM_0, PARAM_1, PARAM_2);

			assertTrue(msg.startsWith(PARAM_1));
			assertTrue(msg.endsWith(PARAM_0));
			assertFalse(msg.contains(PARAM_2));
			assertTrue(msg.contains(PARAM_UNEXPANDED));
		}
	}

	@Test
	public void checkNullKey()
	{
		final Resource r = new Resource(ResourceTest.class);

		assertNull(r._(KEY_NULL));
		assertNull(r._(KEY_NULL, PARAM_0));
	}

	@Test
	public void checkMissingKeys()
	{
		final Resource r = new Resource(ResourceTest.class);

		{
			final String msg = r._(KEY_MISSING);

			/* missing keys return the key instead of exploding */
			assertNotNull(msg);
			assertEquals(KEY_MISSING, KEY_MISSING);
		}

		{
			final String msg = r._(KEY_MISSING, PARAM_0, PARAM_1);

			assertNotNull(msg);
			assertEquals(KEY_MISSING, KEY_MISSING);
		}
	}

	@Test
	public void checkEmptyProperties()
	{
		/* empty properties -> all keys missing, returning the key instead of exploding */
		final Resource r = new Resource();

		{
			final String msg = r._(KEY_MISSING);

			assertEquals(KEY_MISSING, msg);
		}

		{
			final String msg = r._(KEY_WITH_PARAMS, PARAM_0, PARAM_1);

//			assertNotEquals(KEY_MISSING, msg); // FIXME reactivate
			assertTrue(msg.startsWith(PARAM_0));
			assertTrue(msg.endsWith(PARAM_1));
		}
	}

	@Test
	public void checkMissingBundle()
	{
		try
		{
			new Resource(NoPropertyFound.class);
			fail("no exception thrown");
		}
		catch (MissingResourceException e)
		{
			/* OK, this is the exception we want */
		}
	}

	private class NoPropertyFound {
	}
}
