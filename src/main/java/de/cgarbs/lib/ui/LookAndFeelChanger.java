/*
 * Copyright 2014 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.ui;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.UIManager;

/**
 * Utility class to switch Java Swing Look and Feels
 * @author mitch
 *
 */
public class LookAndFeelChanger
{
	/** The supported Look and Feels */
	public enum LookAndFeel {
		GTK,
		METAL,
		MOTIF,
		NIMBUS,
		WINDOWS,
	};

	private final static Map<LookAndFeel,String> lookAndFeels = new LinkedHashMap<LookAndFeel,String>();
	static {
		lookAndFeels.put(LookAndFeel.WINDOWS, 	"com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		lookAndFeels.put(LookAndFeel.NIMBUS, 	"com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		lookAndFeels.put(LookAndFeel.GTK, 		"com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
		lookAndFeels.put(LookAndFeel.METAL, 	"javax.swing.plaf.metal.MetalLookAndFeel");
		lookAndFeels.put(LookAndFeel.MOTIF, 	"com.sun.java.swing.plaf.motif.MotifLookAndFeel");
	};

	/**
	 * directly tries to set a specific Look and Feel
	 * @param lf the Look and Feel that should be used
	 * @return true: success; false: Look and Feel not available or other error
	 */
	public static boolean setLookAndFeel(final LookAndFeel lf)
	{
		try
		{
			UIManager.setLookAndFeel(lookAndFeels.get(lf));
		}
		catch (Exception e)
		{
			// this also catches null values for lf
			return false;
		}
		return true;
	}

	/**
	 * Go through a default list of standard Look and Feels and use
	 * the first one found.  The list is ordered from "prettiest"
	 * to "most bland", totally subjective to the author of cgarbs-javalib
	 */
	public static void setNiceLookAndFeel()
	{
		for (final LookAndFeel lf: lookAndFeels.keySet())
		{
			if (setLookAndFeel(lf))
			{
				return;
			}
		}
	}
}
