package guesser;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import de.cgarbs.lib.exception.DataException;
import de.cgarbs.lib.exception.GlueException;
import de.cgarbs.lib.exception.ValidationErrorList;
import de.cgarbs.lib.glue.Binding;
import de.cgarbs.lib.glue.Glue;
import de.cgarbs.lib.i18n.Resource;
import de.cgarbs.lib.ui.layout.SimpleVerticalLayout;

public class GuesserUI extends JFrame
{
	private static final long serialVersionUID = 1L;

	// these need to be instance variables to be accessed by any ActionListeners
	private Glue<GuesserModel> glue;
	private Resource R;

	/**
	 * Main entry point to run the UI
	 * @param arguments
	 */
	public static void main(String[] arguments)
	{
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	GuesserUI frame = new GuesserUI();
                        frame.setVisible(true);
                } catch (Exception e) {
                        e.printStackTrace();
                }
            }
        });
	}

	public GuesserUI() throws DataException, GlueException
	{
		// get our resource file
		R = new Resource("guesser.Guesser");

		// create a new DataModel
		GuesserModel dataModel = new GuesserModel();

		// create the glue to bind the model to the UI
		glue = new Glue<GuesserModel>(dataModel);

		// create bindings for the attributes
		Binding b_the_number = glue.addBinding(GuesserModel.THE_NUMBER);

		// create a Swing container with the model attributes
		Container jDataModel = SimpleVerticalLayout.builder()
				.startNextGroup(R._("GRP_instructions"))
				.addAttribute(b_the_number)
				.build();


		// create a button to check the model
        JButton btnCheck = new JButton(R._("BTN_check"));
        btnCheck.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent arg0)
           {
                try
                {
                    glue.validate();

                    JOptionPane.showMessageDialog(
                            GuesserUI.this,
                            R._("TXT_correct_guess")
                            );
                }
                catch (ValidationErrorList e)
                {
                    JOptionPane.showMessageDialog(
                            GuesserUI.this,
                            // behold: a resource with a parameter
                            R._("TXT_wrong_guess", e.getErrorList())
                            );
                }
            }
        });

		// set up other Swing stuff for the UI
        setTitle(R._("TIT_windowtitle"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(500, 300));
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        contentPane.setLayout(gbl_contentPane);

        // add the data model to the UI
        GridBagConstraints gbc_pnlDataModel = new GridBagConstraints();
        gbc_pnlDataModel.fill = GridBagConstraints.BOTH;
        gbc_pnlDataModel.gridx = 0;
        gbc_pnlDataModel.gridy = 0;
        gbc_pnlDataModel.weightx = 1;
        gbc_pnlDataModel.weighty = 1;
        contentPane.add(jDataModel, gbc_pnlDataModel);

        // add the button to the UI
        GridBagConstraints gbc_pnlButton = new GridBagConstraints();
        gbc_pnlButton.fill = GridBagConstraints.BOTH;
        gbc_pnlButton.gridx = 0;
        gbc_pnlButton.gridy = 1;
        gbc_pnlButton.weightx = 1;
        gbc_pnlButton.weighty = 1;
        contentPane.add(btnCheck, gbc_pnlButton);

	}
}
