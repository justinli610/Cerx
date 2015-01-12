package battleComponents;

public interface CommandFormat {
	public int getMPCost();
	public String toString();
	public TargetType getTargetType();
	public DmgType getDamageType();
}
