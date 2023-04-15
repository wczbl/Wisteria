package game.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import game.Game;
import game.dialogue.DialogueManager;
import game.entity.ETeam;
import game.entity.Entity;
import game.entity.mob.Bird;
import game.entity.mob.Mob;
import game.entity.mob.Player;
import game.entity.mob.SlimeSpawner;
import game.entity.mob.boss.AeroMage;
import game.entity.mob.boss.Boss;
import game.entity.mob.boss.snake.Snake;
import game.entity.mob.boss.snake.SnakeNeck;
import game.entity.mob.boss.snake.SnakePart;
import game.entity.mob.builds.tree.AppleTree;
import game.entity.mob.builds.tree.FirTree;
import game.entity.mob.builds.tree.PineTree;
import game.entity.mob.builds.tree.Shrubbery;
import game.entity.mob.builds.tree.TreeStump;
import game.entity.mob.npc.Guardian;
import game.entity.mob.npc.Warper;
import game.entity.particle.ParticleSystem;
import game.gfx.CreditsScreen;
import game.gfx.core.Bitmap;
import game.gfx.core.Screen;
import game.gfx.helper.BitmapHelper;
import game.gfx.sprite.SpriteCollector;
import game.item.ItemEntity;
import game.item.equipment.EquipmentItem;
import game.level.tile.Tile;

public class Level {

	// TILE
	private static final int GRASS_TILE = 0xFFFFFFFF;
	private static final int WATER_TILE = 0xFF0000FF;
	private static final int DEEP_WATER_TILE = 0xFF0000CC;
	private static final int SAND_TILE = 0xFFFFFF00;
	private static final int LAVA_TILE = 0xFFFF7F00;
	private static final int ROAD_TILE = 0xFF6B6B6B;
	private static final int ROCK_TILE = 0xFFF79090;
	
	// ENTITY
	private static final int APPLE_TREE = 0xFF00FF00;
	private static final int FIR_TREE = 0xFF00CC00;
	private static final int TREE_STUMP = 0xFF008800;
	private static final int BLUEBERRY = 0xFF005500;
	
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
	private int levelNum;
	private int mobCount;
	private int bossCount;
	private int respX;
	private int respY;
	private Game game;
	private Comparator<Entity> spriteSorter = new Comparator<Entity>() {

		public int compare(Entity e0, Entity e1) {
			if(e1.getY() < e0.getY()) return 1;
			if(e1.getY() > e0.getY()) return -1;
			return 0;
		}
		
	};
	
	public Level(int levelNum, Game game) {
		this.levelNum = levelNum;
		this.game = game;
		loadTiles(levelNum); 
		loadObjects(levelNum);
		trySpawn();
	}
	
	@SuppressWarnings("unchecked")
	private void loadTiles(int levelNum) {
		Bitmap map = BitmapHelper.loadBitmapFromResources("/maps/" + levelNum + ".png");
		this.w = map.getWidth();
		this.h = map.getHeight();
		this.fog = new Fog(this.w, this.h, true);
		this.tiles = new byte[this.w * this.h];
		
		this.entitiesInTiles = new ArrayList[w * h];
		for(int i = 0; i < w * h; i++) {
			this.entitiesInTiles[i] = new ArrayList<Entity>();
		}
		
		for(int j = 0; j < this.h; j++) {
			for(int i = 0; i < this.w; i++) {
				int val = map.getPixels()[i + j * this.w];
				int xx = (i << 4) + 8;
				int yy = (j << 4) + 8;
				switch(val) {
					case GRASS_TILE: {
						setTile(i, j, Tile.grass, 0);
						break;
					}
					
					case WATER_TILE: {
						setTile(i, j, Tile.water, 0);
						break;
					}
					
					case DEEP_WATER_TILE: {
						setTile(i, j, Tile.deepWater, 0);
						break;
					}
					
					case SAND_TILE: {
						setTile(i, j, Tile.sand, 0);
						break;
					}
					
					case ROAD_TILE: {
						setTile(i, j, Tile.road, 0);
						break;
					}
					
					case ROCK_TILE: {
						setTile(i, j, Tile.rock, 0);
						break;
					}
					
					case LAVA_TILE: {
						setTile(i, j, Tile.lava, 0);
						break;
					}
					
					case APPLE_TREE: {
						add(new AppleTree(xx, yy));
						break;
					}
					
					case FIR_TREE: {
						int r = this.random.nextInt(100);
						if(r < 72) break;
						
						if(r < 75) {
							add(new TreeStump(xx, yy, this.game.getSpriteCollector()));
							break;
						}
						
						if(this.random.nextBoolean()) add(new FirTree(xx, yy, this.game.getSpriteCollector()));
						else add(new PineTree(xx, yy, this.game.getSpriteCollector()));
						
						break;
					}
					
					case TREE_STUMP: {
						add(new TreeStump(xx, yy, this.game.getSpriteCollector()));
						break;
					}
					
					case BLUEBERRY: {
						add(new Shrubbery(xx, yy, this.game.getSpriteCollector()));
						break;
					}
					
				}
			}
		}
	}
	
	private void loadObjects(int levelNum) {
		Bitmap map = BitmapHelper.loadBitmapFromResources("/maps/" + levelNum + "O.png");
		for(int j = 0; j < map.getHeight(); j++) {
			for(int i = 0; i < map.getWidth(); i++) {
				int val = map.getPixels()[i + j * map.getWidth()];
				int xx = (i << 4) + 8;
				int yy = (j << 4) + 8;
				
				if(val == 0xFFFFFFFF) continue;
				
				if(val == 0xFF20FFFF) {
					SnakePart prev = new Snake(xx, yy);
					add(prev);
										
					for(int body = 0; body < 16; body++) {
						prev = new SnakeNeck(xx, yy, prev);
						add(prev);
					}
					
					continue;
				}
				
				if(val == 0xFF21FFFF) {
					add(new AeroMage(xx, yy));
					continue;
				}
				
				switch(((val >> 16) & 0xFF)) {
					case 0xFF: {
						this.respX = xx;
						this.respY = yy;
						
						if(levelNum != 0) add(new Warper(this.respX + 30, this.respY - 30, false));
						
						if(levelNum > 3) {
							for(int aa = 0; aa < levelNum - 3; aa++) {
								add(new Guardian(this.respX + this.random.nextInt(61) - 30, this.respY + this.random.nextInt(61) - 30));
							}
						}
						
						break;
					}					
				}
			}
		}
	}
	
	public void add(Entity e) {
		if(e instanceof Player) {
			this.player = (Player)e;
			this.player.setX(this.respX);
			this.player.setY(this.respY);
			this.fog.clearFog2(this.player.getX() >> 4, this.player.getY() >> 4, this.player.getClearFogRadius());
		}
		
		e.setRemoved(false);
		this.entities.add(e);
		e.init(this);
	
		if(e instanceof Mob) this.mobCount++;
		if(e instanceof Boss) this.bossCount++;
		
		insertEntity(e.getX() >> 4, e.getY() >> 4, e);
	}
	
	public void remove(Entity e) {
		this.entities.remove(e);
		int xt = e.getX() >> 4;
		int yt = e.getY() >> 4;
		removeEntity(xt, yt, e);
	}
	
	public void tick() {
		for(int i = 0; i < this.w * this.h / 50; i++) {
			int xt = this.random.nextInt(this.w);
			int yt = this.random.nextInt(this.h);
			getTile(xt, yt).tick(this, xt, yt);
		}
				
		for(int i = 0; i < this.entities.size(); i++) {
			Entity entity = this.entities.get(i);
            int xto = entity.getX() >> 4;
            int yto = entity.getY() >> 4;

            entity.tick();

            if (entity.isRemoved()) {
                if (entity instanceof Mob) this.mobCount--;
                if (entity instanceof Boss) this.bossCount--;

                if (this.bossCount == 0) {

                    for (int aa = 0; aa < Boss.drops[this.levelNum].length; aa++) {
                        this.add(new ItemEntity(new EquipmentItem(Boss.drops[this.levelNum][aa]), entity.getX() + this.random.nextInt(31) - 15, entity.getY() + this.random.nextInt(31) - 15));
                    }

                    int offsetX = 0;
                    int offsetY = 0;
                    int xx;
                    int yy;
                    while (true) {

                        xx = (this.player.getX() >> 4) + offsetX;
                        yy = (this.player.getY() >> 4) + offsetY;

                        offsetX += this.random.nextInt(2) * 2 - 1;
                        offsetY += this.random.nextInt(2) * 2 - 1;

                        if (xx == player.getX() >> 4 && yy == player.getY() >> 4) continue;
                        if (this.getTile(xx, yy) == Tile.lava) continue;
                        break;
                    }

                    this.add(new Warper(xx << 4, yy << 4, true));
                    
                    if(this.levelNum == 5) CreditsScreen.getInstance().show();
                    
                    this.bossCount = 100;

                }

                this.entities.remove(i--);
                removeEntity(xto, yto, entity);
            } else {
                int xt = entity.getX() >> 4;
                int yt = entity.getY() >> 4;

                if (xto != xt || yto != yt) {
                    removeEntity(xto, yto, entity);
                    insertEntity(xt, yt, entity);
                    if (entity instanceof Player) {
                    	this.fog.clearFog2(xt, yt, ((Player) entity).getClearFogRadius());
                    }
                }
            }
			
		}
		
		this.game.getFireParticles().tick();
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

	private List<Entity> rowSprites = new ArrayList<Entity>();

	public void renderSprites(Screen screen, int xScroll, int yScroll) {
		int xo = Math.max((xScroll >> 4), 0);
		int yo = Math.max((yScroll >> 4), 0);
		int w = (screen.getViewPort().getWidth() + 16 - 1 >> 4) + 4;
		int h = (screen.getViewPort().getHeight() + 16 - 1 >> 4) + 4;
		screen.setOffset(xScroll, yScroll);
		for(int y = yo; y <= h + yo; y++) {
			for(int x = xo; x <= w + xo; x++) {
				if(x < 0 || y < 0 || x >= this.w || y >= this.h) continue;
				this.rowSprites.addAll(this.entitiesInTiles[x + y * this.w]);
			}
				
			if(this.rowSprites.size() > 0) {
				sortAndRender(screen, this.rowSprites);
			}
				
			this.rowSprites.clear();
		}
		
		this.game.getFireParticles().render(screen);
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
		for(int i = 0; i < 50; i++) {
			Mob mob = new SlimeSpawner();
			if(mob.findStartPos(this)) add(mob);
		}
	
		for(int i = 0; i < 10; i++) {
			Mob mob = new Bird();
			if(mob.findStartPos(this)) add(mob);
		}
	}
	
	public Fog getFog() { return this.fog; }
	public int getLevelNum() { return this.levelNum; }
	public int getMobCount() { return this.mobCount; }
	public int getBossCount() { return this.bossCount; }
	public List<Entity> getEntities() { return this.entities; }
	public void setEntities(List<Entity> entities) { this.entities = entities; }
	public int getWidth() { return this.w; }
	public void setWidth(int w) { this.w = w; }
	public int getHeight() { return this.h; }
	public void setHeight(int h) { this.h = h; }
	public Player getPlayer() { return this.player; }
	public int getMonsterDensity() { return Math.max(0, this.monsterDensity); }
	public void setMonsterDensity(int monsterDensity) { this.monsterDensity = monsterDensity; }
	public DialogueManager getDialogueManager() { return this.dialogueManager; }
	public Game getGame() { return this.game; }
	public ParticleSystem getFireParticles() { return this.game.getFireParticles(); }
	public SpriteCollector getSpriteCollector() { return this.game.getSpriteCollector(); }
}