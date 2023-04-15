package game.entity.mob;

import game.CharacterStats;
import game.entity.Entity;
import game.entity.mob.boss.Boss;
import game.entity.particle.BloodParticle;
import game.entity.particle.FlowTextParticle;
import game.gfx.core.Font;
import game.level.Level;
import game.level.tile.Tile;
import game.sound.Sound;
import game.weapon.arrow.Arrow;

public class Mob extends Entity {
	
	private static final int DEFAULT_KNOCKBACK = 6;
	protected long tickTime;
	protected int hurtTime;
	protected int walkDist;
	protected int dir;
	protected int health = 10;
	protected int maxHealth;
	protected int xKnockback;
	protected int yKnockback;
	protected int viewRadius = 4;
	protected Mob target;
	protected CharacterStats defaultStats = new CharacterStats(0, 10, 0, 0 ,0);
	protected CharacterStats currentStats = new CharacterStats(0, 10, 0, 0 ,0);
	protected CharacterStats compareStats = new CharacterStats(0, 10, 0, 0 ,0);
	protected int slowGroundPeriod = 50;
	protected int bloodCol = 0xCC1100;
	
	public void init(Level level) {
		super.init(level);
		this.health *= (level.getLevelNum() + 1);
		this.maxHealth = this.health;
	}
	
	public void tick() {		
		this.tickTime++;
		
		if(!ignoreBlocks() && this.level.getTile(this.x >> 4, this.y >> 4) == Tile.lava && !(this instanceof Boss)) hurt(this, 10, this.dir ^ 1);
		if(this.health <= 0) die();
		if(this.hurtTime > 0) this.hurtTime--;
		
		if(canRegenerate() && this.health < this.currentStats.getEndurance() && this.tickTime % 420 == 0) {
			int oh = this.health;
			this.health = Math.min(this.currentStats.getEndurance(), this.health + this.currentStats.getRegeneration() + this.currentStats.getEndurance() / 10);
			this.level.add(new FlowTextParticle("+" + (this.health - oh), this.x, this.y, Font.GREEN_COLOR));
		}
		
		if(!this.compareStats.match(this.currentStats.mergeStats(new CharacterStats(0, 0, 0, 0, this.slowGroundPeriod)))) {
			this.compareStats = this.currentStats.mergeStats(new CharacterStats(0, 0, 0, 0, this.slowGroundPeriod));
			updateStats();
		}
	}
	
	public void updateStats() {
		// do nothing
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
		
		if(this.tickTime % (this.slowGroundPeriod + this.currentStats.getSlowPeriod()) == 0) return true;
				
		return super.move(xa, ya);
	}
	
	public void touchedBy(Entity e) {
		if(e instanceof Arrow) {
			Arrow arrow = (Arrow)e;
			if(this.team != arrow.getOwnerTeam()) {
				hurt(this, arrow.getDamage(), this.dir ^ 1);
				arrow.setRemoved(true);
				Sound.monsterHurt.setVolume(0.1f);
				Sound.monsterHurt.play();
			}
		}
	}
	
	public void hurt(Mob mob, int dmg, int attackDir) { doHurt(dmg, attackDir); }
	
	protected void doHurt(int dmg, int attackDir) {
		if(this.hurtTime > 0) return;
		
		if(this instanceof Player) {
			Sound.playerHurt.setVolume(0.1f);
			Sound.playerHurt.play();
		}
		
		dmg = Math.max(0, dmg - this.currentStats.getDefense());
		this.health -= dmg;
		
		this.level.add(new FlowTextParticle("-" + dmg, this.x, this.y, Font.RED_COLOR));
		
		for(int i = 0; i < Math.min(dmg, 10); i++) {
			this.level.add(new BloodParticle(this.x, this.y, this.bloodCol));
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
			int xd = level.getPlayer().getX() - xx;
			int yd = level.getPlayer().getY() - yy;
			if(xd * xd + yd * yd < 1600) return false;
		}
		
		this.x = xx;
		this.y = yy;
		
		if(!ignoreBlocks()) {
			int r = level.getMonsterDensity() * 16;
		
			if(level.getEntities(xx - r, yy - r, xx + r, yy + r, null).size() > 0) return false;
			if(!level.getTile(x, y).mayPass(level, x, y, this)) return false;
		}
		
		return true;
	}
	
	public CharacterStats getCurrentStats() { return this.currentStats; }
	public CharacterStats getDefaultStats() { return this.defaultStats; }
	public CharacterStats getCompareStats() { return this.compareStats; }
	public void setHealth(int health) { this.health = Math.min(this.currentStats.getEndurance(), health); }
	public long getTickTime() { return this.tickTime; }
	public int getHurtTime() { return this.hurtTime; }
	public int getWalkDist() { return this.walkDist; }
	public int getDir() { return this.dir; }
	public int getHealth() { return this.health; }
	public int getMaxHealth() { return this.maxHealth; }
	public int getXKnockback() { return this.xKnockback; }
	public int getYKnockback() { return this.yKnockback; }
	public boolean canRegenerate() { return false; }
	public int getSlowGroundPeriod() { return this.slowGroundPeriod + this.currentStats.getSlowPeriod(); }
	public void setSlowGroundPeriod(int slowGroundPeriod) { this.slowGroundPeriod = slowGroundPeriod; }
	
}