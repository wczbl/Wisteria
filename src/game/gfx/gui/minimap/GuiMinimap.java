package game.gfx.gui.minimap;

import java.awt.Graphics2D;
import java.awt.RenderingHints;

import game.InputHandler;
import game.entity.ETeam;
import game.entity.Entity;
import game.entity.mob.Mob;
import game.entity.mob.builds.tree.Tree;
import game.gfx.core.Bitmap;
import game.gfx.core.Screen;
import game.gfx.gui.GuiPanel;
import game.gfx.helper.BitmapHelper;
import game.gfx.helper.PaletteHelper;
import game.level.Level;
import game.level.tile.Tile;

public class GuiMinimap extends GuiPanel {

	private Level level;
	private Bitmap minimap;
	private Bitmap resizedMinimap;
	private int tick = 10;
	private Mark mark;
	
	public GuiMinimap(int posX, int posY, Level level) {
		super(posX, posY, (level.getWidth() >> 1) >> 3, (level.getHeight() >> 1) >> 3, PaletteHelper.getColor(-1, 530, 0, 111));
		this.level = level;
		this.minimap = new Bitmap(level.getWidth(), level.getHeight());
		this.resizedMinimap = new Bitmap(level.getWidth() >> 1, level.getHeight() >> 1);
		this.mark = new Mark(this.resizedMinimap);
	}
	
	public void markObject(int x, int y, int col) {
		int xx = Math.min(1 + (x >> 4), this.level.getWidth() - 1);
		int yy = Math.min(0 + (y >> 4), this.level.getHeight() - 1);
		
		this.minimap.getPixels()[xx + yy * this.minimap.getWidth()] = col;
		xx = Math.min(1 + (x >> 4), this.level.getWidth() - 1);
		yy = Math.min(1 + (y >> 4), this.level.getHeight() - 1);
		
		this.minimap.getPixels()[xx + yy * this.minimap.getWidth()] = col;
		xx = Math.min(0 + (x >> 4), this.level.getWidth() - 1);
		yy = Math.min(1 + (y >> 4), this.level.getHeight() - 1);
	
		this.minimap.getPixels()[xx + yy * this.minimap.getWidth()] = col;
		xx = Math.min(0 + (x >> 4), this.level.getWidth() - 1);
		yy = Math.min(0 + (y >> 4), this.level.getHeight() - 1);
		
		this.minimap.getPixels()[xx + yy * this.minimap.getWidth()] = col;
	}
	
	public void tick() {
		this.tick++;
		
		if(InputHandler.getInstance(null).minimap.clicked) setVisible(!this.visible);
		
		this.mark.tick();
		
		if(this.tick < 45 || !this.visible) return;
		
		this.tick = 0;
		for(int j = 0; j < this.level.getHeight(); j++) {
			for(int i = 0; i < this.level.getWidth(); i++) {
				if(this.level.getFog() != null && this.level.getFog().getFog(i, j)) {
					this.minimap.getPixels()[i + j * this.minimap.getWidth()] = 0;
				} else {
					switch(this.level.getTile(i, j).getID()) {
						case Tile.GRASS_TILE: {
							this.minimap.getPixels()[i + j * this.minimap.getWidth()] = 0x538153;
							break;
						}
						
						case Tile.WATER_TILE: {
							this.minimap.getPixels()[i + j * this.minimap.getWidth()] = 0x161689;
							break;
						}
						
						case Tile.DEEP_WATER_TILE: {
							this.minimap.getPixels()[i + j * this.minimap.getWidth()] = 0x0E0E3C;
							break;
						}
						
						case Tile.SAND_TILE: {
							this.minimap.getPixels()[i + j * this.minimap.getWidth()] = 0xE2E26F;
							break;
						}
						
						case Tile.ROAD_TILE: {
							this.minimap.getPixels()[i + j * this.minimap.getWidth()] = 0xAC9567;
							break;
						}
						
						default: {
							this.minimap.getPixels()[i + j * this.minimap.getWidth()] = 0x538153;
						}
					}
					
					for(Entity e : this.level.getEntities((i - 1) << 4, (j - 1) << 4, (i + 1) << 4, (j + 1) << 4, null)) {
						if(!(e instanceof Mob) && !(e instanceof Tree)) continue;
						
						int col = 0xAABBCC;
						
						if(e instanceof Mob) col = e.getTeam() == ETeam.ENEMY ? 0xFF0000 : 0xFFCC00;
						if(e instanceof Tree) col = 0x009900;
						
						markObject(e.getX(), e.getY(), col);
					}
				}
				
				if(this.level.getPlayer() != null) markObject(this.level.getPlayer().getX(), this.level.getPlayer().getY(), 0x1CC);
			}
		}
		
		Graphics2D g = this.resizedMinimap.getImage().createGraphics();
		g.drawImage(this.minimap.getImage(), 0, 0, this.resizedMinimap.getWidth(), this.resizedMinimap.getHeight(), null);
		g.dispose();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}
	
	public void render(Screen screen) {
		if(!this.visible) return;
		
		super.render(screen);
		this.mark.render();
		
		BitmapHelper.copy(this.resizedMinimap, 0, 0, this.x + 8, this.y + 8, this.resizedMinimap.getWidth(), this.resizedMinimap.getHeight(), screen.getViewPort());
	}
	
	public void showMark(int x, int y) { this.mark.put((x >> 4) >> 1, (y >> 4) >> 1); }
	public void hideMark() { this.mark.hide(); }
	
}