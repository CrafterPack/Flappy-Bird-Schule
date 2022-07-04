/**
 * 
 */
package view.sounds;

import java.io.File;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.AudioClip;

/**
 * Klasse fuer Spieler-Sounds
 *
 * @author Simon Le
 * @version 04.07.2022
 */

public class PlayerSounds {

	private AudioClip jumpSound;
	private AudioClip dieSound;
	
	public PlayerSounds() {
		jumpSound = new AudioClip(new File("res\\sound\\Game\\player\\jumpsound.wav").toURI().toString());
		dieSound = new AudioClip(new File("res\\sound\\Game\\player\\diesound.wav").toURI().toString());
	}
	
	public void playJumpSound() {
		jumpSound.play();
	}
	
	public void playDieSound() {
		dieSound.play();
	}
	
	public void setJumpSoundVolume(double v) {
		jumpSound.setVolume(v);
	}
	
	public void setDieSoundVolume(double v) {
		dieSound.setVolume(v);
	}
}
