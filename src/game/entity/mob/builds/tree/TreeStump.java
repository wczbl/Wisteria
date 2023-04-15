package game.entity.mob.builds.tree;

import game.gfx.helper.PaletteHelper;
import game.gfx.sprite.SpriteCollector;
import game.gfx.sprite.SpriteWrapper;

public class TreeStump extends Tree {

	private boolean type = random.nextBoolean();
	
	public TreeStump(int x, int y, SpriteCollector spriteCollector) {
		super(x, y, 4, 1);
			
		spriteCollector.resetWrappers();
		spriteCollector.addWrapper(new SpriteWrapper(((this.type) ? 21 : 25) * 8, 8 << 1, 8 << 1, 8 << 1, PaletteHelper.getColor(100, 210, 320, -1)));
		spriteCollector.addWrapper(new SpriteWrapper(((this.type) ? 23 : 27) * 8, 8 << 1, 8 << 1, 8 << 1, PaletteHelper.getColor(100, 210, 320, -1)));
			
		this.sprite = spriteCollector.mergedWrappers("tree_stump_" + (this.type ? "0" : "1"), 1, this.random.nextInt(2), 0x01000000);
	}
}