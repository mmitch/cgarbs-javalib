package de.cgarbs.lib.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.cgarbs.lib.ui.LookAndFeelChanger.LookAndFeel;

public class LookAndFeelChangerTest
{
	@Test
	public void checkSetNiceLookAndFeel()
	{
		LookAndFeelChanger.setNiceLookAndFeel();
		// FIXME how to test the result?
	}

	@Test
	public void checkSetLookAndFeel()
	{
		// supported LookAndFeels are OS dependent, so don't expect everything to work
		int worked = 0;

		for (LookAndFeel lf: LookAndFeel.values())
		{
			if (LookAndFeelChanger.setLookAndFeel(lf))
			{
				worked++;
			}
		}

		assertTrue(worked > 0);


		assertFalse(LookAndFeelChanger.setLookAndFeel(null));
	}
}
