package game.gfx.gui;

import game.Game;
import game.InputHandler;
import game.gfx.gui.GuiMenu.Callback;

public class GuiPauseMenu extends GuiMenu implements Callback {

	public GuiPauseMenu(Game game) {
		super(Game.WIDTH / 3, Game.HEIGHT / 2);
		
		this.panels.add(new GuiTextPanel("Resume", this.x, this.y, GuiManager.FONT_COLOR, GuiManager.MENU_COLOR_SEL));
		this.panels.add(new GuiTextPanel("Controls", this.x, this.y + 24, GuiManager.FONT_COLOR, GuiManager.PANEL_COLOR));
		this.panels.add(new GuiTextPanel("Quit", this.x, this.y + 48, GuiManager.FONT_COLOR, GuiManager.MENU_COLOR));
		
		this.callback = this;
		this.currentCell = 0;
		this.visible = false;
		this.changed = true;
	}

	public void tick() {
		super.tick();
		
		if(InputHandler.getInstance(null).pauseMenu.clicked) setVisible(!this.visible);
	}
	
	public boolean result(int result) {
		switch(result) {
			case 0: 
				hide();
				GuiManager.hasOpenedMenu = false;
				break;
				
			case 1: 
				GuiManager.getInstance().get("controls").show();
				return true;
				
			case 2:
				System.exit(0);
				return true;
		}
		
		return false;
	}
	
	public void setVisible(boolean v) {
		super.setVisible(v);
		
		GuiManager.hasOpenedMenu = v;
		GuiManager.paused = v;
	}

}