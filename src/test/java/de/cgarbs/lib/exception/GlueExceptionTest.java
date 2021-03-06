/*
 * Copyright 2015 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.exception;

import static org.junit.Assert.*;

import org.junit.Test;

import de.cgarbs.lib.exception.GlueException.ERROR;

public class GlueExceptionTest
{
	public static final String GIVEN_MESSAGE = "foo";
	public static final ERROR GIVEN_ERROR = ERROR.BINDING_NOT_IMPLEMENTED;
	public static final String GIVEN_THROWABLE_MESSAGE = "bazinga!";
	public static final Throwable GIVEN_THROWABLE = new RuntimeException(GIVEN_THROWABLE_MESSAGE);

	@Test
	public void checkForMissingMessages()
	{
		for (ERROR error: ERROR.values())
		{
			final GlueException exception = new GlueException(error, (String) null);
			assertEquals(error, exception.getError());
			assertNotNull(exception.getMessage());
			assertTrue(exception.getMessage().length() > 0);
			assertEquals(exception.ERRORTEXT.get(error), exception.getMessage());
		}
	}

	@Test
	public void checkConstructors()
	{
		{
			final GlueException exception = new GlueException(GIVEN_ERROR, GIVEN_MESSAGE);

			assertEquals(GIVEN_ERROR, exception.getError());
			assertTrue(exception.getMessage().contains(GIVEN_MESSAGE));
			assertTrue(exception.getLocalizedMessage().contains(GIVEN_MESSAGE));
			assertNull(exception.getCause());
		}

		{
			final GlueException exception = new GlueException(GIVEN_ERROR, GIVEN_MESSAGE, GIVEN_THROWABLE);

			assertEquals(GIVEN_ERROR, exception.getError());
			assertTrue(exception.getMessage().contains(GIVEN_MESSAGE));
			assertTrue(exception.getLocalizedMessage().contains(GIVEN_MESSAGE));
			assertFalse(exception.getMessage().contains(GIVEN_THROWABLE_MESSAGE));
			assertFalse(exception.getLocalizedMessage().contains(GIVEN_THROWABLE_MESSAGE));
			assertEquals(GIVEN_THROWABLE, exception.getCause());
		}
	}

	@Test
	public void checkPrependMessage()
	{
		final GlueException exception = new GlueException(GIVEN_ERROR, GIVEN_MESSAGE)
											.prependMessage(GIVEN_THROWABLE_MESSAGE);

		{
			final String msg = exception.getMessage();
			assertTrue(msg.contains(GIVEN_MESSAGE));
			assertTrue(msg.contains(GIVEN_THROWABLE_MESSAGE));
			assertTrue(msg.indexOf(GIVEN_MESSAGE) > msg.indexOf(GIVEN_THROWABLE_MESSAGE));
		}

		{
			final String msg = exception.getLocalizedMessage();
			assertTrue(msg.contains(GIVEN_MESSAGE));
			assertTrue(msg.contains(GIVEN_THROWABLE_MESSAGE));
			assertTrue(msg.indexOf(GIVEN_MESSAGE) > msg.indexOf(GIVEN_THROWABLE_MESSAGE));
		}
	}
}
