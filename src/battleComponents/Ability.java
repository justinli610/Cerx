package battleComponents;

public abstract class Ability implements CommandFormat {
	
	public abstract TargetType getTargetType();
	
	/**
	 * @param person - the user of the ability
	 * @param targets - the pre-chosen targets of the ability
	 */
	public abstract void execute(BattleTarget[] targets);
}
