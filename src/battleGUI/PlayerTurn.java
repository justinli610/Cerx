package battleGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import battleComponents.BattleTarget;
import battleComponents.Character;
import bestiary.Monster;

public class PlayerTurn implements Runnable {
	private BattleScreen screen;
	
	private Character target;
	private BattleTarget[] participants;
	
	public PlayerTurn(Character target, BattleTarget[] participants, BattleScreen screen) {
		this.target = target;
		this.participants = participants;
		this.screen = screen;
	}

	@Override
	public void run() {
		target.startTurn();
		
		// Provides a highlight around the active Character
		screen.getBattleField().repaint();
		
		screen.getBattleField().stepForward(target);
		
		// Enable the controls
		screen.setCommandsEnabled(true);
	}
}
