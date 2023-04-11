package game.gfx;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import game.Game;
import game.gfx.core.Screen;
import game.gfx.helper.PaletteHelper;

public class Rasterizer extends Canvas {
	private static final long serialVersionUID = 1L;
	
	protected Screen screen = new Screen(Game.WIDTH, Game.HEIGHT, "/sheet.png");
	private BufferStrategy bs;
	
	public void initRaster() {
		this.bs = getBufferStrategy();
		if(this.bs == null) {
			createBufferStrategy(3);
			requestFocus();
			return;
		}
	}
	
	public void render(int fps) {
		PaletteHelper.getInstance().wrap(this.screen.getViewPort());
		if(this.bs != null) {
			Graphics g = this.bs.getDrawGraphics();
			g.drawImage(this.screen.getViewPort().getImage(), 0, 0, getWidth(), getHeight(), null);
			g.dispose();
			this.bs.show();
		}
	}
	
	public Screen getScreen() { return this.screen; }
}