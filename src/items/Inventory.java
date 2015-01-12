package items;

import java.io.Serializable;
import java.util.ArrayList;

public class Inventory implements Serializable {
	private static final long serialVersionUID = -3371602423496558041L;
	
	private static ArrayList<EquippableItem> equipInventory;
	private static ArrayList<Item> itemInventory;
	
	private static Inventory inventory;
	
	/**
	 * Constructs an inventory with a default list of items and equipment.
	 */
	private Inventory() {
		// Initialise stuff
	}
	
	// Initiailise this
	public static Inventory getInventory() {
		return inventory;
	}
	
	public static void loadInventory(Inventory inventory) {
		Inventory.inventory = inventory;
	}
}
