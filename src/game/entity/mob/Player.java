package game.entity.mob;

import game.InputHandler;
import game.entity.ETeam;
import game.entity.Entity;
import game.entity.mob.builds.Mushroom;
import game.gfx.core.Screen;
import game.gfx.helper.PaletteHelper;
import game.item.Coin;
import game.item.Heart;
import game.level.Level;
import game.level.tile.Tile;
import game.weapon.Arrow;

public class Player extends Mob {
	
	private InputHandler input;
	private int color;
	private int score = 1000;
	private int clearFogRadius = 4;
	
	public Player(InputHandler input) {
		this.team = ETeam.PLAYER;
		this.input = input;
		this.color = PaletteHelper.getColor(-1, 100, 522, 555);
	}
	
	public void tick() {
		int xa = 0;
		int ya = 0;
		
		if(this.input.up.down) ya--;
		if(this.input.down.down) ya++;
		if(this.input.left.down) xa--;
		if(this.input.right.down) xa++;
		
		this.color = PaletteHelper.getColor(-1, 100, 522, 555);

		if(this.input.attack.down && this.level.getEntities(this.x - 8, this.y - 8, this.x + 8, this.y + 8, null).size() == 1 && this.score >= Mushroom.cost && this.level != null) {
			this.level.add(new Mushroom(this.x, this.y));
			this.score -= Mushroom.cost;
		}
		
		if(this.input.mouse.down) {
			int xd = this.input.getXMousePos() - this.x;
			int yd = this.input.getYMousePos() - this.y;
			double m = Math.sqrt(xd * xd + yd * yd);
			xd = (int)Math.round(xd / m);
			yd = (int)Math.round(yd / m);
			if(this.tickTime % 6 == 0) {
				this.level.add(new Arrow(this.team, this.x, this.y, xd, yd, this.random.nextInt(this.score / 1000 + 3) + 1));
			}
			
			this.color = PaletteHelper.getColor(-1, 111, 444, 555);
		}
		
		move(xa, ya);
		super.tick();
	}
	
	public void render(Screen screen) {
		int xo = this.x - 4;
		int yo = this.y - 6;
		screen.render(xo, yo, 0, 48, 8, 8, this.color, 0);
	}
	
	public void touchedBy(Entity e) {
		if(e instanceof Coin) {
			Coin coin = (Coin)e;
			this.score += coin.getCost();
			coin.setRemoved(true);
		}
		
		if(e instanceof Heart) {
			Heart heart = (Heart)e;
			this.health += heart.getHealth();
			heart.setRemoved(true);
		}
		
		if(e.getTeam() != this.team) e.touchedBy(this);
		
	}
	
	public boolean findStartPos(Level level) {
		int x = this.random.nextInt(level.getWidth());
		int y = this.random.nextInt(level.getHeight());
		
		while(level.getTile(x, y) != Tile.grass) {
			x = this.random.nextInt(level.getWidth()); 
			y = this.random.nextInt(level.getHeight());
		}
		
		this.x = x * 16 + 8;
		this.y = y * 16 + 8;
		
		return true;
	}
	
	public int getClearFogRadius() { return this.clearFogRadius; }
	public int getScore() { return this.score; }

}