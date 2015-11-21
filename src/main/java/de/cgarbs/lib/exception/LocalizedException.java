/*
 * Copyright 2014 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * Base class for exceptions implementing localization (they mustn't really, but they should)
 * and error codes.
 *
 * @author Christian Garbs <mitch@cgarbs.de>
 * @since 0.1.0
 */
public abstract class LocalizedException extends Exception
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	protected static final Map<ERRORIF, String> ERRORTEXT = new HashMap<ERRORIF, String>();

	protected ERRORIF error = null;

	/**
	 * Base class for the Error code Enums of the subclassed Exceptions
	 *
	 * @author Christian Garbs <mitch@cgarbs.de>
	 * @since 0.3.0
	 */
	public interface ERRORIF {};

	protected String localizedMessage = null;

	protected LocalizedException(ERRORIF error, Throwable t)
	{
		this(error, t.getMessage(), t);
	}

	protected LocalizedException(ERRORIF error, String message, Throwable t)
	{
		this(message, t);
		this.error = error;
	}

	protected LocalizedException(ERRORIF error, String message)
	{
		this(message);
		this.error = error;
	}

	protected LocalizedException(String message)
	{
		this(message, message);
	}

	protected LocalizedException(String message, String localizedMessage)
	{
		super(message);
		this.localizedMessage = localizedMessage;
	}

	protected LocalizedException(String message, Throwable t)
	{
		this(message, message, t);
	}

	protected LocalizedException(String message, String localizedMessage, Throwable t)
	{
		super(message, t);
		this.localizedMessage = localizedMessage;
	}

	@Override
	public String getMessage()
	{
		String message = ERRORTEXT.get(error);
		if (super.getMessage() != null)
		{
			message += "::" + super.getMessage();
		}
		return message;
	}

	@Override
	public String getLocalizedMessage()
	{
		return localizedMessage;
	}

	/**
	 * returns only the message belonging to the exception's error code
	 * without the 'real' message text appended
	 * @return the error text of the error code without anything else
	 */
	public String getMessageOnly()
	{
		return super.getMessage();
	}
}
