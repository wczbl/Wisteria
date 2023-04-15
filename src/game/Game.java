package game;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import game.entity.mob.Player;
import game.gfx.CreditsScreen;
import game.gfx.Rasterizer;
import game.gfx.core.Font;
import game.gfx.gui.GuiManager;
import game.gfx.gui.GuiPanel;
import game.gfx.gui.GuiStatusPanel;
import game.gfx.helper.PaletteHelper;
import game.item.resource.Resource;
import game.item.resource.ResourceItem;
import game.level.Level;
import game.level.tile.Tile;
import game.sound.Sound;

public class Game extends Rasterizer implements Runnable {
	private static final long serialVersionUID = 1L;
	
	public static final String TITLE = "Wisteria v1.0";
	public static final int WIDTH = 320;
	public static final int HEIGHT = 240;
	public static final int SCALE = 3;
	private boolean running;
	
	private int xScroll;
	private int yScroll;
	private Level level;
	private Level[] levels = new Level[6];
	private Player player;	
	private int tickCount;
	public static boolean soundOn = true;
	
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
		InputHandler.getInstance(this);
		
		this.player = new Player(this);
		
		for (int i = 0; i < 6; i++) {
			this.levels[i] = new Level(i, this);
		}
						
		changeLevel(0);
		
		Sound.backgroundMusic.setVolume(0.1f);
		Sound.backgroundMusic.loop();
    }
	
	public void tick() {
		this.tickCount++;
		
		
		
		if(!hasFocus()) InputHandler.getInstance(null).releaseAll();
		else {
			InputHandler.getInstance(null).tick();
			
			if(InputHandler.getInstance(null).sound.clicked) {
				soundOn = !soundOn;
				
				if(soundOn) {
					Sound.backgroundMusic.setVolume(0.1f);
					Sound.backgroundMusic.loop();
				}
				else Sound.backgroundMusic.stop();
			}
			 
			if(CreditsScreen.getInstance().isShow()) {
				CreditsScreen.getInstance().tick();
			} else {
				if(this.player.isRemoved()) {
					if(InputHandler.getInstance(null).action.clicked) {
						changeLevelByDir(0);
					}
				}				
				
				GuiManager.getInstance().tick();
				
				if(!GuiManager.paused) {
					this.level.tick();
					Tile.tickCount++;
					
					this.weather.tick(this.level);					
				}
				
			}
			
			
			
		}
	}
	
	public void render(int fps) {
		initRaster();
		
		if(CreditsScreen.getInstance().isShow()) {
			CreditsScreen.getInstance().render(screen);
		} else {
			this.xScroll = this.player.getX() - this.screen.getViewPort().getWidth() / 2;
			this.yScroll = this.player.getY() - (this.screen.getViewPort().getHeight() - 8) / 2;
			
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

			GuiPanel panel;
			if ((panel = GuiManager.getInstance().get("money")) != null) {
				ResourceItem coin = this.player.getInventory().findResource(Resource.coin);
				ResourceItem bigCoin = this.player.getInventory().findResource(Resource.bigCoin);
				
				int score = ((coin != null ? coin.getCount() : 0) + (bigCoin != null ? (bigCoin.getCount() << 1) : 0));
				((GuiStatusPanel) panel).setText(score);
			}
			
			if ((panel = GuiManager.getInstance().get("health")) != null) {
				((GuiStatusPanel) panel).setText2(this.player.getHealth() + "/" + this.player.getCurrentStats().getEndurance());
			}
			
			
			GuiManager.getInstance().render(this.screen);
					
			if(!hasFocus() && !GuiManager.paused) {
				String text = "CLICK TO FOCUS!";
				int col = this.tickCount / 20 % 2 == 0 ? PaletteHelper.getColor(5, 333, 333, 333) : PaletteHelper.getColor(5, 555, 555, 555);
				Font.drawPanel(text, this.screen, (Game.WIDTH - text.length() * 8) / 2, Game.HEIGHT / 2, col);
			}
			
			if(this.player.isRemoved()) {
				String text = "GAME OVER";
				Font.draw(text, this.screen, (Game.WIDTH - text.length() * 8) / 2, Game.HEIGHT / 3, PaletteHelper.getColor(555, 111, 111, 115));
				text = "Press SPACE to Restart";
				Font.draw(text, this.screen, (Game.WIDTH - text.length() * 8) / 2, Game.HEIGHT / 2, PaletteHelper.getColor(555, 111, 111, 115));
			}
		}
		
		super.render(this.player.isRemoved());
	}
	
	public void changeLevelByDir(int dir) { changeLevel(this.level.getLevelNum() + dir); }
	
	public void changeLevel(int levelNum) {
		if (this.level != null) this.level.remove(this.player);
		
		if(levelNum != 0) {
			Sound.startGame.setVolume(0.1f);
			Sound.startGame.play();
		}
		
		this.level = this.levels[levelNum % 6];
		this.level.add(this.player);
		GuiManager.getInstance().init(this.level);
		InputHandler.getInstance(null).releaseAll();
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