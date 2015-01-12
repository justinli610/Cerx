package battleComponents;

import java.io.Serializable;
import java.util.ArrayList;

import menu.Equipment;
import battleGUI.BattleModel;

public abstract class Character extends BattleTarget implements Serializable {
	private static final long serialVersionUID = 3683155294788906L;
	
	private ArrayList<CommandFormat> abilities, techniques;
	private int exp;
	private String name;
	private Equipment equip;
	
	public Character(StatPackage stats) {
		super(stats);
		
		name = setName();
		
		abilities = new ArrayList<CommandFormat>();
		techniques = new ArrayList<CommandFormat>();

		equip = new Equipment();
	}
	
	public int getExp() {
		return exp;
	}

	public void addExp(int exp) {
		this.exp += exp;
	}

	public void levelUp() {
		getStats().setLevel(getStats().getLevel() + 1);
	}
	
	/////////////////////////////////////////////////////////////////////////////
	// Battle housekeeping
	//
	
	public void learn(Magic spell) {
		if (!spells.contains(spell))
			spells.add(spell);
	}
	
	public void learn(Ability ability) {
		if (!abilities.contains(ability))
			abilities.add(ability);
	}
	
	/**
	 * Transfers all active techniques into techList
	 */
	public void updateTechList() {
		
	}
	
	public CommandFormat[] getAbilities() {
		if (abilities.size() > 0)
			return abilities.toArray(new CommandFormat[] {});
		else
			return new CommandFormat[] {};
	}
	
	public CommandFormat[] getSpells() {
		if (spells.size() > 0)
			return spells.toArray(new CommandFormat[] {});
		else
			return new CommandFormat[] {};
	}
	
	public CommandFormat[] getTechniques() {
		if (techniques.size() > 0)
			return techniques.toArray(new CommandFormat[] {});
		else
			return new CommandFormat[] {};
	}

	public Equipment getEquipment() {
		return equip;
	}
	
	/////////////////////////////////////////////////////////////////////////////
	// Meta
	//
	
	@Override
	/**
	 * Characters always have their stats visible.
	 */
	public boolean isScanned() {
		return true;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	/////////////////////////////////////////////////////////////////////////////
	// Battle commands
	//
	
	/**
	 * The attack command, always available to each character.
	 * 
	 * @param target - The target of the attack
	 */
	public class Attack extends Ability {
		private final TargetType target = TargetType.SINGLE;
		private final DmgType type = DmgType.PHYSICAL;
		private final int mpCost = 0;
		private final Status[] status = null;
		private final int[] statusAcc = null;
		
		public void attack(BattleTarget[] targets) {
			double attDamage;
			int finalDamage;
			
			attDamage = getDamage(getStats().getStrength(), 1) * 16;
			finalDamage = (int) Math.round(attDamage);

			targets[0].takeDamage(finalDamage, type, null, status, statusAcc);
		}

		@Override
		public int getMPCost() {
			return mpCost;
		}

		@Override
		public TargetType getTargetType() {
			return target;
		}

		@Override
		public void execute(BattleTarget[] targets) {
			attack(targets);
		}

		@Override
		public DmgType getDamageType() {
			return type;
		}
	}
	
	/**
	 * 
	 * A class representing the properties of the Blitz ability
	 *
	 */
	public class Blitz extends Ability implements Serializable {
		private final TargetType target = TargetType.GROUP;
		private final DmgType type = DmgType.PHYSICAL;
		private final int mpCost = 0;
		private final Status[] status = null;
		private final int[] statusAcc = null;
		
		@Override
		public TargetType getTargetType() {
			return target;
		}
		
		@Override
		public void execute(BattleTarget[] targets) {
			blitz(targets);
		}
		
		public void blitz (BattleTarget[] targets) {
			double attDamage;
			int finalDamage;
			
			for (BattleTarget target : targets) {
				attDamage = getDamage(getStats().getStrength(), 0.5) * 16;
				finalDamage = (int) Math.round(attDamage);
				target.takeDamage(finalDamage, type, null, status, statusAcc);
			}
		}
		
		@Override
		public int getMPCost() {
			return mpCost;
		}
		
		@Override
		public String toString() {
			return "Blitz";
		}

		@Override
		public DmgType getDamageType() {
			return type;
		}
	}

	public String getImageIcon() {
		// TODO Auto-generated method stub
		return "This is an image";
	}
}
