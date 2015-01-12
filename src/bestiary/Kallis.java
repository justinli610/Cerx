package bestiary;

import battleComponents.Character;
import battleComponents.StatPackage;
import battleGUI.BattleModel;

public class Kallis extends Character {
	private static final long serialVersionUID = 3524787658764435364L;
	
	private static final StatPackage stats = new StatPackage(1, 212, 49, 5, 14, 1, 12, 22);
	public static final Character KALLIS = new Kallis();
	
	public Kallis() {
		super(stats);
	}

	@Override
	protected BattleModel createBattleModel() {
		BattleModel b = new BattleModel("Monsters\\Terra.png", 60, 30);
		return b;
	}

	@Override
	public String setName() {
		return "Kallis";
	}

}
