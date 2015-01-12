package field;

import java.io.Serializable;

public class Environment implements Serializable {
	private static final long serialVersionUID = -3102663976610113717L;
	
	public static final boolean DAY = false, NIGHT = true;
	public static final int NONE = 1, RAIN = 2, STORM = 3;
	
	public boolean time;
	public int weather;
	public int rainFrequency;
	
	public Environment(boolean time, int weather, int rainFrequency) {
		this.time = time;
		this.weather = weather;
		this.rainFrequency = rainFrequency;
	}
}