package scene;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.JPanel;
import javax.swing.Timer;

import field.CharacterConverter;
import field.CharacterModel;
import field.GameState;
import field.Location;
import field.MapPanel;

/**
 * 
 * Organises the flow of a cut scene by reading from a file and calling
 * the appropriate methods.
 *
 */
public class Cutscene implements Runnable {
	private Scanner scanner;
	private static File file = new File("Sample Script.txt"); // Assign this something
	
	private boolean started = false;
	private boolean ready = true;
	private boolean sustainGlassPane = false;

	private int code;
	
	/**
	 * Constructs a cut scene starting from the given ID key.
	 * 
	 * @param gState - the GameFrameNew that's running the program
	 * @param key - the letter that represents this event in the logicscape
	 * @param id - the identification number in the text file that signals the
	 * beginning of this cut scene.
	 */
	public Cutscene(int id) {
		
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// Find the sequence header
		scanner.findWithinHorizon("" + id, 0);
	}
	
	/**
	 * Creates a Cutscene unassociated with a script
	 */
	public Cutscene() {}

	/**
	 * Starts the cut scene.
	 */
	public void start() {
		// Can only run once
		if (!started) {
			started = true;
			scanner.nextLine();
			
			// Register this cut scene with the dialog box and 
			// bring up the glass pane
			GameState.getGameState().getGameFrame().getDialogbox().registerCutscene(this);
			GameState.getGameState().getGameFrame().getDialogbox().setDialogVisible(false);
			GameState.getGameState().getGameFrame().getGlassPane().setVisible(true);
			((JPanel) GameState.getGameState().getGameFrame().getGlassPane()).grabFocus();
			
			// Take action depending on the String
			processString(scanner.nextLine());
		}
	}

	/**
	 * Searches for and reads the next action, then performs it.
	 */
	public void next() {
		String line;
		
		// Read the tail
		code = Integer.parseInt(scanner.findWithinHorizon(Pattern.compile("\\d{4}"), 0));
		
		// If it's not a stop code...
		if (code != 0) {
			// Find the matching head and store it
			line = find(code + "");
			code = Integer.parseInt(line.substring(0, 4));
			
			scanner.nextLine();
			line = scanner.nextLine(); // Retrieve the text in this line
			
			processString(line);
		} else if (!sustainGlassPane) {
			GameState.getGameState().getGameFrame().getGlassPane().setVisible(false);
			GameState.getGameState().getGameFrame().startTimers();
		}	
	}

	@Override
	/**
	 * Starts the cutscene.
	 */
	public void run() {
		start();
	}
	
	/**
	 * Search wraps around.
	 * 
	 * @param query - the String to look for
	 * @return the searched String
	 */
	private String find(String query) {
		// Search for the query
		String found = scanner.findWithinHorizon(query, 0);
		// If not found, wrap around from the beginning
		if (found == null) {
			scanner.close();
			try {
				scanner = new Scanner(file);
			} catch (FileNotFoundException e) {}
			
			found = scanner.findWithinHorizon(query, 0);
		}
		
		// Return the line containing this code
		return found;
	}

	/**
	 * Reads the String and chooses an action to perform.
	 * 
	 * @param nextLine - the String to read
	 */
	private void processString(String nextLine) {
		// Split the String into its parts
		String[] parts = nextLine.split(": ");
		
		if (parts[0].equals("Dialog")) { // Complete
			dialogEvent(parts);
		} else if (parts[0].equals("Wait")) { // Complete
			waitEvent(parts);
		} else if (parts[0].equals("Fade out")) { // Complete
			fadeOutEvent(parts);
		} else if (parts[0].equals("Fade in")) { // Complete
			fadeInEvent(parts);
		} else if (parts[0].equals("Switch")) { // Complete
			switchEvent(parts);
		} else if (parts[0].equals("Move")) {
			moveEvent(parts);
		} else if (parts[0].equals("Pan")) { // Complete
			panEvent(parts);
		} else if (parts[0].equals("Receive")) {
			receiveEvent(parts);
		} else if (parts[0].equals("Music")) {
			musicEvent(parts);
		} else if (parts[0].equals("Character in")) { // Complete
			characterInEvent(parts);
		} else if (parts[0].equals("Character out")) {
			characterOutEvent(parts);
		} else if (parts[0].equals("Exeunt")) { // Complete
			exeuntEvent(parts);
		} else if (parts[0].equals("Center")) {
			centerEvent(parts);
		} else if (parts[0].equals("Player")) {
			playerEvent(parts);
		} else if (parts[0].equals("Sustain")) {
			sustain(parts);
		}
	}

	private void playerEvent(String[] parts) {
		CharacterModel[] characters = GameState.getGameState().getNPCs();
		int index = 0;
		
		ready = false;
		
		// Figure out which character
		for (int i = 0; i < characters.length; i++) {
			if (characters[i].getName().equals(parts[1]))
				index = i;
		}
		
		GameState.getGameState().setPlayer(characters[index]);
		
		ready = true;
		next();
	}

	/**
	 * Centres the camera on a player
	 * @param parts - the other tokens (parameters) in the string
	 */
	private void centerEvent(String[] parts) {
		CharacterModel[] models = GameState.getGameState().getNPCs();
		int index = 0;
		
		// Figure out which character
		for (int i = 0; i < models.length; i++) {
			if (models[i].getName().equals(parts[1]))
				index = i;
		}
		
		GameState.getGameState().getGameFrame().centreOnPlayer(models[index]);
		next();
	}

	private void exeuntEvent(String[] parts) {
		GameState.getGameState().clearNPCs();
		next();
	}

	private void characterOutEvent(String[] parts) {
		CharacterModel model = CharacterConverter.getCharacter(parts[1]);
		GameState.getGameState().removeNPC(model);
		next();
	}

	private void characterInEvent(String[] parts) {
		CharacterModel model;
		String coordinates[];
		int x, y;
		
		ready = false;
		
		// Find the player coordinates
		coordinates = parts[2].split("\\s");
		x = Integer.parseInt(coordinates[0].substring(1));
		y = Integer.parseInt(coordinates[1].substring(1));
		
		model = CharacterConverter.getCharacter(parts[1]);
		model.setFootPrint(x, y);
		
		GameState.getGameState().addNPC(model);
		
		ready = true;
		next();
	}

	private void dialogEvent(String[] parts) {
		// Set the dialog box's text
		GameState.getGameState().getGameFrame().getDialogbox().set(parts[1], parts[2]);
		GameState.getGameState().getGameFrame().getDialogbox().setDialogVisible(true);
	}

	private void waitEvent(String[] parts) {
		ready = false;
		// Set up a timer
		Timer t = new Timer(Integer.parseInt(parts[1].trim()), new WaitHandler());
		t.setRepeats(false);
		t.start();
	}

	private void fadeOutEvent(String[] parts) {
		ready = false;
		
		Color c = Color.BLACK;
		
		if (!parts[1].equals("<par>")) {
			String args[] = parts[1].split(" ");
			c = new Color(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), 0);
		}
		
		Timer timer = new Timer(50, null);
		timer.addActionListener(new FadeOutHandler(c, timer));
		timer.start();
	}

	private void fadeInEvent(String[] parts) {
		ready = false;
		
		Timer timer = new Timer(50, null);
		timer.addActionListener(new FadeInHandler(timer));
		timer.start();
	}

	private void switchEvent(String[] parts) {
		int x, y;
		String[] coordinates;
		Location newLocation = null;
		
		// Decode the location
		for (int i = 0; i < GameState.getGameState().getLocationList().length; i++) {
			if (parts[1].equals(GameState.getGameState().getLocationList()[i].getName()))
				newLocation = GameState.getGameState().getLocationList()[i];
		}
		
		// Find the player coordinates
		coordinates = parts[2].split("\\s");
		x = Integer.parseInt(coordinates[0].substring(1));
		y = Integer.parseInt(coordinates[1].substring(1));
		
		GameState.getGameState().switchLocation(newLocation, x, y);
		
		next();
	}

	private void moveEvent(String[] parts) {
		ready = false;
		
		// Do whatever movement things are necessary
		// TODO: Finish this part
		
		ready = true;
		next(); // Move this command into the ActionListener's actionPerformed method
	}

	private void panEvent(String[] parts) {
		String[] commands;
		char direction;
		int distance, direct = -1;
		Timer[] timers;
		
		ready = false;
		
		MapPanel p = GameState.getGameState().getMapPanel();
		
		// Dissect the String
		parts[1].trim();
		commands = parts[1].split("\\s");
		timers = new Timer[commands.length];
		
		for (int i = 0; i < commands.length; i++) {
			direction = commands[i].charAt(0);
			
			if (direction == 'U')
				direct = PanHandler.UP;
			else if (direction == 'D')
				direct = PanHandler.DOWN;
			else if (direction == 'L')
				direct = PanHandler.LEFT;
			else if (direction == 'R')
				direct = PanHandler.RIGHT;
			
			distance = Integer.parseInt(commands[i].substring(1));
			
			timers[i] = new Timer(50, null);
			timers[i].addActionListener(new PanHandler(timers, i, p, distance, direct));
			
			timers[i].start();
		}
	}

	private void receiveEvent(String[] parts) {
		// TODO Auto-generated method stub
		next();
	}

	private void musicEvent(String[] parts) {
		// TODO Auto-generated method stub
		next();
	}

	private void sustain(String[] parts) {
		sustainGlassPane = true;
	}

	/**
	 * Returns the cut scene's state (whether or not it is ready
	 * to process the next event.
	 * 
	 * @return - the cut scene's state
	 */
	public boolean isReady() {
		return ready;
	}

//	public static void main(String args[]) {
//		GameState g = new GameState();
//		Cutscene test = new Cutscene(g, 10000);
//		g.start();
//		test.start();
//	}
	
	/**
	 * 
	 * After the timer counts down, the next event will be called.
	 *
	 */
	private class WaitHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ready = true;
			next();
		}
		
	}
	
	/**
	 * 
	 * Gradually fades the screen.
	 *
	 */
	private class FadeOutHandler implements ActionListener {
		private Timer t;
		private int opaque = 0;
		
		/**
		 * Color should be fully transparent
		 * @param color
		 * @param timer
		 */
		public FadeOutHandler(Color color, Timer timer) {
			this.t = timer;
			
			GameState.getGameState().getGameFrame().getDialogbox().setColor(color);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			opaque += 10;
			
			GameState.getGameState().getGameFrame().getDialogbox().setOpacity(opaque);
			GameState.getGameState().getGameFrame().getDialogbox().repaint();
			
			if (GameState.getGameState().getGameFrame().getDialogbox().getOpacity() == 255) {
				t.stop();
				ready = true;
				next();
				
				if (GameState.getGameState().isInBattle())
					GameState.getGameState().initiateBattle2();
			}
		}
	}
	
	/**
	 * 
	 * Gradually fades the screen.
	 *
	 */
	private class FadeInHandler implements ActionListener {
		private Timer t;
		private int opaque = GameState.getGameState().getGameFrame().getDialogbox().getOpacity();
		
		public FadeInHandler(Timer t) {
			this.t = t;
		}
		
		public FadeInHandler(Timer t, boolean independent) {
			this(t);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			opaque -= 10;
			
			GameState.getGameState().getGameFrame().getDialogbox().setOpacity(opaque);
			
			if (GameState.getGameState().getGameFrame().getDialogbox().getOpacity() == 0) {
				t.stop();
				ready = true;
				next();
			}
		}
	}
	
	/**
	 * 
	 * Scrolls the screen automatically via Cutscene events and Timer activation.
	 *
	 */
	private class PanHandler implements ActionListener {
		public static final int UP = 1, DOWN = 2, LEFT = 3, RIGHT = 4;
		private final int SPEED;
		private int count = 0;
		private MapPanel p;
		private int shift, direction;
		
		private Timer t; // Reference
		private Timer[] timerArray;
		
		/**
		 * Create a PanHandler with the specified parameters and a default scrolling
		 * speed of 10 pixels.
		 * 
		 * @param t - the timer associated with this object
		 * @param p - the MapPanel used to display the map
		 * @param shift - the distance shifted, in pixels
		 * @param direction - the direction to shift in, using PanHandler's constant values
		 */
		public PanHandler(Timer[] timers, int i, MapPanel p, int shift, int direction) {
			this(timers, i, p, shift, direction, 10);
		}
		
		/**
		 * Create a PanHandler with the specified parameters.
		 * 
		 * @param t - the timer associated with this object
		 * @param p - the MapPanel used to display the map
		 * @param shift - the distance shifted, in pixels
		 * @param direction - the direction to shift in, using PanHandler's constant values
		 * @param speed - the speed, in pixels, to shift every 100 milliseconds
		 */
		public PanHandler(Timer[] timers, int i, MapPanel p, int shift, int direction, int speed) {
			this.p = p;
			this.shift = shift;
			this.direction = direction;
			this.t = timers[i];
			this.SPEED = speed;
			this.timerArray = timers;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			count += SPEED;
			
			if (direction == RIGHT)
				p.setVisibleX(p.getVisibleX() + SPEED);
			else if (direction == LEFT)
				p.setVisibleX(p.getVisibleX() - SPEED);
			else if (direction == UP)
				p.setVisibleY(p.getVisibleY() - SPEED);
			else if (direction == DOWN)
				p.setVisibleY(p.getVisibleY() + SPEED);
			
			p.repaint();
			
			// When the screen has panned far enough
			if (count >= shift) {
				boolean shouldContinue = true;
				
				t.stop(); // Stop the timer
				
				// Check if all the timers are done
				for (int i = 0; i < timerArray.length; i++) {
					if (timerArray[i].isRunning())
						shouldContinue = false;
				}
				
				if (shouldContinue) {
					ready = true; // Allow the user to continue
					next();
				}
			}
		}
	} // End PanHandler
	
}
