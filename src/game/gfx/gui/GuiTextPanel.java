package game.gfx.gui;

import java.util.LinkedList;
import java.util.List;

import game.Game;
import game.gfx.core.Font;
import game.gfx.core.Screen;

public class GuiTextPanel extends GuiPanel {

	protected int textCol;
	protected String originalMessage;
	
	private List<String> formatedText = new LinkedList<String>();
	
	public GuiTextPanel(String message, int posX, int posY, int textCol, int panelCol) {
		super(posX, posY, panelCol);
		init(message, textCol);
	}
	
	public GuiTextPanel(String message, int posX, int posY, int textCol) {
		super(posX, posY);
		init(message, textCol);
	}
	
	public GuiTextPanel(String message, int posX, int posY) {
		super(posX, posY);
		init(message, GuiManager.FONT_COLOR/* PaletteHelper.getColor(5, 555, 555, 555)*/);
	}
	
	public GuiTextPanel(List<String> messages, int x, int y, int w) {
		super(x, y);
		this.textCol = GuiManager.FONT_COLOR/* PaletteHelper.getColor(5, 555, 555, 555)*/;
			
		this.setFormatedText(messages, w);
	}
	
	private void init(String message, int textCol) {
		this.originalMessage = message;
		this.textCol = textCol;
		this.changed = true;
		this.visible = true;
			
		formatString(message);
	}
	
	protected void formatString(String message) {
		if (this.formatedText.size() > 0) {
			this.formatedText.clear();
		}
			
		if (message.isEmpty()) {
			message = "TEXT";
		}
			
		final int maxLen = (Game.WIDTH / 8) / 2 + 5;
		String temp;
		int strWidth = 0;
		while (message.length() > maxLen) {
			int i = message.substring(0, maxLen).lastIndexOf(" ");
			if (i != -1 && i != maxLen) {
				temp = message.substring(0, i);
				message = message.substring(i + 1);
			} else {
				temp = message.substring(0, maxLen);
				message = message.substring(maxLen);
			}
			
			strWidth = Math.max(strWidth, temp.length());
			this.formatedText.add(temp);
		}
			
		if (message.length() <= maxLen) {
			this.formatedText.add(message);
		}
			
		this.sizeX = (this.formatedText.size() == 1) ? message.length() + 2 : maxLen + 2;
		this.sizeY = this.formatedText.size() + 2;
	}
	
	public void setText(String text) {
		formatString(text);
	    this.changed = true;
	}
	
	public void setFormatedText(List<String> formatedText, int w) {
		this.formatedText = formatedText;
		this.sizeX = w + 2;
		this.sizeY = this.formatedText.size() + 2;
		this.changed = true;
		this.visible = true;
	}
	
	public void paintFrame(Screen screen) {
		super.paintFrame(screen);
		int h = 1;
		for (String text : formatedText) {
			Font.drawToBitmap(text, screen, 8, h * 8, this.textCol, this.image);
			h++;
		}
			
		this.changed = false;
	}
	
}