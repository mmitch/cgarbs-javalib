package de.cgarbs.lib.ui.layout;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import org.junit.Before;
import org.junit.Test;

import de.cgarbs.lib.exception.DataException;
import de.cgarbs.lib.exception.GlueException;

public class SimpleVerticalLayoutTest extends BaseLayoutTest
{
	@Before
	public void setUp() throws DataException, GlueException
	{
		super.setUp();

		container = SimpleVerticalLayout.builder()
				.startNextGroup(GROUP_1)
				.addAttribute(binding1)
				.addAttribute(binding2)
				.startNextGroup(GROUP_2)
				.addAttribute(binding3)
				.addAttribute(binding4)
				.startNextGroup(GROUP_3)
				.addAttribute(binding5)
				.addAttribute(binding6)
				.build();
	}

	@Test
	public void checkContainer()
	{
		// The Container is a ScrollPane...
		assertThat(container, is(instanceOf(JScrollPane.class)));
		JScrollPane scrollpane = (JScrollPane) container;

		// ...containing a ViewPort and to Scrollbars...
		assertThat(scrollpane.getComponents().length, is(equalTo(3)));
		assertThat(scrollpane.getComponent(0), is(instanceOf(JViewport.class)));
		JViewport viewport = (JViewport) scrollpane.getComponent(0);

		// ...the ViewPort containing one JPanel...
		assertThat(viewport.getComponents().length, is(equalTo(1)));
		assertThat(viewport.getComponent(0), is(instanceOf(JPanel.class)));
		JPanel jpanel = (JPanel) viewport.getComponent(0);

		// ...containing all Groups and Elements (flat)
		assertThat(jpanel.getComponents().length, is(equalTo(GROUP_COUNT + (BINDING_COUNT*2))));
		for(Component component: jpanel.getComponents())
		{
			// FIXME TODO implement further tests here
			// LABEL ELEMENT ELEMENT LABEL ELEMENT ELEMENT LABEL ELEMENT ELEMENT
		}
	}
}
