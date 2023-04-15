package game.entity.particle;

public class SmashParticle extends Particle {

	public SmashParticle(int x, int y, int col) { super(x, y, col); }
	
	public void tick() {
		super.tick();
		
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