/*
 * Copyright 2014 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.exception;

import de.cgarbs.lib.data.DataAttribute;
import de.cgarbs.lib.i18n.Resource;

public class ValidationError extends LocalizedException
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Error codes for this exception.
	 *
	 * @author Christian Garbs <mitch@cgarbs.de>
	 *
	 */
	public enum ERROR implements ERRORIF
	{
		STRING_TOO_SHORT,
		STRING_TOO_LONG,
		FILE_NOT_EXISTING,
		FILE_NOT_READABLE,
		FILE_NOT_WRITABLE,
		NUMBER_TOO_SMALL,
		NUMBER_TOO_LARGE,
		NUMBER_FORMAT,
		NULL_NOT_ALLOWED,
	}

	static
	{
		ERRORTEXT.put(ERROR.STRING_TOO_LONG,   "STRING_TOO_LONG");
		ERRORTEXT.put(ERROR.FILE_NOT_EXISTING, "FILE_NOT_EXISTING");
		ERRORTEXT.put(ERROR.FILE_NOT_READABLE, "FILE_NOT_READABLE");
		ERRORTEXT.put(ERROR.FILE_NOT_WRITABLE, "FILE_NOT_WRITABLE");
		ERRORTEXT.put(ERROR.NUMBER_TOO_SMALL,  "NUMBER_TOO_SMALL");
		ERRORTEXT.put(ERROR.NUMBER_TOO_LARGE,  "NUMBER_TOO_LARGE");
		ERRORTEXT.put(ERROR.NUMBER_FORMAT,     "NUMBER_FORMAT");
		ERRORTEXT.put(ERROR.NULL_NOT_ALLOWED,  "NULL_NOT_ALLOWED");
	}

	protected static Resource R = new Resource(ValidationError.class);

	protected DataAttribute attribute;

	public ValidationError(DataAttribute attribute, String message, ERROR error, String... params)
	{
		super(message, R._(ERRORTEXT.get(error), params));
		this.attribute = attribute;
		this.error = error;
	}

	public ValidationError(DataAttribute attribute, String message, String localizedMessage)
	{
		super(message, localizedMessage);
		this.attribute = attribute;
		// FIXME: set an ERROR value!
	}

	public ValidationError(DataAttribute attribute, DataException e)
	{
		this(attribute, e.getMessageOnly(), e.getLocalizedMessage());
	}

	public String getMessage()
	{
		return attribute.getAttributeName() + ": " + super.getMessage();
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
