package field;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.EnumMap;

import javax.imageio.ImageIO;

import fieldComponents.Direction;

/**
 * 
 * Holds the sprite images that represent a character, as well as the character's
 * location, as a coordinate.
 *
 */
public class CharacterModel implements Serializable {
	private static final long serialVersionUID = 9062294883412855179L;
	public static final int footPrintLength = 20, footPrintWidth = 15;
	
	private Rectangle place;
	private transient EnumMap<Direction, Image> sprites;
	private File folder;
	private transient Image currentSprite;
	
	private String name;
	
	/**
	 * Creates a character model including all necessary sprites
	 * @param folderPath - the folder that contains all the sprite images
	 */
	CharacterModel(String folderPath) {
		// Represents the solid portion of the character (which is 20x15 pixels)
		place = new Rectangle(0, 0, footPrintLength, footPrintWidth);
		
		// Load the images into the array
		folder = new File(folderPath);
		loadImages();
		
		String[] path = folderPath.split("\\\\");
		name = path[path.length - 1];
		
		// Default to DOWN-facing direction
		setCurrentSprite(Direction.DOWN);
	}
	
	private void loadImages() throws NullPointerException {
		File[] list = folder.listFiles();
		
		// Initialise
		sprites = new EnumMap<>(Direction.class);
		
		// Attempt to read the images and assign them to the correct direction
		Direction key;
		for (int i = 0; i < list.length; i++) {
			try {
				// Figure out which direction to map to
				key = whichDirection(list[i].getName());
				sprites.put(key, ImageIO.read(list[i]));
			} catch (IOException e) {
				System.err.println("Couldn't read in character images from folder.");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Calculate the direction given the file name
	 * 
	 * @param fileName
	 * @return
	 */
	private Direction whichDirection(String fileName) {
		final String extension = ".png";
		if (fileName.endsWith("Left" + extension))
			return Direction.LEFT;
		else if (fileName.endsWith("Right" + extension))
			return Direction.RIGHT;
		else if (fileName.endsWith("Up" + extension))
			return Direction.UP;
		else if (fileName.endsWith("Down" + extension))
			return Direction.DOWN;
		else {
			System.err.println("Character direction not supported! Defaulting to 'down'");
			return Direction.DOWN;
		}
	}

	/**
	 * Allows class methods to modify the images
	 * @return the list of sprites for the character
	 */
	private Collection<Image> getSprites() {
		return sprites.values();
	}
	
	public Image getCurrentSprite() {
		return currentSprite;
	}
	
	public void setCurrentSprite(Direction d) {
		switch (d) {
		case LEFT:
			currentSprite = sprites.get(Direction.LEFT);
			break;
		case UP:
			currentSprite = sprites.get(Direction.UP);
			break;
		case RIGHT:
			currentSprite = sprites.get(Direction.RIGHT);
			break;
		case DOWN:
		default: // DOWN
			currentSprite = sprites.get(Direction.DOWN);
		}
	}
	
	/**
	 * Changes the colours of the image to make it appear like night time.
	 */
	public void nighttime() {
		for (Image img : getSprites()) {
			// Convert to a buffered image
			BufferedImage b = (BufferedImage) img;
			RescaleOp rescaleOp;
			rescaleOp = new RescaleOp(1f, -80, null);
			rescaleOp.filter(b, b); // Source and destination are the same
		}
	}
	
	/**
	 * Reset the images by re-reading them.
	 */
	public void daytime() {
		loadImages();
	}
	
	/**
	 * Sets the character's location.
	 * 
	 * @param x - the x-coordinate relative to the MapPanel
	 * @param y - the y-coordinate relative to the MapPanel
	 */
	public void setFootPrint(int x, int y) {
		place.x = x;
		place.y = y;
	}
	
	/**
	 * Returns the Rectangle representing the solid portion of the character.
	 * The location is relative to the Map itself, not the MapPanel.
	 * 
	 * @return the Rectangle around the character
	 */
	public Rectangle getFootPrint() {
		return place;
	}

	public String getName() {
		return name;
	}
}
