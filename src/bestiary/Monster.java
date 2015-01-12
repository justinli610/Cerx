package bestiary;

import battleComponents.*;
import battleComponents.Character;
import items.*;

public abstract class Monster extends BattleTarget {
	private Item commonDrop, rareDrop;
	private int cDropRate, rDropRate;
	private String displayName;
	
	/**
	 * A list of BattleTargets selected after {@link #futureAction}.
	 */
	protected BattleTarget[] futureTargets;
	protected int futureAction;
	
	public Monster(int lvl, int mHP, int mMP, int str, int mag, int vit, int spr, int agt) {
		this(new StatPackage(lvl, mHP, mMP, str, mag, vit, spr, agt));
	}
	
	public Monster(StatPackage stats) {
		super(stats);
		addInstanceCount();
		
		setDisplayName();
	}

	/**
	 * A default implementation that randomly chooses a single active Character target
	 * 
	 * @param allTargets - the current participants in battle
	 * @return allTargets[i] - the randomly chosen target
	 */
	public BattleTarget[] chooseTargets(BattleTarget[] allTargets) {
		int i;
		
		// Pick a target at random. 
		// If that target is not a Character or is not active, pick again
		do {
			i = (int) ( getRandom() / 100.0 * allTargets.length);
		} while (!(allTargets[i] instanceof Character) || !allTargets[i].isActive());
		
		return new BattleTarget[] {allTargets[i]};
	}
	
	/**
	 * Convenience method to select all party members as targets.
	 * 
	 * @param targets - the list of battle participants
	 * @return - an array of party members
	 */
	public BattleTarget[] chooseParty(BattleTarget[] targets) {
		BattleTarget[] allTargets;
		int length = 0;
		
		// Find the length of the array
		for (int i = 0; i < targets.length; i++)
			if (targets[i] instanceof Character)
				length++;
		
		// Initialise the array
		allTargets = new BattleTarget[length];
		
		// Fill the array
		length = 0; // Becomes a couner
		for (int i = 0; i < allTargets.length; i++)
			if (targets[i] instanceof Character)
				allTargets[length++] = targets[i];
		
		
		return allTargets;
	}
	
	/**
	 * Convenience method to select all enemies as targets.
	 * 
	 * @param targets - the list of battle participants
	 * @return an array of party members
	 */
	public BattleTarget[] chooseEnemies(BattleTarget[] targets) {
		BattleTarget[] allTargets;
		int length = 0;
		
		// Find the length of the array
		for (int i = 0; i < targets.length; i++)
			if (targets[i] instanceof Monster)
				length++;
		
		// Initialise the array
		allTargets = new BattleTarget[length];
		
		// Fill the array
		length = 0; // Becomes a counter
		for (int i = 0; i < targets.length; i++)
			if (targets[i] instanceof Monster)
				allTargets[length++] = targets[i];
		
		
		return allTargets;
	}
	
	/**
	 * Convenience method to select self as target.
	 * 
	 * @return an array with just this Monster
	 */
	public BattleTarget[] chooseSelf() {
		BattleTarget[] allTargets = new BattleTarget[1];

		allTargets[0] = this;
		
		return allTargets;
	}
	
	/**
	 * Allows the Monster to choose its next move. The targets MUST be chosen in this method
	 * as {@link #chooseTargets(BattleTarget[] allTargets) chooseTargets()} will 
	 * not be called separately.
	 * 
	 * @param allTargets - an array of all currently participating BattleTargets
	 * @return - the name of the action, or null if nothing should be displayed
	 */
	public abstract String chooseAction(BattleTarget[] allTargets);

	/**
	 * Carries out the chosen actions.
	 */
	public abstract void autobattle();

	/**
	 * The instance count starts at 0 and increases by 1 for every Monster of the subclass
	 * type created.
	 * @return - the current number of active Monster subtypes.
	 */
	public abstract int getInstanceCount();

	/**
	 * Used for lettering of identical monsters (e.g. "Strident A").
	 */
	public abstract void addInstanceCount();
	
	public abstract void resetInstanceCount();

	public Item getCommonDrop() {
		return commonDrop;
	}
	
	public void setCommonDrop(Item item) {
		commonDrop = item;
	}
	
	public Item getRareDrop() {
		return rareDrop;
	}

	public void setRareDrop(Item item) {
		rareDrop = item;
	}

	public int getcDropRate() {
		return cDropRate;
	}

	public void setcDropRate(int cDropRate) {
		this.cDropRate = cDropRate;
	}

	public int getrDropRate() {
		return rDropRate;
	}

	public void setrDropRate(int rDropRate) {
		this.rDropRate = rDropRate;
	}

	public BattleTarget[] getFutureTargets() {
		return futureTargets;
	}
	
	private void setDisplayName() {
		displayName = getName() + " " + (char) (getInstanceCount() + 64);
	}

	@Override
	public String toString() {
		return displayName;
	}
}
