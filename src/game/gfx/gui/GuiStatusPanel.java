package game.gfx.gui;

import game.gfx.core.Bitmap;
import game.gfx.core.Font;
import game.gfx.core.Screen;
import game.gfx.helper.BitmapHelper;
import game.gfx.helper.PaletteHelper;

public class GuiStatusPanel extends GuiPanel {

	private final int negativeColor = PaletteHelper.getColor(-1, -1, -1, 500);
    private final int positiveColor = PaletteHelper.getColor(-1, -1, -1, 50);
    private final int neutralColor = PaletteHelper.getColor(-1, -1, -1, 555);

    private String text;
    private int val;
    private int pal;
    private int xOffs;
    private int yOffs;
    private int currentColor = this.neutralColor;
    private long time;

	public GuiStatusPanel(int x, int y, int xOffs, int yOffs, int val, int col) {
		this.x = x;
		this.y = y;
			
		setText(val);
		this.sizeY = 2;
		this.pal = col;
			
		this.xOffs = xOffs * 8;
		this.yOffs = yOffs * 8;
			
		this.visible = true;
	}
	
	public void setText2(String t) {
		this.text = t;
		this.sizeX = 2 + 1 + this.text.length();
		this.changed = true;
	}
	
	public void setText(int t) {
		if (t == this.val) {
			return;
		}
			
		this.time = System.currentTimeMillis() + 1000;
			
		if (t > this.val) {
			this.currentColor = this.positiveColor;
		} else {
			this.currentColor = this.negativeColor;
		}
			
		this.val = t;
		setText2("" + t);
	}
	
	public void tick() {
	    if (this.currentColor == this.neutralColor) {
	    	return;
	    }
	
	    if (System.currentTimeMillis() > this.time) {
	    	this.currentColor = this.neutralColor;
	    }
	
	    this.changed = true;
	}
	
	protected void paintFrame(Screen screen) {
	 Bitmap temp = new Bitmap(this.sizeX * 8, this.sizeY * 8);
		
	 BitmapHelper.fill(temp, 0xFF00FF);
		
	 BitmapHelper.scaleDraw(screen.getSprites(), 1, 0, 0, this.xOffs, this.yOffs, 8 << 1, 8 << 1, this.pal, 0, temp);
		
	 
	 Font.drawToBitmap(this.text, screen, 16, 4, this.currentColor, temp);
	 BitmapHelper.drawShadow(temp, 0xFF00FF, 0x555555);
		
	 this.image = null;
	 this.image = temp;
	 this.changed = false;
	}
}