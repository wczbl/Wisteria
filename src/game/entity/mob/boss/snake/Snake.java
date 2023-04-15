package game.entity.mob.boss.snake;

import game.CharacterStats;
import game.entity.ETeam;
import game.gfx.core.Screen;
import game.gfx.helper.PaletteHelper;

public class Snake extends SnakePart {

	public Snake(int x, int y) {
		this.x = x;
		this.y = y;
		this.xr = 6;
		this.yr = 6;
		this.health = 200;
		this.currentStats = new CharacterStats(0, 100, 0, 0, 0);
		this.team = ETeam.ENEMY;
	}
	
	public void render(Screen screen) {
		int xo = this.x - 8;
		int yo = this.y - 7;
		
		int col = PaletteHelper.getColor(-1, 500 - (this.level.getLevelNum() % 5) * 100, 411 - (this.level.getLevelNum() % 4) * 100, 322);
		if(this.hurtTime > 0) col = PaletteHelper.getColor(-1, 555, 555, 555);
		
		screen.render(2, xo + 1, yo + 1, 16, 24, PaletteHelper.getColor(-1, 0, 0, 0), 2);
		screen.render(2, xo, yo, 16, 24, col, 0);
	}
	
	public void setMovement(double xa, double ya, double rot) {
		this.xa = xa;
		this.ya = ya;
	}
}