package game.entity.mob;

import game.entity.ETeam;
import game.entity.Entity;
import game.entity.particle.BloodParticle;
import game.gfx.helper.PaletteHelper;
import game.level.Level;
import game.level.tile.Tile;
import game.weapon.Arrow;

public class Mob extends Entity {
	
	private static final int DEFAULT_KNOCKBACK = 6;
	protected long tickTime;
	protected int hurtTime;
	protected int walkDist;
	protected int dir;
	protected int health = 10;
	protected int xKnockback;
	protected int yKnockback;
	protected int viewRadius = 4;
	protected Mob target;

	public void tick() {
		this.tickTime++;
		
		if(!ignoreBlocks() && this.level.getTile(this.x >> 4, this.y >> 4) == Tile.lava) hurt(this, 4, this.dir ^ 1);
		if(this.health <= 0) die();
		if(this.hurtTime > 0) this.hurtTime--;
	}
	
	public boolean move(int xa, int ya) {
		if(this.xKnockback < 0) {
			move2(-1, 0);
			this.xKnockback++;
		}
		
		if(this.xKnockback > 0) {
			move2(1, 0);
			this.xKnockback--;
		}
		
		if(this.yKnockback < 0) {
			move2(0, -1);
			this.yKnockback++;
		}
		
		if(this.yKnockback > 0) {
			move2(0, 1);
			this.yKnockback--;
		}
		
		if(xa != 0 || ya != 0) {
			this.walkDist++;
			if(xa < 0) this.dir = 2;
			if(xa > 0) this.dir = 3;
			if(ya < 0) this.dir = 1;
			if(ya > 0) this.dir = 0;
		}
		
		return super.move(xa, ya);
	}
	
	public void touchedBy(Entity e) {
		if(e instanceof Arrow) {
			Arrow arrow = (Arrow)e;
			if(this.team != arrow.getOwnerTeam() && !(this instanceof Arrow)) {
				hurt(this, arrow.getDamage(), arrow.getDir());
				arrow.setRemoved(true);
			}
		}
	}
	
	public void hurt(Mob mob, int dmg, int attackDir) { doHurt(dmg, attackDir); }
	
	protected void doHurt(int dmg, int attackDir) {
		if(this.hurtTime > 0) return;
		
		int col = PaletteHelper.getColor(-1, 500, 500, 500);
		if(this.team == ETeam.PLAYER) col = PaletteHelper.getColor(-1, 505, 505, 505);
		
		this.health -= dmg;
		
		for(int i = 0; i < dmg; i++) {
			this.level.add(new BloodParticle(this.x, this.y, col));
		}
		
		
		if(attackDir == 0) this.yKnockback = DEFAULT_KNOCKBACK;
		if(attackDir == 1) this.yKnockback = -DEFAULT_KNOCKBACK;
		if(attackDir == 2) this.xKnockback = -DEFAULT_KNOCKBACK;
		if(attackDir == 3) this.xKnockback = DEFAULT_KNOCKBACK;
		
		this.hurtTime = 10;
	}
	
	public boolean findStartPos(Level level) {
		int x = this.random.nextInt(level.getWidth());
		int y = this.random.nextInt(level.getHeight());
		int xx = x * 16 + 8;
		int yy = y * 16 + 8;
		
		if(level.getPlayer() != null) {
			int xd = level.getPlayer().getX() - this.x;
			int yd = level.getPlayer().getY() - this.y;
			if(xd * xd + yd * yd < 6400) return false;
		}
		
		this.x = xx;
		this.y = yy;
		
		if(!ignoreBlocks()) {
			int r = level.getMonsterDensity() * 16;
		
			if(level.getEntities(xx - r, yy - r, xx + r, yy + r, null).size() > 0) return false;
			if(!level.getTile(x, y).mayPass(level, xx, yy, this)) return false;
		}	
		
		
		return true;
	}
	
	protected void die() { this.removed = true; }
	public long getTickTime() { return this.tickTime; }
	public int getHurtTime() { return this.hurtTime; }
	public int getWalkDist() { return this.walkDist; }
	public int getDir() { return this.dir; }
	public int getHealth() { return this.health; }
	public int getXKnockback() { return this.xKnockback; }
	public int getYKnockback() { return this.yKnockback; }
	
	
}