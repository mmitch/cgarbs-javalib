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
	// FIXME incompatible! here and in all subclasses: rename error/ERROR to errorCode/ERRORCODE

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

	protected LocalizedException(final ERRORIF error, final Throwable t)
	{
		this(error, t.getMessage(), t);
	}

	protected LocalizedException(final ERRORIF error, final String message, final Throwable t)
	{
		this(message, t);
		this.error = error;
	}

	protected LocalizedException(final ERRORIF error, final String message)
	{
		this(message);
		this.error = error;
	}

	protected LocalizedException(final String message)
	{
		this(message, message);
	}

	protected LocalizedException(final String message, final String localizedMessage)
	{
		super(message);
		this.localizedMessage = localizedMessage;
	}

	protected LocalizedException(final String message, final Throwable t)
	{
		this(message, message, t);
	}

	protected LocalizedException(final String message, final String localizedMessage, final Throwable t)
	{
		super(message, t);
		this.localizedMessage = localizedMessage;
	}

	@Override
	public String getMessage()
	{
		final StringBuilder message = new StringBuilder(ERRORTEXT.get(error));
		if (super.getMessage() != null)
		{
			message.append("::").append(super.getMessage());
		}
		return message.toString();
	}

	@Override
	public String getLocalizedMessage()
	{
		return localizedMessage;
	}

	/**
	 * returns only the message given to the exception constructor
	 * without the prefix added by the error code
	 * @return the exception message without the error code text
	 */
	public String getMessageOnly()
	{
		return super.getMessage();
	}
}
