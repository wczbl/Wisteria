package game.level;

import game.gfx.core.Screen;
import game.gfx.helper.PaletteHelper;

public class Fog {

	private boolean[] fog;
	private int w;
	private int h;
	private CircleMap circleMap;
	
	public Fog(int w, int h) {
		this.w = w;
		this.h = h;
		this.fog = new boolean[w * h];
		for(int i = 0; i < w * h; i++) {
			this.fog[i] = true;
		}
	}
	
	public boolean getFog(int x, int y) {
		return x < 0 || y < 0 || x >= this.w || y >= this.h || this.fog[x + y * this.w];
	}
	
	private CircleMap getCircleMap(int radius) {
		if(this.circleMap != null && this.circleMap.getRadius() == radius) return this.circleMap;
		this.circleMap = new CircleMap(radius);
		this.circleMap.drawCircle();
		this.circleMap.fillCircle();
		return this.circleMap;
	}
	
	void clearFog2(int xCenter, int yCenter, int radius) {
		int w = radius << 1;
		int h = radius << 1;
		int xOffs = xCenter - radius;
		int yOffs = yCenter - radius;
		for(int j = 0; j < h; j++) {
			for(int i = 0; i < w; i++) {
				try {
					this.fog[i + xOffs + (j + yOffs) * this.w] = !getCircleMap(radius).getCircle()[i + j * w] && this.fog[i + xOffs + (j + yOffs) * this.w];
					continue;
				} catch(Exception e) {
					// ignore
				}
			}
		}
	}
	
	public void clearFog1(int xCenter, int yCenter, int radius) {
		int x0 = Math.max(xCenter - radius, 0);
		int y0 = Math.max(yCenter - radius, 0);
		int x1 = Math.min(xCenter + radius, this.w - 1);
		int y1 = Math.min(yCenter + radius, this.h - 1);
		for(int y = y0; y < y1; y++) {
			for(int x = x0; x < x1; x++) {
				this.fog[x + y * this.w] = false;
			}			
		}
	}
	
	public void clearFog(int x, int y) {
		if(x >=  0 && y >= 0 && x < this.w && y < this.h) {
			this.fog[x + y * this.w] = false;
		}
	}
	
	public void render(Screen screen, int x, int y) {
		if(getFog(x, y)) {
			boolean u = !getFog(x, y - 1);
			boolean d = !getFog(x, y + 1);
			boolean l = !getFog(x - 1, y);
			boolean r = !getFog(x + 1, y);
			
			boolean ul = !getFog(x - 1, y - 1);
			boolean dl = !getFog(x - 1, y + 1);
			boolean ur = !getFog(x + 1, y - 1);
			boolean dr = !getFog(x + 1, y + 1);
			
			int col = PaletteHelper.getColor(0, 0, 0, -1);
			int fogFill = PaletteHelper.getColor(0, 0, 0, 0);	
			
			x <<= 4;
			y <<= 4;
			
			if(!u && !l) {
				if(!ul) {
					screen.render(x, y, 0, 0, 8, 8, fogFill, 0);
				} else {
					screen.render(x, y, 40, 0, 8, 8, col, 3);					
				}
			} else {
				screen.render(x, y, (l ? 9 : 8) * 8, (u ? 2 : 1) * 8, 8, 8, col, 3);
			}
			
			if(!u && !r) {
				if(!ur) {
					screen.render(x + 8, y, 0, 0, 8, 8, fogFill, 0);
				} else {
					screen.render(x + 8, y, 48, 0, 8, 8, col, 3);					
				}
			} else {
				screen.render(x + 8, y, (r ? 7 : 8) * 8, (u ? 2 : 1) * 8, 8, 8, col, 3);					
			}
			
			if(!d && !l) {
				if(!dl) {
					screen.render(x, y + 8, 0, 0, 8, 8, fogFill, 0);
				} else {
					screen.render(x, y + 8, 40, 0, 8, 8, col, 3);					
				}
			} else {
				screen.render(x, y + 8, (l ? 9 : 8) * 8, (d ? 0 : 1) * 8, 8, 8, col, 3);
			}
			
			if(!d && !r) {
				if(!dr) {
					screen.render(x + 8, y + 8, 0, 0, 8, 8, fogFill, 0);
				} else {
					screen.render(x + 8, y + 8, 48, 8, 8, 8, col, 3);					
				}
			} else {
				screen.render(x + 8, y + 8, (r ? 7 : 8) * 8, (d ? 0 : 1) * 8, 8, 8, col, 3);					
			}
		}
	}
	
}