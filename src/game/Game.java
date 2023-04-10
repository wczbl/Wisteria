package game;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import game.entity.mob.Player;
import game.gfx.Rasterizer;
import game.gfx.core.Font;
import game.gfx.helper.PaletteHelper;
import game.level.Level;
import game.level.tile.Tile;

public class Game extends Rasterizer implements Runnable {
	private static final long serialVersionUID = 1L;
	
	public static final String TITLE = "Wisteria v0.1";
	public static final int WIDTH = 320;
	public static final int HEIGHT = 240;
	public static final int SCALE = 2;
	private boolean running;
	
	private int xScroll;
	private int yScroll;
	private Level level;
	private InputHandler input;
	private Player player;	
	
	public void start() {
		this.running = true;
		new Thread(this).start();
	}
	
	public void stop() { this.running = false; }
	
	public void run() {
		init();
		long lastTime = System.nanoTime();
		double delta = 0.0;
		double nsPerTick = 1000000000.0 / 60.0;
		long timer = System.currentTimeMillis();
		int ticks = 0;
		int frames = 0;
		int prevFrames = 0;
		while(this.running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			
			boolean shouldRender = true;
			while(delta >= 1) {
				delta--;
				tick();
				ticks++;
				shouldRender = false;
			}
			
			if(shouldRender) {
				frames++;
				render(prevFrames);
			}
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				prevFrames = frames;
				System.out.println("frames: " + frames + " ticks: " + ticks);
				frames = 0;
				ticks = 0;
			}
			
			try {
				Thread.sleep(2L);
			} catch (InterruptedException e) {
				// ignore
			}
		}
	}
	
	public void init() {
		this.level = new Level(50, 50);
		this.input = new InputHandler(this);
		this.player = new Player(this.input);
		this.player.findStartPos(this.level);
		this.level.add(this.player);
		this.input.releaseAll();
	}
	
	public void tick() {
		if(!hasFocus()) this.input.releaseAll();
		else {
			this.input.tick();
			if(this.player.isRemoved() && this.input.attack.down) {
				init();
			}
			
			this.level.tick();
			
			Tile.tickCount++;
		}
	}
	
	public void render(int fps) {
		initRaster();
		
		this.xScroll = this.player.getX() - this.screen.getViewPort().getWidth() / 2;
		this.yScroll = this.player.getY() - this.screen.getViewPort().getHeight() / 2;
		
		if(this.xScroll < 16) this.xScroll = 16;
		if(this.yScroll < 16) this.yScroll = 16;
		
		if(this.xScroll > this.level.getWidth() * 16 - this.screen.getViewPort().getWidth() - 16) {
			this.xScroll = this.level.getWidth() * 16 - this.screen.getViewPort().getWidth() - 16;
		}
		
		if(this.yScroll > this.level.getHeight() * 16 - this.screen.getViewPort().getHeight() - 16) {
			this.yScroll = this.level.getHeight() * 16 - this.screen.getViewPort().getHeight() - 16;
		}
		
		this.level.renderBackground(this.screen, this.xScroll, this.yScroll);
		this.level.renderSprites(this.screen, this.xScroll, this.yScroll);
		this.level.renderFog(this.screen, this.xScroll, this.yScroll);
		
		Font.draw("FPS: " + fps + " OBJ: " + this.level.getEntities().size(), this.screen, 10, 10, PaletteHelper.getColor(-1, 111, 111, 555));
		Font.draw("SCORE: " + this.player.getScore() + " HP: " + this.player.getHealth(), this.screen, 10, 20, PaletteHelper.getColor(-1, 111, 111, 555));
		
		super.render(fps);
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Dimension d = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
		Game game = new Game();
		frame.setTitle(TITLE);
		game.setMinimumSize(d);
		game.setMaximumSize(d);
		game.setPreferredSize(d);
		frame.setLayout(new BorderLayout());
		frame.add(game, BorderLayout.CENTER);
		frame.pack();
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		game.start();
	}
	
	public int getXScroll() { return this.xScroll; }
	public int getYScroll() { return this.yScroll; }
}