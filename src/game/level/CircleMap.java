package game.level;

public class CircleMap {

	private boolean[] circle;
	private int r;
	private int w;
	private int h;
	
	public CircleMap(int r) {
		this.r = r;
		this.w = this.r << 1;
		this.h = this.r << 1;
		this.circle = new boolean[this.w * this.h];
		for(int i = 0; i < this.w * this.h; i++) {
			this.circle[i] = false;
		}
	}
	
	private void fillMap(int x, int y) {
		if(x >= 0 && y >= 0 && x < this.w && y < this.h) {
			this.circle[x + y * this.w] = true;
		}
	}
	
	private void fillPoints(int cx, int cy, int x, int y) {
		if(0 == x) {
			fillMap(cx, cy + y);
			fillMap(cx, cy - y);
			fillMap(cx + y, cy);
			fillMap(cx - y, cy);
		} else if(x == y) {
			fillMap(cx + x, cy + y);
			fillMap(cx - x, cy + y);
			fillMap(cx + x, cy - y);
			fillMap(cx - x, cy - y);
		} else if(x < y) {
			fillMap(cx + x, cy + y);
			fillMap(cx - x, cy + y);
			fillMap(cx + x, cy - y);
			fillMap(cx - x, cy - y);
			fillMap(cx + y, cy + x);
			fillMap(cx - y, cy + x);
			fillMap(cx + y, cy - x);
			fillMap(cx - y, cy - x);	
		}
	}
	
	public void drawCircle() {
		int x = 0;
		int y = this.r - 1;
		int p = (5 - this.r * 4) / 4;
		fillPoints(this.w >> 1, this.h >> 1, x, y);
		
		while(x < y) {
			x++;
			if(p < 0) p += 2 * x + 1;
			else {
				y--;
				p += 2 * (x - y) + 1;
			}
			
			fillPoints(this.w >> 1, this.h >> 1, x, y);
		}
		
	}
	
	private int findRightRow(int col) {
		for(int row = this.w; row > this.w >> 1; row--) {
			boolean currCol = this.circle[row + col * this.w];
			boolean nextCol = this.circle[(row - 1) + col * this.w];
			if(currCol && !nextCol) return row;
		}
		
		return -1;
	}
	
	private int findLeftRow(int col) {
		for(int row = 0; row < this.w >> 1; row++) {
			boolean currCol = this.circle[row + col * this.w];
			boolean nextCol = this.circle[(row + 1) + col * this.w];
			if(currCol && !nextCol) return row;
		}
		
		return -1;
	}
	
	public void fillCircle() {
		for(int col = 0; col < this.h; col++) {
			int leftRow = findLeftRow(col);
			if(leftRow < 0) continue;
			int rightRow = findRightRow(col);
			if(rightRow < 0) continue;
			
			if(leftRow != rightRow) {
				for(int row = leftRow; row < rightRow; row++) {
					fillMap(row, col);
				}				
			}
			
		}
	}
	
	public boolean[] getCircle() { return this.circle; }
	public void setCircle(boolean[] circle) { this.circle = circle; }
	public int getRadius() { return this.r; }
	public void setRadius(int r) { this.r = r; }
	
}