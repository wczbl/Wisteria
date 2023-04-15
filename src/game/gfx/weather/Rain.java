package game.gfx.weather;

import game.Game;
import game.entity.Entity;
import game.entity.mob.Player;
import game.gfx.core.Screen;
import game.gfx.helper.BitmapHelper;
import game.level.tile.Tile;

public class Rain extends Entity {

	private double xa;
	private double ya;
	private int lifeTime;
	
	public Rain(Player player, double xa, double ya, int speed) {
		if(this.random.nextInt(2) == 0) {
			this.x = (player.getX() - Game.WIDTH + this.random.nextInt(Game.WIDTH << 1));
			this.y = (player.getY() - (Game.HEIGHT >> 1) - 10);
		} else {
			this.x = (player.getX() + (Game.WIDTH >> 1) - 10);
			this.y = (player.getY() - Game.HEIGHT + this.random.nextInt(Game.HEIGHT << 1));
		}
		
		this.xa = xa * speed;
		this.ya = ya * speed;
		this.lifeTime = this.random.nextInt(150);
	}
	
	public void tick() {
		this.lifeTime--;
		if(this.lifeTime <= 0) {
			this.removed = true;
			this.level.add(new Puddle(this.x, this.y));
			int xx = this.x >> 4;
			int yy = this.y >> 4;
			if(this.level.getTile(xx, yy) == Tile.hole) {
				this.level.setTile(xx, yy, Tile.water, 0);
			}
			
			return;
		}
		
		this.x += this.xa;
		this.y += this.ya;
	}
	
	public void render(Screen screen) {
		int xo = this.x - screen.getXOffset();
		int yo = this.y - screen.getYOffset();
		BitmapHelper.drawLine(xo, yo, xo + (int)this.xa, yo + (int)this.ya, 0xFFFFFF, screen.getViewPort());
	}
}