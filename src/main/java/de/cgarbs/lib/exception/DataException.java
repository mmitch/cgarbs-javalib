/*
 * Copyright 2014 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.exception;

public class DataException extends LocalizedException
{
	/**
	 * Error codes for this exception.
	 *
	 * @author Christian Garbs <mitch@cgarbs.de>
	 *
	 */
	public enum ERROR implements ERRORIF
	{
		DUPLICATE_ATTRIBUTE,
		DUPLICATE_USAGE,
		UNKNOWN_ATTRIBUTE,
		INVALID_VALUE,
		JSON_CONVERSION_ERROR,
		IO_ERROR,
	}

	// FIXME add localization
	static
	{
		ERRORTEXT.put(ERROR.DUPLICATE_ATTRIBUTE, "duplicate attribute name");
		ERRORTEXT.put(ERROR.DUPLICATE_USAGE, "attribute used in multiple models");
		ERRORTEXT.put(ERROR.UNKNOWN_ATTRIBUTE, "unknown attribute");
		ERRORTEXT.put(ERROR.INVALID_VALUE, "invalid value");
		ERRORTEXT.put(ERROR.INVALID_VALUE, "invalid value");
		ERRORTEXT.put(ERROR.JSON_CONVERSION_ERROR, "error during JSON conversion");
		ERRORTEXT.put(ERROR.IO_ERROR, "error during file access");
	};

	public DataException(final ERROR error, final String message)
	{
		super(error, message);
	}

	public DataException(final ERROR error, final Throwable t)
	{
		super(error, t);
	}

	public DataException(final ERROR error, final String message, final Throwable t)
	{
		super(error, message, t);
	}

	/**
	 * returns the error code of this exception
	 * @return the error code
	 *
	 * FIXME rename to getErrorCode() ?
	 */
	public ERROR getError()
	{
		return (ERROR) error;
	}

	public DataException prependMessage(final String prefix)
	{
		return new DataException(
				getError(),
				prefix + "::" + super.getMessage(),
				this.getCause()
				);
	}
}
