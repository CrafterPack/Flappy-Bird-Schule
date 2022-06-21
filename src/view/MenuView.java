/**
 * 
 */
package view;

import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

/**
 * Klasse fuer das Menue
 *
 * @author Simon Le
 * @version 21.06.2022
 */

public class MenuView extends MenuSuperClass{
	
	private JTextField nameInput;
	
	public MenuView(Dimension windowSize, ActionListener al) {
		super(windowSize, "Start", "Quit", al);
		
		super.setLabelBounds(40, 40, 400, 200);
		super.getLabel().setBackground(Color.RED);
		super.getLabel().setOpaque(true);
		super.setLabelText("Logo");
		
		nameInput = new JTextField();
		nameInput.setBounds(40, 430, 400, 50);
		nameInput.setFont(new Font("Arial", Font.PLAIN, 40));

		add(nameInput);
	}
	
	public JButton getStartButton() {
		return super.getButton1();
	}
	
	public JButton getQuitButton() {
		return super.getButton2();
	}
	
	public JTextField getNameInput() {
		return nameInput;
	}
}
