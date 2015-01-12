package battleGUI;

import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BattleModel {
	private Image[] forms;
	private Point statusbox;
	private Point damagebox;
	
	public BattleModel(String filePath, int xHitbox, int yHitbox) {
		forms = new Image[1];
		
		try {
			forms[0] = ImageIO.read(new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setHitbox(new Point(xHitbox, yHitbox));
	}

	/**
	 * Retrieves the location of the hitbox.
	 * @return a Point representing the hitbox
	 */
	public Point getHitbox() {
		return statusbox;
	}

	/**
	 * Called by each BattleTarget subclass.
	 * @param hitbox
	 */
	public void setHitbox(Point hitbox) {
		this.statusbox = hitbox;
	}
	
	public Point getDamagebox() {
		return damagebox;
	}

	public void setDamagebox(Point damagebox) {
		this.damagebox = damagebox;
	}

	public Image getImage() {
		return forms[0];
	}
}
