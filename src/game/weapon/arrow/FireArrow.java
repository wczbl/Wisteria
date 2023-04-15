package game.weapon.arrow;

import game.entity.ETeam;
import game.gfx.helper.PaletteHelper;

public class FireArrow extends Arrow {

	public FireArrow(ETeam ownerTeam, int x, int y, double vx, double vy, int dmg) {
		super(ownerTeam, x, y, vx, vy, dmg);
		this.col = PaletteHelper.getColor(-1, 300, 500, 200);
		this.speed = 5;
	}

	public void tick() {
		super.tick();
		this.level.getFireParticles().createExplosion(this.x, this.y, 0.5, -0.6, 4);;
	}
	
}