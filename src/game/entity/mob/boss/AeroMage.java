package game.entity.mob.boss;

import game.entity.ETeam;
import game.entity.Entity;
import game.entity.mob.Player;
import game.gfx.core.Screen;
import game.gfx.helper.PaletteHelper;
import game.weapon.Spark;

public class AeroMage extends Boss {

	private int xa;
	private int ya;
	private int randomWalkTime;
	private int attackDelay;
	private int attackTime;
	private int attackType;
	
	public AeroMage(int x, int y) {
		this.x = x;
		this.y = y;
		this.health = 300;
		this.team = ETeam.ENEMY;
	}
	
	public void tick() {
		super.tick();
		
		if(this.attackDelay > 0) {
			this.dir = (this.attackDelay - 45) / 4 % 4;
			this.dir = (this.dir * 2 % 4) + (this.dir / 2);
			if(this.attackDelay < 45) this.dir = 0;
			this.attackDelay--;
			if(this.attackDelay == 0) {
				this.attackType = 0;
				if(this.health < this.maxHealth / 2) this.attackType = 1;
				if(this.health < this.maxHealth / 10) this.attackType = 2;
				this.attackTime = 120;
			}
			
			return;
		}
		
		if(this.attackTime > 0) {
			this.attackTime--;
			double dir = this.attackTime * 0.25 * (this.attackTime % 2 * 2 - 1);
			double speed = 0.7 + this.attackType * 0.2;
			this.level.add(new Spark(this, Math.cos(dir) * speed, Math.sin(dir) * speed));
			return;
		}
		
		if(this.level.getPlayer() != null && this.randomWalkTime == 0) {
			int xd = this.level.getPlayer().getX() - this.x;
			int yd = this.level.getPlayer().getY() - this.y;
			if(xd * xd + yd * yd < 1024) {
				this.xa = 0;
				this.ya = 0;
				if(xd < 0) this.xa = +1;
				if(xd > 0) this.xa = -1;
				if(yd < 0) this.ya = +1;
				if(yd > 0) this.ya = -1;
			} else if(xd * xd + yd * yd > 6400) {
				this.xa = 0;
				this.ya = 0;
				if(xd < 0) this.xa = -1;
				if(xd > 0) this.xa = +1;
				if(yd < 0) this.ya = -1;
				if(yd > 0) this.ya = +1;				
			}
		}
		
		int speed = this.tickTime % 4 == 0 ? 0 : 1;
		if(!move(this.xa * speed, this.ya * speed) || this.random.nextInt(100) == 0) {
			this.randomWalkTime = 30;
			this.xa = this.random.nextInt(3) - 1;
			this.ya = this.random.nextInt(3) - 1;
		}
		
		if(this.randomWalkTime > 0) {
			this.randomWalkTime--;
			if(this.level.getPlayer() != null && this.randomWalkTime == 0) {
				int xd = this.level.getPlayer().getX() - this.x;
				int yd = this.level.getPlayer().getY() - this.y;
				if(this.random.nextInt(4) == 0 && xd * xd + yd * yd < 2500) {
					if(this.attackDelay == 0 && this.attackTime == 0) {
						this.attackDelay = 120;
					}
				}
			}
		}
	}
	
	protected void doHurt(int dmg, int attackDir) {
		super.doHurt(dmg, attackDir);
		if(this.attackDelay == 0 && this.attackTime == 0) {
			this.attackDelay = 120;
		}
	}
	
	public void render(Screen screen) {
		int xt = 0;
		int yt = 14;
		
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
		
		int col1 = PaletteHelper.getColor(-1, 100, 550 - (this.level.getLevelNum() % 5) * 100, 555);
		int col2 = PaletteHelper.getColor(-1, 100, 550 - (this.level.getLevelNum() % 5) * 100, 532);
		
		if(this.health < this.maxHealth / 10) {
			if(this.tickTime / 3 % 2 == 0) {
				col1 = PaletteHelper.getColor(-1, 500, 100, 555);
				col2 = PaletteHelper.getColor(-1, 500, 100, 532);
			}
		} else if(this.health < this.maxHealth / 2) {
			if(this.tickTime / 5 % 4 == 0) {
				col1 = PaletteHelper.getColor(-1, 500, 100, 555);
				col2 = PaletteHelper.getColor(-1, 500, 100, 532);
			}
		}
		
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
	
	public void touchedBy(Entity e) {
		if(e instanceof Player) {
			e.hurt(this, 3 * (this.level.getLevelNum() + 1), this.dir);
		}
		
		super.touchedBy(e);
	}
}