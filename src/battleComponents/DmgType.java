package battleComponents;

public enum DmgType {	
	MAGICAL(true, false), PHYSICAL(true, false), HEAL(false, false), SPECIAL(true, false),
	REVIVE(false, false), MP_MAGICAL(true, true), MP_PHYSICAL(true, true), MP_HEAL(false, true),
	MP_SPECIAL(true, true), POISON(true, false), NONE_POS(false, false), NONE_NEG(true, false);
	
	private boolean offensive;
	private boolean targetsMP;
	
	DmgType(boolean offensive, boolean targetsMP) {
		this.offensive = offensive;
		this.targetsMP = targetsMP;
	}

	public boolean isOffensive() {
		return offensive;
	}

	public boolean isTargetsMP() {
		return targetsMP;
	}
}
