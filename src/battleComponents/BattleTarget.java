package battleComponents;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import battleGUI.BattleModel;

public abstract class BattleTarget implements Serializable {
	private static final long serialVersionUID = 3061074709444620740L;

	private transient BattleModel battleModel;
	
	// Must provide status and element resistances here
	private StatPackage stats;
	private final int[] statusResist, elementResist;
	private final int[] currentStatus; // Represented as the time remaining for each status
	protected ArrayList<CommandFormat> spells;
	
	private String name, scanDescription;
	
	private boolean active, scanned;
	private int currHP, currMP;
	
	private int damageTaken;
	private DmgType damageTakenType;
	
	/**
	 * Reference to the most recent targets (can use to, e.g., track and repeatedly attack a target)
	 */
	private BattleTarget[] currTargets;
	
	public BattleTarget(StatPackage stats) {
		this.setStats(stats);
		statusResist = new int[Status.values().length];
		elementResist = new int[Element.values().length];
		currentStatus = new int[Status.values().length - 1];
		spells = new ArrayList<CommandFormat>();
		
		currHP = stats.getMaxHP();
		currMP = stats.getMaxMP();
		active = true;
		
		battleModel = createBattleModel();
		
		for (int i = 0; i < elementResist.length; i++)
			elementResist[i] = 100;
		
		name = setName();
	}
	
	/**
	 * Must be overridden by subclasses to provide a valid
	 * BattleModel object.
	 */
	protected abstract BattleModel createBattleModel();

	public StatPackage getStats() {
		return stats;
	}

	public void setStats(StatPackage stats) {
		this.stats = stats;
	}

	public int getStatusResist(Status status) {
		return statusResist[status.getIndex()];
	}

	public void setStatusResist(Status status, int value) {
		statusResist[status.getIndex()] = value;
	}

	public int[] getElementResist() {
		return elementResist;
	}

	/**
	 * Sets the specified elemental resistance to a value between -100 and 200, where 200 means
	 * double damage, 100 is regular damage, 0 is no damage, and -100 is absorbed health.
	 * @param e - the Element to modify
	 * @param value - an integer between -100 and 200
	 */
	public void setElementResist(Element e, int value) {
		if (value < -100)
			elementResist[e.getIndex()] = -100;
		else if (value > 200)
			elementResist[e.getIndex()] = 200;
		else
			elementResist[e.getIndex()] = value;
	}

	/**
	 * Obtains the remaining duration of the status effect.
	 * @param type - the Status to check
	 * @return The time remaining, in seconds, of the status effect. A
	 * value of 0 indicates an inactive status
	 */
	public int getCurrentStatus(Status type) {
		return currentStatus[type.getIndex()];
	}
	
	/**
	 * Sets the specified status duration.
	 * @param type - the Status to be set
	 * @param duration - the time, in seconds, that the status will last.
	 * If duration is set to 0, the status is not active.
	 */
	public void setCurrentStatus(Status type, int duration) {
		currentStatus[type.getIndex()] = duration;
	}

	private final static Random myRandom = new Random();
	
	/**
	 * For use with attacks and target choices.
	 * @return an integer from 0-99
	 */
	public int getRandom() {
		return myRandom.nextInt(100);
	}
	
	/**
	 * Returns a damage value based on a stat, a constant multiplier, and a random amount.
	 * @param stat - strength, magic, or whatever the damage may be calculated from.
	 * @param dmgConst - the power multiplier of the ability
	 * @return the 
	 */
	protected double getDamage(int stat, double dmgConst) {
		double damage;
		double dmgFactor = myRandom.nextDouble() * 21;
		
		damage = 6 * stat * Math.pow(1.017, stat);
		
		damage *= (dmgFactor / 100 + 1);
		damage /= 16;
		damage *= dmgConst;
		
		return damage;
	}
	
	/**
	 * The (invisible) cast command that handles all magic usage.
	 * 
	 * @param spell - the spell to be cast
	 * @param targets - the targets of the spell
	 */
	public void cast(Magic spell, BattleTarget[] targets) {
		double attDamage;
		int finalDamage;
		
		setCurrMP(getCurrMP() - spell.getMPCost());
		
		attDamage = getDamage(getStats().getMagic(), spell.getDmgConst()) * 16;
		finalDamage = (int) Math.round(attDamage);
		
		for (BattleTarget target : targets)
			target.takeDamage(finalDamage, spell.getDamageType(), spell.getElement(), 
					spell.getStatus(), spell.getAccuracy());
	}
	
	// Figure out how to deal with Reflect
	public void takeDamage(int damage, DmgType type, Element elem, Status[] status, int[] statusAcc) {
		// Verify elemental properties
		if (elem != null) {
			damage = (int) (damage * getElementResist()[elem.getIndex()] / 100.0);
		}
		
		// Verify status properties
		if (status != null) {
			for (int i = 0; i < status.length; i++) {
				if (status[i].getType() == StatusType.SCAN) {
					setScanned(true);
				} else {
					int chance = statusAcc[i];

					if (chance > 100)
						chance -= getStatusResist(status[i]);
					else
						chance = (int) ( chance / 100.0 * (100 - getStatusResist(status[i])) );

					if (getRandom() < chance)
						setCurrentStatus(status[i], 15);
				}
			}
		}
		
		// Deal the damage
		if (type == DmgType.HEAL || type == DmgType.REVIVE) {
			if (type == DmgType.REVIVE)
				this.setActive(true);
			
			if (this.isActive())
				setCurrHP(getCurrHP() + damage);
			else
				damage = 0;
			
			damage *= -1; // Will make damage taken negative
		} else if (type == DmgType.MP_HEAL) {
			if (this.isActive())
				setCurrMP(getCurrMP() + damage);
			
			damage *= -1; // Will make damage taken negative
		} else if (type == DmgType.PHYSICAL || type == DmgType.MAGICAL || type == DmgType.SPECIAL) {
			// Penetrates all defense
			if (type == DmgType.SPECIAL)
				setCurrHP(getCurrHP() - damage);
			else if (type == DmgType.PHYSICAL) {
				damage = (int) (damage / Math.pow( 1.008, getStats().getVitality() ));
				setCurrHP(getCurrHP() - damage);
			} else { // Magical damage
				damage = (int) (damage / Math.pow( 1.007, getStats().getSpirit() ));
				setCurrHP(getCurrHP() - damage);
			}
		} else if (type == DmgType.MP_PHYSICAL || type == DmgType.MP_MAGICAL || type == DmgType.MP_SPECIAL) {
			// Penetrates all defense
			if (type == DmgType.SPECIAL)
				setCurrMP(getCurrMP() - damage);
			else if (type == DmgType.PHYSICAL) {
				damage = (int) (damage / Math.pow( 1.008, getStats().getVitality() ));
				setCurrMP(getCurrMP() - damage);
			} else { // Magical damage
				damage = (int) (damage / Math.pow( 1.007, getStats().getSpirit() ));
				setCurrMP(getCurrMP() - damage);
			}
		} else if (type == DmgType.POISON) {
			setCurrHP(getCurrHP() - damage);
		}
		
		if (getCurrHP() == 0)
			setActive(false);
		
		setDamageTaken(damage);
		setDamageTakenType(type);
	}
	
	public int getDamageTaken() {
		return damageTaken;
	}

	public void setDamageTaken(int damageTaken) {
		this.damageTaken = damageTaken;
	}

	public DmgType getDamageTakenType() {
		return damageTakenType;
	}

	public void setDamageTakenType(DmgType damageTakenType) {
		this.damageTakenType = damageTakenType;
	}

	private void setActive(boolean b) {
		active = b;
	}

	/**
	 * If false, flags the BattleTarget as dead, but still on the field
	 * (i.e. non-ejected). The ATB meter will cease to flow, the
	 * BattleTarget's status will be reset, and it will become
	 * untargettable.
	 * 
	 * @return active - the current state of the BattleTarget
	 */
	public synchronized boolean isActive() {
		return active;
	}
	
	public synchronized boolean isScanned() {
		return scanned;
	}
	
	public void setScanned(boolean scanned) {
		this.scanned = scanned;
	}
	
	public String getScanDescription() {
		return scanDescription;
	}

	protected void setScanDescription(String scanDescription) {
		this.scanDescription = scanDescription;
	}

	public int getCurrHP() {
		return currHP;
	}

	public void setCurrHP(int currHP) {
		if (currHP >= 0) {
			this.currHP = currHP;
			if (this.currHP > this.stats.getMaxHP())
				this.currHP = this.stats.getMaxHP();
		}
		else {
			this.currHP = 0;
			setActive(false);
		}
	}
	
	public int getCurrMP() {
		return currMP;
	}

	public void setCurrMP(int currMP) {
		if (currMP >= 0)
			this.currMP = currMP;
		else
			this.currMP = 0;
	}

	public BattleTarget[] getCurrTargets() {
		return currTargets;
	}

	/**
	 * Sets the most recently attacked BattleTargets.
	 * @param currTargets - the most recently attacked BattleTargets.
	 */
	public void setCurrTargets(BattleTarget[] currTargets) {
		this.currTargets = currTargets;
	}

	public BattleModel getBattleModel() {
		if (battleModel == null) {
			battleModel = createBattleModel();
		}
		
		return battleModel;
	}

	public String getName() {
		return name;
	}

	public abstract String setName();

	@Override
	public String toString() {
		return getName();
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Battle housekeeping, for use by the EnemyTurn thread ONLY
	
	private boolean myTurn;
	
	public void startTurn() {
		myTurn = true;
	}
	
	public void endTurn() {
		myTurn = false;
		
		// Check for poison
		if (getCurrentStatus(Status.POISON) > 0) { // 5-7% max HP per turn
			double percent = Math.random() * 0.02 + 0.05;
			int poisonDamage = (int) (getStats().getMaxHP() * percent);
			
			takeDamage(poisonDamage, DmgType.POISON, null, null, null);
		}
	}
	
	public boolean getTurn() {
		return myTurn;
	}
}
