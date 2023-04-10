package game.gfx.core;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import game.gfx.helper.BitmapHelper;

public class Bitmap {

	private final int w;
	private final int h;
	private int[] pixels;
	private BufferedImage image;
	
	public Bitmap(int w, int h) {
		this.w = w;
		this.h = h;
		this.image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		this.pixels = ((DataBufferInt)this.image.getRaster().getDataBuffer()).getData();
	}
	
	public void drawPoint(int xOffs, int yOffs, int col) { BitmapHelper.drawPoint(xOffs, yOffs, col, this); }
	public void drawLine(int x0, int y0, int x1, int y1, int col) { BitmapHelper.drawLine(x0, y0, x1, y1, col, this); }
	public void drawTriangle(int x0, int y0, int x1, int y1, int x2, int y2, int col) { BitmapHelper.drawTriangle(x0, y0, x1, y1, x2, y2, col, this); }
	
	public void draw(Bitmap b, int xOffs, int yOffs, int xo, int yo, int w, int h, int cols, int bits) { 
		BitmapHelper.scaleDraw(b, 1, xOffs, yOffs, xo, yo, w, h, cols, bits, this);
	}
	
	public void draw(Bitmap b, int scale, int xOffs, int yOffs, int xo, int yo, int w, int h, int cols, int bits) {
		BitmapHelper.scaleDraw(b, scale, xOffs, yOffs, xo, yo, w, h, cols, bits, this);
	}
	
	public static GraphicsConfiguration getDefaultConfig() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		return gd.getDefaultConfiguration();
	}
	
	public BufferedImage tilt(BufferedImage image, double angle, GraphicsConfiguration gc) {
		int t = image.getColorModel().getTransparency();
		BufferedImage result = gc.createCompatibleImage(this.w, this.h, t);
		Graphics2D g = result.createGraphics();
		g.rotate(angle, this.w / 2, this.h / 2);
		g.drawRenderedImage(image, null);
		return result;
	}
	
	public void rotate(double theta) {
		this.image = tilt(this.image, Math.toRadians(theta), getDefaultConfig());
		this.pixels = ((DataBufferInt)this.image.getRaster().getDataBuffer()).getData();
	}
	
	
	public void draw(double angle, Bitmap b, int xOffs, int yOffs, int xo, int yo, int w, int h, int cols, int bits) {
		Bitmap temp = new Bitmap(w, h);
		BitmapHelper.copy(b, xo, yo, 0, 0, w, h, temp);
		temp.rotate(angle);
		BitmapHelper.scaleDraw(temp, 1, xOffs, yOffs, 0, 0, w, h, cols, bits, this);
	}
	
	public int[] getPixels() { return this.pixels; }
	public int getWidth() { return this.w; }
	public int getHeight() { return this.h; }
	public BufferedImage getImage() { return this.image; }
}