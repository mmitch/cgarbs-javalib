/*
 * Copyright 2014 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.exception;


public class GlueException extends LocalizedException
{
	/**
	 * Error codes for this exception.
	 *
	 * @author Christian Garbs <mitch@cgarbs.de>
	 *
	 */
	public enum ERROR implements ERRORIF
	{
		BINDING_NOT_IMPLEMENTED,
	}

	// FIXME add localization
	static {
		ERRORTEXT.put(ERROR.BINDING_NOT_IMPLEMENTED, "binding not implemented");
	};

	public GlueException(ERROR error, String message)
	{
		super(error, message);
	}

	public GlueException(ERROR error, String message, Throwable t)
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

	public GlueException prependMessage(String prefix)
	{
		return new GlueException(
				getError(),
				prefix + "::" + super.getMessage(),
				this.getCause()
				);
	}
}
