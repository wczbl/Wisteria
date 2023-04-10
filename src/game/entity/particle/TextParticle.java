package game.entity.particle;

import game.gfx.core.Font;
import game.gfx.core.Screen;
import game.gfx.helper.PaletteHelper;

public class TextParticle extends Particle {

	private String text;
	private int col;
	
	public TextParticle(String text, int x, int y, int col) {
		super(x, y);
		this.text = text;
		this.col = col;
	}
	
	public void render(Screen screen) {
		Font.draw(this.text, screen, this.x - this.text.length() * 4 + 1, this.y - (int)this.zz + 1, PaletteHelper.getColor(-1, 0, 0, 0));
		Font.draw(this.text, screen, this.x - this.text.length() * 4, this.y - (int)this.zz, this.col);
	}

}