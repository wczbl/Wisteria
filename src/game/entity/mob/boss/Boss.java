package game.entity.mob.boss;

import game.entity.Entity;
import game.entity.mob.Mob;
import game.item.equipment.Equipment;

public class Boss extends Mob {

	public static final Equipment[][] drops = { {Equipment.basicArmor, Equipment.basicShoes}, {Equipment.powerBow}, {Equipment.strongerBasicArmor, Equipment.powerShoes}, {Equipment.strongArmor}, {Equipment.rareArmor, Equipment.rareBow, Equipment.rareShoes}, {Equipment.ultraShoes, Equipment.ultraBow, Equipment.ultraArmor} };
	
	public boolean canSwim() { return true; }
	public boolean blocks(Entity e) { return true; }
}