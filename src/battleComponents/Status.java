package battleComponents;

enum StatusType {
	BUFF, AUGMENTATION, DEBUFF, DEBILITATION, ESUNA, DISPEL, SCAN;
}

public enum Status {
	SCAN(-1, "Scan", StatusType.SCAN),
	POISON(0, "Poison", StatusType.DEBILITATION), SLEEP(1, "Sleep", StatusType.DEBILITATION),
	SILENCE(2, "Silence", StatusType.DEBILITATION), BLIND(3, "Blind", StatusType.DEBILITATION);
	
	private int index;
	private String name;
	private StatusType type;

	Status (int index, String name, StatusType type) {
		setIndex(index);
		setName(name);
		setType(type);
	}

	public int getIndex() {
		return index;
	}

	private void setIndex(int index) {
		this.index = index;
	}

	private void setName(String name) {
		this.name = name;
	}

	public StatusType getType() {
		return type;
	}

	private void setType(StatusType type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
