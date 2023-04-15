package game.gfx.gui;

import java.util.LinkedList;
import java.util.List;

import game.InputHandler;
import game.gfx.core.Screen;


public class GuiMenu extends GuiPanel {

	public interface Callback {
		public boolean result(int result);
	}
	
	protected List<GuiTextPanel> panels = new LinkedList<GuiTextPanel>();
	protected Callback callback;
	protected int currentCell = -1;
	
	public GuiMenu(int x, int y) {
		this.x = x;
		this.y = y;
		this.visible = false;
	}
	
	public void showMenu(List<String> strings, Callback callback) {
		if(strings == null || callback == null || strings.size() == 0) {
			System.err.println("Menu error: Null Pointer");
			return;
		}
		
		if(this.visible) this.callback.result(-1);
		
		GuiManager.hasOpenedMenu = true;
		this.callback = callback;
		int i = 0; 
		
		this.panels.clear();
		for(String s : strings) {
			this.panels.add(new GuiTextPanel(s, this.x, this.y + (i * 24), GuiManager.FONT_COLOR, (i++ == 0) ? GuiManager.MENU_COLOR_SEL : GuiManager.MENU_COLOR));
		}
		
		this.currentCell = 0;
		this.changed = true;
		this.visible = true;
	}
	
	public void selectNext() {
		if(!this.visible) return;
		
		int lastCell = this.currentCell;
		this.currentCell--;
		
		if(this.currentCell < 0) this.currentCell = this.panels.size() - 1;
		
		this.panels.get(lastCell).setPanelColor(GuiManager.MENU_COLOR);
		this.panels.get(this.currentCell).setPanelColor(GuiManager.MENU_COLOR_SEL);
		this.changed = true;
	}
	
	public void selectPrev() {
		if(!this.visible) return;
		
		int lastCell = this.currentCell;
		this.currentCell++;
		
		if(this.currentCell >= this.panels.size()) this.currentCell = 0;
		
		this.panels.get(lastCell).setPanelColor(GuiManager.MENU_COLOR);
		this.panels.get(this.currentCell).setPanelColor(GuiManager.MENU_COLOR_SEL);
		this.changed = true;
	}
	
	public void select() {
		if(!this.visible) return;
		if(!this.callback.result(this.currentCell)) setVisible(false);
	}
	
	public void tick() {
		if(!this.visible) return;
		
		if(InputHandler.getInstance(null).up.clicked) selectNext();
		if(InputHandler.getInstance(null).down.clicked) selectPrev();
		if(InputHandler.getInstance(null).action.clicked) select();
	}
	
	public void render(Screen screen) {
		if(!this.visible) return;
		for(GuiTextPanel panel : this.panels) {
			panel.render(screen);
		}
	}
	
}