/*
 * Copyright 2014 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.exception;

/**
 * An exception during JSON conversion.
 *
 * @author Christian Garbs <mitch@cgarbs.de>
 * @since 0.3.0
 */
public class JSONException extends LocalizedException
{
	/**
	 * Error codes for this exception.
	 *
	 * @author Christian Garbs <mitch@cgarbs.de>
	 *
	 */
	public enum ERROR implements ERRORIF
	{
		/** error during Java to JSON conversion */
		JAVA_TO_JSON,

		/** error during JSON to Java conversion */
		JSON_TO_JAVA
	}

	// FIXME add localization
	static
	{
		ERRORTEXT.put(ERROR.JAVA_TO_JSON, "error converting from Java object to JSON notation");
		ERRORTEXT.put(ERROR.JSON_TO_JAVA, "error converting from JSON notation to Java object");
	};

	public JSONException(final ERROR error, final Throwable t)
	{
		super(error, t.getMessage(), t);
	}

	public JSONException(final ERROR error, final String message, final Throwable t)
	{
		super(message, t);
		this.error = error;
	}

	public JSONException(final ERROR error, final String message)
	{
		super(message);
		this.error = error;
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
}
