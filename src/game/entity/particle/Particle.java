package game.entity.particle;

import game.entity.Entity;
import game.gfx.core.Screen;
import game.gfx.helper.BitmapHelper;

public class Particle extends Entity {

	protected int time;
	protected double xa;
	protected double ya;
	protected double za;
	protected double xx;
	protected double yy;
	protected double zz;
	protected int col;
	protected int speed = 1;
	
	public Particle(int x, int y, int col) {
		this.x = x;
		this.y = y;
		this.col = col;
		this.xx = x;
		this.yy = y;
		this.zz = 2;
		this.xa = this.random.nextGaussian() * 0.3;
		this.ya = this.random.nextGaussian() * 0.2;
		this.za = this.random.nextFloat() * 0.7 + 2;
		this.time = 60;
	}
	
	public Particle() {}
	
	public void initParticle(int x, int y, double xa, double ya) {
		this.x = x;
		this.y = y;
		this.xa = xa;
		this.ya = ya;
	}
	
	public void tick() {
		this.time--;
		if(this.time < 0) this.removed = true;
	}
	
	public void render(Screen screen) {
		int xo = this.x - screen.getXOffset();
		int yo = this.y - screen.getYOffset();
		
		if(this.zz > 0) {
			BitmapHelper.drawPoint(xo, yo, 0, screen.getViewPort());
		}
	
		BitmapHelper.drawPoint(xo, yo - (int)this.zz + 1, 0, screen.getViewPort());
		BitmapHelper.drawPoint(xo, yo - (int)this.zz, this.col, screen.getViewPort());
	}
	public int getTime() { return this.time; }
	public void setTime(int time) { this.time = time; }
	public int getSpeed() { return this.speed; }
	public void setSpeed(int speed) { this.speed = speed; }
	public int getColor() { return this.col; }
	public void setColor(int col) { this.col = col; }
	public double getXa() { return this.xa; }
	public void setXa(double xa) { this.xa = xa; }
	public double getYa() { return this.ya; }
	public void setYa(double ya) { this.ya = ya; }
	public double getZa() { return this.za; }
	public void setZa(double za) { this.za = za; }
	public double getXx() { return this.xx; }
	public void setXx(double xx) { this.xx = xx; }
	public double getYy() { return this.yy; }
	public void setYy(double yy) { this.yy = yy; }
	public double getZz() { return this.zz; }
	public void setZz(double zz) { this.zz = zz; }
	
	public void add(double xa, double ya) {
		this.xa += xa;
		this.ya += ya;
	}
}