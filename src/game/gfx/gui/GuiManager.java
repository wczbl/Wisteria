package game.gfx.gui;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

import game.Game;
import game.gfx.core.Screen;
import game.gfx.gui.minimap.GuiMinimap;
import game.gfx.helper.PaletteHelper;
import game.level.Level;

public class GuiManager {
	
	private static final ConcurrentLinkedQueue<GuiPanel> queuedPanels = new ConcurrentLinkedQueue<GuiPanel>();
	private static final ConcurrentMap<String, GuiPanel> panels = new ConcurrentHashMap<String, GuiPanel>();
	
	private static GuiManager i;
	
	public static final int PANEL_COLOR = PaletteHelper.getColor(-1, 1, 5, 445);
	public static final int FONT_COLOR = PaletteHelper.getColor(-1, -1, -1, 555);
	public static final int MENU_COLOR = PaletteHelper.getColor(-1, 5, 5, 555);
	public static final int MENU_COLOR_SEL = PaletteHelper.getColor(-1, 15, 15, 555);

	public static boolean paused;
	public static boolean hasOpenedMenu;
	
	private GuiManager() {}
	
	private boolean findSamePanel(GuiPanel toFind) {
		for(GuiPanel panel : panels.values()) {
			if(panel.getX() == toFind.getX() && panel.getY() == toFind.getY()) {
				System.out.println("Duplicate panel: " + toFind.toString());
				return false;
			}
		}
		
		return false;
	}
	
	public void addToQueue(GuiPanel panel) { queuedPanels.add(panel); }
	
	public void add(GuiPanel panel, String name) {
		if(!findSamePanel(panel)) panels.put(name, panel);
		else System.out.println("Duplicate panel: " + panel);
	}
	
	public GuiPanel get(String name) { return panels.get(name); }
	
	public void tick() {
		List<String> strings = new LinkedList<String>();
		Set<String> keys = panels.keySet();
		for(String key : keys) {
			GuiPanel panel = panels.get(key);
			if(panel.closed) strings.add(key);
			else panel.tick();
		}
		
		for(String s : strings) {
			System.out.println("Removed " + s);
			panels.remove(s);
		}
	}
	
	public void render(Screen screen) {
		for(GuiPanel panel : panels.values()) {
			panel.render(screen);
		}
	}
	
	public void remove(String name) {
		GuiPanel p;
		if((p  = panels.get(name)) != null) p.close();
	}
	
	public static GuiManager getInstance() {
		if(i == null) i = new GuiManager();
		return i;
	}
	
	public void init(Level level) {
		panels.clear();
		
		add(new GuiStatusPanel(10, 220, 0, 1, 123, PaletteHelper.getColor(300, 555, 311, -1)), "health");
		add(new GuiStatusPanel(70, 220, 2, 1, 123, PaletteHelper.getColor(430, 430, 540, -1)), "money");
		add(new GuiMenu(50, 100), "menu");
		add(new GuiHelpPanel(1, 5), "controls");
		add(new GuiPauseMenu(null), "pauseMenu");
		add(new GuiInventory(1, 5, level.getPlayer()), "inventory");
		
		GuiManager.getInstance().add(new GuiMinimap(Game.WIDTH - (level.getWidth() + 8 * 5) / 2, 4, level), "minimap");
	}
}