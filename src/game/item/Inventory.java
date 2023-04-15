package game.item;

import java.util.ArrayList;
import java.util.List;

import game.item.equipment.Equipment;
import game.item.equipment.EquipmentItem;
import game.item.equipment.EquipmentType;
import game.item.resource.Resource;
import game.item.resource.ResourceItem;

public class Inventory {

	public List<Item> items = new ArrayList<Item>();
	
	public void add(Item item) { add(this.items.size(), item); }
	
	public void add(int slot, Item item) {
		if(item instanceof ResourceItem) {
			ResourceItem toTake = (ResourceItem)item;
			ResourceItem has = findResource(toTake.getResource());
			if(has == null) this.items.add(slot, toTake);
			else has.addCount(toTake.getCount());
		} else if(item instanceof EquipmentItem) {
			EquipmentItem toTake = (EquipmentItem)item;
			EquipmentItem has = findEquipmentByType(toTake.getEquipmentType());
			if(has == null) this.items.add(slot, toTake);
			else {
				slot = this.items.indexOf(has);
				this.items.remove(has);
				this.items.add(slot, toTake);
			}
		} else this.items.add(slot, item);
	}
	
	public EquipmentItem findEquipment(Equipment equipment) {
		for(Item item : this.items) {
			if(item instanceof EquipmentItem) {
				EquipmentItem has = (EquipmentItem)item;
				if(has.getEquipment() == equipment) return has;
			}
		}
		
		return null;
	}
	
	public EquipmentItem findEquipmentByType(EquipmentType equipmentType) {
		for(Item item : this.items) {
			if(item instanceof EquipmentItem) {
				EquipmentItem has = (EquipmentItem)item;
				if(has.getEquipmentType() == equipmentType) return has;
			}
		}
		
		return null;
	}
	
	public ResourceItem findResource(Resource resource) {
		for(Item item : this.items) {
			if(item instanceof ResourceItem) {
				ResourceItem has = (ResourceItem)item;
				if(has.getResource() == resource) return has;
			}
		}
		
		return null;
	}
	
	public boolean hasResource(Resource r, int count) {
		ResourceItem ri = findResource(r);
		if(ri == null) return false;
		return ri.getCount() >= count;
	}
	
	public boolean removeResource(Resource r, int count) {
		ResourceItem ri = findResource(r);
		if(ri == null) return false;
		if(ri.getCount() < count) return false;
		ri.addCount(-count);
		if(ri.getCount() <= 0) this.items.remove(ri);
		return true;
	}
	
	public int count(Item item) {
		if(item instanceof ResourceItem) {
			ResourceItem ri = findResource(((ResourceItem)item).getResource());
			if(ri != null) return ri.getCount();
		} else {
			int count = 0;
			for(Item item1 : this.items) {
				if(item1.matches(item)) count++;
			}
			
			return count;
		}
		
		return 0;
	}
	
	public List<Item> getItems() { return this.items; }
}