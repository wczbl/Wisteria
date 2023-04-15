package game.entity.mob.builds.tree;

import game.entity.Entity;
import game.gfx.core.Screen;
import game.gfx.helper.BitmapHelper;

public class Tree extends Entity {

	public Tree(int x, int y, int xr, int yr) {
		this.x = x;
		this.y = y;
		this.xr = xr;
		this.yr = yr;
	}
	
	public void render(Screen screen) {
		int xo = (this.x - (this.xr << 1)) - screen.getXOffset();
		int yo = (this.y - (this.yr << 1)* 3 - this.yr) - screen.getYOffset();
		BitmapHelper.drawNormal(this.sprite, xo, yo, screen.getViewPort(), 0xFF00FF);
	}
	
	public boolean blocks(Entity e) { return true; }
}