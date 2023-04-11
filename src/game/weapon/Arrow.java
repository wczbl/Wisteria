package game.weapon;

import game.entity.ETeam;
import game.entity.mob.Mob;
import game.gfx.core.Screen;
import game.gfx.helper.PaletteHelper;

public class Arrow extends Mob {
	
	private int dmg;
	private int lifeTime;
	private ETeam ownerTeam;
	private double beta;
	
	public Arrow(ETeam ownerTeam, int x, int y, double vx, double vy, int dmg) {
		this.ownerTeam = ownerTeam;
		this.x = x;
		this.y = y;
		this.lifeTime = 35;
		this.dmg = dmg;
		this.beta = Math.atan2(vy, vx);
	}
	
	public void tick() {
		int speed = 4;
		double xa = speed;
		double ya = Math.sin(this.random.nextGaussian());
		double xxa = xa * Math.cos(this.beta) - ya * Math.sin(this.beta);
		double yya = ya * Math.cos(this.beta) + xa * Math.sin(this.beta);
		
		if(!move((int)Math.floor(xxa), (int)Math.floor(yya))) {
			this.removed = true;
		}
		
		this.lifeTime--;
		if(this.lifeTime <= 0) this.removed = true;
	}
	
	public void render(Screen screen) {
		int col = PaletteHelper.getColor(-1, 555, 333, 111);
		screen.render(Math.toDegrees(this.beta) + 90, this.x - this.xr, this.y - this.yr, 0, 64, col, 0);
	}
	
	public int getDamage() { return this.dmg; }
	public ETeam getOwnerTeam() { return this.ownerTeam; }
	public boolean ignoreBlocks() { return true; }
}