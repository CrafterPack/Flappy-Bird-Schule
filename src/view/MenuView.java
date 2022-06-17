/**
 * 
 */
package view;

import javax.swing.JButton;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;

/**
 * Klasse fuer das Menue
 *
 * @author Simon Le
 * @version 17.06.2022
 */

public class MenuView extends MenuSuperClass{
	
	public MenuView(Dimension windowSize, ActionListener al) {
		super(windowSize, "Start", "Quit", al);
		
		super.setLabelBounds(40, 40, 400, 200);
		super.getLabel().setBackground(Color.RED);
		super.getLabel().setOpaque(true);
		super.setLabelText("Logo");
	}
	
	public JButton getStartButton() {
		return super.getButton1();
	}
	
	public JButton getQuitButton() {
		return super.getButton2();
	}
}
