package items;

import javax.swing.ImageIcon;

import battleComponents.StatPackage;

public class Sword extends EquippableItem {
	private static final long serialVersionUID = 1320854803154458162L;
	
	private static Sword sword = new Sword();
	private static int count;
	private static boolean created;

	public Sword() {
		this(true);
		count++;
	}
	
	private Sword(boolean b) {
		super(new StatPackage(0, 0, 0, 0, 0, 0, 0, 0));
	}

	@Override
	public int getCount() {
		return count;
	}

	@Override
	public void consume() {
		count--;
	}

	@Override
	protected String getName() {
		return "Sword";
	}

	@Override
	public String getDescription() {
		return "A simple sword. Nothing remarkable.";
	}

	@Override
	public ImageIcon getIcon() {
		return new ImageIcon();
	}

}
