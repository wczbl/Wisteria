package game.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import game.Game;

public class Sound {

	public static final Sound menu = new Sound("/snd/menu.wav");
	public static final Sound backgroundMusic = new Sound("/snd/back.wav");
	public static final Sound startGame = new Sound("/snd/startgame.wav");
	public static final Sound pickup = new Sound("/snd/pickup.wav");
	public static final Sound playerHurt = new Sound("/snd/playerhurt.wav");
	public static final Sound monsterHurt = new Sound("/snd/monsterhurt.wav");
	private Clip clip;
	
	private Sound(String name) {
		try {
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(Sound.class.getResource(name));
			this.clip = AudioSystem.getClip();
			this.clip.open(audioStream);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
		if(!Game.soundOn) return;
		
		try {
			this.clip.setFramePosition(0);
			this.clip.start();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loop() {
		if(!Game.soundOn || this.clip.isRunning()) return;
		
		try {
			this.clip.setFramePosition(0);
			this.clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setVolume(float val) {
		if(this.clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
			FloatControl volume = (FloatControl)this.clip.getControl(FloatControl.Type.MASTER_GAIN);
			float dB = (float)(Math.log(val) / Math.log(10) * 20);
			if(dB >= 1f) dB = 1f;
			volume.setValue(dB);
		}
	}
	
	public void increaseVolume(float val) {
		
	}
	
	public void stop() { this.clip.stop(); }
}
