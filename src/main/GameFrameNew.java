package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.EnumMap;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.Timer;

import menu.Menu;
import field.BattleMeter;
import field.CharacterModel;
import field.GameState;
import field.MapPanel;
import field.RainPanel;
import fieldComponents.Direction;
import fieldComponents.GameIO;
import scene.CutsceneOverlayPanel;


/**
 * 
 * Sets up the components and receives keyboard input.
 * Contains the main method.
 *
 */
public class GameFrameNew extends JFrame{
	public static final int WIDTH = 1200, HEIGHT = 700; // 800x600
	private MapPanel myMap;
	private CharacterModel player;
	private CutsceneOverlayPanel dialogBox;
	private CharacterMove characterListener;

	/**
	 * Create the game.
	 */
	public GameFrameNew(MapPanel map) {
		// Forwards to the other constructor
		this(map, "Game");
	}
	
	/**
	 * Create the game.
	 * @param title - the title for the frame
	 */
	public GameFrameNew(MapPanel map, String title) {
		registerMapPanel(map);
		
		setResizable(false);
		setBounds(0, 0, WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(title);
	}
	
	/**
	 * Must be separated from the constructor because it points back to the uninitialised
	 * GameState object.
	 */
	public void createCharacterListener() {
		if (characterListener == null) {
			characterListener = new CharacterMove();
			myMap.addKeyListener(characterListener);
		}
	}

	/**
	 * Sets the specified MapPanel to be displayed.
	 * @param panel
	 */
	private void registerMapPanel(MapPanel panel) {
		myMap = panel;
		myMap.addKeyListener(characterListener);
		myMap.addKeyListener(new ShowMenu());
		
		setContentPane(myMap);
	}
	
	/**
	 * Sets the specified character as the one the player is controlling.
	 * 
	 * @param character - the character to control.
	 */
	public void registerPlayer(CharacterModel character) {
		myMap.registerPlayer(character);
		player = character;
	}

	/**
	 * Adds a RainPanel to the layered pane.
	 * @param panel - the RainPanel to add
	 */
	public void addRain(RainPanel panel) {
		getLayeredPane().add(panel, JLayeredPane.PALETTE_LAYER);
	}
	
	/**
	 * Assigns the specified Dialogbox to be this object's glass pane.
	 * @param box - the Dialogbox to assign
	 */
	public void registerDialogbox(CutsceneOverlayPanel box) {
		this.dialogBox = box;
		
		setGlassPane(dialogBox);
	}
	
	public CutsceneOverlayPanel getDialogbox() {
		return dialogBox;
	}
	
	/**
	 * Centres the camera on the player.
	 */
	public void centreOnPlayer() {
		centreOnPlayer(player);
	}
	
	/**
	 * Centres the camera on the player.
	 */
	public void centreOnPlayer(CharacterModel model) {
		myMap.setVisibleX(model.getFootPrint().x - getWidth() / 2);
		myMap.setVisibleY(model.getFootPrint().y - getHeight() / 2);
		
		if (myMap.getVisibleX() < 0)
			myMap.setVisibleX(0);
		else if (myMap.getVisibleX() > myMap.getPreferredSize().width - getWidth())
			myMap.setVisibleX(myMap.getPreferredSize().width - getWidth());
		
		if (myMap.getVisibleY() < 0)
			myMap.setVisibleY(0);
		else if (myMap.getVisibleY() > myMap.getPreferredSize().height - getHeight())
			myMap.setVisibleY(myMap.getPreferredSize().height - getHeight());
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {	
		//GameState.loadGameState(GameIO.load());
		//GameState.getGameState().startFromLoaded();
		
		GameState.getGameState().newGame();
	}
	
	/**
	 * Allows the movement Timers to start again.
	 */
	public void startTimers() {
		characterListener.start();
	}
	
	/**
	 * Stops the movement Timers from running.
	 */
	public void stopTimers() {
		characterListener.stop();
	}
	
	public class ShowMenu implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyChar() == 'd') {
				Menu menu = new Menu(GameState.getGameState());
				setContentPane(menu);
				GameState.getGameState().setRainOn(false);
				menu.requestTabFocus();
				
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
		}
		
	}
	
	/**
	 * 
	 * Receives input from the keyboard and controls the timers.
	 *
	 */
	public class CharacterMove implements KeyListener {
		private static final int delay = 30;
		
		private EnumMap<Direction, Timer> timers = new EnumMap<>(Direction.class);
		private EnumMap<Direction, MovementHandler> movers = new EnumMap<>(Direction.class);
		
		// Track which timer should be active
		private EnumMap<Direction, Boolean> keyDown = new EnumMap<>(Direction.class);
		
		public CharacterMove() {
			BattleMeter meter = new BattleMeter();
			
			// Set up the battle meter
			GameState.getGameState().setBattleMeter(meter);
			
			// Create an ActionListener to handle each direction
			movers.put(Direction.UP, new MovementHandler(meter, KeyEvent.VK_UP));
			movers.put(Direction.RIGHT, new MovementHandler(meter, KeyEvent.VK_RIGHT));
			movers.put(Direction.DOWN, new MovementHandler(meter, KeyEvent.VK_DOWN));
			movers.put(Direction.LEFT, new MovementHandler(meter, KeyEvent.VK_LEFT));
			
			// Create the directional booleans
			for (Direction d : Direction.values())
				keyDown.put(d, false);
			
			// Create the timers based on the action listeners
			for (Direction d : Direction.values())
				timers.put(d, new Timer(delay, movers.get(d)));
		}
		
		/**
		 * Stops all the timers. Used for cutscenes.
		 */
		public void stop() {
			for (Direction d : Direction.values()) {
				keyDown.put(d, false);
				timers.get(d).stop();
				timers.get(d).setRepeats(false);
			}
		}
		
		/**
		 * Restart all the timers. Called whenever a cutscene finishes.
		 */
		public void start() {
			for (Direction d : Direction.values()) {
				timers.get(d).setRepeats(true);
			}
			
			characterListener.keyReleased(new KeyEvent(myMap, 0, System.currentTimeMillis(), 
					0, KeyEvent.VK_UP, ' '));
			characterListener.keyReleased(new KeyEvent(myMap, 0, System.currentTimeMillis(), 
					0, KeyEvent.VK_DOWN, ' '));
			characterListener.keyReleased(new KeyEvent(myMap, 0, System.currentTimeMillis(), 
					0, KeyEvent.VK_LEFT, ' '));
			characterListener.keyReleased(new KeyEvent(myMap, 0, System.currentTimeMillis(), 
					0, KeyEvent.VK_RIGHT, ' '));
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			// TODO: change the sprite depending on which key is pressed

			// Start the corresponding timer if it's not currently active
			if (e.getKeyCode() == KeyEvent.VK_UP && keyDown.get(Direction.UP) == false) {
				keyDown.put(Direction.UP, true);
				player.setCurrentSprite(Direction.UP);
				timers.get(Direction.UP).start();
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT && keyDown.get(Direction.RIGHT) == false) {
				keyDown.put(Direction.RIGHT, true);
				player.setCurrentSprite(Direction.RIGHT);
				timers.get(Direction.RIGHT).start();
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN && keyDown.get(Direction.DOWN) == false) {
				keyDown.put(Direction.DOWN, true);
				player.setCurrentSprite(Direction.DOWN);
				timers.get(Direction.DOWN).start();
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT && keyDown.get(Direction.LEFT) == false) {
				keyDown.put(Direction.LEFT, true);
				player.setCurrentSprite(Direction.LEFT);
				timers.get(Direction.LEFT).start();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// Stop the corresponding timer
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				keyDown.put(Direction.UP, false);
				timers.get(Direction.UP).stop();
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				keyDown.put(Direction.RIGHT, false);
				timers.get(Direction.RIGHT).stop();
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				keyDown.put(Direction.DOWN, false);
				timers.get(Direction.DOWN).stop();
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				keyDown.put(Direction.LEFT, false);
				timers.get(Direction.LEFT).stop();
			}
			
			// Change the direction that the character is facing (assuming another key is still held)
			for (Direction d : Direction.values()) {
				if (keyDown.get(d))
					player.setCurrentSprite(d);
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {} // No action
	}

	/**
	 * 
	 * Controls the movement of the player along the field and scrolling the map,
	 * if necessary.
	 *
	 */
	final class MovementHandler implements ActionListener {
		private static final int SPEED = 5; // Number of pixels to move per step
		// Distance from edge of window that will cause the map to shift
		private static final int H_SHIFT_BOUNDARY = 250, V_SHIFT_BOUNDARY = 200;
		
		private BattleMeter meter;
		private int x, y;
		
		/**
		 * Responsible for one-directional movement.
		 * 
		 * @param meter - the BattleMeter to alert for movements
		 * @param direction - the direction that this handler moves the character
		 */
		public MovementHandler(BattleMeter meter, int direction) {
			this.meter = meter;
			
			if (direction == KeyEvent.VK_UP) {
				x = 0; y = -SPEED;
			} else if (direction  == KeyEvent.VK_DOWN) {
				x = 0; y = SPEED;
			} else if (direction == KeyEvent.VK_RIGHT) {
				x = SPEED; y = 0;
			} else if (direction == KeyEvent.VK_LEFT) {
				x = -SPEED; y = 0;
			}
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			boolean reset = false; // Whether there was an intersection
			char key = ' ';
			
			// Move the player
			player.getFootPrint().translate(x, y);
			
			// Refresh the list of solid areas
			myMap.findSolidTiles();
			myMap.findEventTiles();
			
			// Check if a cutscene is triggered
			
			// Check if there is an intersection between an event tile and the
			// player. If so, figure out what event was triggered
			boolean eventTriggered = false; // Prevents event triggering twice if adjacent squares are intersected
			for (int i = 0; i < myMap.getEventTiles().length && !eventTriggered; i++) {
				if (player.getFootPrint().intersects(myMap.getEventTiles()[i])) {
					boolean destroyEvent = false;
					eventTriggered = true;
					
					key = myMap.getEventAt(myMap.getEventTiles()[i].x,
							myMap.getEventTiles()[i].y);
					
					// Pass the key to the GameState to deal with
					destroyEvent = GameState.getGameState().createCutscene(key);
					
					if (destroyEvent == true) {
						// Erase the cutscene from the map
						myMap.removeEventTiles(key);
					}
					
					// Stop the movement timers
					characterListener.stop();
				}
			}
			
			// Check if there is an intersection between a solid tile and the
			// player. If so, undo the translation.
			for (int i = 0; i < myMap.getSolidTiles().length; i++) {
				if (player.getFootPrint().intersects(
						myMap.getSolidTiles()[i])) {
					player.getFootPrint().translate(-x, -y);
					reset = true;
				}
			}
			
			// Check if the player is outside the window bounds
			if (!reset && !myMap.getBounds().contains(player.getFootPrint().x - myMap.getVisibleX(), 
					player.getFootPrint().y - myMap.getVisibleY(), 
					player.getFootPrint().width, player.getFootPrint().height)) {
				player.getFootPrint().translate(-x, -y);
				reset = true;
			}
			
			// Reposition the map if necessary
			shiftMap();
			
			// Count down the encounter timer if the movement was successful
			if (!reset)
				meter.countDown(SPEED);
			
			// Repaint
			myMap.repaint();
		}

		/**
		 * Repositions (scrolls) the map.
		 */
		private void shiftMap() {
			// Check which edge the player is close to, then check if there's anymore of the map to draw.
			// These conditions are separated into two clauses to avoid problems with pressing/releasing
			// multiple arrow keys.
			
			// Right
			if (myMap.getVisibleX() + myMap.getWidth() - player.getFootPrint().x < H_SHIFT_BOUNDARY) {
				if (myMap.getVisibleX() + myMap.getWidth() < myMap.getLogicScape()[0].length*40)
					myMap.setVisibleX(myMap.getVisibleX() + SPEED); // Move it over
				else // Leave it exactly aligned at the edge
					myMap.setVisibleX(myMap.getLogicScape()[0].length*40 - myMap.getWidth());
			} // Left
			else if (player.getFootPrint().x - myMap.getVisibleX() < H_SHIFT_BOUNDARY) {				
				if (myMap.getVisibleX() > SPEED)
					myMap.setVisibleX(myMap.getVisibleX() - SPEED); // Move it left
				else // Leave it exactly aligned at the edge
					myMap.setVisibleX(0);
			}
			
			// Top
			if (player.getFootPrint().y - myMap.getVisibleY() < V_SHIFT_BOUNDARY) {
				if (myMap.getVisibleY() > SPEED)
					myMap.setVisibleY(myMap.getVisibleY() - SPEED);
				else
					myMap.setVisibleY(0);
			} // Bottom
			else if (myMap.getVisibleY() + myMap.getHeight() - player.getFootPrint().y < V_SHIFT_BOUNDARY) {
				if (myMap.getVisibleY() + myMap.getHeight() < myMap.getLogicScape().length*40)
					myMap.setVisibleY(myMap.getVisibleY() + SPEED);
				else
					myMap.setVisibleY(myMap.getLogicScape().length*40 - myMap.getHeight());
			}
		}
	}
}
