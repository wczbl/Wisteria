package game.entity.mob.npc;

import game.entity.Entity;
import game.entity.mob.Player;
import game.gfx.core.Screen;
import game.gfx.helper.PaletteHelper;

public class Warper extends Entity {

	private boolean isUp;

	public Warper(int x, int y, boolean isUp) {
		this.x = x;
		this.y = y;
		this.isUp = isUp;
	}
	
	public void render(Screen screen) {
		int xd = this.x - 8;
		int yd = this.y - 9;
		int col = this.isUp ? 214 : 222;
		int cols = PaletteHelper.getColor(-1, 0, col, 333);
		if(System.currentTimeMillis() / 100 % 2 == 0) cols = PaletteHelper.getColor(-1, 111, col, 533);
		
		screen.render(2, xd, yd, 0, 88, cols, 0);
	}
	
	public void touchedBy(Entity e) {
		if(e instanceof Player) {
			this.level.getGame().changeLevelByDir(this.isUp ? 1 : -1);
		}
	}
}