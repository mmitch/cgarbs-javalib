package de.cgarbs.lib.ui.layout;

import static de.cgarbs.lib.ui.layout.UIMatchers.elementInside;
import static de.cgarbs.lib.ui.layout.UIMatchers.hasLabel;
import static de.cgarbs.lib.ui.layout.UIMatchers.hasValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

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

public class DualColumnVerticalLayoutTest extends BaseLayoutTest
{
	@Before
	public void setUp() throws DataException, GlueException
	{
		super.setUp();

		container = DualColumnVerticalLayout.builder()
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

		assertThat(elementInside(jpanel).at(0, 0), is(instanceOf(JLabel.class)));
		assertThat(elementInside(jpanel).at(0, 0), hasLabel(equalTo(GROUP_1)));

		assertThat(elementInside(jpanel).at(0, 1), is(instanceOf(JLabel.class)));
		assertThat(elementInside(jpanel).at(0, 1), hasLabel(equalTo(theLabel(UiTestDataModel.STRING_1))));
		assertThat(elementInside(jpanel).at(1, 1), is(instanceOf(JTextField.class)));
		assertThat(elementInside(jpanel).at(1, 1), hasValue(equalTo(theValue(UiTestDataModel.STRING_1))));

		assertThat(elementInside(jpanel).at(2, 1), is(instanceOf(JLabel.class)));
		assertThat(elementInside(jpanel).at(2, 1), hasLabel(equalTo(theLabel(UiTestDataModel.STRING_2))));
		assertThat(elementInside(jpanel).at(3, 1), is(instanceOf(JTextField.class)));
		assertThat(elementInside(jpanel).at(3, 1), hasValue(equalTo(theValue(UiTestDataModel.STRING_2))));

		assertThat(elementInside(jpanel).at(0, 2), is(instanceOf(JLabel.class)));
		assertThat(elementInside(jpanel).at(0, 2), hasLabel(equalTo(GROUP_2)));

		assertThat(elementInside(jpanel).at(0, 3), is(instanceOf(JLabel.class)));
		assertThat(elementInside(jpanel).at(0, 3), hasLabel(equalTo(theLabel(UiTestDataModel.STRING_3))));
		assertThat(elementInside(jpanel).at(1, 3), is(instanceOf(JTextField.class)));
		assertThat(elementInside(jpanel).at(1, 3), hasValue(equalTo(theValue(UiTestDataModel.STRING_3))));

		assertThat(elementInside(jpanel).at(2, 3), is(instanceOf(JLabel.class)));
		assertThat(elementInside(jpanel).at(2, 3), hasLabel(equalTo(theLabel(UiTestDataModel.STRING_4))));
		assertThat(elementInside(jpanel).at(3, 3), is(instanceOf(JTextField.class)));
		assertThat(elementInside(jpanel).at(3, 3), hasValue(equalTo(theValue(UiTestDataModel.STRING_4))));

		assertThat(elementInside(jpanel).at(0, 4), is(instanceOf(JLabel.class)));
		assertThat(elementInside(jpanel).at(0, 4), hasLabel(equalTo(GROUP_3)));

		assertThat(elementInside(jpanel).at(0, 5), is(instanceOf(JLabel.class)));
		assertThat(elementInside(jpanel).at(0, 5), hasLabel(equalTo(theLabel(UiTestDataModel.STRING_5))));
		assertThat(elementInside(jpanel).at(1, 5), is(instanceOf(JTextField.class)));
		assertThat(elementInside(jpanel).at(1, 5), hasValue(equalTo(theValue(UiTestDataModel.STRING_5))));

		assertThat(elementInside(jpanel).at(2, 5), is(instanceOf(JLabel.class)));
		assertThat(elementInside(jpanel).at(2, 5), hasLabel(equalTo(theLabel(UiTestDataModel.STRING_6))));
		assertThat(elementInside(jpanel).at(3, 5), is(instanceOf(JTextField.class)));
		assertThat(elementInside(jpanel).at(3, 5), hasValue(equalTo(theValue(UiTestDataModel.STRING_6))));
	}
}
