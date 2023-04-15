package game.gfx.gui;

import java.util.LinkedList;

import game.InputHandler;

public class GuiHelpPanel extends GuiTextPanel {

	public GuiHelpPanel(int x, int y) {
		super("", x, y);
		
		LinkedList<String> controls = new LinkedList<String>();
		controls.add("Movement:");
		controls.add("W - Move Forward");
		controls.add("S - Move Backwards");
		controls.add("A - Move Left");
		controls.add("D - Move Right");
		controls.add("Other:");
		controls.add("MOUSE - Attack");
		controls.add("Spacebar - Mushroom");
		controls.add("Q - Eat Apple");
		controls.add("E - Eat Berry");
		controls.add("I - Inventory");
		controls.add("ESC - Pause Menu");
		controls.add("H - Controls");
		controls.add("M - Minimap");
		setFormatedText(controls, 28);
		this.visible = false;
	}
	
	public void tick() {
		if(InputHandler.getInstance(null).controls.clicked) setVisible(!this.visible);
	}

}