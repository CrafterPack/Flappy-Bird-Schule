/**
 * 
 */
package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * In dieser Klasse wird das Leaderboard gespeichert
 * Damit man das Leaderboard nach Schliessen des Spiel beibehaelt, wird es in einer JSON-Datei gespeichert
 * 
 * @author Simon Le
 * @version 21.06.2022
 */

public class Leaderboard {
	
	private ArrayList<Tuple> list; 
	private String currentPlayerName;
	
	public Leaderboard() {
		list = new ArrayList<Tuple>();
		
		/*
		 * Hier wird die bestehende Leaderboard-Liste aus der JSON-Datei geladen
		 * Allerdings wird zuerst geprueft, ob die Datei existiert
		 */
		
		File file = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "Documents" + System.getProperty("file.separator") + "FlappyBirdLeaderBoard.json");
		
		if (file.exists()) {
			JSONParser jParser = new JSONParser();
			
			JSONArray array = null;
			try {
				array = (JSONArray) jParser.parse(new FileReader(file));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			for(Object o : array) {
				JSONObject tmpObj = (JSONObject) o;
				list.add(new Tuple((String) tmpObj.get("Name"), (int) (long) tmpObj.get("Score")));
			}
		}
	}
	
	public void setPlayerName(String name) {
		currentPlayerName = name;
	}
	
	/**
	 * Methode, um einen neuen Highscore hinzuzufuegen
	 * Hat der Spieler bereits einen Highscore, wird der alte ueberschrieben, sonst wird ein neues Tupel mit dem Namen und Highscore hinzugefuegt
	 * Danach wird die neue Liste sofort in der JSON-Datei gespeichert (falls das Programm sich ploetzlich schliessen sollte)
	 *
	 * @param highscore
	 * @throws IOException
	 * @version 21.06.2022
	 */
	public void addPlayerToLeaderBoard(int highscore) throws IOException {
		boolean added = false;
		
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getName().equals(currentPlayerName)) {
				if (highscore > list.get(i).getScore()) {
					list.get(i).setScore(highscore);
				}
				added = true;
			}
		}
		
		if (!added)
			list.add(new Tuple(currentPlayerName, highscore));
		
		File file = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "Documents" + System.getProperty("file.separator") + "FlappyBirdLeaderBoard.json");
		
		if (!file.exists())
			file.createNewFile();
		
		JSONArray array = new JSONArray();
		
		for (int i = 0; i < list.size(); i++) {
			JSONObject tmpObj = new JSONObject();
			tmpObj.put("Name", list.get(i).getName());
			tmpObj.put("Score", list.get(i).getScore());
			
			array.add(tmpObj);
		}
		
		BufferedWriter bfWr = new BufferedWriter(new FileWriter(file));
		bfWr.write(array.toJSONString());
		bfWr.close();
	}
	
	public int getHighScore() {
		for (int i = 0; i < this.list.size(); i++) {
			if (list.get(i).getName().equals(currentPlayerName))
				return list.get(i).getScore();
		}
		
		return 0;
	}
}
