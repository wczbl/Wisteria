package game.entity.mob.boss.snake;

import game.CharacterStats;
import game.entity.ETeam;
import game.gfx.core.Screen;
import game.gfx.helper.PaletteHelper;

public class SnakeNeck extends SnakePart {

	protected SnakePart child;
	protected double baseRot = Math.PI * 1.25;
	protected double rot;
	
	public SnakeNeck(int x, int y, SnakePart child) {
		this.x = x;
		this.y = y;
		this.xr = 6;
		this.yr = 6;
		this.child = child;
		this.health = 100;
		this.currentStats = new CharacterStats(0, 100, 0, 0, 0);
		this.team = ETeam.ENEMY;
	}
	
	public void tick() {
		super.tick();
		
		this.rot = Math.sin(this.time / 40.0) * Math.cos(this.time / 13.0) * 0.5;
		this.rot *= 0.9;
		double rr = this.baseRot + this.rot;
		double xa = Math.sin(rr) * 7;
		double ya = Math.cos(rr) * 7;
		if (!this.child.isRemoved()) {
			this.child.setMovement(xa + x, ya + y, rr);
		}
	}
	
	public void render(Screen screen) {
		int xo = this.x - 8;
		int yo = this.y - 9;
		
		int col = PaletteHelper.getColor(-1, 40 + this.level.getLevelNum() * 100, 51 + this.level.getLevelNum() * 100, 150);
		if(this.hurtTime > 0) col = PaletteHelper.getColor(-1, 555, 555, 555);
		
		screen.render(2, xo + 1, yo + 1, 16, 24, PaletteHelper.getColor(-1, 0, 0, 0), 0);
		screen.render(2, xo, yo, 16, 24, col, 0);
	}
	
	public void setMovement(double xa, double ya, double rot) {
		this.xa = xa;
		this.ya = ya;
		this.baseRot = rot;
	}

	
}