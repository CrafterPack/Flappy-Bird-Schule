package model;

import java.awt.Point;
import java.awt.Rectangle;

/**
 * Spiel-Logik
 *
 * @author Simon Le
 * @version 13.06.2022
 */

public class Model {

	private Rectangle player;
	private double playerPosY;
	private double acceleration;
	
	private Rectangle[][] roehrenArray;
	private boolean[] pipeScored;
	private int lueckenGroesse;
	
	private int score;
	
	/*
	 * Die Position der Hintergründe muss im Model bearbeitet werden, da der Hintergrund sich sonst 
	 * mit der Fps-Geschwindigkeit (z.B. 398808 FPS) statt der Tick-Geschwindigkeit (idealerweise 60 Ticks)
	 * bewegen würde.
	 */
	private int[] backgroundsPosX; 
	
	public Model() {
		
		//Instanzieren des Spieler und der Roehren
		playerPosY = 300f; //Starthoehe kann hier initialisiert werden
		player = new Rectangle(100, (int) playerPosY, 50, 50  );
		
		roehrenArray = new Rectangle[2][3];
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 3; j++) {
				roehrenArray[i][j] = new Rectangle();
			}
		}
		
		
		/*
		 * Fuer die zufaellige Roehrenluecke wird ein zufaelliger Integer fuer die Laenge 
		 * der oberen Roehre initialisiert.
		 * Die Laenge der unteren Roehre erschließt sich dann aus der Laenge der oberen.
		 */
		
		lueckenGroesse = 200; //Groesse der Luecke kann hier initialisiert werden
		
		int r = (int) (Math.random() * (640 - lueckenGroesse));
		roehrenArray[0][0].setBounds(481, 0, 80, r);
		roehrenArray[1][0].setBounds(481, r + lueckenGroesse, 80, 640 - r - lueckenGroesse);
		
		r = (int) (Math.random() * (640 - lueckenGroesse));
		roehrenArray[0][1].setBounds(781, 0, 80, r);
		roehrenArray[1][1].setBounds(781, r + lueckenGroesse, 80, 640 - r - lueckenGroesse);
		
		r = (int) (Math.random() * (640 - lueckenGroesse));
		roehrenArray[0][2].setBounds(1081, 0, 80, r);
		roehrenArray[1][2].setBounds(1081, r + lueckenGroesse, 80, 640 - r - lueckenGroesse );
		
		
		backgroundsPosX = new int[2];
		backgroundsPosX[0] = 0;
		backgroundsPosX[1] = 500;
		
		pipeScored = new boolean[3];
	}
	
	
	/**
	 * Methode fuer die Game Loop
	 * Hier bekommt der Spieler Gravitation und die Roehren bewegen sich
	 * 
	 * @version 13.06.2022
	 */
	public void tick() {
		acceleration += 0.15f;
		playerPosY+=acceleration;
		if (playerPosY <= 0f) { //Der Spieler kann nicht ueber die Decke hinaus fliegen
			playerPosY = 0f; 
			acceleration = 0f;
		}
		Point newPos = new Point();
		newPos.setLocation(player.getX(), playerPosY);
		player.setLocation(newPos);   
		
		for(int i = 0; i < 3; i++) {    
				if (roehrenArray[0][i].getX() <= -80) {
					int r = (int) (Math.random() * (640 - lueckenGroesse));    
					roehrenArray[0][i].setBounds(820, 0, 80, r);
					roehrenArray[1][i].setBounds(820, r + lueckenGroesse, 80, 640 - r - lueckenGroesse);
					pipeScored[i] = false; 
				}
				else {
					roehrenArray[0][i].setLocation((int) roehrenArray[0][i].getX() - 2  , (int) roehrenArray[0][i].getY());
					roehrenArray[1][i].setLocation((int) roehrenArray[1][i].getX() - 2  , (int) roehrenArray[1][i].getY());
				}
				
				if (pipeScored[i] != true && roehrenArray[0][i].getX() <= player.getX()) { 
					score++; 
					pipeScored[i] = true;
				}
		}
	}
	
	/**
	 * Methode, um den Hintergrund zu bewegen
	 * Idealerweise wuerde diese Methode in der Tick-Methode stehen, allerdings laeuft die 
	 * Tick-Methode erst, wenn man spielt und der Hintergrund bewegt sich bereits im Menue
	 *
	 * @version 30.05.2022
	 */
	public void moveBackground() {
		for(int i = 0; i < 2; i++) {
			if (backgroundsPosX[i] <= -500) {
				backgroundsPosX[i] = 499;
			}
			else {
				backgroundsPosX[i] -= 1;
			}
		}
	}
	
	/**
	 * Beim ausfuehren dieser Methode gewinnt der Spieler an Hoehe
	 * 
	 * @version 13.06.2022
	 */
	public void springen() {
		acceleration-=5f;
	}  
	
	/**
	 * ueberprueft, ob der Spieler eine Roehre beruehrt
	 *   
	 * @version 23.05.2022
	 */
	public boolean isPlayerColliding() {
		for(Rectangle[] r1 : roehrenArray) {
			for(Rectangle r2: r1) {
				if (r2.intersects(player)) {
					return true;
				}      
			}
		}
		
		return false;
	}
	
	public Rectangle getPlayerDimensions() {
		return player;
	}
	
	public Rectangle[][] getPipeArray() {
		return roehrenArray;
	}
	
	public int[] getBackgroundsPosX() {
		return backgroundsPosX;
	}
	
	public int getScore() {
		return score;
	}
}
