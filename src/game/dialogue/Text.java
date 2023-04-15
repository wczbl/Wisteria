package game.dialogue;

public class Text {

	protected String text;
	protected int x;
	protected int y;
	protected int col;
	protected boolean removed;
	protected int lifeTime = 100;
	
	public Text(String text, int x, int y, int col) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.col = col;
	}
	
	public void tick() {
		if(this.lifeTime <= 0) this.removed = true;
	}
	
	public String getOriginalText() { return this.text; }
	public String getText() { return this.text; }
	public void setText(String text) { this.text = text; }
	public int getX() { return this.x; }
	public void setX(int x) { this.x = x; }
	public int getY() { return this.y; }
	public void setY(int y) { this.y = y; }
	public int getColor() { return this.col; }
	public void setColor(int col) { this.col = col; }
	public boolean isRemoved() { return this.removed; }
	public void setRemoved(boolean removed) { this.removed = removed; }
	
}