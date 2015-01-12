package battleComponents;

public enum Element {
	FIRE("Fire", 0), ICE("Ice", 1), LIGHTNING("Lightning", 2), 
	WATER("Water", 3), WIND("Wind", 4), EARTH("Earth", 5);
	
	String name;
	int index;
	
	Element(String name, int index) {
		this.name = name;
		this.index = index;
	}
	
	public String toString() {
		return name;
	}

	public int getIndex() {
		return index;
	}
}
