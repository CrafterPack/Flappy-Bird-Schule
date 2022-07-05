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
		super();
		player = new PlayerSounds();
		bgMusic = new BackgroundMusic();
		
		player.setJumpSoundVolume(0.3);
		player.setDieSoundVolume(0.7);
		player.setExplosionSound(0.7);
		bgMusic.setVolume(0.4);
		
		bgMusic.play();
	}
	
	public void jump() {
		player.playJumpSound();
	}
	
	public void die() {
		bgMusic.stop();
		player.playDieSound();
	}
	
	public void explode() {
		player.playExplosionSound();
	}
	
	public void restart() {
		bgMusic = new BackgroundMusic();
		bgMusic.play();
	}
	
	public void pause() {
		bgMusic.stop();
	}
	
	public void resume() {
		bgMusic.play();
	}
}
