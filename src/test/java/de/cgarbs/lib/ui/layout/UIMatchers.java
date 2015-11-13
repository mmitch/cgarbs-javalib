package de.cgarbs.lib.ui.layout;

import static org.junit.Assert.fail;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

public class UIMatchers
{
	public static InnerElements elementInside(final JPanel jPanel)
	{
		LayoutManager layout = jPanel.getLayout();
		if (! (layout instanceof GridBagLayout))
		{
			fail("Component has no GridBagLayout, but " + layout.getClass());
		}
		return new InnerElements(jPanel, (GridBagLayout) layout);
	}

	public static class InnerElements
	{
		GridBagLayout gbl;
		Container container;

		private InnerElements(final Container container, GridBagLayout gbl)
		{
			this.container = container;
			this.gbl = gbl;
		}

		public Component at(final int x, final int y)
		{
			for (final Component comp: container.getComponents())
			{
				final GridBagConstraints gbc = gbl.getConstraints(comp);
				if (gbc.gridx == x && gbc.gridy == y)
				{
					return comp;
				}
			}
			return null;
		}
	}

	public static Matcher<Component> hasLabel(final Matcher<String> m)
	{
		return new FeatureMatcher<Component, String>(m, "string", "string") {

			@Override
			protected String featureValueOf(Component actual)
			{
				if (actual instanceof JLabel)
				{
					return ((JLabel) actual).getText();
				}
				return "CLASS" + actual.getClass() + " DOES NOT WORK HERE"; // fixme: stupid!
			}
		};
	}

	public static Matcher<Component> hasBorderTitle(final Matcher<String> m)
	{
		return new FeatureMatcher<Component, String>(m, "string", "string") {

			@Override
			protected String featureValueOf(Component actual)
			{
				if (actual instanceof JPanel)
				{
					Border border = ((JPanel) actual).getBorder();
					if (border instanceof TitledBorder)
					{
						return ((TitledBorder) border).getTitle();
					}
					return "BORDER CLASS " + border.getClass() + " DOES NOT WORK HERE"; // fixme: stupid!
				}
				return "CLASS" + actual.getClass() + " DOES NOT WORK HERE"; // fixme: stupid!
			}
		};
	}

	protected static Matcher<Component> hasValue(final Matcher<String> m)
	{
		return new FeatureMatcher<Component, String>(m, "string", "string") {

			@Override
			protected String featureValueOf(Component actual)
			{
				if (actual instanceof JTextField)
				{
					return ((JTextField) actual).getText();
				}
				if (actual instanceof JPanel)
				{
					return ((JLabel) actual).getText();
				}
				return "CLASS" + actual.getClass() + " DOES NOT WORK HERE"; // fixme: stupid!
			}
		};
	}
}
