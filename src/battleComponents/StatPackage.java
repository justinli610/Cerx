package battleComponents;

import java.io.Serializable;

/**
 * This class establishes a convenient representation of BattleTarget statistics
 * using an object to represent each stat
 */
public class StatPackage implements Serializable {
	private static final long serialVersionUID = 7366907696221862124L;
	
	private int level, maxHP, maxMP, strength, magic, vitality, spirit, agility;
	
	public StatPackage(int lvl, int mHP, int mMP, int str, int mag, int vit, int spr, int agt) {
		setLevel(lvl);
		setMaxHP(mHP);
		setMaxMP(mMP);
		setStrength(str);
		setMagic(mag);
		setVitality(vit);
		setSpirit(spr);
		setAgility(agt);
	}
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		if (level <= 100)
			this.level = level;
		else
			System.err.print("Invalid level assignment!");
	}

	public int getMaxHP() {
		return maxHP;
	}

	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}

	public int getMaxMP() {
		return maxMP;
	}

	public void setMaxMP(int maxMP) {
		this.maxMP = maxMP;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getMagic() {
		return magic;
	}

	public void setMagic(int magic) {
		this.magic = magic;
	}

	public int getVitality() {
		return vitality;
	}

	public void setVitality(int vitality) {
		this.vitality = vitality;
	}

	public int getSpirit() {
		return spirit;
	}

	public void setSpirit(int spirit) {
		this.spirit = spirit;
	}

	public int getAgility() {
		return agility;
	}

	public void setAgility(int agility) {
		this.agility = agility;
	}

}
