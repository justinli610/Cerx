package field;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

// Contains terrain types
public enum Terrain {
	pathQ("Cropped\\NW.png", 'q'), pathW("Cropped\\N.png", 'w'),
	pathE("Cropped\\NE.png", 'e'), pathA("Cropped\\W.png", 'a'),
	pathS("Cropped\\C.png", 's'), pathD("Cropped\\E.png", 'd'),
	pathZ("Cropped\\SW.png", 'z'), pathX("Cropped\\S.png", 'x'),
	pathC("Cropped\\SE.png", 'c'), pathInvC("Cropped\\4.png", 'C'),
	pathInvE("Cropped\\2.png", 'E'), pathInvQ("Cropped\\1.png", 'Q'),
	pathInvZ("Cropped\\3.png", 'Z'), path1("Cropped\\Cliffveg5.png", 'W'),
	path2("Cropped\\Cliffveg6.png", 'A'), path3("Cropped\\Cliffveg8.png", 'S'),
	path4("Cropped\\Cliffveg12.png", 'D'), path5("Cropped\\Cliffveg13.png", 'X'),
	// Anything with a char cast requires num lock to be enabled
	paveQ("Cropped\\Pave5.png", (char) ('q' + 127)), paveW("Cropped\\Pave6.png", (char) ('w' + 127)),
	paveE("Cropped\\Pave7.png", (char) ('e' + 127)), paveA("Cropped\\Pave10.png", (char) ('a' + 127)),
	paveS("Cropped\\Pave11.png", (char) ('s' + 127)), paveD("Cropped\\Pave12.png", (char) ('d' + 127)),
	paveZ("Cropped\\Pave15.png", (char) ('z' + 127)), paveX("Cropped\\Pave16.png", (char) ('x' + 127)),
	paveC("Cropped\\Pave17.png", (char) ('c' + 127)), paveInvQ("Cropped\\Pave8.png", (char) ('Q' + 127)),
	paveInvE("Cropped\\Pave9.png", (char) ('E' + 127)), paveInvZ("Cropped\\Pave13.png", (char) ('Z' + 127)),
	paveInvC("Cropped\\Pave14.png", (char) ('C' + 127)), pave1("Cropped\\Pave1.png", (char) ('W' + 127)),
	pave2("Cropped\\Pave2.png", (char) ('A' + 127)), pave3("Cropped\\Pave3.png", (char) ('D' + 127)),
	pave4("Cropped\\Pave4.png", (char) ('X' + 127)), villFloor1("Cropped\\Villfloor2.png", (char) ('1' + 127)),
	villFloor2("Cropped\\Villfloor3.png", (char) ('2' + 127)), villFloor3("Cropped\\Villfloor1.png", (char) ('3' + 127)),
	villFloor4("Cropped\\Villfloor4.png", (char) ('4' + 127)), villFloor5("Cropped\\Villfloor5.png", (char) ('5' + 127)),
	villFloor6("Cropped\\Villfloor6.png", (char) ('6' + 127)),
	grass("Cropped\\Grass.png", 'g'),
	nothing("Cropped\\Void.png", 'v');
		
	private Image img;
	private char key;
	private File imageFile;
	
	// Constructor
	Terrain(String filePath, char key) {
		imageFile = new File(filePath);
		try {
			this.img = ImageIO.read(imageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.key = key;
	}
	
	/**
	 * Changes the colours of the image to make it appear like night time.
	 */
	public static void desaturate() {
		for (Terrain t : Terrain.values()) {
			// Convert to a buffered image
			BufferedImage b = (BufferedImage) t.getImage();
			RescaleOp rescaleOp;
			rescaleOp = new RescaleOp(1f, -200, null);
			rescaleOp.filter(b, b); // Source and destination are the same
		}
	}
	
	/**
	 * Reset the images by re-reading them.
	 */
	public static void daytime() {
		for (Terrain t : Terrain.values()) {
			try {
				t.img = ImageIO.read(t.imageFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Image getImage() {
		return img;
	}
	
	public char getKey() {
		return key;
	}
}
