package game.level.tile;

import java.util.Random;

import game.gfx.core.Screen;
import game.level.Level;

public class LavaTile extends Tile {

	private Random random = new Random();
	
	public LavaTile(int id) { super(id); }

	public void render(Screen screen, Level level, int x, int y) {
		this.random.setSeed(((tickCount + (x / 2 - y) * 4311) / 10) * 54687121L + x * 3271612L + y * 3412987161L);
		screen.render(x * 16, y * 16, this.random.nextInt(4) * 8, 0, 8, 8, this.lavaColor, this.random.nextInt(4));
		screen.render(x * 16 + 8, y * 16, this.random.nextInt(4) * 8, 0, 8, 8, this.lavaColor, this.random.nextInt(4));
		screen.render(x * 16, y * 16 + 8, this.random.nextInt(4) * 8, 0, 8, 8, this.lavaColor, this.random.nextInt(4));
		screen.render(x * 16 + 8, y * 16 + 8, this.random.nextInt(4) * 8, 0, 8, 8, this.lavaColor, this.random.nextInt(4));
	}
	
}