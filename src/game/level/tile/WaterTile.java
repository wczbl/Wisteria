package game.level.tile;

import java.util.Random;

import game.entity.Entity;
import game.gfx.core.Screen;
import game.gfx.helper.PaletteHelper;
import game.level.Level;

public class WaterTile extends Tile {

	private Random random = new Random();
	
	public WaterTile(int id) { 
		super(id); 
		this.connectsToSand = true;
		this.connectsToWater = true;
	}

	public void render(Screen screen, Level level, int x, int y) {
		this.random.setSeed(((tickCount + (x / 2 - y) * 4311) / 10) * 54687121L + x * 3271612L + y * 3412987161L);
		
		int tranCol = PaletteHelper.getColor(3, this.waterMainColor, this.dirtMainColor - 111, this.dirtMainColor);
		int tranCol1 = PaletteHelper.getColor(3, this.waterMainColor, this.sandMainColor - 110, this.sandMainColor);
		
		boolean u = !level.getTile(x, y - 1).connectsToWater;
		boolean d = !level.getTile(x, y + 1).connectsToWater;
		boolean l = !level.getTile(x - 1, y).connectsToWater;
		boolean r = !level.getTile(x + 1, y).connectsToWater;
		boolean su = u && level.getTile(x, y - 1).connectsToSand;
		boolean sd = d && level.getTile(x, y + 1).connectsToSand;
		boolean sl = l && level.getTile(x - 1, y).connectsToSand;
		boolean sr = r && level.getTile(x + 1, y).connectsToSand;
		
		if(!u && !l) {
			screen.render(x * 16 + 0, y * 16 + 0, this.random.nextInt(4) * 8, 0, 8, 8, this.waterColor, this.random.nextInt(4));
		} else {
			screen.render(x * 16 + 0, y * 16 + 0, (l ? 14 : 15) * 8, (u ? 0 : 1) * 8, 8, 8, su || sl ? tranCol1 : tranCol, 0);			
		}
		
		if(!u && !r) {
			screen.render(x * 16 + 8, y * 16 + 0, this.random.nextInt(4) * 8, 0, 8, 8, this.waterColor, this.random.nextInt(4));
		} else {
			screen.render(x * 16 + 8, y * 16 + 0, (r ? 16 : 15) * 8, (u ? 0 : 1) * 8, 8, 8, su || sr ? tranCol1 : tranCol, 0);			
		}
		
		if(!d && !l) {
			screen.render(x * 16 + 0, y * 16 + 8, this.random.nextInt(4) * 8, 0, 8, 8, this.waterColor, this.random.nextInt(4));
		} else {
			screen.render(x * 16 + 0, y * 16 + 8, (l ? 14 : 15) * 8, (d ? 2 : 1) * 8, 8, 8, sd || sl ? tranCol1 : tranCol, 0);			
		}
		
		if(!d && !r) {
			screen.render(x * 16 + 8, y * 16 + 8, this.random.nextInt(4) * 8, 0, 8, 8, this.waterColor, this.random.nextInt(4));
		} else {
			screen.render(x * 16 + 8, y * 16 + 8, (r ? 16 : 15) * 8, (d ? 2 : 1) * 8, 8, 8, sd || sr ? tranCol1 : tranCol, 0);			
		}
		
	}
	
	public boolean mayPass(Level level, int x, int y, Entity e) { return false; }
	
}