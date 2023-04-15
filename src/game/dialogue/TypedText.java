package game.dialogue;

public class TypedText extends Text {

	private int pos;
	private int period;
	private long time;
	
	public TypedText(String text, int x, int y, int col, int period) {
		super(text, x, y, col);
		this.period = period;
		this.time = System.currentTimeMillis();
	}
	
	public void reset() {
		this.time = System.currentTimeMillis();
		this.pos = 0;
		this.lifeTime = 100;
		this.removed = true;
	}
	
	public void tick() {
		if(System.currentTimeMillis() - this.time > this.period) {
			this.pos = Math.min(this.text.length(), this.pos + 1);
			this.time = System.currentTimeMillis();
		}
		
		if(this.pos == this.text.length()) this.lifeTime--;
		
		super.tick();
	}
	
	public String getText() { return this.text.substring(0, this.pos); }

}