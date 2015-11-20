/*
 * Copyright 2014 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.exception;

import java.util.HashMap;
import java.util.Map;

public class DataException extends LocalizedException
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private static final Map<ERROR, String> ERRORTEXT = new HashMap<ERROR, String>();
	private ERROR error = ERROR.UNDEFINED;

	public enum ERROR
	{
		UNDEFINED,
		DUPLICATE_ATTRIBUTE,
		DUPLICATE_USAGE,
		UNKNOWN_ATTRIBUTE,
		INVALID_VALUE,
		JSON_CONVERSION_ERROR,
		IO_ERROR,
	}

	static
	{
		ERRORTEXT.put(ERROR.UNDEFINED, "undefined");
		ERRORTEXT.put(ERROR.DUPLICATE_ATTRIBUTE, "duplicate attribute name");
		ERRORTEXT.put(ERROR.DUPLICATE_USAGE, "attribute used in multiple models");
		ERRORTEXT.put(ERROR.UNKNOWN_ATTRIBUTE, "unknown attribute");
		ERRORTEXT.put(ERROR.INVALID_VALUE, "invalid value");
		ERRORTEXT.put(ERROR.INVALID_VALUE, "invalid value");
		ERRORTEXT.put(ERROR.JSON_CONVERSION_ERROR, "error during JSON conversion");
		ERRORTEXT.put(ERROR.IO_ERROR, "error during file access");
	};

	public DataException(ERROR error, String message)
	{
		super(message);
		this.error = error;
	}

	public DataException(ERROR error, Throwable t)
	{
		this(error, t.getMessage(), t);
	}

	public DataException(ERROR error, String message, Throwable t)
	{
		super(message, t);
		this.error = error;
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

	public String getMessageOnly()
	{
		return super.getMessage();
	}

	public ERROR getError()
	{
		return error;
	}

	public DataException prependMessage(String prefix)
	{
		return new DataException(
				this.error,
				prefix + "::" + super.getMessage(),
				this.getCause()
				);
	}
}
