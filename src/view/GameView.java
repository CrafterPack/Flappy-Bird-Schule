/**
 * 
 */
package view;

import java.awt.Font;
import java.awt.Image;
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
 * @version 08.07.2022
 */

public class GameView extends JPanel {

	private JLabel player;
	private JLabel[][] roehrenArray;
	private JLabel[] backgrounds;
	private JLabel scoreCounter, notification;

	public GameView(Dimension windowSize, Rectangle playerDimensions, Rectangle[][] roehrenArrayDimensions, int[] backgroundsPosX) {
		super();

		// Instanziieren des Spielers
		player = new JLabel();
		player.setBounds(playerDimensions);
		player.setIcon(new ImageIcon(new ImageIcon("res\\texture\\Game\\Car.png").getImage()));
		player.setVisible(true);
		add(player);

		scoreCounter = new JLabel();
		scoreCounter.setBounds((int) windowSize.getWidth() / 2 - 50, 100, 100, 100);
		scoreCounter.setFont(new Font("Arial", Font.PLAIN, 50));
		scoreCounter.setHorizontalAlignment(SwingConstants.CENTER);
		add(scoreCounter);
		
		notification = new JLabel();
		notification.setBounds((int) windowSize.getWidth() / 2 - 200, 10, 400, 100);
		notification.setFont(new Font("Arial", Font.PLAIN, 40));
		notification.setHorizontalAlignment(SwingConstants.CENTER);
		add(notification);

		// Instanziieren der Roehren
		roehrenArray = new JLabel[2][3];
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 3; j++) {
				roehrenArray[i][j] = new JLabel();
				roehrenArray[i][j].setIcon(new ImageIcon(new ImageIcon("res\\texture\\Game\\Pipe" + (i+1) + ".png").getImage().getScaledInstance((int) roehrenArrayDimensions[i][j].getWidth(), (int) roehrenArrayDimensions[i][j].getHeight() + 1, Image.SCALE_DEFAULT))); //Da Icons nicht die Laenge oder Hohe 0 haben duerfen, steht bei getHeight() +1
				roehrenArray[i][j].setBounds(roehrenArrayDimensions[i][j]);
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
	
	/**
	 * Methode, um das Icon der Roehren neuzuskalieren, wenn die sich vertikal bewegen oder zurueckgesetzt werden
	 *
	 * @param i
	 * @param j
	 * @param newWidth
	 * @param newHeight
	 * @version 26.06.2022
	 */
	public void resizePipeIcon(int i, int j, int newWidth, int newHeight) {
		roehrenArray[i][j].setIcon(new ImageIcon(new ImageIcon("res\\texture\\Game\\Pipe" + (i+1) + ".png").getImage().getScaledInstance((int) newWidth, (int) newHeight + 1, Image.SCALE_DEFAULT))); //Da Icons nicht die Laenge oder Hohe 0 haben duerfen, steht bei getHeight() +1
	}
	
	/**
	 * Methode, um dem Spieler eine Benachrichtigung zu geben
	 * Der Spieler bekommt alle 20 Punkte einen Bonus, bei dem er fuer eine bestimmte Zeit durch die Roehren fliegen kann
	 * Diese Methode zeigt dem Spieler dann die verbleibende Zeit  
	 *
	 * @param text
	 * @version 08.07.2022
	 */
	public void setNotification(String text) {
		notification.setText(text);
	}
}
