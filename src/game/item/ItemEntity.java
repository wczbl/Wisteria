package game.item;

import game.entity.Entity;
import game.entity.mob.Player;
import game.entity.particle.SmashParticle;
import game.gfx.core.Screen;
import game.gfx.helper.PaletteHelper;
import game.sound.Sound;

public class ItemEntity extends SmashParticle {

	private Item item;
	
	public ItemEntity(Item item, int x, int y) { 
		super(x, y, 0); 
		this.item = item;
		this.time = item.getMaxTime();
	}
	
	public void tick() {
		super.tick();
		
		if(this.zz == 0 && this.level.getPlayer() != null) {
			int xd = this.level.getPlayer().getX() - this.x;
			int yd = this.level.getPlayer().getY() - this.y;
			if(xd * xd + yd * yd < 20 * 20) {
				if(xd < 0) this.xa = -1;
				if(xd > 0) this.xa = +1;
				if(yd < 0) this.ya = -1;
				if(yd > 0) this.ya = +1;
			}
			
			move((int)this.xa, (int)this.ya);
		}
	}
	
	public void render(Screen screen) {
		if(this.time < this.item.getMaxTime() / 3) {
			if(this.time / 6 % 2 == 0) return;
		}
		
		screen.render(this.item.getScale(), this.x - 4 * this.item.getScale(), this.y - 4 * this.item.getScale(), this.item.getXSprite() * 8, this.item.getYSprite() * 8, PaletteHelper.getColor(-1, 0, 0, 0), 0);
		screen.render(this.item.getScale(), this.x - 4 * this.item.getScale(), this.y - 4 * this.item.getScale() - (int)this.zz, this.item.getXSprite() * 8, this.item.getYSprite() * 8, this.item.getColor(), 0);
	}
	
	public void touchedBy(Entity e) { e.touchItem(this); }
	
	public void take(Entity e) {
		if(e instanceof Player) {
			if(this.level == null) return;
			Sound.pickup.setVolume(0.1f);
			Sound.pickup.play();
		}
		this.item.onTake(this);
		this.removed = true;
	}
	
	public Item getItem() { return this.item; }
	public boolean ignoreBlocks() { return true; }
	
}