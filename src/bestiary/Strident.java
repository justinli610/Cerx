package bestiary;
import battleComponents.*;
import battleGUI.BattleModel;

public class Strident extends Monster {
	private static int counter = 0;
	
	private static final int ATTACK = 0, PIERCING_STRIKE = 1;
	
	/**
	 * The custom Strident constructor for special encounters
	 * 
	 * @param stats - The StatPackage containing Strident statistics
	 */
	public Strident(StatPackage stats) {
		super(stats);
	}
	
	/**
	 * The standard Strident constructor with default statistics
	 */
	public Strident() {
		super(2, 159, 0, 3, 0, 3, 28, 5);
	}

	/**
	 * Deals regular damage to a single target.
	 * @param target - an array that can contain only one BattleTarget.
	 */
	public void attack(BattleTarget[] target) {
		double attDamage;
		int finalDamage;

		attDamage = getDamage(getStats().getStrength(), 1) * 16;
		finalDamage = (int) Math.round(attDamage);

		target[0].takeDamage(finalDamage, DmgType.PHYSICAL, null, null, null);
	}

	/**
	 * Regular physical attack, but with a 50% chance to critical.
	 * @param target - an array that can contain only one BattleTarget.
	 */
	public void piercingStrike(BattleTarget[] target) {
		double attDamage;
		int finalDamage;

		attDamage = getDamage(getStats().getStrength(), 1) * 16;
		finalDamage = (int) Math.round(attDamage);

		if (getRandom() >= 50)
			finalDamage *= 2;

		target[0].takeDamage(finalDamage, DmgType.PHYSICAL, null, null, null);
	}

	@Override
	public void autobattle() {
		if (futureAction == ATTACK)
			attack(futureTargets);
		else if (futureAction == PIERCING_STRIKE)
			piercingStrike(futureTargets);
		
		setCurrTargets(futureTargets);
	}

	@Override
	public String chooseAction(BattleTarget[] allTargets) {
		String actionName;
		
		if (getRandom() > 30) {
			futureAction = ATTACK;
			actionName = null;
		} else {
			futureAction = PIERCING_STRIKE;
			actionName = "Piercing Strike";
		}
		
		futureTargets = chooseTargets(allTargets);
		
		return actionName;
	}

	@Override
	protected BattleModel createBattleModel() {
		BattleModel b = new BattleModel("Monsters\\Strident.png", 77, 42);
		return b;
	}

	@Override
	public String setName() {
		return "Strident";
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
