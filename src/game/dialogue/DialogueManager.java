package game.dialogue;

import java.util.ArrayList;
import java.util.List;

import game.gfx.core.Font;
import game.gfx.core.Screen;

public class DialogueManager {

	private List<Text> textList = new ArrayList<Text>();
	
	public void addTypewriterText(String text, int x, int y, int col) {
		this.textList.add(new TypedText(text, x, y, col, 100));
	}
	
	public boolean hasText(String msg) {
		for(Text text : this.textList) {
			if(!msg.equals(text.getOriginalText())) continue;
			return true;
		}
		
		return false;
	}
	
	public void tick() {
		for(int i = 0; i < this.textList.size(); i++) {
			Text text = this.textList.get(i);
			if(text.isRemoved()) {
				this.textList.remove(i--);
				continue;
			}
			
			text.tick();
		}
	}
	
	public void render(Screen screen) {
		for(Text text : this.textList) {
			Font.drawPanel(text.getText(), screen, text.getX(), text.getY(), text.getColor());
		}
	}
	
}