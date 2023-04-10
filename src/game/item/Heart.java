package game.item;

import game.entity.particle.Particle;
import game.gfx.core.Screen;
import game.gfx.helper.PaletteHelper;

public class Heart extends Particle {

	private int health;
	
	public Heart(int x, int y, int health) {
		super(x, y);
		this.health = health;
		this.time = 600;
		this.xr = 3;
		this.yr = 3;
	}
	
	public void tick() {
		super.tick();
		
		if(this.zz == 0 && this.level.getPlayer() != null) {
			int xd = this.level.getPlayer().getX() - this.x;
			int yd = this.level.getPlayer().getY() - this.y;
			int mag = this.level.getPlayer().getScore() / 200;
			if(xd * xd + yd * yd < mag * mag + 400) {
				if(xd < 0) this.xa = -1;
				if(xd > 0) this.xa = +1;
				if(yd < 0) this.ya = -1;
				if(yd > 0) this.ya = +1;
			}
			
			move((int)this.xa, (int)this.ya);
		}
	}
	
	public void render(Screen screen) {
		int col = PaletteHelper.getColor(-1, 0, 552, 555);
		if(this.time < 200 && System.currentTimeMillis() / 250 % 2 == 0) {
			col = PaletteHelper.getColor(-1, -1, -1, -1);
		}
		
		screen.render(this.x - 4, this.y - 5 - (int)this.zz, 0, 32, 8, 8, col, 0);
	}
	
	public int getHealth() { return this.health; }

}