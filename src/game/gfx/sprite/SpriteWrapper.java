package game.gfx.sprite;

public class SpriteWrapper {

	private int xo;
    private int yo;
    private int width;
    private int height;
    private int colors;

    public SpriteWrapper(int xo, int yo, int width, int height, int colors) {
        this.xo = xo;
        this.yo = yo;
        this.width = width;
        this.height = height;
        this.colors = colors;
    }

    public int getXo() {
        return xo;
    }

    public void setXo(int xo) {
        this.xo = xo;
    }

    public int getYo() {
        return yo;
    }

    public void setYo(int yo) {
        this.yo = yo;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getColors() {
        return colors;
    }

    public void setColors(int colors) {
        this.colors = colors;
    }
	
}