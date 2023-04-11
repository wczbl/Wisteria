package game.entity.npc;

import game.entity.Entity;
import game.entity.mob.Player;
import game.gfx.core.Screen;
import game.gfx.helper.PaletteHelper;

public class Board extends Entity {

	private String text;
	
	public Board(String text, int x, int y) {
		this.x = x;
		this.y = y;
		this.text = text;
	}
	
	public void touchedBy(Entity e) {
		if(e instanceof Player && !this.level.getDialogueManager().hasText(this.text)) {
			this.level.getDialogueManager().addTypewriterText(this.text, 10, 40, PaletteHelper.getColor(5, 555, 555, 555));
		}
	}
	
	public void render(Screen screen) {
		int col = PaletteHelper.getColor(-1, 0, 222, 333);
		if(System.currentTimeMillis() / 100 % 2 == 0) {
			col = PaletteHelper.getColor(-1, 111, 222, 333);
		}
		
		screen.render(2, this.x - 4, this.y - 5, 0, 88, 8, 8, col, 0);
	}
	
	public boolean blocks(Entity e) { return false; }
}