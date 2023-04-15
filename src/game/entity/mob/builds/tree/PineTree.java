package game.entity.mob.builds.tree;

import game.gfx.helper.PaletteHelper;
import game.gfx.sprite.SpriteCollector;
import game.gfx.sprite.SpriteWrapper;

public class PineTree extends Tree {

	public PineTree(int x, int y, SpriteCollector spriteCollector) {
		super(x, y, 8, 8);
			
		spriteCollector.resetWrappers();
		spriteCollector.addWrapper(new SpriteWrapper(136, 128, 32, 64, PaletteHelper.getColor(30, 20, 40, -1)));
		spriteCollector.addWrapper(new SpriteWrapper(168, 128, 32, 64, PaletteHelper.getColor(10, 20, 10, -1)));
		spriteCollector.addWrapper(new SpriteWrapper(200, 128, 32, 64, PaletteHelper.getColor(100, 210, 320, -1)));
		
		this.sprite = spriteCollector.mergedWrappers("tree_pine", 1, this.random.nextInt(2), 0x01000000);
	}
	
}