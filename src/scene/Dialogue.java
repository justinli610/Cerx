package scene;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class Dialogue extends JPanel implements KeyListener, Event {
	private boolean finished = false;
	
	public boolean isFinished() {
		return finished;
	}

	@Override
	public void execute() {
		// Display the panel
		
		// signals for the next event to activate
		next();
	}

	@Override
	public void next() {
		finished = true;
		notify();
	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_X)
			next();
	}

}
