/**
 * 
 */
package view;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * JPanel erscheint wenn man stirbt
 *
 * @author Simon Le
 * @version 22.06.2022
 */

public class DeathScreen extends MenuSuperClass{
	
	private JButton leaderBoardButton;

	public DeathScreen(Dimension windowSize, ActionListener al) {
		super(windowSize, "Restart", "Quit", al);
		
		leaderBoardButton = new JButton("Leaderboard");
		leaderBoardButton.setBounds(40, 430, 400, 50);
		leaderBoardButton.addActionListener(al);
		add(leaderBoardButton);
	}
	
	public void setScore(int score, int highscore) {
		super.setLabelText("<html>You died. Score: " + score + "<br/> Your highscore: " + highscore + "<html>");
	}
	
	public JButton getRestartButton() {
		return super.getButton1();
	}
	
	public JButton getQuitButton() {
		return super.getButton2();
	}
	
	public JButton getOpenLeaderBoardButton() {
		return leaderBoardButton;
	}
}
