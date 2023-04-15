package game.gfx.gui;

import java.util.LinkedList;
import java.util.List;

import game.InputHandler;
import game.entity.mob.Player;
import game.gfx.core.Font;
import game.gfx.core.Screen;
import game.gfx.helper.PaletteHelper;
import game.item.Inventory;
import game.item.Item;
import game.item.equipment.EquipmentItem;
import game.item.equipment.EquipmentType;
import game.item.resource.Resource;
import game.item.resource.ResourceItem;

public class GuiInventory extends GuiPanel {
	private int str;
	private int sta;
	private int def;
	private int speed;
	
	protected GuiPanel[] panels = new GuiPanel[7];
	
	protected Item weapon = null;
	protected Item armor = null;
	protected Item boots = null;
	protected ResourceItem apple = null;
	
	private Player player = null;

	public GuiInventory(int x, int y, Player player) {
		this.x = x;
		this.y = y;
		this.player = player;
			
		List<String> messages = new LinkedList<String>();
			
		messages.add("Strength");
		messages.add("Endurance");
		messages.add("Defense");
		messages.add("Speed");
			
		int x1 = x - 1;
			
		this.panels[0] = new GuiPanel(x1, y, 15, 15);
		this.panels[1] = new GuiTextPanel(messages, x - 1, y + 135, 15);
		this.panels[2] = new GuiPanel(x1 + 8, y + 8, 5, 6, PaletteHelper.getColor(-1, 1, 111, 445));
		this.panels[3] = new GuiPanel(x1 + 8 * 8, y + 8, 5, 6, PaletteHelper.getColor(-1, 1, 111, 445));
		this.panels[4] = new GuiPanel(x1 + 8 * 8, y + 9 * 8, 5, 5, PaletteHelper.getColor(-1, 1, 111, 445));
		this.panels[5] = new GuiPanel(x1 + 8, y + 9 * 8, 1, 1, PaletteHelper.getColor(-1, 1, 111, 445));
		this.panels[6] = new GuiPanel(x1 + 8, y + 12 * 8, 1, 1, PaletteHelper.getColor(-1, 1, 111, 445));
			
		this.visible = false;
		this.changed = true;
	}
		
	public void render(Screen screen) {
		if (!this.visible) return;
		
		for (GuiPanel panel : this.panels) {
			panel.render(screen);
		}
		
		int posX = this.panels[1].getX() + 12 * 8 + ((this.speed < 100) ? 8 : 0);
		int posY = this.panels[1].getY() + 4 * 8;
		
		Font.drawToBitmap("" + this.speed + "%", screen, posX, posY, GuiManager.FONT_COLOR, screen.getViewPort());
		
		posX = this.panels[1].getX() + 14 * 8 + ((this.str < 10) ? 8 : 0);
		posY = this.panels[1].getY() + 8;
		
		Font.drawToBitmap("" + this.str, screen, posX, posY, GuiManager.FONT_COLOR, screen.getViewPort());
		
		posX = this.panels[1].getX() + 14 * 8 + ((this.sta < 10) ? 8 : 0);
		posY += 8;
		
		Font.drawToBitmap("" + this.sta, screen, posX, posY, GuiManager.FONT_COLOR, screen.getViewPort());
		
		posX = this.panels[1].getX() + 14 * 8 + ((this.def < 10) ? 8 : 0);
		posY += 8;
		
		Font.drawToBitmap("" + this.def, screen, posX, posY, GuiManager.FONT_COLOR, screen.getViewPort());
		
		Inventory inventory = this.player.getInventory();
		
		EquipmentItem item;
		
		if ((item = inventory.findEquipmentByType(EquipmentType.WEAPON)) != null) {
			item.renderInventory(screen, this.x + 16, this.y + 16);
		}
		
		if ((item = inventory.findEquipmentByType(EquipmentType.ARMOR)) != null) {
			item.renderInventory(screen, this.x + 5 * 16, this.y + 16);
		}
		
		if ((item = inventory.findEquipmentByType(EquipmentType.SHOES)) != null) {
			item.renderInventory(screen, this.x + 5 * 16, this.y + 77);
		}
		
		ResourceItem resourceItem;
		
		if ((resourceItem = inventory.findResource(Resource.getResourceByName("apple"))) != null) {
			resourceItem.renderInventory(screen, this.x + 10, this.y + 77, this.x + 4 * 8, this.y + 10 * 8);
		}
		
		if ((resourceItem = inventory.findResource(Resource.getResourceByName("berry"))) != null) {
			resourceItem.renderInventory(screen, this.x + 10, this.y + 103, this.x + 4 * 8, this.y + 13 * 8);
		}
		
	}
		
	public void tick() {
		if (InputHandler.getInstance(null).inventory.clicked) {
			setVisible(!this.visible);
		}
	}
	
	public void setStr(int str) {
		if (this.str == str) return;
		this.str = str;
		this.changed = true;
	}
	
	public void setSta(int sta) {
		if (this.sta == sta) return;
		this.sta = sta;
		this.changed = true;
	}
	
	public void setDef(int def) {
		if (this.def == def) return;
		this.def = def;
		this.changed = true;
	}
	
	public void setSpeed(int speed) {
		if (this.speed == speed) return;
		this.speed = speed;
		this.changed = true;
	}
	
	public void setWeapon(Item weapon) { this.weapon = weapon; }
	public void setArmor(Item armor) { this.armor = armor; }
	public void setBoots(Item boots) { this.boots = boots; }
	public void setApple(ResourceItem apple) { this.apple = apple; }
	
}