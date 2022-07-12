/**
 * 
 */
package view.sounds;

import java.io.File;

import javafx.scene.media.AudioClip;

/**
 * Klasse fuer Spieler-Sounds
 *
 * @author Simon Le
 * @version 04.07.2022
 */

public class PlayerSounds {

	private AudioClip jumpSound, dieSound, explosionSound;
	
	public PlayerSounds() {
		//Instanziieren
		jumpSound = new AudioClip(new File("res\\sound\\Game\\player\\jumpsound.wav").toURI().toString());
		dieSound = new AudioClip(new File("res\\sound\\Game\\player\\diesound.wav").toURI().toString());
		explosionSound = new AudioClip(new File("res\\sound\\Game\\player\\explosionSound.wav").toURI().toString());
	}
	
	/**
	 * Methode, um den Spring-Sound abzuspielen 
	 *
	 * @version 04.07.2022
	 */
	public void playJumpSound() {
		jumpSound.play();
	}
	
	/**
	 * Methode, um den Sterbe-Sound abzuspielen 
	 *
	 * @version 04.07.2022
	 */
	public void playDieSound() {
		dieSound.play();
	}
	
	/**
	 * Methode, um den Explosionssound abzuspielen 
	 *
	 * @version 04.07.2022
	 */
	public void playExplosionSound() {
		explosionSound.play();
	}
	
	/**
	 * Setter-Methoden fuer die Lautstarken der einzelnen Sounds
	 */
	
	public void setJumpSoundVolume(double v) {
		jumpSound.setVolume(v);
	}
	
	public void setDieSoundVolume(double v) {
		dieSound.setVolume(v);
	}
	
	public void setExplosionSound(double v) {
		explosionSound.setVolume(v);
	}
}
