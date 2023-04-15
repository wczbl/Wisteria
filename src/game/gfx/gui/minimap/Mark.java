package game.gfx.gui.minimap;

import game.gfx.core.Bitmap;
import game.gfx.gui.GuiPanel;
import game.gfx.helper.BitmapHelper;

public class Mark extends GuiPanel {

	private long tick;
	private final int interval = 1000;
	private Bitmap parent;
	
	public Mark(Bitmap parent) {
		this.parent = parent;
		this.image = new Bitmap(3, 3);
		BitmapHelper.fill(this.image, 0xFF00FF);
		this.image.getPixels()[1] = 0xFF0000;
		this.image.getPixels()[3] = 0xFF0000;
		this.image.getPixels()[4] = 0xFF0000;
		this.image.getPixels()[5] = 0xFF0000;
		this.image.getPixels()[7] = 0xFF0000;
		this.visible = false;
		this.changed = false;
	}
	
	public void put(int x, int y) {
		this.x = Math.max(x - 1, 0);
		this.y = Math.max(y - 1, 0);
		this.visible = true;
		this.changed = true;
	}

	public void tick() {
		if(this.changed && this.tick < System.currentTimeMillis()) {
			this.visible = !this.visible;
			this.tick = System.currentTimeMillis() + this.interval;
		}
	}
	
	public void render() {
		if(!this.visible) return;
		BitmapHelper.copy(this.image, 0, 0, this.x, this.y, 3, 3, this.parent);
	}
	
	public void hide() {
		this.visible = false;
		this.changed = false;
	}	
	
}