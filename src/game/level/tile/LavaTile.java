package game.level.tile;

import game.gfx.core.Screen;
import game.gfx.helper.PaletteHelper;
import game.level.Level;

public class LavaTile extends Tile {
	
	public LavaTile(int id) { 
		super(id); 
		this.connectsToSand = true;
		this.connectsToLava = true;
		this.liquid = true;
	}

	public void render(Screen screen, Level level, int x, int y) {
		this.random.setSeed(((tickCount + (x / 2 - y) * 4311) / 10) * 54687121L + x * 3271612L + y * 3412987161L);
		
		int tranCol = PaletteHelper.getColor(3, 500, this.dirtMainColor - 111, this.dirtMainColor);
		int tranCol1 = PaletteHelper.getColor(3, 500, this.sandMainColor - 110, this.sandMainColor);
		
		boolean u = !level.getTile(x, y - 1).connectsToLava;
		boolean d = !level.getTile(x, y + 1).connectsToLava;
		boolean l = !level.getTile(x - 1, y).connectsToLava;
		boolean r = !level.getTile(x + 1, y).connectsToLava;
		boolean su = u && level.getTile(x, y - 1).connectsToSand;
		boolean sd = d && level.getTile(x, y + 1).connectsToSand;
		boolean sl = l && level.getTile(x - 1, y).connectsToSand;
		boolean sr = r && level.getTile(x + 1, y).connectsToSand;
		
		if(!u && !l) {
			screen.render(x * 16 + 0, y * 16 + 0, this.random.nextInt(4) * 8, 0, 8, 8, this.lavaColor, this.random.nextInt(4));
		} else {
			screen.render(x * 16 + 0, y * 16 + 0, (l ? 14 : 15) * 8, (u ? 0 : 1) * 8, 8, 8, su || sl ? tranCol1 : tranCol, 0);			
		}
		
		if(!u && !r) {
			screen.render(x * 16 + 8, y * 16 + 0, this.random.nextInt(4) * 8, 0, 8, 8, this.lavaColor, this.random.nextInt(4));
		} else {
			screen.render(x * 16 + 8, y * 16 + 0, (r ? 16 : 15) * 8, (u ? 0 : 1) * 8, 8, 8, su || sr ? tranCol1 : tranCol, 0);			
		}
		
		if(!d && !l) {
			screen.render(x * 16 + 0, y * 16 + 8, this.random.nextInt(4) * 8, 0, 8, 8, this.lavaColor, this.random.nextInt(4));
		} else {
			screen.render(x * 16 + 0, y * 16 + 8, (l ? 14 : 15) * 8, (d ? 2 : 1) * 8, 8, 8, sd || sl ? tranCol1 : tranCol, 0);			
		}
		
		if(!d && !r) {
			screen.render(x * 16 + 8, y * 16 + 8, this.random.nextInt(4) * 8, 0, 8, 8, this.lavaColor, this.random.nextInt(4));
		} else {
			screen.render(x * 16 + 8, y * 16 + 8, (r ? 16 : 15) * 8, (d ? 2 : 1) * 8, 8, 8, sd || sr ? tranCol1 : tranCol, 0);			
		}
		
	}
	
	public void tick(Level level, int xt, int yt) {
		int xn = xt;
		int yn = yt;
		
		if(this.random.nextBoolean()) xn += this.random.nextInt(2) * 2 - 1;
		else yn += this.random.nextInt(2) * 2 - 1;
		
		if(level.getTile(xn, yn) == Tile.hole) level.setTile(xn, yn, this, 0);
		
	}
	
}