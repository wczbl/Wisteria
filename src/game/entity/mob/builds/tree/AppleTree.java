package game.entity.mob.builds.tree;

import game.entity.mob.Player;
import game.gfx.core.Screen;
import game.gfx.helper.BitmapHelper;
import game.gfx.helper.PaletteHelper;
import game.gfx.sprite.SpriteWrapper;
import game.item.Item;
import game.item.ItemEntity;
import game.item.resource.Resource;
import game.item.resource.ResourceItem;

public class AppleTree extends Tree {

	private boolean hasApple;
	private long time = System.currentTimeMillis() + 45000;
	
	public AppleTree(int x, int y) {
		super(x, y, 16, 12);
	}
	
	public void tick() {
		super.tick();
		
		if(this.time > System.currentTimeMillis() || this.hasApple) return;
		this.hasApple = true;
		wrapSprite(true);
	}
	
	public void render(Screen screen) {
		wrapSprite(false);
		int xo = (this.x - this.xr * 2) - screen.getXOffset();
		int yo = (this.y - this.yr * 2 - 24) - screen.getYOffset();
		
		BitmapHelper.drawNormal(this.sprite, xo, yo, screen.getViewPort(), 0xFF00FF);
	}
	
	private void wrapSprite(boolean drawAura) {
		this.level.getSpriteCollector().resetWrappers();
		this.level.getSpriteCollector().addWrapper(new SpriteWrapper(136, 32, 32, 32, PaletteHelper.getColor(20, 40, 30, -1)));
		this.level.getSpriteCollector().addWrapper(new SpriteWrapper(168, 32, 32, 32, PaletteHelper.getColor(10, 10, 20, -1)));
		this.level.getSpriteCollector().addWrapper(new SpriteWrapper(200, 32, 32, 32, PaletteHelper.getColor(100, 210, 320, -1)));
	
		if(this.hasApple) {
			this.level.getSpriteCollector().addWrapper(new SpriteWrapper(136, 0, 32, 32, PaletteHelper.getColor(310, 400, 510, -1)));
		}
		
		this.sprite = this.level.getSpriteCollector().mergedWrappers("tree" + (this.hasApple ? "_apple" : ""), 2, 0, (drawAura) ? 0x01000000 : 0);
	}
	
	public boolean interact(Item item, Player player, int dir) {
		if(!this.hasApple) return false;
		
		this.time = System.currentTimeMillis() + 120000;
		this.hasApple = false;
		
		wrapSprite(true);
		
		for(int i = 0; i < this.random.nextInt(3) + 4; i++) {
			this.level.add(new ItemEntity(new ResourceItem(Resource.apple), this.x + this.random.nextInt(31) - 15, this.y + this.random.nextInt(31)));
			
		}
		
		return false;
	}

}