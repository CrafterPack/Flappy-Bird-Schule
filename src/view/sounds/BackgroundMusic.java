/**
 * 
 */
package view.sounds;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import javafx.embed.swing.JFXPanel;
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
		musicList = new ArrayList<Media>();
		musicList.add(new Media(new File("res\\sound\\Game\\background\\music1.wav").toURI().toString()));
		musicList.add(new Media(new File("res\\sound\\Game\\background\\music2.wav").toURI().toString()));
		musicList.add(new Media(new File("res\\sound\\Game\\background\\music3.wav").toURI().toString()));
		musicList.add(new Media(new File("res\\sound\\Game\\background\\music4.wav").toURI().toString()));
		Collections.shuffle(musicList);
		
		mediaplayer = new MediaPlayer(musicList.get(currentMusicIndex));
		mediaplayer.setOnEndOfMedia(new Runnable() {
			@Override
			public void run() {
				nextMusic();
			}
		});
	}
	
	public void play() {
		mediaplayer.play();
	}
	
	public void stop() {
		mediaplayer.stop();
	}
	
	public void setVolume(double v) {
		mediaplayer.setVolume(v);
	}
	
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
