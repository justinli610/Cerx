package battleGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import battleComponents.BattleTarget;
import bestiary.Monster;

/**
 * 
 * A thread that guides a BattleTarget through its turn.
 *
 */
public class EnemyTurn implements Runnable {
	private BattleScreen screen;
	
	private Monster target;
	private BattleTarget[] participants;
	
	public EnemyTurn(Monster target, BattleTarget[] participants, BattleScreen screen) {
		this.target = target;
		this.screen = screen;
		this.participants = participants;
	}
	
	@Override
	public void run() {
		String info;
		
		// Have the Enemy choose its action
		target.startTurn();
		info = target.chooseAction(participants);
		
		screen.getBattleField().repaint(); // Provides a highlight around the active Monster
		screen.getBattleField().stepForward(target);
		
		// Display the action name
		if (info != null) {
			screen.getInfoBar().setText(info);
		}
		
		// Wait a while
		Timer t = new Timer(1500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				takeAction();
				screen.getBattleField().stepBackward(target);
				target.endTurn();
				// Allow poison to register
				screen.getBattleField().repaint();
				screen.update();
				
				screen.checkBattleOver();
				ATB.startATBs();
			}
		});
		t.setRepeats(false);
		t.start();
	}

	/**
	 * Allows the Monster to perform the action it chose.
	 */
	private void takeAction() {
		target.autobattle();
		// Log the actions by displaying damage dealt
		for (BattleTarget bt : target.getFutureTargets())
			screen.getBattleField().displayDamage(bt, bt.getDamageTaken(), bt.getDamageTakenType());
		
		screen.update();
		screen.getBattleField().repaint();
		
		ATB.verifyAlive();
	}
}
