package field;

/**
 * 
 * Contains information about all the encounter groups and encounter rate of an area.
 *
 */
public class EncounterInfo {
	private int encounterRate;
	private EncounterGroup[] encounters;
	
	public EncounterInfo(int encounterRate, EncounterGroup[] encounters) {
		this.encounterRate = encounterRate;
		this.encounters = encounters;
	}

	public int getEncounterRate() {
		return encounterRate;
	}

	public void setEncounterRate(int encounterRate) {
		this.encounterRate = encounterRate;
	}

	public EncounterGroup[] getEncounters() {
		return encounters;
	}
}

/**
 * 
 * Represents an group of enemies that can be encountered, as well as the
 * chance of the group appearing. Enemies are stored as String values. Encounter
 * chances are not absolute values, but rather treated relative to each other.
 *
 */
class EncounterGroup {
	private double chance;
	private String[] enemies;
	
	/**
	 * Constructs an EncounterGroup with the specified chance of the
	 * encounter occurring.
	 * 
	 * @param chance - the chance of the encounter occurring
	 * @param enemies - the list of enemies.
	 */
	public EncounterGroup(double chance, String[] enemies) {
		this.chance = chance;
		this.enemies = enemies;
	}

	public double getChance() {
		return chance;
	}

	public String[] getEnemies() {
		return enemies;
	}
}