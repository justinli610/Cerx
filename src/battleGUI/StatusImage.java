package battleGUI;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * 
 * Contains the image icons that BattleTargets display when they are under a status effect.
 *
 */
public enum StatusImage {
	poison("Misc\\Poison.gif"), silence("Misc\\Silence.gif"), blind("Misc\\Darkness.gif"),
	sleep("Misc\\Sleep.gif");
	
	private ImageIcon image;
	private Image staticImage;
	
	StatusImage(String filePath) {
		image = new ImageIcon(filePath);
		try {
			staticImage = ImageIO.read(new File(filePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ImageIcon getImageIcon() {
		return image;
	}
	
	public Image getImage() {
		return staticImage;
	}
}
