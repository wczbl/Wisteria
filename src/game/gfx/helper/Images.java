package game.gfx.helper;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Images {

	public static BufferedImage load(String path) {
		BufferedImage image;
		try {
			image = ImageIO.read(Images.class.getResourceAsStream(path));
			BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics g = result.getGraphics();
			g.drawImage(image, 0, 0, null);
			g.dispose();
			image = result;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return image;
	}
	
	public static BufferedImage[][] loadAndCut(String path, int sw, int sh){
		BufferedImage sheet;
		try {
			sheet = ImageIO.read(Images.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		int w = sheet.getWidth();
		int h = sheet.getHeight();
		int xs  = w / sw;
		int ys  = h / sh;
		BufferedImage[][] result = new BufferedImage[xs][ys];
		for(int x = 0; x < xs; x++) {
			for(int y = 0; y < ys; y++) {
				BufferedImage image = new BufferedImage(sw, sh, BufferedImage.TYPE_INT_ARGB);
				Graphics g = image.getGraphics();
				g.drawImage(sheet, -x * xs, -y * ys, null);
				g.dispose();
				result[x][y] = image;
			}			
		}
		
		return result;
	}
}