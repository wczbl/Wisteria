package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public class InputHandler implements KeyListener, MouseListener, MouseMotionListener {

	public List<Key> keys = new ArrayList<Key>();
	public Key up = new Key();
	public Key down = new Key();
	public Key left = new Key();
	public Key right = new Key();
	public Key mouse = new Key();
	public Key attack = new Key();
	public Key menu = new Key();
	
	public int mx;
	public int my;
	
	private Game game;
	
	public InputHandler(Game game) {
		this.game = game;
		game.addKeyListener(this);
		game.addMouseListener(this);
		game.addMouseMotionListener(this);
	}
	
	public void tick() {
		for(Key key : this.keys) {
			key.tick();
		}
	}
	
	public void releaseAll() {
		for(Key key : this.keys) {
			key.down = false;
		}
	}
	
	public void mouseDragged(MouseEvent e) {
		this.mx = e.getX();
		this.my = e.getY();
	}

	public void mouseMoved(MouseEvent e) {
		this.mx = e.getX();
		this.my = e.getY();
	}
	
	public int getXMousePos() {
		return this.mx / Game.SCALE + this.game.getXScroll();
	}
	
	public int getYMousePos() {
		return this.my / Game.SCALE + this.game.getYScroll();
	}
	
	public void mouseClicked(MouseEvent e) {}
	public void mousePressed(MouseEvent e) { this.mouse.toggle(true); }
	public void mouseReleased(MouseEvent e) { this.mouse.toggle(false);	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) { toggle(e, true); }
	public void keyReleased(KeyEvent e) { toggle(e, false); }
	
	public void toggle(KeyEvent e, boolean pressed) {
		if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_NUMPAD8 || e.getKeyCode() == KeyEvent.VK_UP) this.up.toggle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_NUMPAD2 || e.getKeyCode() == KeyEvent.VK_DOWN) this.down.toggle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_NUMPAD4 || e.getKeyCode() == KeyEvent.VK_LEFT) this.left.toggle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_NUMPAD6 || e.getKeyCode() == KeyEvent.VK_RIGHT) this.right.toggle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_Z || e.getKeyCode() == KeyEvent.VK_X) this.attack.toggle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_P || e.getKeyCode() == KeyEvent.VK_ESCAPE) this.menu.toggle(pressed);
	}
	
	public class Key {
		public int presses;
		public int absorbs;
		
		public boolean down;
		public boolean clicked;
		
		public Key() { InputHandler.this.keys.add(this); }
		
		public void tick() {
			if(this.absorbs < this.presses) {
				this.absorbs++;
				this.clicked = true;
			} else this.clicked = false;
		}
		
		public void toggle(boolean pressed) {
			if(pressed != this.down) this.down = pressed;
			if(pressed) this.presses++;
		}
		
	}

}