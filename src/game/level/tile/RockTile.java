package game.level.tile;

import game.entity.Entity;
import game.gfx.core.Screen;
import game.gfx.helper.PaletteHelper;
import game.level.Level;

public class RockTile extends Tile {	
	public RockTile(int id) { super(id); }

	public void render(Screen screen, Level level, int x, int y) {
		screen.render(2, x * 16, y * 16, 32, 0, 8, 8, PaletteHelper.getColor(333, 222, 0, 333), 0);
	}
	
	public boolean mayPass(Level level, int x, int y, Entity e) { return false; }
}