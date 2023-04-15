package game.gfx;

import java.util.ArrayList;
import java.util.List;

import game.InputHandler;
import game.gfx.core.Font;
import game.gfx.core.Screen;
import game.gfx.gui.GuiManager;
import game.gfx.helper.BitmapHelper;

public class CreditsScreen {

	private static CreditsScreen i;
	private List<String> text = new ArrayList<String>();
	private int textY = 240;
	private boolean show;
	private long tick;
	
	public static CreditsScreen getInstance() {
		if(i == null) i = new CreditsScreen();
		return i;
	}
	
	public CreditsScreen() {
		this.text.add("Developer");
		this.text.add("czbl");
		
		this.text.add("Art");
		this.text.add("czbl");
		this.text.add("free art");
		
		this.text.add("Special Thanks");
		this.text.add("My cousin");
	}
	
	public void tick() {
		if(InputHandler.getInstance(null).action.clicked) this.show = false;
		
		if(this.tick < System.currentTimeMillis()) {
			this.tick = System.currentTimeMillis() + 50;
			this.textY--;
			
			if(this.textY < 0) this.show = false;
		}
	}
	
	public void render(Screen screen) {
		BitmapHelper.fill(screen.getViewPort(), 0x000000);
		
		for(int i = 0; i < this.text.size(); i++) {
			Font.draw(this.text.get(i), screen, 0, this.textY + (i * 8), GuiManager.FONT_COLOR);
		}
	}
	
	public void show() {
		this.show = true;
		this.textY = 240;
	}
	
	public boolean isShow() { return this.show; }	
}