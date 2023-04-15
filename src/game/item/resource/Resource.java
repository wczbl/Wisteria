package game.item.resource;

import java.util.HashMap;
import java.util.Map;

import game.entity.mob.Player;
import game.gfx.helper.PaletteHelper;
import game.level.Level;
import game.level.tile.Tile;

public class Resource {
	
	public static final Map<String, Resource> resources = new HashMap<String, Resource>();
	public static final Resource heart = new Resource("Heart", 0, 4, PaletteHelper.getColor(-1, 0, 500, 555));
	public static final Resource apple = new Resource("Apple", 0, 3, PaletteHelper.getColor(-1, 0, 510, 555));
	public static final Resource coin = new Resource("Coin", 0, 3, PaletteHelper.getColor(-1, 0, 552, 555));
	public static final Resource bigCoin = new Resource("B.Coin", 1, 3, PaletteHelper.getColor(-1, 0, 552, 555));
	public static final Resource berry = new Resource("Berry", 0, 3, PaletteHelper.getColor(-1, 0, 15, 35));
	
	
	private final String name;
	private final int xSprite;
	private final int ySprite;
	private final int col;
	
	public Resource(String name, int xSprite, int ySprite, int col) {
		if(name.length() > 6) throw new RuntimeException("Name cannot be longer than six characters!!");
		
		this.name = name;
		this.xSprite = xSprite;
		this.ySprite = ySprite;
		this.col = col;
		
		resources.put(name.toLowerCase(), this);
	}
	
	public static Resource getResourceByName(String name) { return resources.get(name); }
	
	public boolean interactOn(Tile tile, Level level, int xt, int yt, Player player, int attackDir) {
		return false;
	}
	
	public String getName() { return this.name; }
	public int getColor() { return this.col; } 
	public int getXSprite() { return this.xSprite; } 
	public int getYSprite() { return this.ySprite; } 
}