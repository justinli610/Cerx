package bestiary;

import battleComponents.BattleTarget;
import battleComponents.DmgType;
import battleComponents.Element;
import battleComponents.StatPackage;
import battleComponents.Status;
import battleGUI.BattleModel;

public class JovianHound extends Monster {
	private static int counter = 0;
	
	private static final int ATTACK = 0, POISON_FANG = 1;
	
	public JovianHound() {
		super(new StatPackage(2, 184, 1, 12, 2, 3, 4, 20));
		
		setElementResist(Element.FIRE, 150);
	}

	@Override
	public String chooseAction(BattleTarget[] allTargets) {
		String actionName = null;
		
		// Will always use Poison Fang if not at max HP
		if (getCurrHP() != getStats().getMaxHP()) {
			actionName = "Poison Fang";
			futureAction = POISON_FANG;
		} else
			futureAction = ATTACK;
		
		futureTargets = chooseTargets(allTargets);
		
		return actionName;
	}
	
	@Override
	public BattleTarget[] chooseTargets(BattleTarget[] allTargets) {
		BattleTarget[] b = new BattleTarget[1];
		allTargets = chooseParty(allTargets); // Filter out enemies
		b[0] = allTargets[0];
		
		for (int i = 1; i < allTargets.length; i++) {
			if (futureAction == ATTACK) { // Member with lowest HP
				if (allTargets[i].getCurrHP() < allTargets[i-1].getCurrHP())
					b[0] = allTargets[i];
			} else { // Member with highest HP
				if (allTargets[i].getCurrHP() > allTargets[i-1].getCurrHP())
					b[0] = allTargets[i];
			}
		}
		
		setCurrTargets(b);
		
		return b;
	}

	@Override
	public void autobattle() {
		if (futureAction == ATTACK)
			attack(futureTargets);
		else if (futureAction == POISON_FANG)
			poisonFang(futureTargets);
		
		setCurrTargets(futureTargets);
	}
	
	private void poisonFang(BattleTarget[] futureTargets) {
		double attDamage;
		int finalDamage;

		attDamage = getDamage(getStats().getStrength(), 1) * 16;
		finalDamage = (int) Math.round(attDamage);
		
		futureTargets[0].takeDamage(finalDamage, DmgType.PHYSICAL, 
				null, new Status[] {Status.POISON}, new int[] {50});
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
		BattleModel b = new BattleModel("Monsters\\Jovian Hound.png", 77, 42);
		return b;
	}

	@Override
	public String setName() {
		return "Jovian Hound";
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
