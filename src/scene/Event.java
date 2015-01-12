package scene;

public interface Event {
	public void execute();
	
	public void next();
	
	public boolean isFinished();
}
