/**
 * 
 */
package view.menu;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * JPanel, wenn man das Spiel pausiert
 * 
 * @author Simon Le
 * @version 17.06.2022
 *
 */
public class PauseMenu extends MenuSuperClass {
	
	public PauseMenu(Dimension windowSize, ActionListener al) {
		super(windowSize, "Resume", "Quit", al);
		
		super.setLabelText("Game Paused");
	}
	
	public JButton getResumeButton() {
		return super.getButton1();
	}
	
	public JButton getQuitButton() {
		return super.getButton2();
	}
}
