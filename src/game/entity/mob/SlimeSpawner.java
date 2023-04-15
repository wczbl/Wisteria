package game.entity.mob;

import game.entity.Entity;
import game.gfx.core.Screen;
import game.gfx.helper.PaletteHelper;
import game.item.ItemEntity;
import game.item.resource.Resource;
import game.item.resource.ResourceItem;

public class SlimeSpawner extends Mob {

	private int spawnTime;
	
	public SlimeSpawner() {
		this.xr = 6;
		this.yr = 4;
		this.health = 50;
		this.spawnTime = 300 + this.random.nextInt(300);
	}
	
	public void tick() {
		super.tick();
		
		if(this.hurtTime <= 0) this.spawnTime--;
		
		if(this.spawnTime <= 0) {
			this.spawnTime = 300 + this.random.nextInt(300);
			this.level.add(new Slime(this.x, this.y));
		}
	}
	
	public void render(Screen screen) {
		int xt = 0;
		int yt = 6;
		int xo = this.x - 8;
		int yo = this.y - 11;
				
		int col = PaletteHelper.getColor(-1, 10, 242 + this.level.getLevelNum() * 10, 555);
		if(this.hurtTime > 0) col = PaletteHelper.getColor(-1, 555, 555, 555);
		
		screen.render(2, xo, yo, xt * 8, yt * 8, 8, 8, col, 0);
	}
	
	protected void die() {
		super.die();
		
		int count = this.random.nextInt(2) + 2;
		for(int i = 0; i < count; i++) {
			this.level.add(new ItemEntity(new ResourceItem(Resource.bigCoin), this.x + this.random.nextInt(11) - 5, this.y + this.random.nextInt(11) - 5));
		}
		
	}
	
	public boolean blocks(Entity e) { return true; }
	public boolean canSwim() { return false; }
	
	public void touchedBy(Entity e) {
		if(e.getTeam() != this.team) {
			e.hurt(this, 2 * (this.level.getLevelNum() + 1), this.dir);
		}
		
		super.touchedBy(e);
	}
}