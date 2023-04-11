package game.gfx.core;

import game.gfx.helper.BitmapHelper;

public class Screen {

	private Bitmap viewPort;
	private Bitmap sprites;
	private int xOffset;
	private int yOffset;
	
	public Screen(int w, int h, String sheetPath) {
		this.viewPort = new Bitmap(w, h);
		this.sprites = BitmapHelper.loadBitmapFromResources(sheetPath);
	}
	
	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	public void render(int xOffs, int yOffs, int xo, int yo, int w, int h, int colors, int bits) {
		this.viewPort.draw(this.sprites, xOffs - this.xOffset, yOffs - this.yOffset, xo, yo, w, h, colors, bits);
	}
	
	public void render(int xOffs, int yOffs, int xo, int yo, int colors, int bits) {
		this.viewPort.draw(this.sprites, xOffs - this.xOffset, yOffs - this.yOffset, xo, yo, 8, 8, colors, bits);
	}
	
	public void render(int scale, int xOffs, int yOffs, int xo, int yo, int w, int h, int colors, int bits) {
		this.viewPort.draw(this.sprites, scale, xOffs - this.xOffset, yOffs - this.yOffset, xo, yo, w, h, colors, bits);
	}
	
	public void render(int scale, int xOffs, int yOffs, int xo, int yo, int colors, int bits) {
		this.viewPort.draw(this.sprites, scale, xOffs - this.xOffset, yOffs - this.yOffset, xo, yo, 8, 8, colors, bits);
	}
	
	public void render(double angle, int xOffs, int yOffs, int xo, int yo, int w, int h, int colors, int bits) {
		this.viewPort.draw(1, angle, this.sprites, xOffs - this.xOffset, yOffs - this.yOffset, xo, yo, w, h, colors, bits);
	}
	
	public void render(double angle, int xOffs, int yOffs, int xo, int yo, int colors, int bits) {
		this.viewPort.draw(1, angle, this.sprites, xOffs - this.xOffset, yOffs - this.yOffset, xo, yo, 8, 8, colors, bits);
	}
	
	public void render(int scale, double angle, int xOffs, int yOffs, int xo, int yo, int w, int h, int colors, int bits) {
		this.viewPort.draw(scale, angle, this.sprites, xOffs - this.xOffset, yOffs - this.yOffset, xo, yo, w, h, colors, bits);
	}
	
	public void render(int scale, double angle, int xOffs, int yOffs, int xo, int yo, int colors, int bits) {
		this.viewPort.draw(scale, angle, this.sprites, xOffs - this.xOffset, yOffs - this.yOffset, xo, yo, 8, 8, colors, bits);
	}
		
	public Bitmap getViewPort() { return this.viewPort; }
	public void setViewPort(Bitmap viewPort) { this.viewPort = viewPort; }
	public Bitmap getSprites() { return this.sprites; }
	public void setSprites(Bitmap sprites) { this.sprites = sprites; }
	public int getXOffset() { return this.xOffset; }
	public int getYOffset() { return this.yOffset; }
	
}