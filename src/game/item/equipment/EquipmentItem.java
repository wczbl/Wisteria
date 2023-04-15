package game.item.equipment;

import game.CharacterStats;
import game.gfx.core.Screen;
import game.item.Item;
import game.item.ItemEntity;

public class EquipmentItem extends Item {
	public Equipment equipment;
	
	public EquipmentItem(Equipment equipment) { this.equipment = equipment; }
	
	public void renderIcon(Screen screen, int x, int y) {
		screen.render(x, y, this.equipment.getXSprite() * 8, this.equipment.getYSprite() * 8, this.equipment.getColor(), 0);
	}
	
	public void renderInventory(Screen screen, int x, int y) {
		screen.render(x, y, this.equipment.getXSprite() * 8, this.equipment.getYSprite() * 8, this.equipment.getColor(), 0);
	}
	
	public boolean matches(Item item) {
		if(item instanceof EquipmentItem) {
			EquipmentItem other = (EquipmentItem)item;
			return other.getEquipment() == this.equipment;
		}
		
		return false;
	}
	
	public int getColor() { return this.equipment.getColor(); }
	public int getXSprite() { return this.equipment.getXSprite(); }
	public int getYSprite() { return this.equipment.getYSprite(); }
	public int getScale() { return 3; }
	public int getMaxTime() { return 2000; }
	public String getName() { return this.equipment.getName(); }
	public boolean canAttack() { return true; }
	public CharacterStats getBonusStats() { return this.equipment.getBonusStats(); }
	public EquipmentType getEquipmentType() { return this.equipment.getEquipmentType(); }
	public Equipment getEquipment() { return this.equipment; }
	public void onTake(ItemEntity e) {}
}