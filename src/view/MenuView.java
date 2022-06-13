/**
 * 
 */
package view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;

/**
 * Klasse fuer das Menue
 *
 * @author Simon Le
 * @version 27.05.2022
 */

public class MenuView extends JPanel{
	
	private JLabel banner;
	private JButton startButton, quitButton;
	
	public MenuView(Dimension windowSize) {
		
		banner = new JLabel();
		startButton = new JButton("Start");
		quitButton = new JButton("Quit");
		
		banner.setBounds(40, 40, 400, 200);
		banner.setBackground(Color.RED);
		banner.setText("Logo");
		banner.setOpaque(true);
		banner.setVisible(true);
		
		startButton.setBounds(40, 500, 190, 100);
		startButton.setVisible(true);
		
		quitButton.setBounds(250, 500, 190, 100);
		quitButton.setVisible(true);
		
		add(banner);
		add(startButton);
		add(quitButton);
		setBounds(0, 0, (int) windowSize.getWidth(), (int) windowSize.getHeight());
		setLayout(null);
		setOpaque(false);
		setVisible(true);
	}
	
	public void addActionListener(ActionListener al) {
		startButton.addActionListener(al);
		quitButton.addActionListener(al);
	}
	
	public JButton getStartButton() {
		return startButton;
	}
	
	public JButton getQuitButton() {
		return quitButton;
	}
}
