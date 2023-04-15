package game.item.equipment;

import java.util.HashMap;
import java.util.Map;

import game.CharacterStats;
import game.gfx.helper.PaletteHelper;

public class Equipment {
	public static final Map<String, Equipment> equipable = new HashMap<String, Equipment>();
	
	public static final Equipment basicBow = new Equipment("Bow", 0, 12, PaletteHelper.getColor(-1, 100, 220, 555), new CharacterStats(0, 0, 8, 0, 0, 20), EquipmentType.WEAPON);
	public static final Equipment powerBow = new Equipment("P.Bow", 0, 12, PaletteHelper.getColor(-1, 100, 500, 555), new CharacterStats(0, 0, 12, 0, 0, 15), EquipmentType.WEAPON);
	public static final Equipment rareBow = new Equipment("R.Bow", 0, 12, PaletteHelper.getColor(-1, 100, 0, 555), new CharacterStats(0, 0, 15, 0, 0, 10), EquipmentType.WEAPON);
	public static final Equipment ultraBow = new Equipment("U.Bow", 0, 12, PaletteHelper.getColor(-1, 100, 541, 555), new CharacterStats(0, 0, 20, 0, 0, 5), EquipmentType.WEAPON);
	
	public static final Equipment basicArmor = new Equipment("Armor", 1, 12, PaletteHelper.getColor(-1, 100, 220, 555), new CharacterStats(0, 5, 0, 0, 0), EquipmentType.ARMOR);
	public static final Equipment strongerBasicArmor = new Equipment("B.Armor", 1, 12, PaletteHelper.getColor(-1, 100, 220, 555), new CharacterStats(1, 10, 0, 0, 0), EquipmentType.ARMOR);
	public static final Equipment strongArmor = new Equipment("S.Armor", 1, 12, PaletteHelper.getColor(-1, 100, 500, 555), new CharacterStats(2, 15, 0, 0, 0), EquipmentType.ARMOR);
	public static final Equipment strongerArmor = new Equipment("Str.Armor", 1, 12, PaletteHelper.getColor(-1, 100, 0, 555), new CharacterStats(3, 20, 0, 0, 0), EquipmentType.ARMOR);
	public static final Equipment powerArmor = new Equipment("P.Armor", 1, 12, PaletteHelper.getColor(-1, 100, 220, 555), new CharacterStats(4, 25, 0, 0, 0), EquipmentType.ARMOR);
	public static final Equipment rareArmor = new Equipment("R.Armor", 1, 12, PaletteHelper.getColor(-1, 100, 0, 555), new CharacterStats(5, 50, 0, 5, 0), EquipmentType.ARMOR);
	public static final Equipment ultraArmor = new Equipment("U.Armor", 1, 12, PaletteHelper.getColor(-1, 100, 541, 555), new CharacterStats(10, 100, 0, 10, 0), EquipmentType.ARMOR);
	
	public static final Equipment basicShoes = new Equipment("Shoes", 2, 12, PaletteHelper.getColor(-1, 100, 220, 555), new CharacterStats(0, 0, 0, 0, 0, 1), EquipmentType.SHOES);
	public static final Equipment powerShoes = new Equipment("P.Shoes", 2, 12, PaletteHelper.getColor(-1, 100, 500, 555), new CharacterStats(0, 0, 0, 0, 0, 2), EquipmentType.SHOES);
	public static final Equipment rareShoes = new Equipment("R.Shoes", 2, 12, PaletteHelper.getColor(-1, 100, 0, 555), new CharacterStats(0, 0, 0, 0, 0, 5), EquipmentType.SHOES);
	public static final Equipment ultraShoes = new Equipment("U.Shoes", 2, 12, PaletteHelper.getColor(-1, 100, 541, 555), new CharacterStats(0, 0, 0, 0, 0, 20), EquipmentType.SHOES);
	
	private final EquipmentType type;
	private final CharacterStats bonusStats;
	private final String name;
	private final int xSprite;
	private final int ySprite;
	private final int col;
	
	public Equipment(String name, int xSprite, int ySprite, int col, CharacterStats bonusStats, EquipmentType type) {
		this.bonusStats = bonusStats;
		this.col = col;
		this.type = type;
		this.name = name;
		this.xSprite = xSprite;
		this.ySprite = ySprite;
		equipable.put(name.toLowerCase(), this);
	}
	
	public static Equipment getEquipmentByName(String name) { return equipable.get(name); }
	public CharacterStats getBonusStats() { return this.bonusStats; }
	public int getColor() { return this.col; }
	public String getName() { return this.name; }
	public EquipmentType getEquipmentType() { return this.type; }
	public int getXSprite() { return this.xSprite; }
	public int getYSprite() { return this.ySprite; }
	
}