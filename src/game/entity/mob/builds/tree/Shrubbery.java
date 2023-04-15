package game.entity.mob.builds.tree;

import game.entity.mob.Player;
import game.gfx.helper.PaletteHelper;
import game.gfx.sprite.SpriteCollector;
import game.gfx.sprite.SpriteWrapper;
import game.item.Item;
import game.item.ItemEntity;
import game.item.resource.Resource;
import game.item.resource.ResourceItem;

public class Shrubbery extends Tree {
	
	private boolean berry = false;
	private boolean type = random.nextBoolean();
	private long tick = System.currentTimeMillis() + 60000;
	private SpriteCollector spriteCollector;
		
	public Shrubbery(int x, int y, SpriteCollector spriteCollector) {
		super(x, y, 4, 1);
		this.spriteCollector = spriteCollector;
		wrapSprite(this.berry);
	}
		
	private void wrapSprite(boolean flag) {
		this.spriteCollector.resetWrappers();
		this.spriteCollector.addWrapper(new SpriteWrapper(((this.type) ? 21 : 25) * 8, 0, 8 << 1, 8 << 1, PaletteHelper.getColor(20, 30, 40, -1)));
		this.spriteCollector.addWrapper(new SpriteWrapper(((this.type) ? 23 : 27) * 8, 0, 8 << 1, 8 << 1, PaletteHelper.getColor(10, (flag ? 5 : -1), 10, -1)));
		
		this.sprite = this.spriteCollector.mergedWrappers("shrubbery_" + (this.type ? "0" : "1") + (flag ? "_berry" : ""), 1, 0, 0);
	}
		
	public void tick() {
		super.tick();
		
		if (this.tick > System.currentTimeMillis() || this.berry) return;
		
		wrapSprite((this.berry = !this.berry));
	}
		
		
	public boolean interact(Item item, Player player, int dir) {
		if (!this.berry) return false;
		
		wrapSprite((this.berry = !this.berry));
		
		this.tick = System.currentTimeMillis() + 120000;
		
		for (int i = 0; i < random.nextInt(3) + 4; i++) {
			this.level.getPlayer().touchedBy(new ItemEntity(new ResourceItem(Resource.berry), 0, 0));
		}
		
		return false;
	}
}