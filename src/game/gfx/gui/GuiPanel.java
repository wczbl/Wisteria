package game.gfx.gui;

import game.gfx.core.Bitmap;
import game.gfx.core.Screen;
import game.gfx.helper.BitmapHelper;

public class GuiPanel {

	protected boolean visible;
	protected int x = 0;
	protected int y = 0;
	protected int sizeX = 2;
	protected int sizeY = 2;
	protected Bitmap image;
	protected boolean changed = false;
	protected boolean closed = false;
	protected int panelColor;
	
	public GuiPanel(int posX, int posY, int sizeX, int sizeY) {
		init(posX, posY, sizeX, sizeY, GuiManager.PANEL_COLOR);
	}
	
	public GuiPanel(int posX, int posY, int sizeX, int sizeY, int panelColor) {
		init(posX, posY, sizeX, sizeY, panelColor);
	}
	
	public GuiPanel(int posX, int posY) {
		init(posX, posY, 0, 0, GuiManager.PANEL_COLOR);
	}
	
	public GuiPanel(int posX, int posY, int panelColor) {
		init(posX, posY, 0, 0, panelColor);
	}
	
	protected GuiPanel() {}
	
	private void init(int posX, int posY, int sizeX, int sizeY, int panelColor) {
		this.x = posX;
		this.y = posY;
		this.sizeX = sizeX + 2;
		this.sizeY = sizeY + 2;
			
		this.changed = true;
		this.visible = true;
		this.panelColor = panelColor;
			
		this.image = new Bitmap(this.sizeX * 8, this.sizeY * 8);
	}
	
	public void show() { setVisible(true); }	
	public void hide() { setVisible(false); }
		
	public void setPanelColor(int color) {
		this.panelColor = color;
		this.changed = true;
	}
	
	public boolean getVisible() { return this.visible; }
	
	public void setVisible(boolean v) {
		if (this.visible != v) {
			this.visible = v;
			if (this.visible) this.changed = true;
		}
	}

	public void put(int x, int y) {
		this.x = x;
		this.y = y;
		this.changed = true;
	}
	
	public void setSize(int newX, int newY) {
		this.x = (newX < 1) ? 1 : newX;
		this.y = (newY < 1) ? 1 : newY;
		this.changed = true;
	}
	
	protected void setChanged() {
		if (!this.changed) this.changed = true;
	}
	
	public void close() {
		this.closed = true;
		this.visible = false;
		this.changed = true;
	}
	
	public void paint(Screen screen) {
		if (!changed) return;	
		paintFrame(screen);
	}

	protected void paintFrame(Screen screen) {
		Bitmap temp = new Bitmap(this.sizeX * 8, this.sizeY * 8);
		
		int tx = (this.sizeX - 1) * 8;
		int ty = (this.sizeY - 1) * 8;
		
		BitmapHelper.fill(temp, 0xFF00FF);
		BitmapHelper.drawHalfTile(screen.getSprites(), 0, 0, 0, 104, this.panelColor, 0, temp);
		BitmapHelper.drawHalfTile(screen.getSprites(), tx, 0, 0, 104, this.panelColor, 1, temp);
		BitmapHelper.drawHalfTile(screen.getSprites(), 0, ty, 0, 104, this.panelColor, 2, temp);
		BitmapHelper.drawHalfTile(screen.getSprites(), tx, ty, 0, 104, this.panelColor, 3, temp);
		
		for (int x = 1; x < sizeX - 1; x++) {
			BitmapHelper.drawHalfTile(screen.getSprites(), x * 8, 0, 8, 104, this.panelColor, 0, temp);
			BitmapHelper.drawHalfTile(screen.getSprites(), x * 8, ty, 8, 104, this.panelColor, 2, temp);
		}
		for (int y = 1; y < sizeY - 1; y++) {
			BitmapHelper.drawHalfTile(screen.getSprites(), 0, y * 8, 16, 104, this.panelColor, 0, temp);
			BitmapHelper.drawHalfTile(screen.getSprites(), tx, y * 8, 16, 104, this.panelColor, 1, temp);
		}
		
		for (int x = 1; x < sizeX - 1; x++) {
			for (int y = 1; y < sizeY - 1; y++) {
				BitmapHelper.drawHalfTile(screen.getSprites(), x * 8, y * 8, 24, 104, this.panelColor, 0, temp);
			}
		}
		
		this.image = null;
		this.image = temp;
		this.changed = false;
	}
		
	public void render(Screen screen) {
		if (!this.visible) return;
		paint(screen);
		BitmapHelper.copy(this.image, 0, 0, this.x, this.y, this.sizeX * 8, this.sizeY * 8, screen.getViewPort(), 0xFF00FF);
	}
		
	public void tick() {}
	public boolean isClosed() { return this.closed; }
	public int getX() { return this.x; }
	public int getY() { return this.y; }
	public Bitmap getImage() { return this.image; }
}