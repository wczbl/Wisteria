package game.level.tile;

import game.gfx.core.Screen;
import game.gfx.helper.PaletteHelper;
import game.level.Level;

public class HoleTile extends Tile {

	public HoleTile(int id) {
		super(id);
		this.connectsToSand = true;
		this.connectsToLava = true;
		this.connectsToWater = true;
	}
	
	public void render(Screen screen, Level level, int x, int y) {
		int tranCol = PaletteHelper.getColor(3, this.holeMainColor, this.dirtMainColor - 111, this.dirtMainColor);
		int tranCol1 = PaletteHelper.getColor(3, this.holeMainColor, this.sandMainColor - 110, this.sandMainColor);
		
		boolean u = !level.getTile(x, y - 1).connectsToLiquid();
		boolean d = !level.getTile(x, y + 1).connectsToLiquid();
		boolean l = !level.getTile(x - 1, y).connectsToLiquid();
		boolean r = !level.getTile(x + 1, y).connectsToLiquid();
		boolean su = u && level.getTile(x, y - 1).connectsToSand;
		boolean sd = d && level.getTile(x, y + 1).connectsToSand;
		boolean sl = l && level.getTile(x - 1, y).connectsToSand;
		boolean sr = r && level.getTile(x + 1, y).connectsToSand;
		
		if(!u && !l) {
			screen.render(x * 16 + 0, y * 16 + 0, 0, 0, 8, 8, this.holeColor, 0);
		} else {
			screen.render(x * 16 + 0, y * 16 + 0, (l ? 14 : 15) * 8, (u ? 0 : 1) * 8, 8, 8, su || sl ? tranCol1 : tranCol, 0);			
		}
		
		if(!u && !r) {
			screen.render(x * 16 + 8, y * 16 + 0, 8, 0, 8, 8, this.holeColor, 0);
		} else {
			screen.render(x * 16 + 8, y * 16 + 0, (r ? 16 : 15) * 8, (u ? 0 : 1) * 8, 8, 8, su || sr ? tranCol1 : tranCol, 0);			
		}
		
		if(!d && !l) {
			screen.render(x * 16 + 0, y * 16 + 8, 16, 0, 8, 8, this.holeColor, 0);
		} else {
			screen.render(x * 16 + 0, y * 16 + 8, (l ? 14 : 15) * 8, (d ? 2 : 1) * 8, 8, 8, sd || sl ? tranCol1 : tranCol, 0);			
		}
		
		if(!d && !r) {
			screen.render(x * 16 + 8, y * 16 + 8, 24, 0, 8, 8, this.holeColor, 0);
		} else {
			screen.render(x * 16 + 8, y * 16 + 8, (r ? 16 : 15) * 8, (d ? 2 : 1) * 8, 8, 8, sd || sr ? tranCol1 : tranCol, 0);			
		}
	}

}