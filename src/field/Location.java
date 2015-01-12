package field;

import java.io.Serializable;
import java.util.ArrayList;

import scene.Cutscene;

/**
 * 
 * Represents a location in the world.
 *
 */
public class Location implements Serializable {
	private static final long serialVersionUID = -2511783912843847060L;

	public static final int LAST_LOCATION = 1;
	
	private Map map;
	private ArrayList<CharacterModel> characters; // Doesn't seem necessary either
	private ArrayList<Cutscene> cutscenes; // Doesn't seem necessary anymore
	
	private String name;
	private Environment setting;
	
	// private Music fieldTrack; // If null, no music will play
	// private Music battleTrack; // Each location can have a different battle theme, if desired
	
	// Should contain all possible encounter groups and encounter rate
	// Actually, this information will be stored in the BattleMeter instead. The Location really
	// doesn't need a reference to it.
	
	/**
	 * Creates a location from the files located in the specified folder.
	 * 
	 * @param name - the path where these files are stored. Should also be
	 * the name of the map
	 */
	public Location(String name) {
		this.name = name;
		// Create the map
		map = MapUtilities.load("Maps\\" + name);
		
		// Set weather and time of day
		setting = MapUtilities.getSetting(this);
		
		loadCharacters();
	}

	/**
	 * Keeps track of the characters that are in this scene.
	 */
	private void loadCharacters() {
		characters = new ArrayList<CharacterModel>();
	}

	public Map getMap() {
		return map;
	}
	
	/**
	 * Provides the Location's Setting object for reading and modifying.
	 * 
	 * @return the Location's Setting object
	 */
	public Environment getSetting() {
		return setting;
	}

	/**
	 * Reverts the time and weather to the Location's default, as specified
	 * in the info file.
	 */
	public void resetSetting() {
		setting = MapUtilities.getSetting(this);
	}
	
	/**
	 * Searches through the initialisation file to match the logicscape key value
	 * with the ID of that cutscene.
	 * 
	 * @param key - the logicscape letter
	 * @return the ID of the cutscene
	 */
	public int getCutsceneID(char key) {
		return MapUtilities.getCutsceneID(name, key);
	}

	/**
	 * Returns the name of the location. This is the same name that appears
	 * on the Location's folder and folder subfiles.
	 * 
	 * @return - the name of the location
	 */
	public String getName() {
		return name;
	}
}
