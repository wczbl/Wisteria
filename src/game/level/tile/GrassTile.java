package game.level.tile;

import game.gfx.core.Screen;
import game.level.Level;

public class GrassTile extends Tile {

	public GrassTile(int id) { super(id); }
	
	public void render(Screen screen, Level level, int x, int y) {
		screen.render(x * 16, y * 16, 0, 0, 8, 8, this.grassColor, 0);
		screen.render(x * 16 + 8, y * 16, 8, 0, 8, 8, this.grassColor, 1);
		screen.render(x * 16, y * 16 + 8, 16, 0, 8, 8, this.grassColor, 2);
		screen.render(x * 16 + 8, y * 16 + 8, 24, 0, 8, 8, this.grassColor, 3);
	}

}