package field;

import java.awt.Toolkit;

/**
 * 
 * Initiates random encounters based on the Location, and creates the
 * enemy groups specified in the Location's info file.
 *
 */
public class BattleMeter {
	private Location location;
	private int timer;
	private EncounterInfo encounterInfo;
	
	/**
	 * Loads the encounter rate and encounter groups from the file.
	 * @param l - the current location
	 */
	public void setLocation(Location l) {
		this.location = l;
		
		// Load encounter list
		encounterInfo = MapUtilities.getEncounterRate(location);
		
		calculateNextEncounter();
	}
	
	/**
	 * Calculates the next randomly generated travel distance before an
	 * encounter.
	 */
	public void calculateNextEncounter() {
		if (encounterInfo.getEncounterRate() == 0)
			timer = Integer.MAX_VALUE;
		else {
			timer = (int) ((1100 - encounterInfo.getEncounterRate()) * 2 + // Fixed amount
					Math.random() * (1100 - encounterInfo.getEncounterRate()) * 0.5); // Random variation
		}
	}
	
	/**
	 * Decreases the encounter timer by the specified amount.
	 * 
	 * @param amount - the amount to decrease by
	 */
	public void countDown(int amount) {
		if (timer != Integer.MAX_VALUE)
			timer -= amount;
		
		if (timer < 0) {
			Toolkit.getDefaultToolkit().beep();
			
			// Initiate a fight
			EncounterGroup enemies = randomEncounter();
			GameState.getGameState().initiateBattle(enemies);
			
			// Generate another number
			calculateNextEncounter();
		}
	}
	
	/**
	 * Chooses one of the EncounterInfo's enemy formations based on their
	 * probabilities.
	 * 
	 * @return - the chosen enemy formation
	 */
	public EncounterGroup randomEncounter() {
		double total = 0;
		double p;
		double cumulativeProbability = 0.0;
		
		// Sum the probabilities
		for (EncounterGroup group : encounterInfo.getEncounters()) {
			total += group.getChance();
		}
		
		p = Math.random() * total;
		
		for (EncounterGroup group : encounterInfo.getEncounters()) {
		    cumulativeProbability += group.getChance();
		    if (p <= cumulativeProbability) {
		        return group;
		    }
		}
		
		return null;
	}
	
	/**
	 * Sets the encounter rate.
	 * 
	 * @param rate - the new rate
	 */
	public void setEncounterRate(int rate) {
		encounterInfo.setEncounterRate(rate);
	}
}