/*
 * Copyright 2014 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package guesser;

import de.cgarbs.lib.data.DataModel;
import de.cgarbs.lib.data.type.IntAttribute;
import de.cgarbs.lib.exception.DataException;
import de.cgarbs.lib.i18n.Resource;

/**
 * This is the model holding all our data that
 * should be visible in the UI.
 */
public class GuesserModel extends DataModel
{
	// declare IDs for all attributes
	public static final String THE_NUMBER = "THE_NUMBER";

	public GuesserModel() throws DataException
	{
		// set the properties file with the resources
		super(new Resource("guesser.Guesser"));

		// add an Integer attribute with conditions
		addAttribute(THE_NUMBER,
				IntAttribute
				.builder()
				.setNullable(false)
				.setMinValue(7)
				.setMaxValue(7)
				.build()
				);
	}

	@Override
	public String getModelName()
	{
		return "GuesserModel";
	}
}
