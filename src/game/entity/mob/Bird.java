package game.entity.mob;

import game.entity.Entity;
import game.gfx.core.Screen;
import game.gfx.helper.PaletteHelper;
import game.item.Coin;

public class Bird extends Mob {

	private int xt;
	private int yt;
	private double angle;
	private int speed;
	private int xTarget;
	private int yTarget;
	private double angleInc;
	
	public Bird() {
		this.xr = 2;
		this.yr = 2;
		this.xt = 0;
		this.yt = 7;
		this.angle = 0;
		this.speed = 1;
		this.xTarget = 0;
		this.yTarget = 0;
		this.angleInc = 10;
	}
	
	public void tick() {
		super.tick();
		
		if(this.tickTime / 100 % 2 == 0) this.angleInc = 8 + this.random.nextInt(10);
		
		if(this.tickTime / 500 % 2 == 0) {
			this.xTarget = 80 + this.random.nextInt((this.level.getWidth() - 5) * 16);
			this.yTarget = 80 + this.random.nextInt((this.level.getHeight() - 5) * 16);
		}
		
		if(this.level.getPlayer() != null && !this.level.getPlayer().isRemoved()) {
			int xd = this.level.getPlayer().getX() - this.x;
			int yd = this.level.getPlayer().getY() - this.y;
			int vr = this.viewRadius * 16;
			
			if(xd * xd + yd * yd < vr * vr) {
				this.xTarget = this.level.getPlayer().getX();
				this.yTarget = this.level.getPlayer().getY();
			}
		}
		
		double testAngle = this.angle + this.angleInc <= 360 ? this.angle + this.angleInc : this.angle + this.angleInc - 360;
		double testAngle1 = this.angle - this.angleInc >= 0 ? this.angle - this.angleInc : 360 + (this.angle - this.angleInc);
		
		int xx = this.x + (int)Math.round(Math.cos(Math.toRadians(testAngle))) * this.speed;
		int yy = this.y + (int)Math.round(Math.sin(Math.toRadians(testAngle))) * this.speed;
		
		int xd = this.xTarget - xx;
		int yd = this.yTarget - yy;
		
		int dist = xd * xd + yd * yd;
	
		int xx1 = this.x - (int)Math.round(Math.cos(Math.toRadians(testAngle))) * this.speed;
		int yy1 = this.y - (int)Math.round(Math.sin(Math.toRadians(testAngle))) * this.speed;
	
		int xd1 = this.xTarget - xx1;
		int yd1 = this.yTarget - yy1;
		
		int dist1 = xd1 * xd1 + yd1 * yd1;
		
		if(dist < dist1) this.angle = testAngle;
		else if(dist > dist1) this.angle = testAngle1;
		
		if(System.currentTimeMillis() % 2 == 0) this.xt = this.xt < 3 ? this.xt + 1 : 0;
		
		int xa = (int)Math.round(Math.cos(Math.toRadians(this.angle))) * this.speed;
		int ya = (int)Math.round(Math.sin(Math.toRadians(this.angle))) * this.speed;
		
		move(xa, ya);
	}
	
	public void render(Screen screen) {
		int xo = this.x - 4;
		int yo = this.y - 4;
		
		int col = PaletteHelper.getColor(-1, 0, 421, 532);
		if(this.hurtTime > 0) col = PaletteHelper.getColor(-1, 10, 511, 500);
		
		screen.render(this.angle * this.speed + 90, xo, yo, this.xt * 8, this.yt * 8, 8, 8, col, 0);
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
	}
	
	public boolean ignoreBlocks() { return true; }
	public boolean blocks(Entity e) { return false; }
}