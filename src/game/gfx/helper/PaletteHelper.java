package game.gfx.helper;

import game.gfx.core.Bitmap;
import game.gfx.weather.Weather;

public class PaletteHelper {

	private int[] colors = new int[256];
	private static final PaletteHelper i = new PaletteHelper();
	
	public static PaletteHelper getInstance() { return i; }
	
	public PaletteHelper() { init(); }
	
	
	private void init() {
		int pp = 0;
		for(int r = 0; r < 6; r++) {
			for(int g = 0; g < 6; g++) {
				for(int b = 0; b < 6; b++) {
					int rr = r * 255 / 5;
					int gg = g * 255 / 5;
					int bb = b * 255 / 5;
					
					int mid = (rr * 30 + gg * 59 + bb * 11) / 100;
					
					int r1 = (rr + mid * 1) / 2 * 230 / 255 + 10;
					int g1 = (gg + mid * 1) / 2 * 230 / 255 + 10;
					int b1 = (bb + mid * 1) / 2 * 230 / 255 + 10;
					
					this.colors[pp++] = r1 << 16 | g1 << 8 | b1;
				}
			}
		}
	}
	
	public int getColor(byte indexColor) { return this.colors[indexColor]; }

	public void wrap(Bitmap src, boolean isGray, Weather weather) {
		for(int y = 0; y < src.getHeight(); y++) {
			for(int x = 0; x < src.getWidth(); x++) {
				int cc = src.getPixels()[x + y * src.getWidth()];
				if(cc < 255 && cc > -1) {
					int val = this.colors[cc];
					
					if(weather.isThunder()) val = 0xFFFFFF;
					else {
						if(isGray) {
							int r = (val >> 16) & 0xFF;
							int g = (val >> 8) & 0xFF;
							int b = (val >> 0) & 0xFF;
							r = (int)(0.21 * r + 0.71 * g + 0.07 * b) & 0xFF;
							g = (int)(0.21 * r + 0.71 * g + 0.07 * b) & 0xFF;
							b = (int)(0.21 * r + 0.71 * g + 0.07 * b) & 0xFF;
							val = r << 16 | g << 8 | b;
						}						
					}
					
					src.getPixels()[x + y * src.getWidth()] = val;
				}
				
			}			
		}
	}
	
	public static int getColor(int a, int b, int c, int d) {
		return (getColor(d) << 24) + (getColor(c) << 16) + (getColor(b) << 8) + getColor(a);
	}
	
	public static int getColor(int d) {
		if(d < 0) return 255;
		
		int r = d / 100 % 10;
		int g = d / 10 % 10;
		int b = d % 10;
		
		return r * 36 + g * 6 + b;
	}
	
}