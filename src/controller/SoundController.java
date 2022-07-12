/**
 * 
 */
package controller;

import javafx.embed.swing.JFXPanel;
import view.sounds.BackgroundMusic;
import view.sounds.PlayerSounds;

/**
 * Controller-Klasse fuer Sounds
 *
 * @author Simon Le
 * @version 04.07.2022
 */

public class SoundController extends JFXPanel {

	private PlayerSounds player;
	private BackgroundMusic bgMusic;
	
	public SoundController() {
		//Instanziierung
		super();
		player = new PlayerSounds();
		bgMusic = new BackgroundMusic();
		
		//Hier kann die Lautstaerke der einzelnen Sound-Elemente angepasst werden
		player.setJumpSoundVolume(0.3);
		player.setDieSoundVolume(0.7);
		player.setExplosionSound(0.7);
		bgMusic.setVolume(0.6);
		
		bgMusic.play();
	}
	
	/**
	 * Methode, um den Spring-Sound abzuspielen
	 *
	 * @version 04.07.2022
	 */
	public void jump() {
		player.playJumpSound();
	}
	
	/**
	 * Methode, um den Sterbe-Sound abzuspielen
	 *
	 * @version 04.07.2022
	 */
	public void die() {
		bgMusic.stop();
		player.playDieSound();
	}
	
	/**
	 * Wenn der Spieler am Boden aufkommt, explodiert er
	 *
	 * @version 04.07.2022
	 */
	public void explode() {
		player.playExplosionSound();
	}
	
	/**
	 * Startet der Spieler das Spiel neu, wird die Hintergrundmusikliste neugemischt und abgespielt
	 *
	 * @version 04.07.2022
	 */
	public void restart() {
		bgMusic = new BackgroundMusic();
		bgMusic.play();
	}
	
	/**
	 * Methode, um die Hintergrundmusik zu pausieren
	 *
	 * @version 04.07.2022
	 */
	public void pause() {
		bgMusic.stop();
	}
	
	/**
	 * Methode, um die Hintergrundmusik fortzufahren
	 *
	 * @version 04.07.2022
	 */
	public void resume() {
		bgMusic.play();
	}
}
