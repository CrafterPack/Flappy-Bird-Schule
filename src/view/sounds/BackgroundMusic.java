/**
 * 
 */
package view.sounds;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Klasse fuer die Hintergrundmusik
 *
 * @author Simon Le
 * @version 04.07.2022
 */

public class BackgroundMusic {

	private ArrayList<Media> musicList;
	private MediaPlayer mediaplayer;
	private int currentMusicIndex;
	
	public BackgroundMusic() {
		//Instanziieren
		musicList = new ArrayList<Media>();
		musicList.add(new Media(new File("res\\sound\\Game\\background\\music1.wav").toURI().toString()));
		musicList.add(new Media(new File("res\\sound\\Game\\background\\music2.wav").toURI().toString()));
		musicList.add(new Media(new File("res\\sound\\Game\\background\\music3.wav").toURI().toString()));
		musicList.add(new Media(new File("res\\sound\\Game\\background\\music4.wav").toURI().toString()));
		Collections.shuffle(musicList); //Musikliste wird gemischt
		
		mediaplayer = new MediaPlayer(musicList.get(currentMusicIndex));
		mediaplayer.setOnEndOfMedia(new Runnable() {
			@Override
			public void run() {
				nextMusic();
			}
		});
	}
	
	/**
	 * Methode, um die Musik abzuspielen 
	 *
	 * @version 04.07.2022
	 */
	public void play() {
		mediaplayer.play();
	}
	
	/**
	 * Methode, um die Musik zu pausieren 
	 *
	 * @version 04.07.2022
	 */
	public void stop() {
		mediaplayer.stop();
	}
	
	/**
	 * Methode, um die Lautstaerke der Musik einzustellen 
	 *
	 * @version 04.07.2022
	 */
	public void setVolume(double v) {
		mediaplayer.setVolume(v);
	}
	
	/**
	 * sobald ein Lied vorbei ist wird das naechste Lied abgespielt  
	 *
	 * @version 04.07.2022
	 */
	private void nextMusic() {
		mediaplayer.stop();
		if (currentMusicIndex == musicList.size() - 1) currentMusicIndex = 0;
		else currentMusicIndex++;
		mediaplayer = new MediaPlayer(musicList.get(currentMusicIndex));
		mediaplayer.setOnEndOfMedia(new Runnable() {
			@Override
			public void run() {
				nextMusic();
			}
		});
		mediaplayer.play();
	}
}
