package game.entity.mob.npc;

import game.entity.ETeam;
import game.entity.Entity;
import game.entity.mob.Mob;
import game.gfx.core.Screen;
import game.gfx.helper.PaletteHelper;

public class NPC extends Mob {

	protected int xStart;
	protected int yStart;
	protected int randomWalkTime;
	protected int shirtCol;
	protected int xa;
	protected int ya;
	protected int col1;
	protected int col2;
	protected int xts;
	protected int yts;
	
	public NPC(int x, int y) {
		this.x = x;
		this.y = y;
		this.xStart = x;
		this.yStart = y;
		this.team = ETeam.PLAYER;
		this.shirtCol = this.random.nextInt(6) * 100 + this.random.nextInt(6) * 10 + this.random.nextInt(6);
		this.col1 = PaletteHelper.getColor(-1, 100, this.shirtCol, 532);
		this.col2 = PaletteHelper.getColor(-1, 100, this.shirtCol, 532);
		this.xts = 0;
		this.yts = this.random.nextBoolean() ? 18 : 14;
	}
	
	public void render(Screen screen) {
		int xt = this.xts;
		int yt = this.yts;
		
		int flip1 = (this.walkDist >> 3) & 1;
		int flip2 = (this.walkDist >> 3) & 1;
		
		if(this.dir == 1) xt += 2;
		
		if(this.dir > 1) {
			flip1 = 0;
			flip2 = ((this.walkDist >> 4) & 1);
			if(this.dir == 2) flip1 = 1;
			xt += 4 + ((this.walkDist >> 3) & 1) * 2;
		}
		
		int xo = this.x - 8;
		int yo = this.y - 11;
		if(isSwimming()) {
			yo += 4;
			int waterCol = PaletteHelper.getColor(-1, -1, -1, 444);
			if(this.tickTime / 8 % 2 == 0) waterCol = PaletteHelper.getColor(-1, 444, -1, -1);
			
			screen.render(xo + 0, yo + 3, 40, 104, waterCol, 0);
			screen.render(xo + 8, yo + 3, 40, 104, waterCol, 1);
		}
		
		int col1 = this.col1;
		int col2 = this.col2;
		
		if(this.hurtTime > 0) {
			col1 = PaletteHelper.getColor(-1, 555, 555, 555);
			col2 = PaletteHelper.getColor(-1, 555, 555, 555);
		}
		
		screen.render(xo + 8 * flip1, yo + 0, xt * 8, yt * 8, col1, flip1);
		screen.render(xo + 8 - 8 * flip1, yo + 0, (xt + 1) * 8, yt * 8, col1, flip1);
		if (!isSwimming()) {
			screen.render(xo + 8 * flip2, yo + 8, xt * 8, (yt + 1) * 8, col2, flip2);
			screen.render(xo + 8 - 8 * flip2, yo + 8, (xt + 1) * 8, (yt + 1) * 8, col2, flip2);
		}
	}
	
	public boolean blocks(Entity e) { return true; }
	public boolean canRegenerate() { return true; }
	public boolean canAttack() { return true; }
	
}