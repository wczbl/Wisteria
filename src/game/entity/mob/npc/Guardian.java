package game.entity.mob.npc;

import java.util.List;

import game.CharacterStats;
import game.entity.ETeam;
import game.entity.Entity;
import game.entity.mob.Mob;
import game.weapon.Weapon;
import game.weapon.arrow.EArrowType;

public class Guardian extends NPC {

	public Guardian(int x, int y) {
		super(x, y);
		this.currentStats = this.currentStats.mergeStats(new CharacterStats(3, 20, 10, 10, 10, 20));
	}
	
	public void tick() {
		super.tick();
		
		if(this.level.getPlayer() != null && this.randomWalkTime == 0) {
			int xd = this.level.getPlayer().getX() - this.x;
			int yd = this.level.getPlayer().getY() - this.y;
			if(xd * xd + yd * yd > 6400) {
				this.xa = 0;
				this.ya = 0;
				if(xd < 0) this.xa = -1;
				if(xd > 0) this.xa = +1;
				if(yd < 0) this.ya = -1;
				if(yd > 0) this.ya = +1;
			} else if(xd * xd + yd * yd < 900) {
				this.xa = 0;
				this.ya = 0;
			}
		}
		
		int speed = (this.tickTime % 4) == 0 ? 0 : 1;
		if(!move(this.xa * speed, this.ya * speed) || this.random.nextInt(100) == 0) {
			this.randomWalkTime = 30;
			this.xa = this.random.nextInt(3) - 1;
			this.ya = this.random.nextInt(3) - 1;
		}
		
		if(this.randomWalkTime > 0) this.randomWalkTime--;
		
		if(canAttack()) {
			if(this.target == null || this.target.isRemoved()) {
				int rr = 80;
				
				List<Entity> entities = this.level.getEntities(this.x - rr, this.y - rr, this.x + rr, this.y + rr, ETeam.ENEMY);
				for(Entity e : entities) {
					if(e instanceof Mob) {
						this.target = (Mob)e;
						break;
					}
				}
			} else {
				int xd = this.target.getX() - this.x;
				int yd = this.target.getY() - this.y;
				double m = Math.sqrt(xd * xd + yd * yd);
				
				if(m > 150) this.target = null;
				
				double vx = xd / m;
				double vy = yd / m;
				
				if(this.tickTime % this.currentStats.getAttackDelay() == 0) {
					boolean isCritical = this.random.nextInt(5) == 0;
					Weapon.fire(isCritical ? EArrowType.FIRE : EArrowType.SIMPLE, this.team, this.x, this.y, vx, vy, this.currentStats.getForce() + (isCritical ? this.currentStats.getForce() : 0), this.level);
				}
			}
		}
	}

}