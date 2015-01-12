package battleComponents;

public enum Magic implements CommandFormat {
	SCAN		("Scan",		1,		0.0,	null,				TargetType.SINGLE,	DmgType.NONE_NEG,	new Status[] {Status.SCAN},																			null),
	FIRE		("Fire", 		4, 		1.0, 	Element.FIRE, 		TargetType.SINGLE, 	DmgType.MAGICAL, 	null, 	null), 
	FIRA		("Fira", 		12, 	2.4, 	Element.FIRE, 		TargetType.SINGLE, 	DmgType.MAGICAL,	null, 	null),
	FIRAGA		("Firaga",		36,		4.8,	Element.FIRE,		TargetType.SINGLE,	DmgType.MAGICAL,	null,	null),
	FIRAJA		("Firaja",		102,	7.6,	Element.FIRE,		TargetType.SINGLE,	DmgType.MAGICAL,	null,	null),
	BLIZZARD	("Blizzard", 	4, 		1.0, 	Element.ICE, 		TargetType.SINGLE, 	DmgType.MAGICAL,	null,	null),
	THUNDER		("Thunder", 	4, 		1.0, 	Element.LIGHTNING, 	TargetType.SINGLE, 	DmgType.MAGICAL,	null,	null),
	WATER		("Water", 		4, 		1.0, 	Element.WATER, 		TargetType.SINGLE, 	DmgType.MAGICAL,	null,	null),
	CURE		("Cure", 		8, 		1.0, 	null, 				TargetType.SINGLE, 	DmgType.HEAL,		null,	null),
	
	RUIN		("Ruin",		4,		0.5,	null,				TargetType.SINGLE,	DmgType.MP_MAGICAL,	null,	null),
	
	BIO			("Bio",			12,		0.0,	null,				TargetType.SINGLE,	DmgType.NONE_NEG,
				new Status[] {Status.POISON},															new int[] {100}),
	SILENCE		("Silence", 	12, 	0.0, 	null, 				TargetType.SINGLE, 	DmgType.NONE_NEG,
				new Status[] {Status.SILENCE}, 															new int[] {100});
	
	private String name;
	private int MP;
	private double dmgConst;
	private TargetType target;
	private Element element;
	private DmgType type;
	private Status[] status;
	private int[] statusAcc;
	
	Magic(String name, int MP, double dmgConst, Element elem, TargetType target, DmgType type, Status[] status, int[] accuracy) {
		setName(name);
		setMPCost(MP);
		setDmgConst(dmgConst);
		setTarget(target);
		setElement(elem);
		setDamageType(type);
		setStatus(status);
		setAccuracy(accuracy);
	}
	
	public double getDmgConst() {
		return dmgConst;
	}

	private void setDmgConst(double dmgConst) {
		this.dmgConst = dmgConst;
	}
	
	@Override
	public TargetType getTargetType() {
		return target;
	}
	
	private void setTarget(TargetType target) {
		this.target = target;
	}

	@Override
	public int getMPCost() {
		return MP;
	}
	
	private void setMPCost(int MP) {
		this.MP = MP;
	}
	
	public Element getElement() {
		return element;
	}
	
	private void setElement(Element element) {
		this.element = element;
	}

	@Override
	public DmgType getDamageType() {
		return type;
	}
	
	private void setDamageType(DmgType type) {
		this.type = type;
	}

	public Status[] getStatus() {
		return status;
	}

	private void setStatus(Status[] status) {
		this.status = status;
	}
	
	public int[] getAccuracy() {
		return statusAcc;
	}

	private void setAccuracy(int[] accuracy) {
		this.statusAcc = accuracy;
	}

	@Override
	public String toString() {
		return name;
	}
	
	private void setName(String name) {
		this.name = name;
	}
}
