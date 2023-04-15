package game.weapon.arrow;

import game.entity.ETeam;
import game.gfx.helper.PaletteHelper;

public class SilverArrow extends Arrow {

	public SilverArrow(ETeam ownerTeam, int x, int y, double vx, double vy, int dmg) {
		super(ownerTeam, x, y, vx, vy, dmg);
		this.col = PaletteHelper.getColor(-1, 111, 222, 222);
	}

}