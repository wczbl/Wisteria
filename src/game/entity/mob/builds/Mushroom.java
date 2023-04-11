package game.entity.mob.builds;

import game.entity.ETeam;
import game.entity.Entity;
import game.entity.mob.Mob;
import game.gfx.core.Screen;
import game.gfx.helper.PaletteHelper;
import game.item.Coin;

public class Mushroom extends Mob {
	
	private int xt;
	private Long time;
	public static final int cost = 300;
	
	public Mushroom(int x, int y) {
		this.x = x;
		this.y = y;
		this.xr = 1;
		this.yr = 1;
		this.team = ETeam.PLAYER;
		this.time = System.currentTimeMillis();
	}
	
	public Mushroom() {
		this.team = ETeam.PLAYER;
		this.time = System.currentTimeMillis();
	}
	
	public void touchedBy(Entity e) {
		if(e.getTeam() != this.team) e.touchedBy(this);
	}
	
	public void tick() {
		super.tick();
		if(System.currentTimeMillis() - this.time > 10000L) {
			if(this.xt == 2 && this.level != null) {
				for(int i = 0; i < 5; i++) {
					this.level.add(new Coin(this.x, this.y, this.random.nextInt(5) + 1));
				}
			}
			
			if(this.xt < 2) this.xt++;
			this.time = System.currentTimeMillis();
		}
	}
	
	public void render(Screen screen) {
		int col = PaletteHelper.getColor(-1, 0, 510, 552);
		screen.render(this.x - 4, this.y - 5, this.xt * 8, 24, 8, 8, col, 0);
	}
	
	public boolean blocks(Entity e) { return true; }

}