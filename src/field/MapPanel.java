package field;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * 
 * Displays the map. Handles all rendering to the screen in field view, except
 * for dialogue, which occurs on the glass pane of GameFrameNew.
 *
 */
public class MapPanel extends JPanel {
	private Location location;
	private Rectangle[] solidTiles, eventTiles;
	private ArrayList<CharacterModel> characters;
	private CharacterModel player;
	private boolean night;
	private boolean rain;
	
	// Stores the top-left coordinates of the visible area of the Map in pixels
	private Rectangle visibleArea;
	private int visibleX, visibleY;
	
	/**
	 * Constructs a MapPanel from the given folder.
	 */
	public MapPanel() {
		// Panel properties
		setBorder(new EmptyBorder(-5, 0, 0, 0));
		setFocusable(true);
		
		visibleArea = new Rectangle(visibleX, visibleY, 
				(int) getPreferredSize().getWidth(), (int) getPreferredSize().getHeight());
		
		// Needed by the GameFrameNew class to determine collisions and events
		findSolidTiles();
		findEventTiles();
		
		// Desaturate the images
		if (night) {
			Landscape.desaturate();
			Terrain.desaturate();
		}
		
		characters = new ArrayList<CharacterModel>();
	}
	
	/**
	 * Change the map that the MapPanel is displaying.
	 * 
	 * @param place - the Location to change to
	 */
	public void setLocation(Location place) {
		location = place;
		
		// Update the solid and event tiles
		findSolidTiles();
		findEventTiles();
		
		setPreferredSize(location.getMap().getPreferredSize());
		repaint();
		revalidate();
		
		// Update the visible area
		visibleArea = new Rectangle(visibleX, visibleY, 
				(int) getWidth(), (int) getHeight());
	}

//	/**
//	 * Loads a new map (should not belong in this class).
//	 * @return the newly loaded map
//	 */
//	public void changeMap() {
//		location.getMap() = MapUtilities.load();
//		
//		repaint();
//		
//		// Update the solid and event tiles
//		findSolidTiles();
//		findEventTiles();
//	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Image img;
		int playerRow = 0;

		super.paintComponent(g2);
		
		visibleArea.setLocation(visibleX, visibleY);

		// Offset the change
		g2.translate(-visibleX, -visibleY);
		
		// Draw the world.getTerrain() only if it's currently on the screen
		for (int row = visibleArea.y/40; row < (visibleArea.y + visibleArea.height)/40 + 1; row++)
			for (int col = visibleArea.x/40; col < (visibleArea.x + visibleArea.width)/40 + 1; col++) {
				// Prevent drawing of areas that are out of bounds
				if (row > -1 && row < location.getMap().getTerrain().length && col > -1 && col < location.getMap().getTerrain()[0].length) {
					img = location.getMap().getTerrain()[row][col].getImage();
					g2.drawImage(img, col*40, row*40, null);
				}
			}

		for (int i = 0; i < characters.size(); i++) {
			// Calculate the position of the characters
			playerRow = getCharacterRow(characters.get(i));
			if (playerRow == -1)
				g2.drawImage(characters.get(i).getCurrentSprite(), (int) characters.get(i).getFootPrint().getMinX() - 2, 
						(int) characters.get(i).getFootPrint().getMinY() - 33, null);
		}
		
		// Repeat for the player
		playerRow = getCharacterRow(player);
		if (playerRow == -1)
			g2.drawImage(player.getCurrentSprite(), (int) player.getFootPrint().getMinX() - 2, 
					(int) player.getFootPrint().getMinY() - 33, null);
		

		// Tracks player's footprint
		// g2.setColor(Color.yellow);
		// g2.fill(player.getFootPrint());

		// Draw the world.getLandscape() only if it's currently on screen
		for (int row = visibleArea.y/40 - 1; row < (visibleArea.y + visibleArea.height)/40 + 5; row++)
			for (int col = visibleArea.x/40 - 3; col < (visibleArea.x + visibleArea.width)/40 + 3; col++) {
				// Prevent drawing of areas that are out of bounds
				if (row > -1 && row < location.getMap().getLandscape().length && col > -1 && col < location.getMap().getLandscape()[0].length) {
					if (location.getMap().getLandscape()[row][col] != null)  {
						img = location.getMap().getLandscape()[row][col].getImage();

						g2.drawImage(img, 
								col*40 - location.getMap().getLandscape()[row][col].getOffset().x, 
								row*40 - location.getMap().getLandscape()[row][col].getOffset().y, null);
					}

					for (int i = 0; i < characters.size(); i++) {
						// If character is in this row, draw them in front of all other features currently on screen
						playerRow = getCharacterRow(characters.get(i));
						if (playerRow == row)
							g2.drawImage(characters.get(i).getCurrentSprite(),
									(int) characters.get(i).getFootPrint().getMinX() - 2,
									(int) characters.get(i).getFootPrint().getMinY() - 33, null);
					}
					
					// Repeat for the player
					playerRow = getCharacterRow(player);
					if (playerRow == row)
						g2.drawImage(player.getCurrentSprite(), (int) player.getFootPrint().getMinX() - 2, 
								(int) player.getFootPrint().getMinY() - 33, null);
				}
			}
	}

	@Deprecated
	/**
	 * Only for testing purposes
	 * @param g2 - the graphics object to draw with
	 */
	private void showLogicscape(Graphics2D g2) {
		g2.setColor(Color.yellow);
	
		for (int i = 0; i < solidTiles.length; i++) {
			g2.fill(solidTiles[i]);
		}
	}

	/**
	 * Creates an array of all the visible world.getLogicscape() tiles that represent solid spaces
	 * so they can be checked against the character models for collision.
	 */
	public void findSolidTiles() {
		int counter = 0;
		Rectangle[] solids;
		
		// Check the number of solid tiles first
		for (int row = visibleArea.y/40; row < (visibleArea.y + visibleArea.height)/40; row++)
			for (int col = visibleArea.x/40; col < (visibleArea.x + visibleArea.width)/40; col++) {
				// If it represents a solid area and is not out of bounds
				if (row < location.getMap().getTerrain().length && col < location.getMap().getTerrain()[0].length &&
						location.getMap().getLogicscape()[row][col] != ' ')  {
					counter++;
				}
			}
		
		solids = new Rectangle[counter];
		counter = 0; // Reset to use again
		
		// Now actually make them into an array
		for (int row = visibleArea.y/40; row < (visibleArea.y + visibleArea.height)/40; row++)
			for (int col = visibleArea.x/40; col < (visibleArea.x + visibleArea.width)/40; col++) {
				// If it represents a solid area and is not out of bounds
				if (row < location.getMap().getTerrain().length && col < location.getMap().getTerrain()[0].length &&
						location.getMap().getLogicscape()[row][col] != ' ')  {
					solids[counter++] = new Rectangle(col*40, row*40, 40, 40);
				}
			}
		
		solidTiles = solids;
	}
	
	/**
	 * Finds and stores all the visible event tiles.
	 */
	public void findEventTiles() {
		int counter = 0;
		Rectangle[] events;
		
		// Check the number of solid tiles first
		for (int row = visibleArea.y/40; row < (visibleArea.y + visibleArea.height)/40; row++)
			for (int col = visibleArea.x/40; col < (visibleArea.x + visibleArea.width)/40; col++) {
				// If it represents an event square and is not out of bounds
				if (row < location.getMap().getTerrain().length && col < location.getMap().getTerrain()[0].length &&
						location.getMap().getLogicscape()[row][col] != '1' && location.getMap().getLogicscape()[row][col] != ' ')  {
					counter++;
				}
			}

		events = new Rectangle[counter];
		counter = 0; // Reset to use again

		// Now actually make them into an array
		for (int row = visibleArea.y/40; row < (visibleArea.y + visibleArea.height)/40; row++)
			for (int col = visibleArea.x/40; col < (visibleArea.x + visibleArea.width)/40; col++) {
				// If it represents an event square and is not out of bounds
				if (row < location.getMap().getTerrain().length && col < location.getMap().getTerrain()[0].length &&
						location.getMap().getLogicscape()[row][col] != '1' && location.getMap().getLogicscape()[row][col] != ' ')  {
					events[counter++] = new Rectangle(col*40, row*40, 40, 40);
				}
			}

		eventTiles = events;
	}
	
	/**
	 * Removes all events represented by the given key.
	 * 
	 * @param key - the character to search for
	 */
	public void removeEventTiles(char key) {
		// Search for the event and replace it with a blank
		for (int row = 0; row < location.getMap().getLogicscape().length; row++)
			for (int col = 0; col < location.getMap().getLogicscape()[0].length; col++) {
				if (location.getMap().getLogicscape()[row][col] == key)
					location.getMap().getLogicscape()[row][col] = ' ';
			}
		
		// Refresh this list
		findEventTiles();
	}

	/**
	 * Calculates the row that the player should be rendered in, to preserve
	 * depth.
	 * 
	 * @return the row 
	 */
	private int getCharacterRow(CharacterModel character) {
		if (character != null)
			return (int) (character.getFootPrint().getCenterY()/40) - 1;
		else
			return -2;
	}
	
	/**
	 * Returns the player's absolute position (relative to the whole map).
	 * 
	 * @return the player's footprint
	 */
	public Rectangle getPlayerPosition() {
		if (player != null)
			return player.getFootPrint();
		else
			return null;
	}

	/**
	 * Registers the specified Character's model as the main player
	 * @param model - the CharacterModel to associate
	 */
	public void registerPlayer(CharacterModel model) {
		player = model;
		
		// Reset the player's image
		player.daytime();
		
		if (night)
			player.nighttime();
	}

	public char[][] getLogicScape() {
		return location.getMap().getLogicscape();
	}
	
	/**
	 * Finds the event letter at the given pixel coordinates on the Map.
	 * @param x - the x coordinate
	 * @param y - the y coordinate
	 * @return the character at the given point on the logicscape
	 */
	public char getEventAt(int x, int y) {
		char value = location.getMap().getLogicscape()[y/40][x/40];
		
		if (value != '1')
			return value;
		return ' ';
	}

	/**
	 * Returns an array of Rectangles representing areas the player cannot
	 * walk through.
	 * @return the array of Rectangles
	 */
	public Rectangle[] getSolidTiles() {
		return solidTiles;
	}
	
	/**
	 * Returns an array of Rectangles representing areas that will trigger
	 * events. Note that no information about the identity of the events is stored.
	 * @return the array of Rectangles
	 */
	public Rectangle[] getEventTiles() {
		return eventTiles;
	}
	
	/**
	 * Updates the non-player characters present in the scene.
	 * @param list - the 
	 */
	public void associateNPCs(ArrayList<CharacterModel> list) {
		characters = list;
		
		// Reset CharacterModels
		for (CharacterModel cm : characters)
			cm.daytime();
		
		if (night) {
			for (CharacterModel cm : characters)
				cm.nighttime();
		}
	}
	
	/**
	 * Checks whether it is night time on the map.
	 * @return true, if it is night
	 */
	public boolean isNight() {
		return night;
	}

	public void setTimeOfDay(boolean night) {
		// Daytime
		if (this.night == true && night == false) {
			// Reload the Terrain and Landscape images
			Landscape.daytime();
			Terrain.daytime();
			
			for (CharacterModel cm : characters)
				cm.daytime();
			
		} else if (this.night == false && night == true) {
			// Shade all the images
			Landscape.desaturate();
			Terrain.desaturate();
			
			for (CharacterModel cm : characters)
				cm.nighttime();
		}
		
		this.night = night;
	}

	public boolean isRain() {
		return rain;
	}

	public void setRain(boolean rain) {
		this.rain = rain;
	}
	
	public int getVisibleX() {
		return visibleX;
	}

	public void setVisibleX(int visibleX) {
		this.visibleX = visibleX;
	}

	public int getVisibleY() {
		return visibleY;
	}

	public void setVisibleY(int visibleY) {
		this.visibleY = visibleY;
	}
}
