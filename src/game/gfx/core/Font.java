package game.gfx.core;

import game.gfx.helper.PaletteHelper;

public class Font {

	private static final int yStart = 30;
	private static final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ      0123456789.,!?'\"-+=/\\%()<>:;    ";

	public static void draw(String text, Screen screen, int x, int y, int col) {
		text = text.toUpperCase();
		for(int i = 0; i < text.length(); i++) {
			int ix = chars.indexOf(text.charAt(i));
			if(ix < 0) continue;
			int xx = ix % 32;
			int yy = ix / 32;
			screen.render(x + i * 8, y, xx * 8, (yStart + yy) * 8, 8, 8, col, 0);
		}
	}

	public static void drawPanel(String text, Screen screen, int xx, int yy, int cols) {
		int w = text.length();
		int h = 1;
		int col = PaletteHelper.getColor(-1, 1, 5, 445);
		screen.render(xx - 8, yy - 8, 0, 104, 8, 8, col, 0);
		screen.render(xx + w * 8, yy - 8, 0, 104, 8, 8, col, 1);
		screen.render(xx - 8, yy + 8, 0, 104, 8, 8, col, 2);
		screen.render(xx + w * 8, yy + 8, 0, 104, 8, 8, col, 3);
		
		for (int x = 0; x < w; ++x) {
			screen.render(xx + x * 8, yy - 8, 8, 104, 8, 8, col, 0);
			screen.render(xx + x * 8, yy + 8, 8, 104, 8, 8, col, 2);
		}
		
		for (int y = 0; y < h; ++y) {
			screen.render(xx - 8, yy + y * 8, 16, 104, 8, 8, col, 0);
			screen.render(xx + w * 8, yy + y * 8, 16, 104, 8, 8, col, 1);
		}
		
		Font.draw(text, screen, xx, yy, cols);
	}
	
	public static void drawFrame(Screen screen, String title, int x0, int y0, int x1, int y1) {
		int col = PaletteHelper.getColor(-1, 1, 50, 445);
		for(int y = y0; y <= y1; y++) {
			for(int x = x0; x <= x1; x++) {
				if(x == x0 && y == y0) {
					screen.render(x * 8, y * 8, 0, 104, 8, 8, col, 0);
					continue;
				}
				
				if(x == x1 && y == y0) {
					screen.render(x * 8, y * 8, 0, 104, 8, 8, col, 1);
					continue;
				}
				
				if(x == x0 && y == y1) {
					screen.render(x * 8, y * 8, 0, 104, 8, 8, col, 2);
					continue;
				}
				
				if(x == x1 && y == y1) {
					screen.render(x * 8, y * 8, 0, 104, 8, 8, col, 3);
					continue;
				}
				
				if(y == y0) {
					screen.render(x * 8, y * 8, 8, 104, 8, 8, col, 0);
					continue;
				}
				
				if(y == y1) {
					screen.render(x * 8, y * 8, 8, 104, 8, 8, col, 2);
					continue;
				}
				
				if(x == x0) {
					screen.render(x * 8, y * 8, 16, 104, 8, 8, col, 0);
					continue;
				}
				
				if(x == x1) {
					screen.render(x * 8, y * 8, 16, 104, 8, 8, col, 1);
					continue;
				}
				
				screen.render(x * 8, y * 8, 16, 104, 8, 8, PaletteHelper.getColor(50, 50, 50, 50), 1);
			}			
		}
		
		draw(title, screen, x0 * 8 + 8, y0 * 8, PaletteHelper.getColor(50, 50, 50, 550));
	}
	
}