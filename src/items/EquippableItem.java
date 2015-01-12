package items;

import fieldComponents.EquipConstants;
import battleComponents.BattleTarget;
import battleComponents.StatPackage;

/**
 * 
 * Represents an Item that may be equipped.
 *
 */
public abstract class EquippableItem extends Item {//Don't need this
	private static final long serialVersionUID = 5061486843528501713L;
	
	private StatPackage modifiers;
	private EquipConstants equipConstant;
	
	public EquippableItem(StatPackage modifiers) {
		this.modifiers = modifiers;
	}

	public StatPackage getModifiers() {
		return modifiers;
	}
	
	public void setEquipConstant(EquipConstants equipConstant) {
		this.equipConstant = equipConstant;
	}

	public EquipConstants getEquipConstant() {
		return equipConstant;
	}
}
