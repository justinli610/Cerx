package bestiary;

import battleComponents.BattleTarget;
import battleComponents.DmgType;
import battleComponents.Element;
import battleComponents.Magic;
import battleComponents.Status;
import battleGUI.BattleModel;

public class Thunderbird extends Monster {
	private static final int ATTACK = 0, THUNDER = 1, FOCUS = 2;
	private static int counter = 0;
	/**
	 * Fast and resistant to magic. Casts magic at the party. Deplete its MP to render it defenseless.
	 * Absorbs Lightning.
	 */
	public Thunderbird() {
		super(6, 97, 40, 1, 13, 1, 78, 25);
		this.setElementResist(Element.LIGHTNING, -25);
	}
	
	public void thunder(BattleTarget[] target) {
		cast(Magic.THUNDER, target);
	}
	
	/**
	 * A weak hit, only used when no other options are available
	 * @param target
	 */
	public void attack(BattleTarget[] target) {
		int attDamage;
		
		for (BattleTarget t : target) {
			attDamage = (int) (getDamage(getStats().getStrength(), 1) * 16);
			t.takeDamage(attDamage, DmgType.PHYSICAL, Element.LIGHTNING, null, null);
		}
	}
	
	/**
	 * Recover a small amount of MP
	 * @param target - must be self
	 */
	public void focus(BattleTarget[] target) {
		int attDamage;
		
		for (BattleTarget t : target) {
			attDamage = (int) getDamage(getStats().getMagic(), 16 * 0.1);
			t.takeDamage(attDamage, DmgType.MP_HEAL, null, null, null);
		}
	}
	
	@Override
	public String chooseAction(BattleTarget[] allTargets) {
		int random = getRandom();
		String actionName = null;
		
		if (getCurrentStatus(Status.SILENCE) > 0)
			futureAction = ATTACK;
		else if (getCurrMP() >= Magic.THUNDER.getMPCost()) {
			futureAction = THUNDER;
			actionName = "Thunder";
		} else if (random < 33) {
			futureAction = FOCUS;
			actionName = "Focus";
		} else
			futureAction = ATTACK;
		
		futureTargets = chooseTargets(allTargets);
		return actionName;
	}
	
	@Override
	public BattleTarget[] chooseTargets(BattleTarget[] allTargets) {
		BattleTarget[] targets = null;
		
		if (futureAction == THUNDER)
			targets = super.chooseTargets(allTargets);
		else if (futureAction == FOCUS)
			targets = chooseSelf();
		else // Attack
			targets = super.chooseTargets(allTargets);
		
		return targets;
	}

	@Override
	public void autobattle() {
		if (futureAction == ATTACK)
			attack(futureTargets);
		else if (futureAction == THUNDER)
			thunder(futureTargets);
		else if (futureAction == FOCUS)
			focus(futureTargets);
		
		// Last targeted group
		setCurrTargets(futureTargets);
	}

	@Override
	public int getInstanceCount() {
		return counter;
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
	protected BattleModel createBattleModel() {
		BattleModel b = new BattleModel("Monsters\\Thunderbird.png", 60, 0);
		return b;
	}

	@Override
	public String setName() {
		return "Thunderbird";
	}

}
