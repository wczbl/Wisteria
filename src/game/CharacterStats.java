package game;

public class CharacterStats {

	private int force;
	private int endurance;
	private int slowPeriod;
	private int defense;
	private int regeneration;
	private int attackDelay;

	public CharacterStats() {}
	
	public CharacterStats(int defense, int endurance, int force, int regeneration, int slowPeriod) {
		this.defense = defense;
		this.endurance = endurance;
		this.force = force;
		this.regeneration = regeneration;
		this.slowPeriod = slowPeriod;
	}

	public CharacterStats(int defense, int endurance, int force, int regeneration, int slowPeriod, int attackDelay) {
		this.defense = defense;
		this.endurance = endurance;
		this.force = force;
		this.regeneration = regeneration;
		this.slowPeriod = slowPeriod;
		this.attackDelay = attackDelay;
	}
	
	public CharacterStats mergeStats(CharacterStats stats) {
		CharacterStats result = new CharacterStats();
		result.setDefense(stats.getDefense() + this.defense);
		result.setEndurance(stats.getEndurance() + this.endurance);
		result.setForce(stats.getForce() + this.force);
		result.setRegeneration(stats.getRegeneration() + this.regeneration);
		result.setSlowPeriod(stats.getSlowPeriod() + this.slowPeriod);
		result.setAttackDelay(stats.getAttackDelay() + this.attackDelay);
		return result;
	}
	
	public boolean match(CharacterStats stats) {
		return stats.getAttackDelay() == this.attackDelay && stats.getDefense() == this.defense && stats.getEndurance() == this.endurance && stats.getForce() == this.force && stats.getRegeneration() == this.regeneration && stats.getSlowPeriod() == this.slowPeriod;
	}

	public int getForce() { return this.force; }
	public void setForce(int force) { this.force = force; }
	public int getEndurance() { return this.endurance; }
	public void setEndurance(int endurance) { this.endurance = endurance; }
	public int getSlowPeriod() { return this.slowPeriod; }
	public void setSlowPeriod(int slowPeriod) { this.slowPeriod = slowPeriod; }
	public int getDefense() { return this.defense; }
	public void setDefense(int defense) { this.defense = defense; }
	public int getRegeneration() { return this.regeneration; }
	public void setRegeneration(int regeneration) { this.regeneration = regeneration; }
	public int getAttackDelay() { return this.attackDelay; }
	public void setAttackDelay(int attackDelay) { this.attackDelay = attackDelay; }

}