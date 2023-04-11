package game.entity.particle;

import game.gfx.core.Screen;
import game.gfx.helper.PaletteHelper;

public class BloodParticle extends Particle {

	private int col;
	private int flip;
	
	public BloodParticle(int x, int y, int col) {
		super(x, y);
		this.col = col;
		this.flip = this.random.nextInt(4);
		this.time = 40;
	}
	
	public void render(Screen screen) {
		screen.render(this.x, this.y, 0, 16, PaletteHelper.getColor(-1, 0, 0, 0), this.flip);
		screen.render(this.x, this.y - (int)this.zz + 1, 0, 16, PaletteHelper.getColor(-1, 0, 0, 0), this.flip);
		screen.render(this.x, this.y - (int)this.zz, 0, 16, this.col, this.flip);
	}
	
}