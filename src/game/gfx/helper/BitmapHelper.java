package game.gfx.helper;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import game.gfx.core.Bitmap;

public class BitmapHelper {

	public static final int BIT_MIRROR_X = 1;
	public static final int BIT_MIRROR_Y = 2;
	
	public static void fill(Bitmap b, int col) {
		for(int i = 0; i < b.getWidth() * b.getHeight(); i++) {
			b.getPixels()[i] = col;
		}
	}
	
	public static Bitmap loadBitmapFromResources(String name) {
		try {
			BufferedImage image = ImageIO.read(BitmapHelper.class.getResource(name));
			int w = image.getWidth();
			int h = image.getHeight();
			Bitmap result = new Bitmap(w, h);
			image.getRGB(0, 0, w, h, result.getPixels(), 0, w);
			for(int i = 0; i < result.getPixels().length; i++) {
				result.getPixels()[i] = (result.getPixels()[i] & 0xFF) / 64;
			}
			
			return result;
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void copy(Bitmap src, int xOffs, int yOffs, int xo, int yo, int w, int h, Bitmap dst) {
		xo = Math.max(0, Math.min(xo, dst.getWidth() - 1));
		yo = Math.max(0, Math.min(yo, dst.getWidth() - 1));
		w = Math.min(dst.getWidth() - 1, xo + w);
		h = Math.min(dst.getHeight() - 1, yo + h);
		xOffs = Math.min(src.getWidth() - w, xOffs - xo);
		yOffs = Math.min(src.getHeight() - h, yOffs - yo);
		for(int j = yo; j < h; j++) {
			for(int i = xo; i < w; i++) {
				dst.getPixels()[i + j * dst.getWidth()] = src.getPixels()[xOffs + i + (yOffs + j) * src.getWidth()];
			}			
		}
	}
	
	public static void drawPoint(int xOffs, int yOffs, int col, Bitmap dst) {
		if(xOffs > 0 && yOffs > 0 && xOffs < dst.getWidth() - 1 && yOffs < dst.getHeight() - 1) {
			dst.getPixels()[xOffs + yOffs * dst.getWidth()] = col;
		}
	}
	
	public static void drawLine(int x0, int y0, int x1, int y1, int col, Bitmap dst) {
		Graphics2D g = dst.getImage().createGraphics();
		g.setColor(new Color(col));
		g.drawLine(x0, y0, x1, y1);
	}
	
	public static void drawLine1(int x0, int y0, int x1, int y1, int col, Bitmap dst) {
		int xi;
		int yi;
		int vbs = x0 + y0 * dst.getWidth();
		int dx = x1 - x0;
		int dy = y1 - y0;
		
		if(dx >= 0) xi = 1;
		else {
			xi = -1;
			dx = -dx;
		}
		
		if(dy >= 0) yi = dst.getWidth();
		else {
			yi = -dst.getWidth();
			dy = -dy;
		}
		
		int dx2 = dx << 1;
		int dy2 = dy << 1;
		
		if(dx > dy) {
			int err = dy2 - dx;
			for(int i = 0; i <= dx; i++) {
				if(vbs < dst.getPixels().length && vbs >= 0) dst.getPixels()[vbs] = col;
				
				if(err >= 0) {
					err -= dx2;
					vbs += yi;
				}
				
				err += dy2;
				vbs += xi;
			}
		} else {
			int err = dx2 - dy;
			for(int i = 0; i <= dy; i++) {
				if(vbs < dst.getPixels().length && vbs >= 0) dst.getPixels()[vbs] = col;
				
				if(err >= 0) {
					err -= dy2;
					vbs += xi;
				}
				
				err += dx2;
				vbs += yi;
			}
			
		}
	}
	
	public static void drawTriangle(int x0, int y0, int x1, int y1, int x2, int y2, int col, Bitmap dst) {
		Polygon p = new Polygon(new int[] { x0, x1, x2 }, new int[]{ y0, y1, y2 }, 3);
		Graphics2D g = dst.getImage().createGraphics();
		g.setColor(new Color(col));
		g.fillPolygon(p);
	}
	
	public static void scaleDraw(Bitmap src, int scale, int xOffs, int yOffs, int xo, int yo, int w, int h, int cols, int bits, Bitmap dst) {
		boolean mirrorX = (bits & BIT_MIRROR_X) > 0;
		boolean mirrorY = bits * BIT_MIRROR_Y > 0;
		for(int y = 0; y < h * scale; y++) {
			int yp = y + yOffs;
			if(yp < 0 || yp >= dst.getHeight()) continue;
			int ys = y;
			if(mirrorY) ys = h - 1 - y;
			for(int x = 0; x < w * scale; x++) {
				int xp = x + xOffs;
				if(xp < 0 || xp >= dst.getWidth()) continue;
				int xs = x;
				if(mirrorX) xs = w - 1 - x;
				int col = cols >> src.getPixels()[xs / scale + xo + (ys / scale + yo) * src.getWidth()] * 8 & 0xFF;
				if(col >= 255) continue;
				dst.getPixels()[xp + yp * dst.getWidth()] = col;
			}
		}
	}
	
	public static void smoothRGBBitmap(Bitmap b, int count) {
		for(int i = 0; i < count; i++) {
			for(int y = 1; y < b.getHeight() - 1; y++) {
				for(int x = 1; x < b.getHeight() - 1; x++) {
					
					int cc = (b.getPixels()[x + y * b.getWidth()]);
					int cl = (b.getPixels()[(x - 1) + y * b.getWidth()]);
					int cr = (b.getPixels()[(x + 1) + y * b.getWidth()]);
					int cu = (b.getPixels()[x + (y - 1) * b.getWidth()]);
					int cd = (b.getPixels()[x + (y + 1) * b.getWidth()]);
				
					int clu = (b.getPixels()[(x - 1) + (y - 1) * b.getWidth()]);
					int cru = (b.getPixels()[(x + 1) + (y - 1) * b.getWidth()]);
					int cld = (b.getPixels()[(x - 1) + (y + 1) * b.getWidth()]);
					int crd = (b.getPixels()[(x + 1) + (y + 1) * b.getWidth()]);
					
					int mr = ((cc & 0xFF) + (cd & 0xFF) + (cl & 0xFF) + (cr & 0xFF) + (cu & 0xFF) + (cld & 0xFF) + (clu & 0xFF) + (crd & 0xFF) + (cru & 0xFF)) / 9;
					int mg = (((cc >> 8) & 0xFF) + ((cd >> 8) & 0xFF) + ((cl >> 8) & 0xFF) + ((cr >> 8) & 0xFF) + ((cu >> 8) & 0xFF) + ((cld >> 8) & 0xFF) + ((clu >> 8) & 0xFF) + ((crd >> 8) & 0xFF) + ((cru >> 8) & 0xFF)) / 9;
					int mb = (((cd >> 16) & 0xFF) + ((cl >> 16) & 0xFF) + ((cr >> 16) & 0xFF) + ((cu >> 16) & 0xFF) + ((cld >> 16) & 0xFF) + ((clu >> 16) & 0xFF) + ((crd >> 16) & 0xFF) + ((cru >> 16) & 0xFF)) / 9;
					
					b.getPixels()[x + y * b.getWidth()] = mb << 16 | mg << 8 | mr;
				}
			}	
		}
	}
	
}