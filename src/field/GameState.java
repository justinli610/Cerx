package field;

import fieldComponents.GameIO;
import items.Inventory;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

import main.GameFrameNew;
import scene.Cutscene;
import scene.CutsceneOverlayPanel;
import battleComponents.Character;
import battleComponents.Magic;
import battleComponents.Status;
import battleGUI.BattleScreen;
import battleGUI.GameOverScreen;
import bestiary.Arkin;
import bestiary.Kallis;
import bestiary.Roevin;

/**
 * 
 * The central processing area of the game. Keeps track of everything that's happening and
 * allows communication among different parts of the program.
 *
 */
public class GameState implements Serializable {
	private static final long serialVersionUID = -4498048877878083890L;
	private transient static GameState gameState;
	private transient static boolean created = false;
	
	// Keeps reference to music player
	private boolean sustainMusic; // If true, music won't change when requested to
	
	// The displays
	private transient GameFrameNew gameFrame;
	private transient MapPanel mapPanel;
	private transient CutsceneOverlayPanel dialogBox;
	private transient RainPanel rain;
	
	// The character in control
	private CharacterModel player;
	private ArrayList<CharacterModel> NPCs;
	// The list of party members
	private ArrayList<Character> party;
	
	// Player's inventory
	private Inventory inventory;
	
	// Location-based objects
	private Location currentLocation;
	private transient Location[] locations;
	private transient BattleMeter battleMeter;
	
	// Battle
	private transient boolean inBattle;
	private transient BattleScreen battleScreen;
	
	/**
	 * Initialises the GameState to a new game.
	 */
	private GameState() {
		NPCs = new ArrayList<CharacterModel>();
		
		// Initialise the displays
		mapPanel = new MapPanel();
		mapPanel.associateNPCs(NPCs);
		gameFrame = new GameFrameNew(mapPanel);
		dialogBox = new CutsceneOverlayPanel();
		rain = new RainPanel(50, false);
		
		gameFrame.addRain(rain);
		gameFrame.registerDialogbox(dialogBox);
		
		rain.start();
		
		loadLocations();
		
		setupParty();
	}

	/**
	 * The only way to retrieve the current GameState.
	 * @return - the current GameState
	 */
	public static GameState getGameState() {
		if (!created) {
			created = true;
			gameState = new GameState();
			gameState.getGameFrame().createCharacterListener();
		}
		
		return gameState;
	}
	
	public static void loadGameState(GameState g) {
		created = true;
		gameState = g;
		
		// Initialise the displays
		g.mapPanel = new MapPanel();
		g.mapPanel.associateNPCs(g.NPCs);
		g.gameFrame = new GameFrameNew(g.mapPanel);
		g.gameFrame.createCharacterListener();
		g.dialogBox = new CutsceneOverlayPanel();
		g.rain = new RainPanel(50, false);
		
		g.gameFrame.addRain(g.rain);
		g.gameFrame.registerDialogbox(g.dialogBox);
		
		g.rain.start();
		
		g.loadLocations();
		
		g.setPlayer(g.player);
		g.gameFrame.centreOnPlayer();
	}
	
	public static void saveGameState() {
		GameIO.save();
	}
	
	private void setupParty() {
		party = new ArrayList<Character>(3);
		
		party.add(Arkin.ARKIN);
		party.add(Kallis.KALLIS);
		
		Kallis.KALLIS.learn(Magic.CURE);
		Kallis.KALLIS.learn(Magic.THUNDER);
		Kallis.KALLIS.learn(Magic.WATER);
		
		Roevin.ROEVIN.learn(Magic.FIRE);
		Roevin.ROEVIN.learn(Magic.BLIZZARD);
		Roevin.ROEVIN.learn(Magic.BIO);
		
		Arkin.ARKIN.learn(Magic.RUIN);
		Arkin.ARKIN.learn(Magic.SCAN);
		Arkin.ARKIN.learn(Arkin.ARKIN.new Blitz());
		Arkin.ARKIN.learn(Magic.SCAN);
	}
	
	/**
	 * Starts the game.
	 */
	public void newGame() {
		// Screen starts black, into cutscene
		createCutscene(11111);
		
		gameFrame.setVisible(true);
		switchLocation(locations[3], 80, 800);
	}
	
	public void startFromLoaded() {
		createCutscene(11112);
		
		gameFrame.setVisible(true);
		switchLocation(currentLocation, player.getFootPrint().x, player.getFootPrint().y);
	}
	
	private void createCutscene(int id) {
		Cutscene scene = new Cutscene(id);
		
		Thread cutsceneThread = new Thread(scene, "Cutscene");
		cutsceneThread.start();
	}

	/**
	 * Creates a cutscene from the given character key.
	 * 
	 * @param key - the character representation of the event
	 * @return true if the event should be destroyed, otherwise false
	 */
	public boolean createCutscene(char key) {
		int id = getCurrentLocation().getCutsceneID(key);
		Cutscene scene = new Cutscene(id);
		
		Thread cutsceneThread = new Thread(scene, "Cutscene");
		cutsceneThread.start();
		
		// IDs over 50000 mean the cutscene can occur multiple times
		if (id >= 50000)
			return false;
		return true;
	}

	// TODO: Load all the maps in the Maps folder instead of
	// doing this manually.
	public void loadLocations() {
		locations = new Location[11];
		locations[0] = new Location("Sample");
		locations[1] = new Location("Fence");
		locations[2] = new Location("House");
		locations[3] = new Location("LevelOneVillage"); 
		locations[4] = new Location("House2");
		locations[5] = new Location("House3");
		locations[6] = new Location("House4");
		locations[7] = new Location("House5");
		locations[8] = new Location("House6");
		locations[9] = new Location("House7");
		locations[10] = new Location("House8");
	}

	/**
	 * Change to the specified location, placing the player at the specified
	 * coordinates.
	 * 
	 * @param location - the location to change to
	 * @param x - the x-coordinate of the player, relative to the map
	 * @param y - the y-coordinate of the player, relative to the map
	 */
	public void switchLocation(Location location, int x, int y) {
		// TODO: Save the properties of the current location before switching.
		
		location.resetSetting();
		
		// Set the rain panel
		if (location.getSetting().weather == Environment.NONE)
			rain.stop();
		else if (location.getSetting().weather == Environment.RAIN) {
			rain.setFrequency(location.getSetting().rainFrequency);
			rain.setStorm(false);
			rain.start();
		} else {
			rain.setFrequency(location.getSetting().rainFrequency);
			rain.setStorm(true);
			rain.start();
		}
		
		currentLocation = location;
		mapPanel.setLocation(location);
		mapPanel.setTimeOfDay(currentLocation.getSetting().time);
		
		battleMeter.setLocation(location);
		
		if (player != null) {
			// Update the player's location on the new map
			player.setFootPrint(x, y);
			// Set the camera on the player
			gameFrame.centreOnPlayer();
		}
		
		// Refresh the screen
		gameFrame.revalidate();
		gameFrame.repaint();
		
		// Set the music
	}
	
	// Change the music
	// TODO: Implement
	public void changeMusic() {
		if (sustainMusic)
			;
	}
	
	public boolean isInBattle() {
		return inBattle;
	}

	private void setInBattle(boolean inBattle) {
		this.inBattle = inBattle;
	}

	/**
	 * Brings up the battle screen.
	 * @param encounter - the group of enemies to battle
	 */
	private EncounterGroup encounter;
	public void initiateBattle(EncounterGroup encounter) {
		GameState.saveGameState();
		setInBattle(true);
		
		gameFrame.stopTimers();
		
		// Fade out
		Cutscene battle = new Cutscene(10001);
		battle.run();
		rain.stop();
		this.encounter = encounter;
	}
	
	public void initiateBattle2() {
		battleScreen = new BattleScreen(getParty(), 
				EncounterConverter.getEnemies(encounter));
		encounter = null;
		
		gameFrame.setContentPane(battleScreen);
		
		// Fade in
		Cutscene battle = new Cutscene(10002);
		battle.run();
	}
	
	/**
	 * Returns to the field.
	 */
	public void endBattle() {
		gameFrame.setContentPane(mapPanel);
		mapPanel.requestFocusInWindow();
		
		for (int i = 0; i < party.size(); i++) {
			party.get(i).setCurrHP(party.get(i).getStats().getMaxHP());
			party.get(i).setCurrMP(party.get(i).getStats().getMaxMP());
			
			party.get(i).setCurrentStatus(Status.POISON, 0);
		}
		
		if (currentLocation.getSetting().weather != Environment.NONE)
			rain.start();
		
		gameFrame.startTimers();
		setInBattle(false);
	}
	
	public void displayGameOver() {
		gameFrame.setContentPane(new GameOverScreen());
	}

	public Location[] getLocationList() {
		return locations;
	}

	public GameFrameNew getGameFrame() {
		return gameFrame;
	}
	
	public MapPanel getMapPanel() {
		return mapPanel;
	}
	
	/**
	 * Returns the current location.
	 * 
	 * @return the current Location
	 */
	public Location getCurrentLocation() {
		return currentLocation;
	}

	public BattleMeter getBattleMeter() {
		return battleMeter;
	}

	public void setBattleMeter(BattleMeter battleMeter) {
		this.battleMeter = battleMeter;
	}

	public Inventory getInventory() {
		return inventory;
	}
	
	public Character[] getParty() {
		return party.toArray(new Character[] {});
	}

	public void addNPC(CharacterModel model) {
		NPCs.add(model);
		mapPanel.repaint();
	}

	public void removeNPC(CharacterModel nPCs) {
		NPCs.remove(nPCs);
		mapPanel.repaint();
	}
	
	public void clearNPCs() {
		NPCs.clear();
		mapPanel.repaint();
	}
	
	public CharacterModel[] getNPCs() {
		return NPCs.toArray(new CharacterModel[0]);
	}

	public void setPlayer(CharacterModel player) {
		this.player = player;
		gameFrame.registerPlayer(player);
	}
	
	public void setRainOn(boolean b) {
		if (b)
			rain.start();
		else
			rain.stop();
	}
}
