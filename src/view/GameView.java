/**
 * 
 */
package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Klasse fuer die Spieloberflaeche
 *
 * @author Simon Le
 * @version 31.05.2022
 */

public class GameView extends JPanel {

	private JLabel player;
	private JLabel[][] roehrenArray;
	private JLabel[] backgrounds;
	private JLabel scoreCounter;

	public GameView(Dimension windowSize, Rectangle playerDimensions, Rectangle[][] roehrenArrayDimensions, int[] backgroundsPosX) {
		super();

		// Instanziieren des Spielers
		player = new JLabel();
		player.setBackground(Color.YELLOW);
		player.setBounds(playerDimensions);
		player.setOpaque(true);
		player.setVisible(true);
		add(player);

		scoreCounter = new JLabel();
		scoreCounter.setBounds((int) windowSize.getWidth() / 2 - 50, 100, 100, 100);
		scoreCounter.setFont(new Font("Arial", Font.PLAIN, 50));
		scoreCounter.setHorizontalAlignment(SwingConstants.CENTER);
		add(scoreCounter);

		// Instanziieren der Roehren
		roehrenArray = new JLabel[2][3];
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 3; j++) {
				roehrenArray[i][j] = new JLabel();
				roehrenArray[i][j].setBackground(Color.GREEN);
				roehrenArray[i][j].setBounds(roehrenArrayDimensions[i][j]);
				roehrenArray[i][j].setOpaque(true);
				roehrenArray[i][j].setVisible(true);

				add(roehrenArray[i][j]);
			}
		}		
		
		// Instanziieren des Hintergrunds
		backgrounds = new JLabel[2];
		for (int i = 0; i < 2; i++) {
			backgrounds[i] = new JLabel();
			backgrounds[i].setBounds(backgroundsPosX[i], 0, 500, 640);
			backgrounds[i].setIcon(new ImageIcon(new ImageIcon("res\\texture\\Game\\background.png").getImage()));
			backgrounds[i].setVisible(true);

			add(backgrounds[i]);
		}
		
		

		setBounds(0, 0, (int) windowSize.getWidth(), (int) windowSize.getHeight());
		setLayout(null);
		setVisible(true);
	}

	/**
	 * Methode, um den Spieler zu bewegen
	 *
	 * @param y
	 * @version 23.05.2022
	 */
	public void movePlayer(int y) {
		player.setLocation(player.getX(), y);
	}

	/**
	 * Methode, um alle Roehren zu bewegen
	 *
	 * @param roehrenArrayDimensions
	 * @version 23.05.2022
	 */
	public void movePipes(Rectangle[][] roehrenArrayDimensions) {
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 3; j++) {
				roehrenArray[i][j].setBounds(roehrenArrayDimensions[i][j]);
			}
		}
	}

	/**
	 * Methode, um den Hintergrund zu bewegen
	 *
	 * @param backgroundsPosX
	 * @version 30.05.2022
	 */
	public void moveBackground(int[] backgroundsPosX) {

		for (int i = 0; i < 2; i++) {
			backgrounds[i].setLocation(backgroundsPosX[i], 0);
		}
	}
	
	/**
	 * Methode, um den Scoreboard zu aktualisieren
	 *
	 * @param score
	 * @version 31.05.2022
	 */
	public void updateScore(int score) {
		scoreCounter.setText(Integer.toString(score));
	}
	
	/**
	 * Methode, um die Punktzahlanzeige zu entfernen
	 *
	 * @version 01.06.2022
	 */
	public void removeScore() {
		scoreCounter.setText("");
	}
}
