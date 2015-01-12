package battleGUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import battleComponents.BattleTarget;

/**
 * 
 * Handles events for the ATB bars.
 *
 */
public class ATB extends JProgressBar {
	private static ArrayList<ATB> currentATBs = new ArrayList<ATB>(6);
	private static final Color ACTIVE = UIManager.getColor("ProgressBar.foreground");
	private static final Color FULL = new Color(184, 204, 163);
	private static final Color SLOW = new Color(76, 108, 138);
	// Haste
	// Stop
	
	private BattleTarget owner;
	private BattleScreen screen;
	
	private final Timer worker;
	private double speed;
	
	public static void clearATBList() {
		currentATBs.clear();
		currentATBs.ensureCapacity(6);
	}
	
	public static void stopATBs() {
		for (int i = 0; i < currentATBs.size(); i++) {
			currentATBs.get(i).getTimer().stop();
		}
	}
	
	/**
	 * Resumes the ATB gauges, and resets any gauge that may be full.
	 */
	public static void startATBs() {
		for (ATB atb : currentATBs) {
			if (atb.getPercentComplete() == 1) {
				atb.setForeground(ACTIVE);
				atb.setValue(0);
			}
			
			if (!atb.screen.isWin() && !atb.screen.isGameOver() && atb.owner.isActive())
				atb.getTimer().start();
		}
	}
	
	/**
	 * Instructs the ATB gauges to check if their owners are still active. If not,
	 * the ATB gauge will stop.
	 */
	public static void verifyAlive() {
		for (ATB atb : currentATBs) {
			if (atb.owner.isActive() && !atb.getTimer().isRunning())
				atb.getTimer().start();
			else if (!atb.owner.isActive()) {
				atb.getTimer().stop();
				atb.setBackground(Color.gray);
				atb.setValue(0);
				atb.revalidate();
			}
		}
	}
	
	public ATB(BattleTarget b, BattleScreen screen) {
		// Register this component
		currentATBs.add(this);
		this.screen = screen;
		owner = b;
		
		// Set properties
		getModel().setMinimum(0);
		getModel().setMaximum(100);
		getModel().setValue((int) (owner.getStats().getAgility() * Math.random()));
		
		// Figure out the speed
		speed = (255 - owner.getStats().getAgility());
		speed = Math.pow((-owner.getStats().getAgility()+255), 1.3)/50;
		
		worker = new Timer((int) (speed), new ATBRefiller());
		worker.start();
	}
	
	public Timer getTimer() {
		return worker;
	}

	/**
	 * Resets the ATB's colour to the default value.
	 */
	public void resetForeground() {
		setForeground(ACTIVE);
	}
	
	public void slow() {
		setForeground(SLOW);
	}

	/**
	 * 
	 * Fills the ATB meter.
	 * 
	 */
	class ATBRefiller implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (ATB.this.getPercentComplete() != 1) {
				getModel().setValue((int) (getModel().getValue() + 1));
			}
			
			if (ATB.this.getPercentComplete() == 1) {
				stopATBs();
				
				ATB.this.setForeground(FULL);
				repaint();
				
				screen.requestTurn(owner);
				
				// ATB gauges are restarted in EnemyTurn and PlayerTurn
			}
			
			if (screen.isWin() || screen.isGameOver()) {
				stopATBs();
			}
		}
	}
	
}
