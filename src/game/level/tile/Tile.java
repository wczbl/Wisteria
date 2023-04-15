package game.level.tile;

import java.util.Random;

import game.entity.Entity;
import game.entity.mob.Mob;
import game.entity.mob.Player;
import game.gfx.core.Screen;
import game.gfx.helper.PaletteHelper;
import game.item.Item;
import game.level.Level;

public abstract class Tile {

	public static final Tile[] tiles = new Tile[256];

	public static final int GRASS_TILE = 0;
	public static final int ROCK_TILE = 1;
	public static final int LAVA_TILE = 2;
	public static final int SAND_TILE = 3;
	public static final int WATER_TILE = 4;
	public static final int HOLE_TILE = 5;
	public static final int ROAD_TILE = 6;
	public static final int DEEP_WATER_TILE = 7;
	
	public int lavaMainColor = 500;
	public int grassMainColor = 131;
	public int dirtMainColor = 322;
	public int sandMainColor = 550;
	public int waterMainColor = 5;
	public int holeMainColor = 111;
	public int roadMainColor = 431;
	public int deepWaterMainColor = 2;

	public int holeColor = PaletteHelper.getColor(111, this.holeMainColor, 110, 110);
	public int dirtColor = PaletteHelper.getColor(211, this.dirtMainColor, 433, 544);
	public int grassColor = PaletteHelper.getColor(30, this.grassMainColor, 242, 333);
	public int lavaColor = PaletteHelper.getColor(500, this.lavaMainColor, 520, 550);
	public int sandColor = PaletteHelper.getColor(552, this.sandMainColor, 440, 440);
	public int waterColor = PaletteHelper.getColor(5, this.waterMainColor, 115, 115);
	public int roadColor = PaletteHelper.getColor(431, this.roadMainColor, this.roadMainColor - 110, 330);
	public int deepWaterColor = PaletteHelper.getColor(4, this.deepWaterMainColor, 114, 114);
	
	public static final Tile grass = new GrassTile(0);
	public static final Tile rock = new RockTile(1);
	public static final Tile lava = new LavaTile(2);
	public static final Tile sand = new SandTile(3);
	public static final Tile water = new WaterTile(4);
	public static final Tile hole = new HoleTile(5);
	public static final Tile road = new RoadTile(6);
	public static final Tile deepWater = new DeepWaterTile(7);

	public boolean connectsToGrass;
	public boolean connectsToSand;
	public boolean connectsToLava;
	public boolean connectsToWater;
	protected boolean liquid;
	public static int tickCount;
	protected int slowPeriod = 10;
	protected Random random = new Random();
	protected byte id;
	
	public Tile(int id) {
		if(tiles[id] != null) throw new RuntimeException("Duplicate Tile ID!(ID: " + id + ")");
		
		this.id = (byte)id;
		tiles[id] = this;
	}
	
	public void tick(Level level, int xt, int yt) {}
	public void render(Screen screen, Level level, int x, int y) {}
	
	public boolean interact(Level level, int xt, int yt, Player player, Item item, int attackDir) {
		return false;
	}
	
	public void steppedOn(Level level, int xt, int yt, Entity e) {
		if(!e.ignoreBlocks() && e instanceof Mob) {
			((Mob)e).setSlowGroundPeriod(this.slowPeriod);
		}
	}
	
	public boolean mayPass(Level level, int x, int y, Entity e) { return true; } 
	public void bumpedInto(Level level, int xt, int yt, Entity e) {}
	public byte getID() { return this.id; }
	public void setID(byte id) { this.id = id; }
	public boolean isLiquid() { return this.liquid; }
	
	public boolean connectsToLiquid() { return this.connectsToWater ||  this.connectsToLava; }
	
}