package de.cgarbs.lib.ui.layout;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;
import static de.cgarbs.lib.hamcrest.Swing.elementInside;
import static de.cgarbs.lib.hamcrest.Swing.hasLabel;
import static de.cgarbs.lib.hamcrest.Swing.hasValue;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JViewport;

import org.junit.Before;
import org.junit.Test;

import de.cgarbs.lib.exception.DataException;
import de.cgarbs.lib.exception.GlueException;
import de.cgarbs.lib.ui.UiTestDataModel;

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
		JViewport viewport = getViewportFromScrollpane(container);

		// ...the ViewPort containing one JPanel...
		assertThat(viewport.getComponents().length, is(equalTo(1)));
		assertThat(viewport.getComponent(0), is(instanceOf(JPanel.class)));
		JPanel jpanel = (JPanel) viewport.getComponent(0);

		// ...containing all Groups and Elements (flat)
		assertThat(jpanel.getComponents().length, is(equalTo(GROUP_COUNT + (BINDING_COUNT*2))));

		assertThat(elementInside(jpanel).at(0, 0), is(instanceOf(JLabel.class)));
		assertThat(elementInside(jpanel).at(0, 0), hasLabel(equalTo(GROUP_1)));

		assertThat(elementInside(jpanel).at(0, 1), is(instanceOf(JLabel.class)));
		assertThat(elementInside(jpanel).at(0, 1), hasLabel(equalTo(theLabel(UiTestDataModel.STRING_1))));
		assertThat(elementInside(jpanel).at(1, 1), is(instanceOf(JTextField.class)));
		assertThat(elementInside(jpanel).at(1, 1), hasValue(equalTo(theValue(UiTestDataModel.STRING_1))));

		assertThat(elementInside(jpanel).at(0, 2), is(instanceOf(JLabel.class)));
		assertThat(elementInside(jpanel).at(0, 2), hasLabel(equalTo(theLabel(UiTestDataModel.STRING_2))));
		assertThat(elementInside(jpanel).at(1, 2), is(instanceOf(JTextField.class)));
		assertThat(elementInside(jpanel).at(1, 2), hasValue(equalTo(theValue(UiTestDataModel.STRING_2))));

		assertThat(elementInside(jpanel).at(0, 3), is(instanceOf(JLabel.class)));
		assertThat(elementInside(jpanel).at(0, 3), hasLabel(equalTo(GROUP_2)));

		assertThat(elementInside(jpanel).at(0, 4), is(instanceOf(JLabel.class)));
		assertThat(elementInside(jpanel).at(0, 4), hasLabel(equalTo(theLabel(UiTestDataModel.STRING_3))));
		assertThat(elementInside(jpanel).at(1, 4), is(instanceOf(JTextField.class)));
		assertThat(elementInside(jpanel).at(1, 4), hasValue(equalTo(theValue(UiTestDataModel.STRING_3))));

		assertThat(elementInside(jpanel).at(0, 5), is(instanceOf(JLabel.class)));
		assertThat(elementInside(jpanel).at(0, 5), hasLabel(equalTo(theLabel(UiTestDataModel.STRING_4))));
		assertThat(elementInside(jpanel).at(1, 5), is(instanceOf(JTextField.class)));
		assertThat(elementInside(jpanel).at(1, 5), hasValue(equalTo(theValue(UiTestDataModel.STRING_4))));

		assertThat(elementInside(jpanel).at(0, 6), is(instanceOf(JLabel.class)));
		assertThat(elementInside(jpanel).at(0, 6), hasLabel(equalTo(GROUP_3)));

		assertThat(elementInside(jpanel).at(0, 7), is(instanceOf(JLabel.class)));
		assertThat(elementInside(jpanel).at(0, 7), hasLabel(equalTo(theLabel(UiTestDataModel.STRING_5))));
		assertThat(elementInside(jpanel).at(1, 7), is(instanceOf(JTextField.class)));
		assertThat(elementInside(jpanel).at(1, 7), hasValue(equalTo(theValue(UiTestDataModel.STRING_5))));

		assertThat(elementInside(jpanel).at(0, 8), is(instanceOf(JLabel.class)));
		assertThat(elementInside(jpanel).at(0, 8), hasLabel(equalTo(theLabel(UiTestDataModel.STRING_6))));
		assertThat(elementInside(jpanel).at(1, 8), is(instanceOf(JTextField.class)));
		assertThat(elementInside(jpanel).at(1, 8), hasValue(equalTo(theValue(UiTestDataModel.STRING_6))));
	}
}
