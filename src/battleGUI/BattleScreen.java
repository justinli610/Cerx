package battleGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import battleComponents.BattleTarget;
import battleComponents.Character;
import battleComponents.Magic;
import battleComponents.Character.Blitz;
import bestiary.Arkin;
import bestiary.BabyBehemoth;
import bestiary.JovianHound;
import bestiary.Monster;
import bestiary.Roevin;
import bestiary.Strident;
import bestiary.Thunderbird;
import field.GameState;
import field.Environment;

public class BattleScreen extends JPanel {
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	
	private BattleField battleField;
	private Infobar infoBar;
	
	private ParticipantPanel[] partyPanels, enemyPanels;
	private BattleMenuPanel commandPanel;
	private TargetSelectionMenu attackSelection;
	
	private BattleTarget[] participants;
	private volatile boolean gameOver = false, win = false;

	/**
	 * Create the panel.
	 */
	public BattleScreen(Character[] party, Monster[] enemies) {
		setBackground(new Color(139, 69, 19));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{2.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		// Create the info bar
		infoBar = new Infobar();
		
		battleField = new BattleField(party, enemies);
		battleField.setInfoBar(infoBar);
		GridBagConstraints gbc_battleField = new GridBagConstraints();
		gbc_battleField.gridwidth = 3;
		gbc_battleField.insets = new Insets(0, 0, 5, 0);
		gbc_battleField.fill = GridBagConstraints.BOTH;
		gbc_battleField.gridx = 0;
		gbc_battleField.gridy = 0;
		add(battleField, gbc_battleField);
		
		// Set background colour
//		if (GameState.getGameState().getCurrentLocation().getSetting().time == Setting.NIGHT)
//			battleField.setBackgroundColour(new Color(14, 0, 59));
		
		commandPanel = new BattleMenuPanel(this);
		commandPanel.setEnabled(false);
		GridBagConstraints gbc_commandPanel = new GridBagConstraints();
		gbc_commandPanel.insets = new Insets(0, 5, 0, 5);
		gbc_commandPanel.fill = GridBagConstraints.BOTH;
		gbc_commandPanel.gridx = 0;
		gbc_commandPanel.gridy = 1;
		add(commandPanel, gbc_commandPanel);
		
		// Create the party roster panel
		JPanel characterRosterPanel = new JPanel();
		GridBagConstraints gbc_characterRosterPanel = new GridBagConstraints();
		gbc_characterRosterPanel.insets = new Insets(0, 0, 0, 5);
		gbc_characterRosterPanel.fill = GridBagConstraints.BOTH;
		gbc_characterRosterPanel.gridx = 1;
		gbc_characterRosterPanel.gridy = 1;
		add(characterRosterPanel, gbc_characterRosterPanel);
		characterRosterPanel.setLayout(new BoxLayout(characterRosterPanel, BoxLayout.Y_AXIS));
		
		// Create panels for each of the party members
		ParticipantPanel[] partyPanels = new ParticipantPanel[party.length];
		this.partyPanels = partyPanels;
		
		for (int i = 0; i < partyPanels.length; i++) {
			partyPanels[i] = new ParticipantPanel(party[i], this);
			characterRosterPanel.add(partyPanels[i]);
		}
		
		// Create the enemy roster panel
		JPanel enemyRosterPanel = new JPanel();
		GridBagConstraints gbc_enemyRosterPanel = new GridBagConstraints();
		gbc_enemyRosterPanel.insets = new Insets(0, 0, 0, 5);
		gbc_enemyRosterPanel.fill = GridBagConstraints.BOTH;
		gbc_enemyRosterPanel.gridx = 2;
		gbc_enemyRosterPanel.gridy = 1;
		add(enemyRosterPanel, gbc_enemyRosterPanel);
		enemyRosterPanel.setLayout(new BoxLayout(enemyRosterPanel, BoxLayout.Y_AXIS));
		
		// Create panels for each of the enemies
		ParticipantPanel[] enemyPanels = new ParticipantPanel[enemies.length];
		this.enemyPanels = enemyPanels;
		
		for (int i = 0; i < enemyPanels.length; i++) {
			enemyPanels[i] = new ParticipantPanel(enemies[i], this);
			enemyRosterPanel.add(enemyPanels[i]);
		}
		
		// Create the list of participants
		participants = new BattleTarget[party.length + enemies.length];
		
		for (int i = 0; i < party.length; i++) {
			participants[i] = party[i];
		}
		for (int i = 0; i < enemies.length; i++) {
			participants[i + party.length] = enemies[i];
		}
		
		// Create the TargetSelectionMenu
		attackSelection = new TargetSelectionMenu(null, this, null);
		
		setPreferredSize(new Dimension(1200, 720));
		setKeyBindings();
	}
	
	/**
	 * Updates the ParticpantPanel displays.
	 */
	public void update() {
		for (ParticipantPanel p : partyPanels)
			p.update();
		
		for (ParticipantPanel p : enemyPanels)
			p.update();
	}
	
	/**
	 * Enables and disables the command panel.
	 * @param enabled - the state of the command panel.
	 */
	public void setCommandsEnabled(boolean enabled) {
		commandPanel.setEnabled(enabled);
		requestFocus();
	}
	
	public Infobar getInfoBar() {
		return infoBar;
	}

	public BattleField getBattleField() {
		return battleField;
	}

	public BattleTarget[] getParticipants() {
		return participants;
	}

	/**
	 * Puts the request for a turn in a queue.
	 * @param target - the BattleTarget that requested a turn.
	 */
	public void requestTurn(BattleTarget target) {
		if (target instanceof Monster) {
			executor.submit(new EnemyTurn((Monster) target, participants, this));
		} else {
			commandPanel.setOwner((Character) target);
			executor.submit(new PlayerTurn((Character) target, participants, this));
		}
	}

	/**
	 * Called at the end of each BattleTarget's turn to check if the
	 * battle should continue.
	 */
	public void checkBattleOver() {
		gameOver = true;
		win = true;
		
		for (int i = 0; i < participants.length; i++) {
			try {
				if (participants[i].isActive()) {
					if (participants[i] instanceof Character)
						gameOver = false;
					else
						win = false;
				}
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		
		if (gameOver || win) {
			ATB.stopATBs();
			ATB.clearATBList();
			executor.shutdownNow();
			
			// Clear Monster instance counts
			for (BattleTarget t : participants) {
				if (t instanceof Monster)
					((Monster) t).resetInstanceCount();
			}
			
			// Set the HUD invisible after a moment
			Timer t = new Timer(1000, new BackToField());
			t.setRepeats(false);
			t.start();

			if (win) {
				// Play victory music

				// Switch to EXP reward screen
			}
		}
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public boolean isWin() {
		return win;
	}
	
	class BackToField implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (win)
				GameState.getGameState().endBattle();
			else
				GameState.getGameState().displayGameOver();
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////
	// Key Binding Code

	private void setKeyBindings() {
		InputMap input;
		ActionMap action;
		
		input = getInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		action = getActionMap();
		
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "Scroll up");
		action.put("Scroll up", new UpAction());
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "Scroll down");
		action.put("Scroll down", new DownAction());
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, 0), "Select");
		action.put("Select", new SelectAction());
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "Skip");
		action.put("Skip", new SkipAction());
	}
	
	/**
	 * 
	 * Handles key bindings to the main action menu.
	 *
	 */
	class UpAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			// Only do something if the panel is enabled
			if (commandPanel.isEnabled()) {
				commandPanel.requestFocusInWindow();
				
				commandPanel.increaseIndex();
			}
		}
		
	}
	
	/**
	 * 
	 * Handles key bindings to the main action menu.
	 *
	 */
	class DownAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			// Only do something if the panel is enabled
			if (commandPanel.isEnabled()) {
				commandPanel.requestFocusInWindow();
				
				commandPanel.decreaseIndex();
			}
		}
	}
	
	class SelectAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			// Must be a character's turn in order to make a move
			if (commandPanel.isEnabled()) {
				// Bring up a pop-up window
				if (commandPanel.getSelectedText().equals("Attack")) {
					attackSelection.setOwner(commandPanel.getOwner());
					attackSelection.setAction("Attack");
					attackSelection
							.displayMenu(commandPanel.getOwner().new Attack());
					attackSelection.show(BattleScreen.this, 10, getHeight()
							- attackSelection.getPreferredSize().height - 10);
					attackSelection.requestFocusInWindow();
				} else if (commandPanel.getSelectedText().equals("Magic"))
					commandPanel.showAbilityPanel(AbilitySelectionPanel.MAGIC);
				else if (commandPanel.getSelectedText().equals("Ability"))
					commandPanel
							.showAbilityPanel(AbilitySelectionPanel.ABILITY);
				commandPanel.requestAbilityFocus();
			}
		}
	}
	
	class SkipAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (commandPanel.isEnabled()) {
				commandPanel.setEnabled(false);
				commandPanel.getOwner().endTurn();
				
				getBattleField().stepBackward(commandPanel.getOwner());
				
				ATB.verifyAlive();
				ATB.startATBs();
			}
		}
	}

	public static void main(String[] args) {
		Monster b1 = new Thunderbird();
		Monster b3 = new Strident();
		//BabyBehemoth b2 = new BabyBehemoth();
		
		Arkin.ARKIN.learn(Arkin.ARKIN.new Blitz());
		Arkin.ARKIN.learn(Magic.RUIN);
		Roevin.ROEVIN.learn(Magic.BIO);
		Roevin.ROEVIN.learn(Magic.FIRE);
		Roevin.ROEVIN.learn(Magic.FIRA);
		Roevin.ROEVIN.learn(Magic.FIRAGA);
		Roevin.ROEVIN.learn(Magic.BLIZZARD);
		Roevin.ROEVIN.learn(Magic.CURE);
		Roevin.ROEVIN.learn(Magic.SCAN);
		Roevin.ROEVIN.learn(Magic.SILENCE);
		Roevin.ROEVIN.learn(Magic.THUNDER);
		Roevin.ROEVIN.learn(Magic.WATER);
		
		BattleScreen screen = new BattleScreen(new Character[] {Arkin.ARKIN, Roevin.ROEVIN}, 
				new Monster[] {b1, b3});
		
		JFrame f = new JFrame();
		f.add(screen);
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
}
