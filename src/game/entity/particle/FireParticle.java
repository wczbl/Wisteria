package game.entity.particle;

public class FireParticle extends Particle {

	public void initParticle(int x, int y, double xa, double ya) {
		super.initParticle(x, y, xa, ya);
		this.time = this.random.nextInt(4) + 2;
	}
	
	public void tick() {
		super.tick();
		
		if(this.time < 2) this.col = 0xFF0000;
		else this.col = 0xFF7F00;
		
		this.x += this.xa;
		this.y += this.ya;
	}
	
}