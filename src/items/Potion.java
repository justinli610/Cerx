package items;

import javax.swing.ImageIcon;

import battleComponents.BattleTarget;

public class Potion extends Item implements Usable {
	private static final long serialVersionUID = 2990979843154902331L;
	
	private static int count = 0;
	private static final Potion potion = new Potion(true);
	
	public boolean used = false;
	
	@Override
	public int getCount() {
		return count;
	}

	/**
	 * Objects should ONLY be created this way if they are to be added to the
	 * inventory. Monsters should use getInstance();
	 */
	public Potion() {
		count++;
	}
	
	/**
	 * A constructor that will not increment the count.
	 * @param b - no function.
	 */
	private Potion(boolean b) {}
	
	@Override
	public void use(BattleTarget[] targets) {
		// TODO Implement usage
		consume();
	}

	@Override
	protected String getName() {
		return "Potion";
	}

	@Override
	public void consume() {
		count--;
		
		used = true;
	}

	public static Potion getInstance() {
		return potion;
	}

	@Override
	public String getDescription() {
		return "Heals 200HP.";
	}

	@Override
	public ImageIcon getIcon() {
		return new ImageIcon();
	}
}
