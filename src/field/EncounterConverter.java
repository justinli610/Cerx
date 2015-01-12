package field;

import bestiary.*;

/**
 * 
 * Converts Strings into arrays of Monsters.
 *
 */
public class EncounterConverter {
	/**
	 * No need to create objects of this class.
	 */
	private EncounterConverter() {}
	
	/**
	 * Returns the Monster objects represented by an EncounterGroup.
	 * @param group - an EncounterGroup
	 * @return the Monsters represented by an EncounterGroup
	 */
	public static Monster[] getEnemies(EncounterGroup group) {
		String[] enemyNames = group.getEnemies();
		Monster[] enemies = new Monster[enemyNames.length];
		
		for (int i = 0; i < enemyNames.length; i++) {
			switch(enemyNames[i]) {
			case "Baby Behemoth":
				enemies[i] = new BabyBehemoth(); break;
			case "Strident":
				enemies[i] = new Strident(); break;
			case "Jovian Hound":
				enemies[i] = new JovianHound(); break;
			case "Thunderbird":
				enemies[i] = new Thunderbird(); break;
			default:
				enemies[i] = new BabyBehemoth(); break;
			}
				
		}
		
		return enemies;
	}
}
