/**
 * 
 */
package model;

/**
 * Tupel fuer das Leaderboard
 *
 * @author Simon Le
 * @version 21.06.2022
 */

public class Tuple {
	
	private String name;
	private int score;
	
	public Tuple(String n, int s) {
		name = n;
		score = s;
	}
	
	/**
	 * Getter-Methoden
	 */
	
	public String getName() {
		return name;
	}
	
	public int getScore() {
		return score;
	}
	
	/**
	 * Setter-Methode, die den Score initialisiert 
	 *
	 * @param s
	 * @version 21.06.2022
	 */
	public void setScore(int s) {
		score = s;
	}
}
