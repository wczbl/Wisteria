package game.weapon;

import game.entity.ETeam;
import game.entity.mob.Mob;
import game.gfx.core.Screen;
import game.gfx.helper.PaletteHelper;

public class Arrow extends Mob {
	
	private int vx;
	private int vy;
	private int dmg;
	private int lifeTime;
	private ETeam ownerTeam;
	
	public Arrow(ETeam ownerTeam, int x, int y, int vx, int vy, int dmg) {
		this.ownerTeam = ownerTeam;
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		this.lifeTime = 35;
		this.dmg = dmg;
	}
	
	public void tick() {
		int speed = 4;
		double xa = speed;
		double ya = Math.sin(this.random.nextGaussian());
		double beta = Math.atan2(this.vy, this.vx);
		double xxa = xa * Math.cos(beta) - ya * Math.sin(beta);
		double yya = ya * Math.cos(beta) + xa * Math.sin(beta);
		
		if(!move((int)Math.round(xxa), (int)Math.round(yya))) {
			this.removed = true;
		}
		
		this.lifeTime--;
		if(this.lifeTime <= 0) this.removed = true;
	}
	
	public void render(Screen screen) {
		int col = PaletteHelper.getColor(-1, 32, 330, 535);
		screen.render(this.x - 4, this.y - 5, 0, 8, 8, 8, col, 0);
	}
	
	public int getDamage() { return this.dmg; }
	public ETeam getOwnerTeam() { return this.ownerTeam; }
}