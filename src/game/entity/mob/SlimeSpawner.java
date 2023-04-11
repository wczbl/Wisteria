package game.entity.mob;

import game.entity.Entity;
import game.gfx.core.Screen;
import game.gfx.helper.PaletteHelper;
import game.item.Coin;
import game.item.Heart;

public class SlimeSpawner extends Mob {

	private int spawnTime;
	
	public SlimeSpawner() {
		this.xr = 6;
		this.yr = 4;
		this.health = 50;
		this.spawnTime = 100 + this.random.nextInt(100);
	}
	
	public void tick() {
		super.tick();
		
		if(this.hurtTime <= 0) this.spawnTime--;
		
		if(this.spawnTime <= 0) {
			this.spawnTime = 100 + this.random.nextInt(100);
			this.level.add(new Slime(this.x, this.y));
		}
	}
	
	public void render(Screen screen) {
		int xt = 0;
		int yt = 6;
		int xo = this.x - 8;
		int yo = this.y - 11;
				
		int col = PaletteHelper.getColor(-1, 10, 252, 555);
		if(this.hurtTime > 0) col = PaletteHelper.getColor(-1, 10, 511, 555);
		
		screen.render(2, xo, yo, xt * 8, yt * 8, 8, 8, col, 0);
	}
	
	protected void die() {
		super.die();
		
		int count = this.random.nextInt(6) + 2;
		for(int i = 0; i < count; i++) {
			this.level.add(new Coin(this.x, this.y, this.random.nextInt(5) + 1));
		}
		
		if(this.random.nextInt(20) == 0) this.level.add(new Heart(this.x, this.y, 1));
	}
	
	public boolean blocks(Entity e) { return true; }
	
	public void touchedBy(Entity e) {
		if(e.getTeam() != this.team) {
			e.hurt(this, 2, this.dir);
		}
		
		super.touchedBy(e);
	}
}