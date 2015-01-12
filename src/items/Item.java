package items;

import java.io.Serializable;

import javax.swing.ImageIcon;


public abstract class Item implements Serializable {
	private static final long serialVersionUID = 6567504892733047139L;
	
	private String description;
	private ImageIcon icon;
	
	public abstract int getCount();
	
	private int value;
	private int sellValue;
	
	public Item() {
		setDescription(getDescription());
		setIcon(getIcon());
	}
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getSellValue() {
		return sellValue;
	}
	
	public void setSellValue(int value) {
		sellValue = value;
	}
	
	/**
	 * Decreases the quantity counter. To be used internally only.
	 */
	public abstract void consume();
	
	public abstract String getDescription();

	private void setDescription(String description) {
		this.description = description;
	}
	
	public abstract ImageIcon getIcon();

	private void setIcon(ImageIcon icon){
		this.icon = icon;
	}

	public String toString() {
		return getName();
	}

	protected abstract String getName();
}
