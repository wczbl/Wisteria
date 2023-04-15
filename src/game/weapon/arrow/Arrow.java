package game.weapon.arrow;

import game.entity.ETeam;
import game.entity.Entity;
import game.gfx.core.Screen;
import game.gfx.helper.PaletteHelper;

public class Arrow extends Entity {
	
	private int dmg;
	private int lifeTime;
	private ETeam ownerTeam;
	private double beta;
	protected int col;
	protected int speed;
	
	public Arrow(ETeam ownerTeam, int x, int y, double vx, double vy, int dmg) {
		this.ownerTeam = ownerTeam;
		this.x = x;
		this.y = y;
		this.lifeTime = 35;
		this.dmg = dmg;
		this.beta = Math.atan2(vy, vx);
		this.col = PaletteHelper.getColor(-1, 333, 111, 222);
		this.speed = 4;
	}
	
	public void tick() {
		double xa = this.speed;
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
		screen.render(Math.toDegrees(this.beta) + 90, this.x - this.xr, this.y - this.yr, 0, 64, this.col, 0);
	}
	
	public int getDamage() { return this.dmg; }
	public ETeam getOwnerTeam() { return this.ownerTeam; }
	public boolean ignoreBlocks() { return true; }
}