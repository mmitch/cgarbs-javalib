package de.cgarbs.lib.ui.layout;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import de.cgarbs.lib.exception.GlueException;
import de.cgarbs.lib.ui.AutoLayout;
import de.cgarbs.lib.ui.Element;
import de.cgarbs.lib.ui.Group;

public abstract class BorderedVerticalLayout extends AutoLayout
{
	// Builder pattern start
	public static class Builder extends AutoLayout.Builder<Builder>
	{

		@Override
		public Container build() throws GlueException
		{
			JPanel panel = new JPanel();
			panel.setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weightx = 1;
			gbc.weighty = 1;

			for (Group group: groups)
			{
				JPanel groupPanel = new JPanel();
				groupPanel.setLayout(new GridBagLayout());
				Border newBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), group.getTitle());
				groupPanel.setBorder(newBorder);
				int line = 0;

				for (Element element : group.getElements())
				{
					element.addToComponent(groupPanel, 0, line);
					line++;
				}

				panel.add(groupPanel, gbc);
				gbc.gridy++;
			}

			return wrapInScrollPane(panel);
		}
	}

	public static Builder builder()
	{
		return new Builder();
	}

	// Builder pattern end

}
