package game.gfx.weather;

import game.entity.Entity;
import game.gfx.core.Screen;
import game.gfx.helper.PaletteHelper;

public class Puddle extends Entity {

	private long lifeTime;
	
	public Puddle(int x, int y) {
		this.x = x;
		this.y = y;
		this.lifeTime = 20;
	}
	
	public void tick() {
		this.lifeTime--;
		if(this.lifeTime <= 0) this.removed = true;
	}
	
	public void render(Screen screen) {
		int xo = this.x - 8;
		int yo = this.y - 5;
		
		int waterCol = PaletteHelper.getColor(-1, -1, -1, 444);
		if(this.lifeTime / 8 % 2 == 0) waterCol = PaletteHelper.getColor(-1, 444, -1, -1);
	
		screen.render(xo + 0, yo + 3, 40, 104, waterCol, 0);
		screen.render(xo + 8, yo + 3, 40, 104, waterCol, 1);
	}
	
}