package field;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 * Represents the different types of tiles possible for the landscape. Landscape
 * objects contain references to their images, key code, and drawing offset.
 *
 */
public enum Landscape {
	// Free keys: i, k, o, <space>
	
	// Fencing
	fence1("Cropped\\Landscape\\Fence1.png", '1', 0, 56),
	fence2("Cropped\\Landscape\\Fence2.png", '2', 0, 58),
	fence3("Cropped\\Landscape\\Fence3.png", '3', 0, 57),
	fence4("Cropped\\Landscape\\Fence4.png", '-', 0, 55),
	fence5("Cropped\\Landscape\\Fence5.png", '=', 0, 55),
	fence6("Cropped\\Landscape\\Fence6.png", '6', -4, 16),
	fence7("Cropped\\Landscape\\Fence7.png", '5', 0, 35),
	fence8("Cropped\\Landscape\\Fence8.png", '4', -3, 21),
	fence9("Cropped\\Landscape\\Fence9.png", '`', -1, 56),
	fence10("Cropped\\Landscape\\Fence10.png", '0', -2, 56),
	fence11("Cropped\\Landscape\\Fence11.png", '7', 0, 56),
	fence12("Cropped\\Landscape\\Fence12.png", '8', 0, 48),
	fence13("Cropped\\Landscape\\Fence13.png", '9', 0, 56),
	// Rocks and vegetation
	treeWide("Cropped\\Landscape\\TreeWide.png", 'v', 20, 56), // Takes up 1x2 solid tiles
	treeTall("Cropped\\Landscape\\TreeTall.png", 'b', 12, 68),
	tree2("Cropped\\Landscape\\Tree2.png", 'n', 16, 68), // Takes up 1x2 solid tiles
	tree1("Cropped\\Landscape\\Tree1.png", 'm', 12, 68),
	tree4("Cropped\\Landscape\\Tree4.png", 'y', -4, 18),
	tree5("Cropped\\Landscape\\Tree5.png", 'h', 0, 0),
	tree6("Cropped\\Landscape\\Tree6.png", 'j', 0, 0),
	tree7("Cropped\\Landscape\\Tree7.png", 'u', -4, 18),
	// Lighter colour - DON'T USE THESE
	build1("Cropped\\Landscape\\Townbot10.png", ',', 116, 120),
	build2("Cropped\\Landscape\\Townbot11.png", '.', 0, 120),
	build3("Cropped\\Landscape\\Townbot12.png", '/', 0, 120),
	build4("Cropped\\Landscape\\Townbot7.png", 'l', 116, 40),
	build5("Cropped\\Landscape\\Townbot8.png", ';', 0, 40),
	build6("Cropped\\Landscape\\Townbot9.png", '\'', 0, 40),
	build7("Cropped\\Landscape\\Townbot4.png", 'p', 115, 77),
	build8("Cropped\\Landscape\\Townbot5.png", '[', 0, 50),
	build9("Cropped\\Landscape\\Townbot6.png", ']', 0, 76),
	door("Cropped\\Landscape\\TownGate1.png", '\\', 0, 160),
	closedDoor("Cropped\\Landscape\\TownDoor2.png", 'k', 40, 32),
	window("Cropped\\Landscape\\Townwin1.png", 'o', 0, 160),
	
	// Darker colour
	buildQ("Cropped\\Landscape\\Towntop1.png", 'q', 115, 197),
	buildW("Cropped\\Landscape\\Towntop2.png", 'w', 0, 170),
	buildE("Cropped\\Landscape\\Towntop3.png", 'e', 0, 196),
	buildA("Cropped\\Landscape\\Towntop4.png", 'a', 116, 160),
	buildS("Cropped\\Landscape\\Towntop5.png", 's', 0, 160),
	buildD("Cropped\\Landscape\\Towntop6.png", 'd', 0, 160),
	buildZ("Cropped\\Landscape\\Townbot1.png", 'z', 116, 160),
	buildX("Cropped\\Landscape\\Townbot2.png", 'x', 0, 160),
	buildC("Cropped\\Landscape\\Townbot3.png", 'c', 0, 160),
	buildF("Cropped\\Landscape\\Townjnt1.png", 'f', 0, 160),
	buildG("Cropped\\Landscape\\Townjnt2.png", 'g', 0, 160),
	buildR("Cropped\\Landscape\\Townjnt3.png", 'r', 120, 160), // Additional frame
	buildT("Cropped\\Landscape\\Townjnt4.png", 't', 0, 160), // Additional frame
	
	// Requires CAPS LOCK and no control key
	
	// Interior walls
	indoorQ("Cropped\\Landscape\\Villint1.png", (char) ('Q' + 383), 0, 80),
	indoorW("Cropped\\Landscape\\Villint2.png", (char) ('W' + 383), 0, 80),
	indoorw("Cropped\\Landscape\\Villint3.png", (char) ('w' + 383), 0, 80),
	indoorE("Cropped\\Landscape\\Villint4.png", (char) ('E' + 383), 40, 80),
	indoorc("Cropped\\Landscape\\Villint5.png", (char) ('c' + 383), 0, 80),
	indoorz("Cropped\\Landscape\\Villint6.png", (char) ('z' + 383), 7, 80),
	indoorA("Cropped\\Landscape\\Villint7.png", (char) ('A' + 383), 0, 40),
	indoorD("Cropped\\Landscape\\Villint8.png", (char) ('D' + 383), 0, 40),
	indoore("Cropped\\Landscape\\Villint9.png", (char) ('e' + 383), 0, 42),
	indoorq("Cropped\\Landscape\\Villint10.png", (char) ('q' + 383), 4, 45),
	indoora("Cropped\\Landscape\\Villint11.png", (char) ('a' + 383), 0, 40),
	indoord("Cropped\\Landscape\\Villint12.png", (char) ('d' + 383), 0, 40),
	indoorZ("Cropped\\Landscape\\Villint13.png", (char) ('Z' + 383), 0, 80),
	indoorX("Cropped\\Landscape\\Villint14.png", (char) ('X' + 383), 0, 40),
	indoorx("Cropped\\Landscape\\Villint15.png", (char) ('x' + 383), 0, 40),
	indoorC("Cropped\\Landscape\\Villint16.png", (char) ('C' + 383), 0, 80),
	// T-Joints
	joint1("Cropped\\Landscape\\T-joint1.png", (char) ('!' + 383), 40, 74), // Done
	joint2("Cropped\\Landscape\\T-joint2.png", (char) ('@' + 383), 40, 74), // Done
	joint3("Cropped\\Landscape\\T-joint3.png", (char) ('#' + 383), 0, 80), // Done
	joint4("Cropped\\Landscape\\T-joint4.png", (char) ('$' + 383), 0, 80), // Done
	joint5("Cropped\\Landscape\\T-joint5.png", (char) ('%' + 383), 40, 80), // Done
	joint6("Cropped\\Landscape\\T-joint6.png", (char) ('^' + 383), 40, 80), // Done
	
	// Furniture
	kitchenTilde("Cropped\\Landscape\\Kitchen3.png", (char) ('`' + 383), 0, 40), // Armour
	furniture1("Cropped\\Landscape\\Bench1.png", (char) ('1' + 383), 0, 20), // Benches
	furniture2("Cropped\\Landscape\\Bench2.png", (char) ('2' + 383), 0, 0),
	furniture3("Cropped\\Landscape\\Bench5.png", (char) ('3' + 383), 0, 0),
	furniture4("Cropped\\Landscape\\Bench3.png", (char) ('4' + 383), 0, 20), // Chairs
	furniture5("Cropped\\Landscape\\Bench8.png", (char) ('5' + 383), 0, 20),
	furniture6("Cropped\\Landscape\\Bench4.png", (char) ('6' + 383), 0, 20),
	furniture7("Cropped\\Landscape\\Bench9.png", (char) ('7' + 383), 0, 20),
	furniture8("Cropped\\Landscape\\Bench6.png", (char) ('8' + 383), 0, 10), // Candle
	kitchen9("Cropped\\Landscape\\Kitchen1.png", (char) ('9' + 383), 0, 40), // I don't know what this is
	kitchen0("Cropped\\Landscape\\Kitchen2.png", (char) ('0' + 383), 40, 60), // Fireplace
	
	// Tables
	tableR("Cropped\\Landscape\\Table1.png", (char) ('R' + 383), 0, 0),
	tableT("Cropped\\Landscape\\Table2.png", (char) ('T' + 383), 0, 0),
	tableY("Cropped\\Landscape\\Table3.png", (char) ('Y' + 383), 0, 0),
	tableF("Cropped\\Landscape\\Table6.png", (char) ('F' + 383), 0, 0),
	tableG("Cropped\\Landscape\\Table7.png", (char) ('G' + 383), 0, 0),
	tableH("Cropped\\Landscape\\Table8.png", (char) ('H' + 383), 0, 0),
	tableV("Cropped\\Landscape\\Table11.png", (char) ('V' + 383), 0, 0),
	tableB("Cropped\\Landscape\\Table12.png", (char) ('B' + 383), 0, 0),
	tableN("Cropped\\Landscape\\Table13.png", (char) ('N' + 383), 0, 0),
	tablet("Cropped\\Landscape\\Table4.png", (char) ('t' + 383), 0, 0),
	tablef("Cropped\\Landscape\\Table9.png", (char) ('f' + 383), 0, 0),
	tableg("Cropped\\Landscape\\Table14.png", (char) ('g' + 383), 0, 0),
	tableh("Cropped\\Landscape\\Table10.png", (char) ('h' + 383), 0, 0),
	tableb("Cropped\\Landscape\\Table5.png", (char) ('b' + 383), 0, 0),
	;
	
	private Image img;
	private char key;
	private Point offset;
	private File imageFile;
	
	Landscape(String filePath, char key, int xshift, int yshift) { // Image, char
		imageFile = new File(filePath);
		
		try {
			this.img = ImageIO.read(imageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.key = key;
		
		offset = new Point(xshift, yshift);
	}
	
	/**
	 * Changes the colours of the image to make it appear like night time.
	 */
	public static void desaturate() {
		for (Landscape l : Landscape.values()) {
			// Convert to a buffered image
			BufferedImage b = (BufferedImage) l.getImage();
			RescaleOp rescaleOp;
			rescaleOp = new RescaleOp(1f, -200, null);
			rescaleOp.filter(b, b); // Source and destination are the same
		}
	}
	
	/**
	 * Reset the images by re-reading them.
	 */
	public static void daytime() {
		for (Landscape l : Landscape.values()) {
			try {
				l.img = ImageIO.read(l.imageFile);
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
	
	public Point getOffset() {
		return offset;
	}
}
