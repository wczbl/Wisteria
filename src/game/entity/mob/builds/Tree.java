package game.entity.mob.builds;

import game.entity.ETeam;
import game.entity.Entity;
import game.entity.mob.Mob;
import game.gfx.core.Screen;
import game.gfx.helper.PaletteHelper;
import game.weapon.Arrow;

public class Tree extends Mob {

	public Tree() {
		this.xr = 16;
		this.yr = 16;
		this.team = ETeam.ENEMY;
	}
	
	public void render(Screen screen) {
		int col = PaletteHelper.getColor(0, 520, 141, -1);
		screen.render(this.x - this.xr, this.y - this.yr, 136, 0, 32, 32, col, 0);
	}
	
	public void touchedBy(Entity e) {
		if(e instanceof Arrow) {
			Arrow arrow = (Arrow)e;
			if(this.team != arrow.getOwnerTeam()) {
				arrow.setRemoved(true);
			}
		}
	}
	
	public boolean blocks(Entity e) { return true; }
}