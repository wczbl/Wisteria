package game.weapon;

import game.entity.ETeam;
import game.level.Level;
import game.weapon.arrow.EArrowType;
import game.weapon.arrow.FireArrow;
import game.weapon.arrow.SilverArrow;
import game.weapon.arrow.SimpleArrow;

public class Weapon {

	public static void fire(EArrowType type, ETeam ownerTeam, int x, int y, double vx, double vy, int dmg, Level level) {
		switch(type) {
			case FIRE: {
				level.add(new FireArrow(ownerTeam, x, y, vx, vy, dmg));
				break;
			}
		
			case SILVER: {
				level.add(new SilverArrow(ownerTeam, x, y, vx, vy, dmg));
				break;
			}
			
			case SIMPLE: {
				level.add(new SimpleArrow(ownerTeam, x, y, vx, vy, dmg));
				break;
			}
		}
	}
}