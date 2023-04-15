package game.gfx.weather;

import java.util.Random;

import game.level.Level;

public class WeatherManager {

	private Weather current = new Weather();
	private int rainTime;
	private int speed;
	private Random random = new Random();
	
	public void tick(Level level) {
		this.current.setRain(false);
		this.current.setThunder(false);
		
		if(this.rainTime > 0) {
			this.rainTime--;
			if(this.random.nextInt(500) == 0 && this.speed < 4) {
				this.speed++;
			}
			
			this.current.setRain(true);
			if(this.rainTime > 100) {
				level.add(new Rain(level.getPlayer(), -0.5, 1, this.speed));
			}
		}
		
		if(this.random.nextInt(5000) == 0 && this.rainTime <= 0) {
			this.speed = 2;
			this.rainTime = 5000;
		}
		
		if(this.current.isRain() && this.rainTime < 4000 && this.rainTime > 1000 && this.random.nextInt(100) == 0) {
			this.current.setThunder(true);
		}
	}

	public Weather getWeather() { return this.current; }
}