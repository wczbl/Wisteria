package game.gfx;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import game.Game;
import game.entity.particle.FireParticle;
import game.entity.particle.ParticleSystem;
import game.gfx.core.Screen;
import game.gfx.helper.PaletteHelper;
import game.gfx.sprite.SpriteCollector;
import game.gfx.weather.WeatherManager;

public class Rasterizer extends Canvas {
	private static final long serialVersionUID = 1L;
	
	protected Screen screen;
	protected SpriteCollector spriteCollector;
	protected ParticleSystem fireParticles;
	protected WeatherManager weather = new WeatherManager();
	
	private BufferStrategy bs;
	
	public Rasterizer() {
		this.screen = new Screen(Game.WIDTH, Game.HEIGHT, "/sheet.png");
		this.spriteCollector = new SpriteCollector(this.screen.getSprites());
		try {
			this.fireParticles = new ParticleSystem(FireParticle.class, 2000, 0.03, -0.01, 40);
		} catch (Exception e) {
			// ignore
		}
	}
	
	public void initRaster() {
		this.bs = getBufferStrategy();
		if(this.bs == null) {
			createBufferStrategy(3);
			requestFocus();
			return;
		}
	}
	
	public void render(boolean isGray) {
		PaletteHelper.getInstance().wrap(this.screen.getViewPort(), isGray, this.weather.getWeather());
		if(this.bs != null) {
			Graphics g = this.bs.getDrawGraphics();
			g.drawImage(this.screen.getViewPort().getImage(), 0, 0, getWidth(), getHeight(), null);
			g.dispose();
			this.bs.show();
		}
	}
	
	public Screen getScreen() { return this.screen; }
	public SpriteCollector getSpriteCollector() { return this.spriteCollector; }
	public ParticleSystem getFireParticles() { return this.fireParticles; }
	
}