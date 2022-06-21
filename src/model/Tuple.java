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
	
	public String getName() {
		return name;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int s) {
		score = s;
	}
}
