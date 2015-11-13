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
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JViewport;

import org.junit.Before;
import org.junit.Test;

import de.cgarbs.lib.exception.DataException;
import de.cgarbs.lib.exception.GlueException;
import de.cgarbs.lib.ui.UiTestDataModel;

public class SimpleTabbedLayoutTest extends BaseLayoutTest
{
	@Before
	public void setUp() throws DataException, GlueException
	{
		super.setUp();

		container = SimpleTabbedLayout.builder()
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
		// The Container is a TabbedPane...
		assertThat(container, is(instanceOf(JTabbedPane.class)));
		JTabbedPane tabbedpane = (JTabbedPane) container;

		// ...containing a ScrollPane for every group...
		assertThat(tabbedpane.getTabCount(), is(equalTo(GROUP_COUNT)));

		JViewport viewport1 = getViewportFromScrollpane(tabbedpane.getComponent(0));
		JViewport viewport2 = getViewportFromScrollpane(tabbedpane.getComponent(1));
		JViewport viewport3 = getViewportFromScrollpane(tabbedpane.getComponent(2));

		// check tab titles

		assertThat(tabbedpane.getTitleAt(0), is(equalTo(GROUP_1)));
		assertThat(tabbedpane.getTitleAt(1), is(equalTo(GROUP_2)));
		assertThat(tabbedpane.getTitleAt(2), is(equalTo(GROUP_3)));

		// check groups

		assertThat(viewport1.getComponent(0), is(instanceOf(JPanel.class)));
		assertThat(viewport2.getComponent(0), is(instanceOf(JPanel.class)));
		assertThat(viewport3.getComponent(0), is(instanceOf(JPanel.class)));

		JPanel groupPanel1 = (JPanel) viewport1.getComponent(0);
		JPanel groupPanel2 = (JPanel) viewport2.getComponent(0);
		JPanel groupPanel3 = (JPanel) viewport3.getComponent(0);

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

		// check group 3

		assertThat(elementInside(groupPanel3).at(0, 0), is(instanceOf(JLabel.class)));
		assertThat(elementInside(groupPanel3).at(0, 0), hasLabel(is(equalTo(theLabel(UiTestDataModel.STRING_5)))));
		assertThat(elementInside(groupPanel3).at(1, 0), is(instanceOf(JTextField.class)));
		assertThat(elementInside(groupPanel3).at(1, 0), hasValue(is(equalTo(theValue(UiTestDataModel.STRING_5)))));

		assertThat(elementInside(groupPanel3).at(0, 1), is(instanceOf(JLabel.class)));
		assertThat(elementInside(groupPanel3).at(0, 1), hasLabel(is(equalTo(theLabel(UiTestDataModel.STRING_6)))));
		assertThat(elementInside(groupPanel3).at(1, 1), is(instanceOf(JTextField.class)));
		assertThat(elementInside(groupPanel3).at(1, 1), hasValue(is(equalTo(theValue(UiTestDataModel.STRING_6)))));
	}
}
