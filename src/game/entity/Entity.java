package game.entity;

import java.util.List;
import java.util.Random;

import game.entity.mob.Mob;
import game.gfx.core.Screen;
import game.level.Level;

public class Entity {

	protected final Random random = new Random();
	protected int x; 
	protected int y;
	protected int xr = 4;
	protected int yr = 4;
	protected boolean removed;
	protected Level level;
	protected ETeam team = ETeam.ENEMY;
	
	public void init(Level level) { this.level = level; }
	
	public void tick() {}
	public void render(Screen screen) {}
	
	public boolean move(int xa, int ya) {
		if(xa != 0 || ya != 0) {
			boolean stopped = true;
			if(xa != 0 && move2(xa, 0)) stopped = false;
			if(ya != 0 && move2(0, ya)) stopped = false;
			
			if(!stopped) {
				int xt = this.x >> 4;
				int yt = this.y >> 4;
				this.level.getTile(xt, yt).steppedOn(this.level, xt, yt, this);
			}
			
			return !stopped;
		}
		
		return true;
	}
	
	protected boolean move2(int xa, int ya) {
		if(xa != 0 && ya != 0) throw new IllegalStateException("Move2 can only move one axis at a time");
		
		int xto0 = this.x - this.xr >> 4;
		int yto0 = this.y - this.yr >> 4;
		int xto1 = this.x + this.xr >> 4;
		int yto1 = this.y + this.yr >> 4;
		int xt0 = this.x + xa - this.xr >> 4;
		int yt0 = this.y + ya - this.yr >> 4;
		int xt1 = this.x + xa + this.xr >> 4;
		int yt1 = this.y + ya + this.yr >> 4;
		
		for(int yt = yt0; yt <= yt1; yt++) {
			for(int xt = xt0; xt <= xt1; xt++) {
				if(xt >= xto0 && yt >= yto0 && xt <= xto1 && yt <= yto1) continue;
				this.level.getTile(xt, yt).bumpedInto(this.level, xt, yt, this);
				if(ignoreBlocks() || this.level.getTile(xt, yt).mayPass(this.level, xt, yt, this)) continue;
				return false;
			}
		}
		
		List<Entity> wasInside = this.level.getEntities(this.x - this.xr, this.y - this.yr, this.x + this.xr, this.y + this.yr, null);
		List<Entity> isInside = this.level.getEntities(this.x + xa - this.xr, this.y + ya - this.yr, this.x + xa + this.xr, this.y + ya + this.yr, null);
		for(Entity e : isInside) {
			if(e == this) continue;
			e.touchedBy(this);
		}
		
		isInside.removeAll(wasInside);
		for(Entity e : isInside) {
			if(e == this || !e.blocks(this)) continue;
			return false;
		}
		
		this.x += xa;
		this.y += ya;
		return true;
	}
	
	public boolean intersects(int x0, int y0, int x1, int y1) {
		return this.x + this.xr >= x0 && this.y + this.yr >= y0 && this.x - this.xr <= x1 && this.y - this.yr <= y1;
	}
	
	public void touchedBy(Entity e) {}
	public boolean blocks(Entity e) { return false; }
	public void hurt(Mob mob, int dmg, int attackDir) {}
	public boolean ignoreBlocks() { return false; }
	public int getX() { return this.x; }
	public void setX(int x) { this.x = x; }
	public int getY() { return this.y; }
	public void setY(int y) { this.y = y; }
	public int getXR() { return this.xr; }
	public void setXR(int xr) { this.xr = xr; }
	public int getYR() { return this.yr; }
	public void setYR(int yr) { this.yr = yr; }
	public boolean isRemoved() { return this.removed; }
	public void setRemoved(boolean removed) { this.removed = removed; }
	public Level getLevel() { return this.level; }
	public void setLevel(Level level) { this.level = level; }
	public ETeam getTeam() { return this.team; }
	public void setTeam(ETeam team) { this.team = team; }
	
}