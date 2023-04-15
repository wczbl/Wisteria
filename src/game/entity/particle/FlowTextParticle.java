package game.entity.particle;

public class FlowTextParticle extends TextParticle {

	public FlowTextParticle(String text, int x, int y, int col) {
		super(text, x, y, col);
		this.zz = 0;
		this.time = 35;
	}
	
	public void tick() {
		super.tick();
		this.y -= 2;
	}

}