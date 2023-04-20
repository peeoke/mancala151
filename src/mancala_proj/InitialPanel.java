package mancala_proj;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * This class implements the initial screen of the Mancala game.
 * @author pebbles
 * @version 4/12/2023
 */

public class InitialPanel extends JPanel{
	/**
	 * Creates two buttons that determine the style of the Mancala board
	 * @param s1 a style of the Mancala board
	 * @param s2 a style of the Mancala board
	 */
	public InitialPanel(StyleManager s1, StyleManager s2) {
		JLabel stylePrompt = new JLabel("Choose a style:");
		//change names of buttons after we figure out the styles
		JButton style1Button = new JButton(s1.getName());
		JButton style2Button = new JButton(s2.getName());
		style1Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				//tell MancalaTest.java that style 1 was chosen somehow --> model?
				setVisible(false); //hides the panel
			}
		});
		style2Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				//tell MancalaTest.java that style 2 was chosen somehow
				setVisible(false); //hides the panel
			}
		});
		add(stylePrompt);
		add(style1Button);
		add(style2Button);
	}
}