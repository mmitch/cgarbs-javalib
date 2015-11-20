/*
 * Copyright 2015 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.exception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.cgarbs.lib.exception.DataException.ERROR;

public class DataExceptionTest
{
	public static final String GIVEN_MESSAGE = "foo";
	public static final ERROR GIVEN_ERROR = ERROR.UNDEFINED;
	public static final String GIVEN_THROWABLE_MESSAGE = "bazinga!";
	public static final Throwable GIVEN_THROWABLE = new RuntimeException(GIVEN_THROWABLE_MESSAGE);

	@Test
	public void checkForMissingMessages()
	{
		for (ERROR error: ERROR.values())
		{
			final DataException exception = new DataException(error, (String) null);
			assertEquals(error, exception.getError());
			assertNull(exception.getMessageOnly());
			assertNotNull(exception.getMessage());
			assertTrue(exception.getMessage().length() > 0);
		}
	}

	@Test
	public void checkConstructors()
	{
		{
			final DataException exception = new DataException(GIVEN_ERROR, GIVEN_MESSAGE);

			assertEquals(ERROR.UNDEFINED, exception.getError());
			assertEquals(GIVEN_MESSAGE, exception.getMessageOnly());
			assertTrue(exception.getMessage().contains(GIVEN_MESSAGE));
			assertTrue(exception.getLocalizedMessage().contains(GIVEN_MESSAGE));
			assertNull(exception.getCause());
		}

		{
			final DataException exception = new DataException(GIVEN_ERROR, GIVEN_THROWABLE);

			assertEquals(ERROR.UNDEFINED, exception.getError());
			assertEquals(GIVEN_THROWABLE_MESSAGE, exception.getMessageOnly());
			assertTrue(exception.getMessage().contains(GIVEN_THROWABLE_MESSAGE));
			assertTrue(exception.getLocalizedMessage().contains(GIVEN_THROWABLE_MESSAGE));
			assertEquals(GIVEN_THROWABLE, exception.getCause());
		}

		{
			final DataException exception = new DataException(GIVEN_ERROR, GIVEN_MESSAGE, GIVEN_THROWABLE);

			assertEquals(ERROR.UNDEFINED, exception.getError());
			assertEquals(GIVEN_MESSAGE, exception.getMessageOnly());
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
		final DataException exception = new DataException(GIVEN_ERROR, GIVEN_MESSAGE)
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
