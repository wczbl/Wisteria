package game.gfx.core;

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
	
}