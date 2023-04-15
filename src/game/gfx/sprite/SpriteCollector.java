package game.gfx.sprite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.gfx.core.Bitmap;
import game.gfx.helper.BitmapHelper;

public class SpriteCollector {

	private List<SpriteWrapper> spriteWrappers = new ArrayList<SpriteWrapper>();
	private Map<String, Bitmap> sprites = new HashMap<String, Bitmap>();
	private int width = 0;
	private int height = 0;
	private Bitmap source;
	
	public SpriteCollector(Bitmap source) {
		this.source = source;
	}
	
	public void resetWrappers() {
		this.spriteWrappers.clear();
	}
	
	public void addWrapper(SpriteWrapper spriteWrapper) {
		this.width = Math.max(spriteWrapper.getWidth(), this.width);
		this.height = Math.max(spriteWrapper.getHeight(), this.height);
		this.spriteWrappers.add(spriteWrapper);
	}
	
	public Bitmap mergedWrappers(String name, int scale, int bits, int auraColor) {
		name += "_" + scale + "_" + bits + "_" + (auraColor >> 24);
			
		Bitmap result = this.sprites.get(name);
		if (result != null) return result;
		
			
		if (this.spriteWrappers.size() == 0) return null;
			
		result = new Bitmap(this.width * scale, this.height * scale);
		BitmapHelper.fill(result, 0xFF00FF);
			
			
		for (SpriteWrapper spriteWrapper : this.spriteWrappers) {
			BitmapHelper.scaleDraw(this.source, scale, 0, 0, spriteWrapper.getXo(), spriteWrapper.getYo(), spriteWrapper.getWidth(), spriteWrapper.getHeight(), spriteWrapper.getColors(), bits, result);
		}
			
		if ((auraColor >> 24) == 1) BitmapHelper.drawAura(result, 0xFF00FF, auraColor & 0xFFFFFF);
		
			
		this.sprites.put(name, result);
			
		return result;
	}
}