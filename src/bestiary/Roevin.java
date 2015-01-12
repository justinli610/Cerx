package bestiary;

import battleComponents.Character;
import battleComponents.StatPackage;
import battleGUI.BattleModel;

public class Roevin extends Character {
	private static final StatPackage stats = new StatPackage(5, 198, 53, 2, 13, 9, 16, 19);
	public static final Character ROEVIN = new Roevin();

	public Roevin() {
		super(stats);
		
		setScanDescription("Hot-headed and defiant, Roevin works " +
				"as a meteorologist with his younger brother " +
				"Arkin at the H. C. Altar Observatory. In battle " +
				"he carries a pistol, but prefers to use it as a " +
				"club unless bullets become absolutely necessary.");
	}

	@Override
	public String setName() {
		return "Roevin";
	}

	@Override
	protected BattleModel createBattleModel() {
		BattleModel b = new BattleModel("Monsters\\Sabin.png", 58, 30);
		return b;
	}

}
