/**
 * 
 */
package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * JPanel, wenn man das Spiel pausiert
 * 
 * @author Simon Le
 * @version 15.06.2022
 *
 */
public class PauseMenu extends JPanel {
	
	private JLabel text;
	private JButton resumeButton, quitButton;
	
	public PauseMenu(Dimension windowSize) {
		super();
		
		text = new JLabel("Game Paused");
		text.setBounds((int) windowSize.getWidth() / 2 - 200, 250, 400, 100);
		text.setFont(new Font("Arial", Font.PLAIN, 40));
		text.setHorizontalAlignment(SwingConstants.CENTER);
		text.setVisible(true);
		add(text);
		
		resumeButton = new JButton("Resume");
		resumeButton.setBounds(40, 500, 190, 100);
		resumeButton.setVisible(true);
		add(resumeButton);
		
		quitButton = new JButton("Quit");
		quitButton.setBounds(250, 500, 190, 100);
		quitButton.setVisible(true);
		add(quitButton);
		
		setBounds(0, 0, (int) windowSize.getWidth(), (int) windowSize.getHeight());
		setLayout(null);
		setOpaque(false);
		setVisible(true);
	}
	
	public void addActionListener(ActionListener al) {
		resumeButton.addActionListener(al);
		quitButton.addActionListener(al);
	}
	
	public JButton getResumeButton() {
		return resumeButton;
	}
	
	public JButton getQuitButton() {
		return quitButton;
	}
}
