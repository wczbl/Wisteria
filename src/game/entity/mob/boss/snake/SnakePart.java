package game.entity.mob.boss.snake;

import java.util.List;

import game.entity.Entity;
import game.entity.mob.Mob;
import game.entity.mob.boss.Boss;
import game.level.tile.Tile;

public class SnakePart extends Boss {
	
	protected double xa;
	protected double ya;
	protected int time;
	
	public void tick() {
		super.tick();
		if(this.xa > 0.00001) this.x = (int)this.xa;
		if(this.ya > 0.00001) this.y = (int)this.ya;
		
		this.time++;
		List<Entity> entities = level.getEntities(this.x, this.y, this.x, this.y, null);
		
		for(Entity entity : entities) {
			doHurt(entity);
		}
		
		if(this.health < this.maxHealth) this.level.getFireParticles().createExplosion(this.x, this.y, 0.5, -1.0, (this.maxHealth - this.health) / 30);
		if(this.health < this.maxHealth / 10) this.level.setTile(x >> 4, y >> 4, Tile.lava, 0);
		
		
	}
		
	private void doHurt(Entity e) {
		if(e instanceof SnakePart || e instanceof Boss || e.getTeam() == this.team) return;
		e.hurt(this, 2 * (this.level.getLevelNum() + 1), this.dir);
	}

	public void touchedBy(Entity e) {
		doHurt(e);
		
		super.touchedBy(e);
	}
	
	public void hurt(Mob mob, int dmg, int attackDir) {
		if(this.health < 50) this.time = 0;
		super.hurt(mob, dmg, attackDir);
	}
	
	public boolean canRegenerate() { return true; }
	public void setMovement(double xa, double ya, double rot) {}
}