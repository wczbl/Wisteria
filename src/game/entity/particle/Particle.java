package game.entity.particle;

import game.entity.Entity;

public class Particle extends Entity {

	protected int time;
	protected double xa;
	protected double ya;
	protected double za;
	protected double xx;
	protected double yy;
	protected double zz;
	
	public Particle(int x, int y) {
		this.x = x;
		this.y = y;
		this.xx = x;
		this.yy = y;
		this.zz = 2;
		this.xa = this.random.nextGaussian() * 0.3;
		this.ya = this.random.nextGaussian() * 0.2;
		this.za = this.random.nextFloat() * 0.7 + 2;
		this.time = 60;
	}
	
	public void tick() {
		this.time--;
		if(this.time <= 0) this.removed = true;
		
		if(this.zz != 0) {
			this.xx += this.xa;
			this.yy += this.ya;
			this.zz += this.za;
			
			if(this.zz < 0) {
				this.zz = 0;
				this.xa *= 0.6;
				this.ya *= 0.6;
				this.za *= -0.5;
			}
			
			this.za -= 0.15;
			this.x = (int)this.xx;
			this.y = (int)this.yy;
		}
	}
}