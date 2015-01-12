package bestiary;

import battleComponents.BattleTarget;
import battleComponents.Character;
import battleComponents.DmgType;
import battleComponents.Element;
import battleComponents.StatPackage;
import battleComponents.Status;
import battleGUI.BattleModel;

public class BabyBehemoth extends Monster {
	private static final int ATTACK = 0, HEAVE = 1, MIGHTY_GUARD = 2, BELLOW = 3;
	private int mightyGuardCooldown = 0; // Can be negative, which increases the chances of mightyGuard happening
	private static int counter = 0;
	
	public BabyBehemoth(StatPackage stats) {
		super(stats);
	}
	
	public BabyBehemoth() {
		super(new StatPackage(8, 593, 120, 11, 25, 60, 58, -55));
		this.setElementResist(Element.LIGHTNING, 200);
	}
	
	@Override
	public String chooseAction(BattleTarget[] allTargets) {
		String actionName = null;
		int random = getRandom();
		
		// HP < half, cooldown <= 0, random < 50 + turns since cooldown reached 0
		if (getCurrHP()*1.0 / getStats().getMaxHP() < 0.5 && 
				mightyGuardCooldown <= 0 &&
				random < (50 - mightyGuardCooldown)) {
			mightyGuardCooldown = 8;
			
			futureAction = MIGHTY_GUARD;
			actionName = "Mighty Guard";
		} else if (random > 40) {
			futureAction = ATTACK;
		} else if (random > 10) {
			futureAction = HEAVE;
			actionName = "Heave";
		} else {
			futureAction = BELLOW;
			actionName = "Bellow";
		}
		
		futureTargets = chooseTargets(allTargets);
		
		return actionName;
	}
	
	@Override
	public BattleTarget[] chooseTargets(BattleTarget[] allTargets) {
		BattleTarget[] targets = null;
		
		if (futureAction == MIGHTY_GUARD)
			targets = chooseEnemies(allTargets);
		else if (futureAction == BELLOW)
			targets = chooseParty(allTargets);
		else // Attack
			targets = super.chooseTargets(allTargets);
		
		return targets;
	}

	@Override
	public void autobattle() {
		mightyGuardCooldown--;
		
		if (futureAction == ATTACK)
			attack(futureTargets);
		else if (futureAction == HEAVE)
			heave(futureTargets);
		else if (futureAction == MIGHTY_GUARD)
			mightyGuard(futureTargets);
		else if (futureAction == BELLOW)
			bellow(futureTargets);
		
		setCurrTargets(futureTargets);
	}

	/**
	 * Moderate MP damage to all targets.
	 * @param futureTargets - the targets that have been selected
	 */
	private void bellow(BattleTarget[] futureTargets) {
		double attDamage;
		int finalDamage;
		
		for (BattleTarget t : futureTargets) {
			attDamage = getDamage(getStats().getMagic(), 0.1);
			finalDamage = (int) attDamage;
			t.takeDamage(finalDamage, DmgType.MP_MAGICAL, null, null, null);
		}
	}

	/**
	 * Recovers moderate HP and MP.
	 * @param futureTargets
	 */
	private void mightyGuard(BattleTarget[] futureTargets) {
		double attDamage;
		int finalDamage;
		
		for (BattleTarget t : futureTargets) {
			attDamage = getDamage(getStats().getMagic(), 0.3);
			finalDamage = (int) attDamage;
			t.takeDamage(finalDamage, DmgType.MP_HEAL, null, null, null);
			
			attDamage = getDamage(getStats().getMagic(), 2);
			finalDamage = (int) attDamage;
			t.takeDamage(finalDamage, DmgType.HEAL, null, null, null);
		}
	}

	/**
	 * Major physical damage to a single target.
	 * @param futureTargets
	 */
	private void heave(BattleTarget[] futureTargets) {
		double attDamage;
		int finalDamage;

		attDamage = getDamage(getStats().getStrength(), 1) * 30;
		finalDamage = (int) Math.round(attDamage);

		futureTargets[0].takeDamage(finalDamage, DmgType.PHYSICAL, null, null, null);
	}

	/**
	 * Moderate physical damage to a single target.
	 * @param futureTargets
	 */
	private void attack(BattleTarget[] futureTargets) {
		double attDamage;
		int finalDamage;

		attDamage = getDamage(getStats().getStrength(), 1) * 16;
		finalDamage = (int) Math.round(attDamage);

		futureTargets[0].takeDamage(finalDamage, DmgType.PHYSICAL, null, null, null);
	}

	@Override
	protected BattleModel createBattleModel() {
		BattleModel b = new BattleModel("Monsters\\Behemoth.png", 45, 34);
		
		return b;
	}

	@Override
	public String setName() {
		return "Baby Behemoth";
	}
	
	@Deprecated
	/**
	 * For testing purposes.
	 */
	public void inflictOfStatuses() {
		this.takeDamage(0, DmgType.SPECIAL, null, 
				new Status[] {Status.BLIND, Status.SILENCE, Status.SLEEP, Status.POISON}, 
				new int[] {100, 100, 100, 100});
	}

	@Override
	public void addInstanceCount() {
		counter++;
	}

	@Override
	public void resetInstanceCount() {
		counter = 0;
	}

	@Override
	public int getInstanceCount() {
		return counter;
	}
}
