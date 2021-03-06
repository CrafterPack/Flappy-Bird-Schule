package model;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * Spiel-Logik
 *
 * @author Simon Le
 * @version 08.07.2022
 */

public class Model {

	private Rectangle player;
	private double playerPosY;
	private double acceleration;
	private boolean invincible;
	
	private Rectangle[][] roehrenArray;
	private int[] pipeVerticalVelocity; //Ab einer bestimmten Punktzahl bewegen sich die Roehren vertikal --> zu Beginn 0
	private boolean[] pipeScored;
	private int lueckenGroesse;
	
	private int score;
	
	/*
	 * Die Position der Hintergr?nde muss im Model bearbeitet werden, da der Hintergrund sich sonst 
	 * mit der Fps-Geschwindigkeit (z.B. 398808 FPS) statt der Tick-Geschwindigkeit (idealerweise 60 Ticks)
	 * bewegen w?rde.
	 */
	private int[] backgroundsPosX; 
	
	private Timer timer;
	private int invincibilityTime;
	
	public Model() {
		
		//Instanzieren des Spieler und der Roehren
		playerPosY = 300f; //Starthoehe kann hier initialisiert werden
		player = new Rectangle(100, (int) playerPosY, 133, 50);
		
		roehrenArray = new Rectangle[2][3];
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 3; j++) {
				roehrenArray[i][j] = new Rectangle();
			}
		}
		
		
		/*
		 * Fuer die zufaellige Roehrenluecke wird ein zufaelliger Integer fuer die Laenge 
		 * der oberen Roehre initialisiert.
		 * Die Laenge der unteren Roehre erschlie?t sich dann aus der Laenge der oberen.
		 */
		
		lueckenGroesse = 200; //Groesse der Luecke kann hier initialisiert werden
		
		int r = (int) (Math.random() * (640 - lueckenGroesse));
		roehrenArray[0][0].setBounds(481, 0, 80, r);
		roehrenArray[1][0].setBounds(481, r + lueckenGroesse, 80, 640 - r - lueckenGroesse);
		
		r = (int) (Math.random() * (640 - lueckenGroesse));
		roehrenArray[0][1].setBounds(831, 0, 80, r);
		roehrenArray[1][1].setBounds(831, r + lueckenGroesse, 80, 640 - r - lueckenGroesse);
		
		r = (int) (Math.random() * (640 - lueckenGroesse));
		roehrenArray[0][2].setBounds(1181, 0, 80, r);
		roehrenArray[1][2].setBounds(1181, r + lueckenGroesse, 80, 640 - r - lueckenGroesse );
		
		//Instanziieren des Hintergrundarrays
		backgroundsPosX = new int[2];
		backgroundsPosX[0] = 0;
		backgroundsPosX[1] = 500;
		
		pipeScored = new boolean[3];
		pipeVerticalVelocity = new int[3];
		
		/*
		 * Bereits zu Beginn erhalten die Roehren eine vertikale Geschwindigkeit, allerdings bewegen sie sich erst
		 * ab einem score >= 10 mit der jeweiligen Geschwindigkeit 
		 */
		for (int i = 0; i < pipeVerticalVelocity.length; i++) {
			if(Math.random() < 0.5f) pipeVerticalVelocity[i] = -1;
			else pipeVerticalVelocity[i] = +1;
		}
		
		invincibilityTime = 10;
	}
	
	
	/**
	 * Methode fuer die Game Loop
	 * Hier bekommt der Spieler Gravitation und die Roehren bewegen sich
	 * 
	 * @version 08.07.2022
	 */
	public void tick() {
		addPlayerGravitation();   
		movePipes();
		
		if (score >= 20 && ((double) score/20) % 1 == 0 && !invincible) {
			System.out.println("Bonus!");
			invincible = true;
			
			timer = new Timer(1000, new ActionListener() {
			      public void actionPerformed(ActionEvent evt) {
			          if (invincibilityTime == 0) {
			        	  System.out.println("Time out");
			        	  invincible = false;
			        	  invincibilityTime = 10;
			        	  timer.stop();
			          }
			          else {
			        	  invincibilityTime--;
			        	  System.out.println("bonus time left: " + invincibilityTime);
			          }
			      }
			  });
			timer.start();
		}
	}
	
	/**
	 * Methode, um dem Spieler Gravitation zu geben
	 *
	 * @version 26.06.2022
	 */
	public void addPlayerGravitation() {
		acceleration += 0.15f;
		playerPosY+=acceleration;
		if (playerPosY <= 0f) { //Der Spieler kann nicht ueber die Decke hinaus fliegen
			playerPosY = 0f; 
			acceleration = 0f;
		}
		Point newPos = new Point();
		newPos.setLocation(player.getX(), playerPosY);
		player.setLocation(newPos);  
	}
	
	/**
	 * Methode, um die Roehren nach links zu verschieben (der Spieler bleibt ja auf seiner Stelle und bewegt sich nur vertikal)
	 *
	 * @version 26.06.2022
	 */
	private void movePipes() {
		for(int i = 0; i < 3; i++) {
			/*
			 * upperPipe und lowerPipe werden initialisiert, damit roehrenArray[0][i] bzw. roehrenArray[1][i] nicht st?ndig wiederholt werden
			 */
			Rectangle upperPipe = roehrenArray[0][i];
			Rectangle lowerPipe = roehrenArray[1][i];
			if (upperPipe.getX() <= -80) {
				int r = (int) (Math.random() * (640 - lueckenGroesse));    
				upperPipe.setBounds(970, 0, 80, r);
				lowerPipe.setBounds(970, r + lueckenGroesse, 80, 640 - r - lueckenGroesse);
				pipeScored[i] = false; 
			}
			else {
				upperPipe.setLocation((int) upperPipe.getX() - 2  , (int) upperPipe.getY());
				lowerPipe.setLocation((int) lowerPipe.getX() - 2  , (int) lowerPipe.getY());
				
				if (score >= 10) { //Ab score >= 10 bewegen sich die Roehren vertikal
					movePipesVertically(i);
				}
			}
			
			if (pipeScored[i] != true && roehrenArray[0][i].getX() <= player.getX()) { 
				score++; 
				pipeScored[i] = true;
			}
		}
	}
	
	/**
	 * Ab einer bestimmten Punktzahl bewegen sich die Roehren vertikal
	 * 
	 * @param index
	 * @version 14.06.2022
	 */
	private void movePipesVertically(int index) {
		Rectangle upperPipe = roehrenArray[0][index];
		Rectangle lowerPipe = roehrenArray[1][index];
		
		if (upperPipe.getHeight() >= 640 - lueckenGroesse) pipeVerticalVelocity[index] = -1;
		else if (upperPipe.getHeight() <= 0f) pipeVerticalVelocity[index] = +1;
		
		upperPipe.setBounds((int) upperPipe.getX(), 0, 80, (int) upperPipe.getHeight() + pipeVerticalVelocity[index]);
		lowerPipe.setBounds((int) lowerPipe.getX(), (int) lowerPipe.getY() + pipeVerticalVelocity[index], 80, (int) lowerPipe.getHeight() - pipeVerticalVelocity[index]);
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
		if (!invincible)
			for(Rectangle[] r1 : roehrenArray) {
				for(Rectangle r2: r1) {
					if (r2.intersects(player)) {
						return true;
					}      
				}
			}
		
		return false;
	}
	
	/**
	 * Getter-Methoden
	 */
	
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
	
	public boolean isInvincible() {
		return invincible;
	}
	
	public int getInvincibilityTime() {
		return invincibilityTime;
	}
}
