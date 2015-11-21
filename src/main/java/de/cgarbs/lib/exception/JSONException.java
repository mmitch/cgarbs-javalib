/*
 * Copyright 2014 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * An exception during JSON conversion.
 *
 * @author Christian Garbs <mitch@cgarbs.de>
 * @since 0.3.0
 */
public class JSONException extends LocalizedException
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
		JAVA_TO_JSON,
		JSON_TO_JAVA
	}

	static
	{
		ERRORTEXT.put(ERROR.UNDEFINED, "undefined");
		ERRORTEXT.put(ERROR.JAVA_TO_JSON, "error converting from Java object to JSON notation");
		ERRORTEXT.put(ERROR.JSON_TO_JAVA, "error converting from JSON notation to Java object");
	};

	public JSONException(ERROR error, String message)
	{
		super(message);
		this.error = error;
	}

	public JSONException(ERROR error, Throwable t)
	{
		this(error, t.getMessage(), t);
	}

	public JSONException(ERROR error, String message, Throwable t)
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
}
