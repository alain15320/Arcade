package utils.son;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import gui.generique.PanneauControle;
import moteur.generique.ISon;

public class Son implements ISon {

	private enum EffetSonore {MORT("gameover.wav"), TOC("event.wav");
		public enum Volume {OFF, ON}
		public static Volume volume = Volume.OFF;
		private Clip clip;
		EffetSonore(String nomFichierSon) {
			try {
				File fich = new File ("src/main/ressources/Sons/"+nomFichierSon);
				AudioInputStream ais = AudioSystem.getAudioInputStream(fich);
				clip = AudioSystem.getClip();
				clip.open(ais);
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}
		}
		
		public void play() {
			if (volume==Volume.ON) {
				if (clip.isRunning()) clip.stop();
				clip.setFramePosition(0);
				clip.start();
			}
		}
		public static void init() {
			values();
		}
	}

	@Override
	public void initialiser() {
		EffetSonore.init();
	}

	@Override
	public boolean isOn() {
		return (EffetSonore.volume==EffetSonore.Volume.ON);
	}

	@Override
	public void setOn() {
		EffetSonore.volume = EffetSonore.Volume.ON;
	}

	@Override
	public void setOff() {
		EffetSonore.volume = EffetSonore.Volume.OFF;
	}

	@Override
	public void jouerCollision() {
		EffetSonore.TOC.play();
	}

	@Override
	public void jouerFin() {
		EffetSonore.MORT.play();
	}

}
