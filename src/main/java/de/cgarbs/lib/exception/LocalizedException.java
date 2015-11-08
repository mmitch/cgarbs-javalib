package de.cgarbs.lib.exception;

public abstract class LocalizedException extends Exception
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	protected String localizedMessage = null;

	public LocalizedException(String message)
	{
		this(message, message);
	}

	public LocalizedException(String message, String localizedMessage)
	{
		super(message);
		this.localizedMessage = localizedMessage;
	}

	public LocalizedException(String message, Throwable t)
	{
		this(message, message, t);
	}

	public LocalizedException(String message, String localizedMessage, Throwable t)
	{
		super(message, t);
		this.localizedMessage = localizedMessage;
	}

	public String getLocalizedMessage()
	{
		return localizedMessage;
	}
}
