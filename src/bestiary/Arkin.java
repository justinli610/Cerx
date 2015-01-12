package bestiary;

import battleComponents.Character;
import battleComponents.StatPackage;
import battleGUI.BattleModel;

public class Arkin extends Character {
	private static final long serialVersionUID = -1946559922089290156L;

	private static final StatPackage stats = new StatPackage(3, 294, 20, 6, 8, 3, 6, 21);
	public static final Arkin ARKIN = new Arkin();
	
	public Arkin() {
		super(stats);
		
		// FIIIIIIIIIIIIIIIX
		setScanDescription("Arkin will never admit he is related " +
				"to Roevin, nor will he ever refer to his brother " +
				"as anything less derogatory than \"the audacious idiot.\"");
	}

	@Override
	public String setName() {
		return "Arkin";
	}

	@Override
	protected BattleModel createBattleModel() {
		BattleModel b = new BattleModel("Monsters\\Locke.png", 60, 30);
		return b;
	}

}
