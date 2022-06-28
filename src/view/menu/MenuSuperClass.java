/**
 * 
 */
package view.menu;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Oberklasse fuer die Menues
 * 
 * @author Simon Le
 * @version 26.06.2022
 *
 */
public abstract class MenuSuperClass extends JPanel{
	
	private JLabel label;
	private JButton button1, button2, leaderBoardButton;
	
	public MenuSuperClass(Dimension windowSize, String button1Text, String button2Text, ActionListener al) {
		super();
		
		label = new JLabel();
		label.setBounds((int) windowSize.getWidth() / 2 - 200, 250, 400, 100);
		label.setFont(new Font("Arial", Font.PLAIN, 40));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setVisible(true);
		add(label);
		
		button1 = new JButton(button1Text);
		button1.setBounds(40, 500, 190, 100);
		button1.setVisible(true);
		add(button1);
		button1.addActionListener(al);
		
		button2 = new JButton(button2Text);
		button2.setBounds(250, 500, 190, 100);
		button2.setVisible(true);
		add(button2);
		button2.addActionListener(al);
		
		leaderBoardButton = new JButton("Leaderboard");
		leaderBoardButton.setBounds(40, 430, 400, 50);
		leaderBoardButton.setVisible(true);
		add(leaderBoardButton);
		leaderBoardButton.addActionListener(al);

		setBounds(0, 0, (int) windowSize.getWidth(), (int) windowSize.getHeight());
		setLayout(null);
		setOpaque(false);
		setVisible(true);
	}
	
	protected void setLabelBounds(int x, int y, int width, int height) {
		label.setBounds(x, y, width, height);
	}
	
	protected void setLabelText(String text) {
		label.setText(text);
	}
	
	protected void setLabelIcon(Icon icon) {
		label.setIcon(icon);
	}
	
	protected JLabel getLabel() {
		return label;
	}
	
	protected JButton getButton1() {
		return button1;
	}
	
	protected JButton getButton2() {
		return button2;
	}
	
	public JButton getOpenLeaderBoardButton() {
		return leaderBoardButton;
	}
}
