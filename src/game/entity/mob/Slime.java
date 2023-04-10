package game.entity.mob;

import java.util.List;

import game.entity.ETeam;
import game.entity.Entity;
import game.gfx.core.Screen;
import game.gfx.helper.PaletteHelper;
import game.item.Coin;
import game.item.Heart;

public class Slime extends Mob {
	
	private int xa;
	private int ya;
	private int jumpTime;
	
	public Slime() {}

	public Slime(int x, int y) {
		this.x = x;
		this.y = y;
		this.viewRadius = 6;
	}
	
	public void tick() {
		super.tick();
		
		int speed = 1;
		if(!(move(this.xa * speed, this.ya * speed) && this.random.nextInt(40) != 0 || this.jumpTime > -10)) {
			this.xa = this.random.nextInt(3) - 1;
			this.ya = this.random.nextInt(3) - 1;
			int vr = this.viewRadius * 16;
			
			if(this.target == null) {
				List<Entity> playerTeam = this.level.getEntities(this.x - vr, this.y - vr, this.x + vr, this.y + vr, ETeam.PLAYER);
				for(Entity e : playerTeam) {
					if(e instanceof Mob) {
						this.target = (Mob)e;
						break;
					}
				}
			}
			
			if(this.target != null) {
				int xd = this.target.getX() - this.x;
				int yd = this.target.getY() - this.y;
				
				if(xd < 0) this.xa = -1;
				if(xd > 0) this.xa = +1;
				if(yd < 0) this.ya = -1;
				if(yd > 0) this.ya = +1;
			
				if(xd * xd + yd * yd > vr * vr || this.target.isRemoved()) {
					this.target = null;
				}
			}
			
			if(this.xa != 0 || this.ya != 0) this.jumpTime = 10;
		}
		
		this.jumpTime--;
		if(this.jumpTime == 0) {
			this.xa = 0;
			this.ya = 0;
		}
	}
	
	public void render(Screen screen) {
		int xt = 0;
		int yt = 6;
		int xo = this.x - 4;
		int yo = this.y - 4;
		
		if(this.jumpTime > 0) {
			xt++;
			yo -= 4;
		}
		
		int col = PaletteHelper.getColor(-1, 10, 252, 555);
		if(this.hurtTime > 0) col = PaletteHelper.getColor(-1, 10, 511, 555);
		
		screen.render(xo, yo, xt * 8, yt * 8, 8, 8, col, 0);
	}
	
	public void touchedBy(Entity e) {
		if(e.getTeam() != this.team) {
			e.hurt(this, 1, this.dir);
		}
		
		super.touchedBy(e);
	}
	
	protected void die() {
		super.die();
		
		int count = this.random.nextInt(6) + 2;
		for(int i = 0; i < count; i++) {
			this.level.add(new Coin(this.x, this.y, this.random.nextInt(5) + 1));
		}
		
		if(this.random.nextInt(20) == 0) this.level.add(new Heart(this.x, this.y, 1));
	}
	
	public boolean blocks(Entity e) { return true; }
}