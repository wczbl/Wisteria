package game.entity.particle;

public class BloodParticle extends SmashParticle {

	private int col;
	
	public BloodParticle(int x, int y, int col) {
		super(x, y, col);
		this.col = col;
		this.time = 100 + this.random.nextInt(300);
	}
	
	public void tick() {
		super.tick();
		
		
		if(this.time < 100) {
			if(this.time % 10 == 0) {
				int rr = (this.col >> 16) & 0xFF;
				int gg = (this.col >> 8) & 0xFF;
				int bb = (this.col >> 0) & 0xFF;
				
				this.col = ((rr >> 2) << 16) | ((gg >> 2) << 8) | (bb >> 2);
			}
		}
	}
	
}