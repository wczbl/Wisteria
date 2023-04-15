package game.item.resource;

import game.entity.mob.Player;
import game.gfx.core.Font;
import game.gfx.core.Screen;
import game.gfx.gui.GuiManager;
import game.item.Item;
import game.item.ItemEntity;
import game.level.Level;
import game.level.tile.Tile;

public class ResourceItem extends Item {
	
	private Resource resource;
	private int count = 1;
	
	public ResourceItem(Resource resource) { this.resource = resource; }

	public ResourceItem(Resource resource, int count) {
		this.resource = resource;
		this.count = count;
	}
	
	public int getColor() { return this.resource.getColor(); }
	public int getXSprite() { return this.resource.getXSprite(); }
	public int getYSprite() { return this.resource.getYSprite(); }
	
	public void renderIcon(Screen screen, int x, int y) {
		screen.render(x, y, this.resource.getXSprite() * 8, this.resource.getYSprite() * 8, this.resource.getColor(), 0);
	}
	
	public void renderInventory(Screen screen, int x, int y, int textX, int textY) {
		screen.render(x, y, this.resource.getXSprite() * 8, this.resource.getYSprite() * 8, this.resource.getColor(), 0);
		Font.draw("" + count, screen, (count > 0 ? 8 : 0) + textX, textY, GuiManager.FONT_COLOR);
	}
	
	public boolean interactOn(Tile tile, Level level, int xt, int yt, Player player, int attackDir) {
		if(this.resource.interactOn(tile, level, xt, yt, player, attackDir)) {
			count--;
			return true;
		}
		
		return false;
	}
	
	public String getName() { return this.resource.getName(); }
	public void onTake(ItemEntity e) {}
	public boolean isDepleted() { return this.count <= 0; }
	public Resource getResource() { return this.resource; }
	public int getCount() { return this.count; }
	public void addCount(int count) { this.count += count; }
}