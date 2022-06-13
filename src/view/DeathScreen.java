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
 * JPanel erscheint wenn man stirbt
 *
 * @author Simon Le
 * @version 01.06.2022
 */

public class DeathScreen extends JPanel{
	
	private JLabel text;
	private JButton restartButton, quitButton;

	public DeathScreen(Dimension windowSize) {
		super();
		
		text = new JLabel();
		text.setBounds( (int) windowSize.getWidth() / 2 - 200, 250, 400, 100);
		text.setFont(new Font("Arial", Font.PLAIN, 40));
		text.setHorizontalAlignment(SwingConstants.CENTER);
		text.setVisible(true);
		add(text);
		
		restartButton = new JButton("Restart");
		restartButton.setBounds(40, 500, 190, 100);
		restartButton.setVisible(true);
		add(restartButton);
		
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
		restartButton.addActionListener(al);
		quitButton.addActionListener(al);
	}
	
	public void setScore(int score) {
		text.setText("You died. Score: " + score);
	}
	
	public JButton getRestartButton() {
		return restartButton;
	}
	
	public JButton getQuitButton() {
		return quitButton;
	}
}
