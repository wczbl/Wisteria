package game.level.tile;

import game.gfx.core.Screen;
import game.gfx.helper.PaletteHelper;
import game.level.Level;

public class GrassTile extends Tile {

	public GrassTile(int id) { 
		super(id); 
		this.connectsToGrass = true;
	}
	
	public void render(Screen screen, Level level, int x, int y) {
		int tranCol = PaletteHelper.getColor(this.grassMainColor - 111, this.grassMainColor, this.grassMainColor + 111, this.dirtMainColor);
		
		boolean u = !level.getTile(x, y - 1).connectsToGrass;
		boolean d = !level.getTile(x, y + 1).connectsToGrass;
		boolean l = !level.getTile(x - 1, y).connectsToGrass;
		boolean r = !level.getTile(x + 1, y).connectsToGrass;
		
		if(!u && !l) {
			screen.render(x * 16 + 0, y * 16 + 0, 0, 0, 8, 8, this.grassColor, 0);
		} else {
			screen.render(x * 16 + 0, y * 16 + 0, (l ? 11 : 12) * 8, (u ? 0 : 1) * 8, 8, 8, tranCol, 0);			
		}
		
		if(!u && !r) {
			screen.render(x * 16 + 8, y * 16 + 0, 8, 0, 8, 8, this.grassColor, 0);
		} else {
			screen.render(x * 16 + 8, y * 16 + 0, (r ? 13 : 12) * 8, (u ? 0 : 1) * 8, 8, 8, tranCol, 0);			
		}
		
		if(!d && !l) {
			screen.render(x * 16 + 0, y * 16 + 8, 16, 0, 8, 8, this.grassColor, 0);
		} else {
			screen.render(x * 16 + 0, y * 16 + 8, (l ? 11 : 12) * 8, (d ? 2 : 1) * 8, 8, 8, tranCol, 0);			
		}
		
		if(!d && !r) {
			screen.render(x * 16 + 8, y * 16 + 8, 24, 0, 8, 8, this.grassColor, 0);
		} else {
			screen.render(x * 16 + 8, y * 16 + 8, (r ? 13 : 12) * 8, (d ? 2 : 1) * 8, 8, 8, tranCol, 0);			
		}
		
	}

}