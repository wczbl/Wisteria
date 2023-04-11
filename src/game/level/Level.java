package game.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import game.dialogue.DialogueManager;
import game.entity.ETeam;
import game.entity.Entity;
import game.entity.mob.Bird;
import game.entity.mob.Mob;
import game.entity.mob.Player;
import game.entity.mob.SlimeSpawner;
import game.entity.mob.builds.Mushroom;
import game.entity.mob.builds.Tree;
import game.gfx.core.Screen;
import game.level.tile.Tile;

public class Level {

	private List<Entity> entities = new ArrayList<Entity>();
	private List<Entity>[] entitiesInTiles;
	private Random random = new Random();
	private int w;
	private int h;
	private byte[] tiles;
	private Fog fog;
	private Player player;
	private int monsterDensity = 4;
	private DialogueManager dialogueManager;
	private List<Entity> rowSprites = new ArrayList<Entity>();
	private Comparator<Entity> spriteSorter = new Comparator<Entity>() {

		public int compare(Entity e0, Entity e1) {
			if(e1.getY() < e0.getY()) return 1;
			if(e1.getY() > e0.getY()) return -1;
			return 0;
		}
		
	};
	
	@SuppressWarnings("unchecked")
	public Level(int w, int h) {
		this.w = w;
		this.h = h;
		this.tiles = new byte[w * h];
		this.fog = new Fog(w, h);
		this.dialogueManager = new DialogueManager();
		this.entitiesInTiles = new ArrayList[w * h];
		for(int i = 0; i < w * h; i++) {
			this.entitiesInTiles[i] = new ArrayList<Entity>();
		}
		
		for(int y = 0; y < this.h; y++) {
			for(int x = 0; x < this.w; x++) {
				setTile(x, y, Tile.grass, 0);
				
				if(x >= 2 && y >= 2 && x <= w - 3 && y <= h - 3) continue;
				setTile(x, y, Tile.lava, 0);
			}
		}
		
		trySpawn();
	}
	
	public void add(Entity e) {
		if(e instanceof Player) {
			this.player = (Player)e;
			this.fog.clearFog2(this.player.getX() >> 4, this.player.getY() >> 4, this.player.getClearFogRadius());
		}
		
		this.entities.add(e);
		e.init(this);
		insertEntity(e.getX() >> 4, e.getY() >> 4, e);
	}
	
	public void tick() {
		for(int i = 0; i < this.w * this.h / 50; i++) {
			int xt = this.random.nextInt(this.w);
			int yt = this.random.nextInt(this.h);
			getTile(xt, yt).tick(this, xt, yt);
		}
		
		this.dialogueManager.tick();
		
		for(int i = 0; i < this.entities.size(); i++) {
			Entity e = this.entities.get(i);
			int xto = e.getX() >> 4;
			int yto = e.getY() >> 4;
			
			e.tick();
			
			if(e.isRemoved()) {
				this.entities.remove(i--);
				removeEntity(xto, yto, e);
				continue;
			}
			
			int xt = e.getX() >> 4;
			int yt = e.getY() >> 4;
			
			if(xto != xt || yto != yt) {
				removeEntity(xto, yto, e);
				insertEntity(xt, yt, e);
			}
			
			if(e instanceof Player) this.fog.clearFog2(xt, yt, ((Player)e).getClearFogRadius());
		}
	}
	
	public void renderBackground(Screen screen, int xScroll, int yScroll) {
		int xo = xScroll >> 4;
		int yo = yScroll >> 4;
		int w = screen.getViewPort().getWidth() + 16 - 1 >> 4;
		int h = screen.getViewPort().getHeight() + 16 - 1 >> 4;
		screen.setOffset(xScroll, yScroll);
		for(int y = yo; y <= h + yo; y++) {
			for(int x = xo; x <= w + xo; x++) {
				getTile(x, y).render(screen, this, x, y);
			}
		}
		
		screen.setOffset(0, 0);
	}
	
	public void renderSprites(Screen screen, int xScroll, int yScroll) {
		int xo = xScroll >> 4;
		int yo = yScroll >> 4;
		int w = screen.getViewPort().getWidth() + 16 - 1 >> 4;
		int h = screen.getViewPort().getHeight() + 16 - 1 >> 4;
		screen.setOffset(xScroll, yScroll);
		for(int y = yo; y <= h + yo; y++) {
			for(int x = xo; x <= w + xo; x++) {
				if(x < 0 || y < 0 || x >= this.w || y >= this.h || this.fog.getFog(x, y)) continue;
				this.rowSprites.addAll(this.entitiesInTiles[x + y * this.w]);
			}
				
			if(this.rowSprites.size() > 0) {
				sortAndRender(screen, this.rowSprites);
			}
				
			this.rowSprites.clear();
		}
		
		screen.setOffset(0, 0);	
	}
	
	public void renderFog(Screen screen, int xScroll, int yScroll) {
		int xo = xScroll >> 4;
		int yo = yScroll >> 4;
		int w = screen.getViewPort().getWidth() + 16 - 1 >> 4;
		int h = screen.getViewPort().getHeight() + 16 - 1 >> 4;
		screen.setOffset(xScroll, yScroll);
		for(int y = yo; y <= h + yo; y++) {
			for(int x = xo; x <= w + xo; x++) {
				this.fog.render(screen, x - 1, y - 1);
				this.fog.render(screen, x, y);
			}
		}
		
		screen.setOffset(0, 0);
	}
	
	private void sortAndRender(Screen screen, List<Entity> list) {
		Collections.sort(list, this.spriteSorter);
		for(Entity e : list) {
			e.render(screen);
		}
	}
	
	public List<Entity> getEntities(int x0, int y0, int x1, int y1, ETeam team) {
		ArrayList<Entity> result = new ArrayList<Entity>();
		int xt0 = (x0 >> 4) - 1;
		int yt0 = (y0 >> 4) - 1;
		int xt1 = (x1 >> 4) + 1;
		int yt1 = (y1 >> 4) + 1;
		for(int y = yt0; y <= yt1; y++) {
			for(int x = xt0; x <= xt1; x++) {
				if(x < 0 || y < 0 || x >= this.w || y >= this.h) continue;
				List<Entity> entities = this.entitiesInTiles[x + y * this.w];
				for(Entity e : entities) {
					boolean isTeammate = team == null || team == e.getTeam();
					if(!isTeammate || !e.intersects(x0, y0, x1, y1)) continue;
					result.add(e);
				}
			}			
		}
		
		return result;
	}
	
	public void insertEntity(int x, int y, Entity e) {
		if(x < 0 || y < 0 || x >= this.w || y >= this.h) return;
		this.entitiesInTiles[x + y * this.w].add(e);
	}
	
	public void removeEntity(int x, int y, Entity e) {
		if(x < 0 || y < 0 || x >= this.w || y >= this.h) return;
		this.entitiesInTiles[x + y * this.w].remove(e);
	}
	
	public Tile getTile(int x, int y) {
		if(x < 0 || y < 0 || x >= this.w || y >= this.h) return Tile.rock;
		return Tile.tiles[this.tiles[x + y * this.w]];
	}
	
	public void setTile(int x, int y, Tile tile, int data) {
		if(x < 0 || y < 0 || x >= this.w || y >= this.h) return;
		this.tiles[x + y * this.w] = tile.getID();
	}
	
	public void trySpawn() {
		for(int i = 0; i < 15; i++) {
			Mob mob = new SlimeSpawner();
			if(mob.findStartPos(this)) add(mob);
			
			mob = new Mushroom();
			if(mob.findStartPos(this)) add(mob);
		
			mob = new Bird();
			if(mob.findStartPos(this)) add(mob);
		}
		
		for(int i = 0; i < 30; i++) {
			Mob mob = new Tree();
			if(mob.findStartPos(this)) add(mob);
		}
	}

	public List<Entity> getEntities() { return this.entities; }
	public void setEntities(List<Entity> entities) { this.entities = entities; }
	public int getWidth() { return this.w; }
	public void setWidth(int w) { this.w = w; }
	public int getHeight() { return this.h; }
	public void setHeight(int h) { this.h = h; }
	public Player getPlayer() { return this.player; }
	public int getMonsterDensity() { return this.monsterDensity; }
	public void setMonsterDensity(int monsterDensity) { this.monsterDensity = monsterDensity; }
	public DialogueManager getDialogueManager() { return this.dialogueManager; }
	
}