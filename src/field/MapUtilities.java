package field;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * 
 * Handles map input and output through static methods, and includes methods
 * to convert from a character to a Terrain or Landscape object. Note that
 * all the methods are static, and no objects can be created from this class.
 *
 */
public class MapUtilities {
	private static JFileChooser fileChooser = new JFileChooser();
	
	/**
	 * Cannot instantiate type externally.
	 */
	private MapUtilities() {}

	/**
	 * Creates a map from the chosen filePaths.
	 */
	public static Map load() {
		Map newMap;
		
		// Set file chooser properties
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.resetChoosableFileFilters();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		// Only take action if user presses OK
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File dir = fileChooser.getSelectedFile();


			// Retrieve the files from the specified directory
			File terrainFile = new File(dir.getAbsolutePath() + "\\" + dir.getName() + "Terrain.txt"), 
					landscapeFile = new File(dir.getAbsolutePath() + "\\" + dir.getName() + "Landscape.txt"), 
					logicFile = new File(dir.getAbsolutePath() + "\\" + dir.getName() + "Logicscape.txt");

			// Find the size of the map
			BufferedReader inputTer, inputLand, inputLog;
			try {
				// Attempt to read the file
				inputTer = new BufferedReader(new InputStreamReader(new FileInputStream(terrainFile), "UTF16"));

				String line;
				int character;
				int rows = 1, columns = 0;
				char[] letters;

				// Count the number of characters per line
				do {
					character = inputTer.read();
					columns++;
				} while (character != '\n');
				// Don't count the last two characters
				// I don't know why it's the last two that need to be discounted. It
				// should only be the last character, which will be \n
				columns -= 2;

				// Count the number of lines
				do {
					line = inputTer.readLine();
					rows++;
				} while (line != null);
				rows--; // Don't count the last (empty) line

				newMap = new Map(rows, columns, Map.EMPTY); // Initialise the map

				// Reset the buffered stream for terrain, initialise the one for landscape
				// and logic
				inputTer.close();
				inputTer = new BufferedReader(new InputStreamReader(new FileInputStream(terrainFile), "UTF16"));
				inputLand = new BufferedReader(new InputStreamReader(new FileInputStream(landscapeFile), "UTF16"));
				inputLog = new BufferedReader(new InputStreamReader(new FileInputStream(logicFile), "UTF16"));

				// Fill the new map's Terrain array
				for (int row = 0; row < newMap.getTerrain().length; row++) {
					line = inputTer.readLine(); // Read the next line

					letters = line.toCharArray(); // Convert it into a char array

					// Convert each letter into a Terrain type and insert it
					// into the terrain array
					for (int col = 0; col < letters.length; col++) {
						newMap.getTerrain()[row][col] = convertTerrain(letters[col]);
					}
				}

				// Fill the new map's Landscape array
				for (int row = 0; row < newMap.getLandscape().length; row++) {
					line = inputLand.readLine(); // Read the next line

					letters = line.toCharArray(); // Convert it into a char array

					// Convert each letter into a Landscape type and insert it
					// into the landscape array
					for (int col = 0; col < letters.length; col++) {
						newMap.getLandscape()[row][col] = convertLandscape(letters[col]);
					}
				}

				// Fill the new map's logical array
				for (int row = 0; row < newMap.getLogicscape().length; row++) {
					line = inputLog.readLine(); // Read the next line

					letters = line.toCharArray(); // Convert it into a char array

					// Insert it into the logic array
					for (int col = 0; col < letters.length; col++) {
						newMap.getLogicscape()[row][col] = letters[col];
					}
				}

				// Close the readers to prevent memory leaks
				inputTer.close();
				inputLand.close();
				inputLog.close();

				return newMap;
			} catch (Exception e) { // Most likely an IOException
				e.printStackTrace();
			}
		}
		return null; // Will happen if IOException occurs
	}
	
	
	/**
	 * Loads the specified map.
	 * 
	 * @param filePath - the file path to load from
	 * @return - the loaded map
	 */
	public static Map load(String filePath) {
		Map newMap;

		File dir = new File(filePath);

		// Retrieve the files from the specified directory
		File terrainFile = new File(dir.getAbsolutePath() + "\\" + dir.getName() + "Terrain.txt"), 
				landscapeFile = new File(dir.getAbsolutePath() + "\\" + dir.getName() + "Landscape.txt"), 
				logicFile = new File(dir.getAbsolutePath() + "\\" + dir.getName() + "Logicscape.txt");

		// Find the size of the map
		BufferedReader inputTer, inputLand, inputLog;
		try {
			// Attempt to read the file
			inputTer = new BufferedReader(new InputStreamReader(new FileInputStream(terrainFile), "UTF16"));

			String line;
			int character;
			int rows = 1, columns = 0;
			char[] letters;

			// Count the number of characters per line
			do {
				character = inputTer.read();
				columns++;
			} while (character != '\n');
			// Don't count the last two characters
			// I don't know why it's the last two that need to be discounted. It
			// should only be the last character, which will be \n
			columns -= 2;

			// Count the number of lines
			do {
				line = inputTer.readLine();
				rows++;
			} while (line != null);
			rows--; // Don't count the last (empty) line

			newMap = new Map(rows, columns, Map.EMPTY); // Initialise the map

			// Reset the buffered stream for terrain, initialise the one for landscape
			// and logic
			inputTer.close();
			inputTer = new BufferedReader(new InputStreamReader(new FileInputStream(terrainFile), "UTF16"));
			inputLand = new BufferedReader(new InputStreamReader(new FileInputStream(landscapeFile), "UTF16"));
			inputLog = new BufferedReader(new InputStreamReader(new FileInputStream(logicFile), "UTF16"));

			// Fill the new map's Terrain array
			for (int row = 0; row < newMap.getTerrain().length; row++) {
				line = inputTer.readLine(); // Read the next line

				letters = line.toCharArray(); // Convert it into a char array

				// Convert each letter into a Terrain type and insert it
				// into the terrain array
				for (int col = 0; col < letters.length; col++) {
					newMap.getTerrain()[row][col] = convertTerrain(letters[col]);
				}
			}

			// Fill the new map's Landscape array
			for (int row = 0; row < newMap.getLandscape().length; row++) {
				line = inputLand.readLine(); // Read the next line

				letters = line.toCharArray(); // Convert it into a char array

				// Convert each letter into a Landscape type and insert it
				// into the landscape array
				for (int col = 0; col < letters.length; col++) {
					newMap.getLandscape()[row][col] = convertLandscape(letters[col]);
				}
			}

			// Fill the new map's logical array
			for (int row = 0; row < newMap.getLogicscape().length; row++) {
				line = inputLog.readLine(); // Read the next line

				letters = line.toCharArray(); // Convert it into a char array

				// Insert it into the logic array
				for (int col = 0; col < letters.length; col++) {
					newMap.getLogicscape()[row][col] = letters[col];
				}
			}

			// Close the readers to prevent memory leaks
			inputTer.close();
			inputLand.close();
			inputLog.close();

			return newMap;
		} catch (Exception e) { // Most likely an IOException
			e.printStackTrace();
		}

		return null; // Will happen if IOException occurs
	}


	/**
	 * Saves a map to the user-specified destination
	 * 
	 * @param map - the map to save
	 */
	public static void save(Map map) {
		// Set file chooser properties
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.resetChoosableFileFilters();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		// Only do it if they confirm
		if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			File dir = fileChooser.getSelectedFile();
			String name = dir.getName();

			// Make the directory if it doesn't exist
			if (!fileChooser.getSelectedFile().exists())
				dir.mkdir();

			File terrainFile = new File(dir.getAbsolutePath() + "\\" + name + "Terrain.txt");
			File landscapeFile = new File(dir.getAbsolutePath() + "\\" + name + "Landscape.txt");
			File logicFile = new File(dir.getAbsolutePath() + "\\" + name + "Logicscape.txt");

			BufferedWriter output;
			try {
				// Initialise the buffered writer from the file
				output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(terrainFile), "UTF16"));

				// Writes the characters one by one into the file
				for (int row = 0; row < map.getTerrain().length; row++) {
					for (int col = 0; col < map.getTerrain()[0].length; col++) {
						output.write(map.getTerrain()[row][col].getKey());
					}
					output.newLine(); // Writes a line separator
				}

				output.close(); // Close the writer to prevent resource leaks

				// Repeat for the landscape file

				// Initialise the buffered writer from the file
				output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(landscapeFile), "UTF16"));

				// Writes the characters one by one into the file
				for (int row = 0; row < map.getLandscape().length; row++) {
					for (int col = 0; col < map.getLandscape()[0].length; col++) {
						if (map.getLandscape()[row][col] != null)
							output.write(map.getLandscape()[row][col].getKey());
						else
							output.write(' ');
					}
					output.newLine(); // Writes a line separator
				}

				output.close(); // Close the writer to prevent resource leaks

				// Repeat for the logicscape file

				// Initialise the buffered writer from the file
				output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logicFile), "UTF16"));

				// Writes the characters one by one into the file
				for (int row = 0; row < map.getLogicscape().length; row++) {
					for (int col = 0; col < map.getLogicscape()[0].length; col++) {
						output.write(map.getLogicscape()[row][col]);
					}
					output.newLine(); // Writes a line separator
				}

				output.close(); // Close the writer to prevent resource leaks
			} catch (Exception e) { // Most likely IOException
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Searches the info file to locate the ID of the cutscene.
	 * 
	 * @param name - the name of the Location 
	 * @param key - the key that represents the event.
	 * @return the cutscene's ID
	 */
	public static int getCutsceneID(String name, char key) {
		Scanner scanner;
		File info;
		int id = -1;
		
		info = new File("Maps\\" + name + "\\" + name + "info.txt");
		
		try { // Initialise the scanner
			scanner = new Scanner(info);
			
			// Focus the scanner on the event definitions
			scanner.findWithinHorizon(Pattern.compile("|\\s+Events\\s+\\\\"), 0);
			// Locate the key
			scanner.findWithinHorizon(Pattern.compile("'" + key + "'\\s+"), 0);
			// Find and parse the ID
			id = Integer.parseInt(scanner.findInLine(Pattern.compile("\\d+")));
			
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return id;
	}
	
	/**
	 * Retrieves the Terrain object represented by a character
	 * 
	 * @param key - the character representation of the Landscape object
	 * @return the Terrain object
	 */
	public static Terrain convertTerrain(char key) {
		// Cycle through the enum type
		for (Terrain t : Terrain.values()) {
			if (t.getKey() == key)
				return t;
		}
		
		// If the key doesn't match anything that exists
		return Terrain.nothing;
	}

	/**
	 * Retrieves the Landscape object represented by a character
	 * 
	 * @param key - the character representation of the Landscape object
	 * @return the landscape object
	 */
	public static Landscape convertLandscape(char key) {
		for (Landscape l : Landscape.values()) {
			if (l.getKey() == key)
				return l;
		}
		
		return null;
	}

	/**
	 * Creates a GIF image of the whole map. Unused.
	 * 
	 * @param map - the map to draw
	 */
	@Deprecated
	public static void writeImage(Map map) {
		// Allow the user to select a save path
		File dest = getImageFile();
		
		// Do nothing if user cancelled operation
		if (dest == null)
			return;
		
		BufferedImage image = new BufferedImage(map.getTerrain()[0].length*40, map.getTerrain().length*40,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = image.createGraphics();

		// Draw the terrain
		for (int row = 0; row < map.getTerrain().length; row++)
			for (int col = 0; col < map.getTerrain()[0].length; col++) {
				g2.drawImage(map.getTerrain()[row][col].getImage(),
						col * 40, row * 40, null);
			}

		// Draw the landscape
		for (int row = 0; row < map.getTerrain().length; row++)
			for (int col = 0; col < map.getTerrain()[0].length; col++) {
				if (map.getLandscape()[row][col] != null)
					g2.drawImage(map.getLandscape()[row][col].getImage(),
							col * 40, row * 40, null);
			}
		
		try {
			ImageIO.write(image, "gif", dest);
		} catch (IOException e) {
			System.out.println("Couldn't write file!");
			e.printStackTrace();
		}
	}

	/**
	 * Used with writeImage to get a GIF image.
	 * 
	 * @return - the selected GIF image file.
	 */
	private static File getImageFile() {
		// Set file chooser properties
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    	fileChooser.addChoosableFileFilter(new FileFilter() {
    		// Only show and save as image files
    		@Override
    		public boolean accept(File f) {
    			if (f.isDirectory())
    				return true;
    			if (f.getName().endsWith(".gif"))
    				return true;
    			return false;
    		}

    		@Override
    		// Returns the description of the filter type
    		public String getDescription() {
    			return "GIF Images";
    		}
    	});
		
		if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
			return fileChooser.getSelectedFile();
		else
			return null;
	}

	/**
	 * Reads the weather condition for the specified region.
	 * 
	 * @param location - the current location
	 * @return one of the Location constants
	 */
	public static Environment getSetting(Location location) {
		Scanner scanner;
		String temp;
		Environment setting = null;
		boolean time;
		int weather;
		int rainFrequency;
		
		try {
			scanner = new Scanner(new File(
					"Maps\\" + location.getName() + "\\" + location.getName() + "Info.txt"));
			
			scanner.findWithinHorizon(Pattern.compile("|\\s+Setting\\s+\\\\"), 0);
			
			// Process the time
			scanner.findWithinHorizon("Time: ", 0);
			temp = scanner.nextLine().trim();
			
			if (temp.equalsIgnoreCase("Night"))
				time = Environment.NIGHT;
			else
				time = Environment.DAY;
			
			// Process the weather
			scanner.findWithinHorizon("Weather: ", 0);
			temp = scanner.nextLine().trim();
			
			if (temp.equalsIgnoreCase("Rain"))
				weather = Environment.RAIN;
			else if (temp.equalsIgnoreCase("Storm"))
				weather = Environment.STORM;
			else //if (temp.equalsIgnoreCase("None"))
				weather = Environment.NONE;
			
			// Process the rain frequency
			scanner.findWithinHorizon("Rain Frequency: ", 0);
			temp = scanner.nextLine().trim();
			rainFrequency = Integer.parseInt(temp);
			
			scanner.close();
			
			// Create a new setting object
			setting = new Environment (time, weather, rainFrequency);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return setting;
	}
	
	/**
	 * Reads the encounter information for the specified area.
	 * @param location - the Location to check
	 * @return the EncounterInfo for a Location
	 */
	public static EncounterInfo getEncounterRate(Location location) {
		Scanner scanner;
		EncounterInfo encounterInfo;
		EncounterGroup[] groups = null;
		int rate = 0;
		
		try {
			scanner = new Scanner(new File(
					"Maps\\" + location.getName() + "\\" + location.getName() + "Info.txt"));
			
			scanner.findWithinHorizon(Pattern.compile("|\\s+Encounters\\s+\\\\"), 0);
			scanner.findWithinHorizon("Rate: ", 0);
			rate = Integer.parseInt(scanner.nextLine().trim());
			
			groups = getEncounters(scanner, location);
			
			scanner.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		encounterInfo = new EncounterInfo(rate, groups);
		
		return encounterInfo;
	}
	
	/**
	 * Creates an EncounterGroup array from the encounter groups of the specified location.
	 * 
	 * @param scanner - the currently executing Scanner
	 * @param location - the current Location
	 * @return a list of EncounterGroups for the current location
	 */
	private static EncounterGroup[] getEncounters(Scanner scanner, Location location) {
		ArrayList<EncounterGroup> encounters = new ArrayList<EncounterGroup>();
		
		String[] enemies;
		double chance;
		
		// Position the scanner
		scanner.findWithinHorizon("Enemy Formations:", 0);
		scanner.nextLine();
		
		// While the scanner hasn't read a stop codon
		while(!scanner.hasNext("End")) {
			// Extract the chance of encounter
			chance = scanner.nextDouble();

			// Split the String at comma+space
			enemies = scanner.nextLine().trim().split(",\\s");

			// Create the EncounterGroup
			encounters.add(new EncounterGroup(chance, enemies));
		}
		
		encounters.trimToSize();
		
		// Return the array
		return encounters.toArray(new EncounterGroup[encounters.size()]);
	}
}