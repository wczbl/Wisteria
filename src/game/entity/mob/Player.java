package game.entity.mob;

import java.util.List;

import game.CharacterStats;
import game.Game;
import game.InputHandler;
import game.entity.ETeam;
import game.entity.Entity;
import game.entity.mob.builds.Mushroom;
import game.entity.particle.FlowTextIconParticle;
import game.entity.particle.FlowTextParticle;
import game.gfx.core.Font;
import game.gfx.core.Screen;
import game.gfx.gui.GuiInventory;
import game.gfx.gui.GuiManager;
import game.gfx.helper.PaletteHelper;
import game.item.Inventory;
import game.item.Item;
import game.item.ItemEntity;
import game.item.equipment.Equipment;
import game.item.equipment.EquipmentItem;
import game.item.equipment.EquipmentType;
import game.item.resource.Resource;
import game.item.resource.ResourceItem;
import game.level.Level;
import game.level.tile.Tile;
import game.weapon.Weapon;
import game.weapon.arrow.EArrowType;

public class Player extends Mob {
	
	private EquipmentItem weapon;
	private EquipmentItem armor;
	private EquipmentItem shoes;
	private Inventory inventory = new Inventory();
	private Inventory avgPickups = new Inventory();
	protected Game game;
	private int score = 1000;
	private int clearFogRadius = 4;
	private long collectTime;
	private Item activeItem;
	
	public Player(Game game) {
		this.game = game;
		this.team = ETeam.PLAYER;
		touchItem(new ItemEntity(new EquipmentItem(Equipment.basicBow), this.x, this.y));
		this.bloodCol = 0xCC00CC;
	}
	
	public void init(Level level) {
		super.init(level);
		this.health = this.maxHealth = this.currentStats.getEndurance();
	}
	
	public void tick() {
		int xa = 0;
		int ya = 0;
		
		InputHandler input = InputHandler.getInstance(null);
		
		if(!GuiManager.hasOpenedMenu) {
			if(input.up.down) ya--;
			if(input.down.down) ya++;
			if(input.left.down) xa--;
			if(input.right.down) xa++;
			
			if(input.action.down && this.level.getEntities(this.x - 8, this.y - 8, this.x + 8, this.y + 8, null).size() == 1 && this.score >= Mushroom.cost && this.level != null) {
				ResourceItem coin = this.inventory.findResource(Resource.coin);
				if(coin == null) return;
				ResourceItem bigCoin = this.inventory.findResource(Resource.bigCoin);
				if(bigCoin == null) return;
				
				if(coin.getCount() >= 10 && bigCoin.getCount() >= 3) {
					this.level.add(new Mushroom(this.x, this.y));
					this.inventory.removeResource(Resource.bigCoin, 3);
					this.inventory.removeResource(Resource.coin, 10);
				}
			}
			
			if(input.appleUse.clicked) {
				if(this.health < this.currentStats.getEndurance() && this.inventory.removeResource(Resource.apple, 1)) {
					this.health = Math.min(this.currentStats.getEndurance(), this.health + 1);
					this.level.add(new FlowTextParticle("+1", this.x, this.y, Font.GREEN_COLOR));
				}
			}
		
			if(input.berryUse.clicked) {
				if(this.health < this.currentStats.getEndurance() && this.inventory.removeResource(Resource.berry, 1)) {
					this.health = Math.min(this.currentStats.getEndurance(), this.health + 1);
					this.level.add(new FlowTextParticle("+1", this.x, this.y, Font.GREEN_COLOR));
				}
			}
			
			if(input.mouse.clicked) {
				take();
			} else if(input.mouse.down && this.activeItem == null && this.weapon != null) {
				int xd = input.getXMousePos() - this.x;
				int yd = input.getYMousePos() - this.y;
				double m = Math.sqrt(xd * xd + yd * yd);
				double vx = xd / m;
				double vy = yd / m;
				if(this.tickTime % this.currentStats.getAttackDelay() == 0) {
					boolean isCritical = this.random.nextInt(5) == 0;
					Weapon.fire(isCritical ? EArrowType.FIRE : EArrowType.SIMPLE, this.team, this.x, this.y, vx, vy, this.currentStats.getForce() + (isCritical ? this.currentStats.getForce() : 0), this.level);
				}
			}			
		}
		
		
		move(xa, ya);
		
		if(System.currentTimeMillis() - this.collectTime > 300) {
			updatePickupNotification();
			this.collectTime = System.currentTimeMillis();
		}
		
		super.tick();
	}
	
	public void render(Screen screen) {
		int xt = 0;
		int yt = 18;
		
		int flip1 = (this.walkDist >> 3) & 1;
		int flip2 = (this.walkDist >> 3) & 1;
		
		if(this.dir == 1) xt += 2;
		
		if(this.dir > 1) {
			flip1 = 0;
			flip2 = ((this.walkDist >> 4) & 1);
			if(this.dir == 2) flip1 = 1;
			xt += 4 + ((this.walkDist >> 3) & 1) * 2;
		}
		
		int xo = this.x - 8;
		int yo = this.y - 11;
		if(isSwimming()) {
			yo += 4;
			int waterCol = PaletteHelper.getColor(-1, -1, -1, 444);
			if(this.tickTime / 8 % 2 == 0) waterCol = PaletteHelper.getColor(-1, 444, -1, -1);
			
			screen.render(xo + 0, yo + 3, 40, 104, waterCol, 0);
			screen.render(xo + 8, yo + 3, 40, 104, waterCol, 1);
		}
		
		int col1 = PaletteHelper.getColor(-1, 100, 0, 542);
		int col2 = PaletteHelper.getColor(-1, 100, 0, 542);
		
		if(this.hurtTime > 0) {
			col1 = PaletteHelper.getColor(-1, 555, 555, 555);
			col2 = PaletteHelper.getColor(-1, 555, 555, 555);
		}
		
		screen.render(xo + 8 * flip1, yo + 0, xt * 8, yt * 8, col1, flip1);
		screen.render(xo + 8 - 8 * flip1, yo + 0, (xt + 1) * 8, yt * 8, col1, flip1);
		if (!isSwimming()) {
			screen.render(xo + 8 * flip2, yo + 8, xt * 8, (yt + 1) * 8, col2, flip2);
			screen.render(xo + 8 - 8 * flip2, yo + 8, (xt + 1) * 8, (yt + 1) * 8, col2, flip2);
		}
	}
	
	public void touchedBy(Entity e) {		
		if(e.getTeam() != this.team) e.touchedBy(this);
	}
	
	public void touchItem(ItemEntity e) {
		if(e.isRemoved()) return;
		
		e.take(this);
		this.inventory.add(e.getItem());
		this.avgPickups.add(e.getItem());
		
		if(e.getItem() instanceof ResourceItem) {
			ResourceItem item = (ResourceItem)e.getItem();
			if(item.getResource() == Resource.heart) {
				CharacterStats healthStat = new CharacterStats(0, item.getCount(), 0, 0, 0);
				this.defaultStats = this.defaultStats.mergeStats(healthStat);
				this.currentStats = this.currentStats.mergeStats(healthStat);
			}
			
			if(item.getResource() == Resource.coin) { this.score += this.random.nextInt(5) + 1; }	
			if(item.getResource() == Resource.coin) { this.score += this.random.nextInt(20) + 56; }
		}
		
		if(e.getItem() instanceof EquipmentItem) {
			updateEquipment();
		}
	}
	
	public boolean findStartPos(Level level) {
		while(true) {
			int x = this.random.nextInt(level.getWidth());
			int y = this.random.nextInt(level.getHeight());
			if(level.getTile(x, y) == Tile.grass) {
				this.x = x * 16 + 8;
				this.y = y * 16 + 8;
				return true;
			}
		}
	}
	
	private void updateEquipment() {
		this.currentStats = this.defaultStats.mergeStats(new CharacterStats());
		this.weapon = this.inventory.findEquipmentByType(EquipmentType.WEAPON);
		if(this.weapon != null) this.currentStats = this.currentStats.mergeStats(this.weapon.getBonusStats());
		this.armor = this.inventory.findEquipmentByType(EquipmentType.ARMOR);
		if(this.armor != null) this.currentStats = this.currentStats.mergeStats(this.armor.getBonusStats());
		this.shoes = this.inventory.findEquipmentByType(EquipmentType.SHOES);
		if(this.shoes != null) this.currentStats = this.currentStats.mergeStats(this.shoes.getBonusStats());	
	}
	
	public void updateStats() {
		GuiInventory inv = (GuiInventory)GuiManager.getInstance().get("inventory");
		if(inv != null) {
			int slowPeriod = this.currentStats.getSlowPeriod() + this.slowGroundPeriod;
			int speed = 0;
			if(slowPeriod < 3) speed = 30;
			else if(slowPeriod < 10) speed = 60;
			else if(slowPeriod < 40) speed = 90;
			else speed = 120;
			
			inv.setSpeed(speed);
			inv.setDef(this.currentStats.getDefense());
			inv.setSta(this.currentStats.getEndurance());
			inv.setStr(this.currentStats.getForce());
		}
		
	}
	
	private void updatePickupNotification() {
		if(this.avgPickups.getItems().size() == 0) return;
		Item item = this.avgPickups.getItems().get(0);
		if(item != null) {
			int count = item instanceof ResourceItem ? ((ResourceItem)item).getCount() : 1;
			this.level.add(new FlowTextIconParticle("+" + count, this.x - 8, this.y - 8, Font.YELLOW_COLOR, item.getXSprite(), item.getYSprite(), item.getColor()));
			this.avgPickups.getItems().remove(item);
		}
	}
	
	public boolean take() {
		boolean done = false;
		
		int yo = -2;
		int range = 12;
		
		if (this.dir == 0 && interact(this.x - 8, this.y + 4 + yo, this.x + 8, this.y + range + yo)) done = true;
		if (this.dir == 1 && interact(this.x - 8, this.y - range + yo, this.x + 8, this.y - 4 + yo)) done = true;
		if (this.dir == 3 && interact(this.x + 4, this.y - 8 + yo, this.x + range, this.y + 8 + yo)) done = true;
		if (this.dir == 2 && interact(this.x - range, this.y - 8 + yo, this.x - 4, this.y + 8 + yo)) done = true;
		
		if(this.activeItem != null) {
			int xt = this.x >> 4;
			int yt = (this.y + yo) >> 4;
			int r = 12;
			if(this.dir == 0) yt = (this.y + r + yo) >> 4;
			if(this.dir == 1) yt = (this.y - r + yo) >> 4;
			if(this.dir == 2) xt = (this.x - r) >> 4;
			if(this.dir == 3) xt = (this.x + r) >> 4;
			
			if(xt >= 0 && yt >= 0 && xt < this.level.getWidth() && yt < this.level.getHeight()) {
				this.level.getTile(xt, yt).interact(this.level, xt, yt, this, this.activeItem, this.dir);
				this.activeItem.interactOn(this.level.getTile(xt, yt), this.level, xt, yt, this, this.dir);
				
				if(this.activeItem.isDepleted()) this.activeItem = null;
			}
		}
		
		return done;
	}
	
	private boolean interact(int x0, int y0, int x1, int y1) {
		List<Entity> entities = this.level.getEntities(x0, y0, x1, y1, null);
		for(Entity e : entities) {
			e.interact(this.activeItem, this, dir);
		}
		
		return false;
	}
	
	public Item getActiveItem() { return this.activeItem; }
	public void setActiveItem(Item activeItem) { this.activeItem = activeItem; }
	public boolean ignoreBlocks() { return this.shoes != null && this.shoes.getEquipment() == Equipment.ultraShoes; }
	public boolean canSwim() { return true; }
	public int getClearFogRadius() { return this.clearFogRadius + this.score / 1000; }
	public int getScore() { return this.score; }
	public boolean canRegenerate() { return true; }
	public Inventory getInventory() { return this.inventory; }
}