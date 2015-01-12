package items;

import battleComponents.BattleTarget;

/**
 * 
 * Represents an Item that can be used in battle or on the field.
 *
 */
public interface Usable {
	public void use(BattleTarget[] targets);
}
