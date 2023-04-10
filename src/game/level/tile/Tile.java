package game.level.tile;

import game.entity.Entity;
import game.gfx.core.Screen;
import game.gfx.helper.PaletteHelper;
import game.level.Level;

public abstract class Tile {

	public static final Tile[] tiles = new Tile[256];
	public int dirtColor = PaletteHelper.getColor(211, 322, 433, 544);
	public int grassColor = PaletteHelper.getColor(24, 141, 252, 353);
	public int lavaColor = PaletteHelper.getColor(500, 500, 520, 550);
	public static final Tile grass = new GrassTile(0);
	public static final Tile rock = new RockTile(1);
	public static final Tile lava = new LavaTile(2);
	public static int tickCount;
	protected byte id;
	
	public Tile(int id) {
		if(tiles[id] != null) throw new RuntimeException("Duplicate Tile ID!(ID: " + id + ")");
		
		this.id = (byte)id;
		tiles[id] = this;
	}
	
	public void tick() {}
	public void render(Screen screen, Level level, int x, int y) {}
	
	public boolean mayPass(Level level, int x, int y, Entity e) { return true; } 
	public void bumpedInto(Level level, int xt, int yt, Entity e) {}
	public void steppedOn(Level level, int xt, int yt, Entity e) {}
	public byte getID() { return this.id; }
	public void setID(byte id) { this.id = id; }
	
}