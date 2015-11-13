package de.cgarbs.lib.ui.layout;

import static de.cgarbs.lib.hamcrest.Swing.elementInside;
import static de.cgarbs.lib.hamcrest.Swing.hasBorderTitle;
import static de.cgarbs.lib.hamcrest.Swing.hasLabel;
import static de.cgarbs.lib.hamcrest.Swing.hasValue;
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

public class BorderedVerticalLayoutTest extends BaseLayoutTest
{
	@Before
	public void setUp() throws DataException, GlueException
	{
		super.setUp();

		container = BorderedVerticalLayout.builder()
				.startNextGroup(GROUP_1)
				.addAttribute(binding1)
				.addAttribute(binding2)
				.startNextGroup(GROUP_2)
				.addAttribute(binding3)
				.addAttribute(binding4)
				.addAttribute(binding5)
				.startNextGroup(GROUP_3)
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

		// ...containing a Panel for every group
		assertThat(jpanel.getComponents().length, is(equalTo(GROUP_COUNT)));

		// check groups

		assertThat(elementInside(jpanel).at(0, 0), is(instanceOf(JPanel.class)));
		assertThat(elementInside(jpanel).at(0, 1), is(instanceOf(JPanel.class)));
		assertThat(elementInside(jpanel).at(0, 2), is(instanceOf(JPanel.class)));

		JPanel groupPanel1 = (JPanel) elementInside(jpanel).at(0, 0);
		JPanel groupPanel2 = (JPanel) elementInside(jpanel).at(0, 1);
		JPanel groupPanel3 = (JPanel) elementInside(jpanel).at(0, 2);

		assertThat(groupPanel1, hasBorderTitle(equalTo(GROUP_1)));
		assertThat(groupPanel2, hasBorderTitle(equalTo(GROUP_2)));
		assertThat(groupPanel3, hasBorderTitle(equalTo(GROUP_3)));

		// check group 1

		assertThat(elementInside(groupPanel1).at(0, 0), is(instanceOf(JLabel.class)));
		assertThat(elementInside(groupPanel1).at(0, 0), hasLabel(is(equalTo(theLabel(UiTestDataModel.STRING_1)))));
		assertThat(elementInside(groupPanel1).at(1, 0), is(instanceOf(JTextField.class)));
		assertThat(elementInside(groupPanel1).at(1, 0), hasValue(is(equalTo(theValue(UiTestDataModel.STRING_1)))));

		assertThat(elementInside(groupPanel1).at(0, 1), is(instanceOf(JLabel.class)));
		assertThat(elementInside(groupPanel1).at(0, 1), hasLabel(is(equalTo(theLabel(UiTestDataModel.STRING_2)))));
		assertThat(elementInside(groupPanel1).at(1, 1), is(instanceOf(JTextField.class)));
		assertThat(elementInside(groupPanel1).at(1, 1), hasValue(is(equalTo(theValue(UiTestDataModel.STRING_2)))));

		// check group 2

		assertThat(elementInside(groupPanel2).at(0, 0), is(instanceOf(JLabel.class)));
		assertThat(elementInside(groupPanel2).at(0, 0), hasLabel(is(equalTo(theLabel(UiTestDataModel.STRING_3)))));
		assertThat(elementInside(groupPanel2).at(1, 0), is(instanceOf(JTextField.class)));
		assertThat(elementInside(groupPanel2).at(1, 0), hasValue(is(equalTo(theValue(UiTestDataModel.STRING_3)))));

		assertThat(elementInside(groupPanel2).at(0, 1), is(instanceOf(JLabel.class)));
		assertThat(elementInside(groupPanel2).at(0, 1), hasLabel(is(equalTo(theLabel(UiTestDataModel.STRING_4)))));
		assertThat(elementInside(groupPanel2).at(1, 1), is(instanceOf(JTextField.class)));
		assertThat(elementInside(groupPanel2).at(1, 1), hasValue(is(equalTo(theValue(UiTestDataModel.STRING_4)))));

		assertThat(elementInside(groupPanel2).at(0, 2), is(instanceOf(JLabel.class)));
		assertThat(elementInside(groupPanel2).at(0, 2), hasLabel(is(equalTo(theLabel(UiTestDataModel.STRING_5)))));
		assertThat(elementInside(groupPanel2).at(1, 2), is(instanceOf(JTextField.class)));
		assertThat(elementInside(groupPanel2).at(1, 2), hasValue(is(equalTo(theValue(UiTestDataModel.STRING_5)))));

		// check group 3

		assertThat(elementInside(groupPanel3).at(0, 0), is(instanceOf(JLabel.class)));
		assertThat(elementInside(groupPanel3).at(0, 0), hasLabel(is(equalTo(theLabel(UiTestDataModel.STRING_6)))));
		assertThat(elementInside(groupPanel3).at(1, 0), is(instanceOf(JTextField.class)));
		assertThat(elementInside(groupPanel3).at(1, 0), hasValue(is(equalTo(theValue(UiTestDataModel.STRING_6)))));
	}
}
