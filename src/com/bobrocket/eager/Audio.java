package com.bobrocket.eager;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineEvent;

public class Audio {

	public static void play(String file) {
		try {
			Clip audioClip = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
			
			audioClip.addLineListener((event) -> {
				if (event.getType() == LineEvent.Type.STOP) audioClip.close();
			});
			
			audioClip.open(AudioSystem.getAudioInputStream(Audio.class.getResourceAsStream("/sounds/" + file + ".wav")));
			audioClip.start();
		}
		catch (Exception e) {
			
		}
	}
	
	public static void playLooped(String file) {
		try {
			Clip audioClip = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
			
			audioClip.addLineListener((event) -> {
				if (event.getType() == LineEvent.Type.STOP) audioClip.start();
			});
			
			audioClip.open(AudioSystem.getAudioInputStream(Audio.class.getResourceAsStream("/music/" + file + ".wav")));
			
			audioClip.start();
			audioClip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
