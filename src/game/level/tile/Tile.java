package game.level.tile;

import game.entity.Entity;
import game.gfx.core.Screen;
import game.gfx.helper.PaletteHelper;
import game.level.Level;

public abstract class Tile {

	public static final Tile[] tiles = new Tile[256];
	public int lavaMainColor = 500;
	public int grassMainColor = 141;
	public int dirtMainColor = 322;
	public int sandMainColor = 550;
	public int waterMainColor = 5;
	public int dirtColor = PaletteHelper.getColor(211, this.dirtMainColor, 433, 544);
	public int grassColor = PaletteHelper.getColor(24, this.grassMainColor, 252, 353);
	public int lavaColor = PaletteHelper.getColor(500, this.lavaMainColor, 520, 550);
	public int sandColor = PaletteHelper.getColor(552, this.sandMainColor, 440, 440);
	public int waterColor = PaletteHelper.getColor(5, this.waterMainColor, 115, 115);
	public static final Tile grass = new GrassTile(0);
	public static final Tile rock = new RockTile(1);
	public static final Tile lava = new LavaTile(2);
	public static final Tile sand = new SandTile(3);
	public static final Tile water = new WaterTile(4);
	public boolean connectsToGrass;
	public boolean connectsToSand;
	public boolean connectsToLava;
	public boolean connectsToWater;
	public static int tickCount;
	protected byte id;
	
	public Tile(int id) {
		if(tiles[id] != null) throw new RuntimeException("Duplicate Tile ID!(ID: " + id + ")");
		
		this.id = (byte)id;
		tiles[id] = this;
	}
	
	public void tick(Level level, int xt, int yt) {}
	public void render(Screen screen, Level level, int x, int y) {}
	
	public boolean mayPass(Level level, int x, int y, Entity e) { return true; } 
	public void bumpedInto(Level level, int xt, int yt, Entity e) {}
	public void steppedOn(Level level, int xt, int yt, Entity e) {}
	public byte getID() { return this.id; }
	public void setID(byte id) { this.id = id; }
	
}