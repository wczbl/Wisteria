package game.weapon;

import java.util.List;

import game.entity.Entity;
import game.entity.mob.Mob;
import game.gfx.core.Screen;
import game.gfx.helper.PaletteHelper;

public class Spark extends Entity {
	
	private int lifeTime;
	private double xa;
	private double ya;
	private double xx;
	private double yy;
	private int time;
	private Mob owner;
	
	public Spark(Mob owner, double xa, double ya) {
		this.owner = owner;
		this.team = owner.getTeam();
		this.xx = this.x = owner.getX();
		this.yy = this.y = owner.getY();
		this.xr = 0;
		this.yr = 0;
		this.xa = xa;
		this.ya = ya;
		this.lifeTime = 600 + this.random.nextInt(30);
	}
	
	public void tick() {
		this.time++;
		if(this.time >= this.lifeTime) {
			this.removed = true;
			return;
		}
		
		this.xx += this.xa;
		this.yy += this.ya;
		this.x = (int)this.xx;
		this.y = (int)this.yy;
		
		List<Entity> toHit = this.level.getEntities(this.x, this.y, this.x, this.y, null);
		for(Entity e : toHit) {
			if(e instanceof Mob && e.getTeam() != this.owner.getTeam()) {
				e.hurt(this.owner, this.level.getLevelNum() + 1, ((Mob)e).getDir() ^ 1);
			}
		}
	}
	
	public void render(Screen screen) {
		if(this.time >= this.lifeTime - 120) {
			if(this.time / 6 % 2 == 0) return;
		}
		
		int xt = 8;
		int yt = 13;
	
		screen.render(this.x - 4, this.y - 4 - 2, xt * 8, yt * 8, PaletteHelper.getColor(-1, 555, 555, 555), this.random.nextInt(4));
		screen.render(this.x - 4, this.y - 4 + 2, xt * 8, yt * 8, PaletteHelper.getColor(-1, 0, 0, 0), this.random.nextInt(4));
	}
}