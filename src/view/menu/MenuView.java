/**
 * 
 */
package view.menu;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

/**
 * Klasse fuer das Menue
 *
 * @author Simon Le
 * @version 26.06.2022
 */

public class MenuView extends MenuSuperClass{
	
	private JTextField nameInput;
	
	public MenuView(Dimension windowSize, ActionListener al) {
		super(windowSize, "Start", "Quit", al);
		
		//Im Menue ist das JLabel das Spiellogo
		super.setLabelBounds(38, 40, 404, 200);
		super.setLabelIcon(new ImageIcon(new ImageIcon("res\\texture\\logo.png").getImage()));
		super.getLabel().setOpaque(false);
		
		//Die MenuView hat noch ein JTextField, um den Spielernamen einzugeben
		nameInput = new JTextField();
		nameInput.setBounds(40, 360, 400, 50);
		nameInput.setFont(new Font("Arial", Font.PLAIN, 40));

		add(nameInput);
	}
	
	/**
	 * Getter-Methoden
	 */
	
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
