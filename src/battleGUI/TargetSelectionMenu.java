package battleGUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.InputMap;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;

import ui.SingleTargetRenderer;
import ui.StandardRenderer;
import battleComponents.Ability;
import battleComponents.BattleTarget;
import battleComponents.Character;
import battleComponents.CommandFormat;
import battleComponents.DmgType;
import battleComponents.Magic;
import battleComponents.TargetType;
import bestiary.Monster;

public class TargetSelectionMenu extends JPopupMenu {
	private static final int ROW_COUNT = 5;
	
	private BattleScreen screen;
	private AbilitySelectionPanel abilityPanel;
	
	private Character owner;
	
	private JLabel actionName;
	private JList<BattleTarget> targetList;
	private JList<String> groupTargetList;
	private SingleTargetRenderer singleRenderer;

	private JScrollPane listScroller;
	
	public TargetSelectionMenu(AbilitySelectionPanel abilityPanel, BattleScreen screen, Character owner) {
		this.screen = screen;
		this.abilityPanel = abilityPanel;
		this.owner = owner;
		
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(160, 82, 45), new Color(128, 0, 0)));
		setBackground(Color.WHITE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		actionName = new JLabel("Target");
		actionName.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
		GridBagConstraints gbc_actionName = new GridBagConstraints();
		gbc_actionName.anchor = GridBagConstraints.WEST;
		gbc_actionName.insets = new Insets(0, 10, 5, 0);
		gbc_actionName.gridx = 0;
		gbc_actionName.gridy = 0;
		add(actionName, gbc_actionName);
		
		singleRenderer = new SingleTargetRenderer();
		
		targetList = new JList<BattleTarget>() {
			@Override
			public void addMouseListener(MouseListener m) {}
			@Override
			public void addMouseMotionListener(MouseMotionListener m) {}
		};
		targetList.setCellRenderer(singleRenderer);
		targetList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		targetList.setVisibleRowCount(ROW_COUNT);
		
		groupTargetList = new JList<String>() {
			@Override
			public void addMouseListener(MouseListener m) {}
			@Override
			public void addMouseMotionListener(MouseMotionListener m) {}
		};
		groupTargetList.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 16));
		groupTargetList.setCellRenderer(new StandardRenderer());
		groupTargetList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		groupTargetList.setVisibleRowCount(ROW_COUNT);
		
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.insets = new Insets(0, 5, 5, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 0;
		gbc_list.gridy = 1;
		
		listScroller = new JScrollPane(targetList);
		listScroller.setBackground(new Color(255, 244, 215));
		listScroller.getViewport().setBackground(new Color(255, 244, 215));
		add(listScroller, gbc_list);
		
		// Key bindings
		addKeyBindings();
		
		setFocusable(true);
		setPreferredSize(new Dimension(250, 200));
	}
	
	/**
	 * Must accept the CommandFormat to decide which cell renderer to use.
	 * @param command - the CommandFormat being selected
	 */
	public void displayMenu(CommandFormat command) {
		TargetType type = command.getTargetType();
		
		if (type == TargetType.SINGLE) {
			fillList();
			listScroller.getViewport().setView(targetList);
		} else if (type == TargetType.ALL || type == TargetType.GROUP) {
			listScroller.getViewport().setView(groupTargetList);
			fillGroupList(type);
		}
	}
	
	public void setOwner(Character owner) {
		this.owner = owner;
	}
	
	/**
	 * Populates the JList with fixed choices.
	 * @param type
	 */
	private void fillGroupList(TargetType type) {
		DefaultListModel<String> model = new DefaultListModel<String>();
		
		if (type == TargetType.ALL) {
			model.add(0, "All Participants");
		} else { // Group
			model.add(0, "Allies");
			model.add(0, "Enemies");
		}
		
		groupTargetList.setModel(model);
		setIntelligentIndex(groupTargetList);
	}

	/**
	 * Populates the JList with choices.
	 */
	private void fillList() {
		DefaultListModel<BattleTarget> model = new DefaultListModel<BattleTarget>();
		
		for (int i = 0; i < screen.getParticipants().length; i++) {
			model.add(i, screen.getParticipants()[i]);
		}
		
		targetList.setModel(model);
		setIntelligentIndex(targetList);
	}
	
	/**
	 * Chooses a reasonable initial target depending on the ability chosen.
	 * @param list - Either targetList or groupTargetList
	 */
	private void setIntelligentIndex(JList list) {
		DmgType type;
		boolean found = false;
		
		// Find the attack type
		if (abilityPanel != null)
			type = abilityPanel.getSelectedCommand().getDamageType();
		else
			type = DmgType.PHYSICAL;
		
		if (list == targetList) {
			targetList.setSelectedIndex(0); // Ensure an index gets selected
			
			if (type.isOffensive()) {
				for (int i = 0; i < targetList.getModel().getSize() && !found; i++)
					if (targetList.getModel().getElementAt(i) instanceof Monster && // Is Monster
							targetList.getModel().getElementAt(i).isActive()) { // Is alive
						targetList.setSelectedIndex(i);
						found = true;
					}
			} else if (type == DmgType.HEAL || type == DmgType.MP_HEAL) { // Heal
				for (int i = 0; i < targetList.getModel().getSize() && !found; i++)
					if (targetList.getModel().getElementAt(i) instanceof Character && // Is Character
							targetList.getModel().getElementAt(i).isActive()) { // Is alive
						targetList.setSelectedIndex(i);
						found = true;
					}
			} else { // Reviving
				for (int i = 0; i < targetList.getModel().getSize() && !found; i++)
					if (targetList.getModel().getElementAt(i) instanceof Character && // Is Character
							!targetList.getModel().getElementAt(i).isActive()) { // Is dead
						targetList.setSelectedIndex(i);
						found = true;
					}
			}
		} else { // targetList
			groupTargetList.setSelectedIndex(0); // Ensure an index gets selected
			if (groupTargetList.getModel().getSize() == 1)
				groupTargetList.setSelectedIndex(0);
			else {
				if (type.isOffensive())
					groupTargetList.setSelectedIndex(0);
				else
					groupTargetList.setSelectedIndex(1);
			}
		}
	}

	private void addKeyBindings() {
		InputMap input = targetList.getInputMap(WHEN_IN_FOCUSED_WINDOW);
		ActionMap action = targetList.getActionMap();
		
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "Cancel");
		action.put("Cancel", new CancelAction());
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, 0), "Accept");
		action.put("Accept", new AcceptAction());
		
		InputMap input2 = groupTargetList.getInputMap(WHEN_IN_FOCUSED_WINDOW);
		ActionMap action2 = groupTargetList.getActionMap();
		
		input2.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "Cancel");
		action2.put("Cancel", new CancelAction());
		input2.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, 0), "Accept");
		action2.put("Accept", new AcceptAction());
	}

	/**
	 * Sets the name of the action on this panel.
	 * @param command - the action to take
	 */
	public void setAction(CommandFormat command) {
		actionName.setText(command.toString());
	}
	
	/**
	 * Sets the name of the action on this panel.
	 * @param s - the string to display
	 */
	public void setAction(String s) {
		actionName.setText(s);
	}
	
	@Override
	public boolean requestFocusInWindow() {
		return listScroller.getViewport().getView().requestFocusInWindow();
	}
	

	private void takeTurn() {
		// Hide the menus
		setVisible(false);
		// Disable commands
		screen.setCommandsEnabled(false);
		if (abilityPanel != null)
			abilityPanel.setVisible(false);
		
		String info = actionName.getText();
		
		// Display the action name
		if (!info.equals("Attack")) {
			screen.getInfoBar().setText(info);
		}
		
		// Figure out the targets
		BattleTarget[] targets;
		JList list = (JList) listScroller.getViewport().getView();
		
		if (list == targetList) {
			targets = new BattleTarget[] {targetList.getSelectedValue()};
		} else if (list.getSelectedValue().equals("All Participants")){
			targets = screen.getParticipants();
		} else if (list.getSelectedValue().equals("Allies")) {
			int total = 0;
			
			// Find number of party members
			for (int i = 0; i < screen.getParticipants().length; i++) {
				if (screen.getParticipants()[i] instanceof Character)
					total++;
			}
			
			// Initialise
			targets = new BattleTarget[total];
			
			total = 0; // Becomes index
			// Fill the array
			for (int i = 0; i < screen.getParticipants().length; i++) {
				if (screen.getParticipants()[i] instanceof Character)
					targets[total++] = screen.getParticipants()[i]; 
			}
		} else {
			int total = 0;
			
			// Find number of party members
			for (int i = 0; i < screen.getParticipants().length; i++) {
				if (screen.getParticipants()[i] instanceof Monster)
					total++;
			}
			
			// Initialise
			targets = new BattleTarget[total];
			
			total = 0; // Becomes index
			// Fill the array
			for (int i = 0; i < screen.getParticipants().length; i++) {
				if (screen.getParticipants()[i] instanceof Monster)
					targets[total++] = screen.getParticipants()[i]; 
			}
		}
		
		if (!info.equals("Attack")) {
			Timer t = new Timer(1000, new ActionExecuter(targets));
			t.setRepeats(false);
			t.start();
		} else {
			executeAction(targets);
		}
	}

	private void executeAction(BattleTarget[] targets) {
		// Execute the action
		CommandFormat action;
		
		// Figure out the ability
		if (abilityPanel != null)
			action = abilityPanel.getSelectedCommand();
		else
			action = owner.new Attack();
		
		if (action instanceof Magic) {
			owner.cast((Magic) action, targets);
		} else if (action instanceof Ability) {
			Ability ability = (Ability) action;
			ability.execute(targets);
		}
		
		// Log the actions by displaying damage dealt
		for (BattleTarget bt : targets)
			screen.getBattleField().displayDamage(bt, bt.getDamageTaken(), bt.getDamageTakenType());
		
		owner.endTurn();
		// Allow poison to register
		screen.getBattleField().repaint();
		screen.update();
		
		screen.getBattleField().stepBackward(owner);
		
		ATB.verifyAlive();
		screen.checkBattleOver();
		ATB.startATBs();
	}

	/**
	 * 
	 * Hides the TargetSelectionMenu.
	 *
	 */
	class CancelAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			TargetSelectionMenu.this.setVisible(false);
			
			if (abilityPanel != null) {
				abilityPanel.setVisible(true);
				abilityPanel.requestFocusInWindow();
			}
		}
		
	}
	
	/**
	 * 
	 * Carries out the action selected.
	 *
	 */
	class AcceptAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			// Prevent targeting of dead targets.
			if (listScroller.getViewport().getView() == targetList) {
				if (abilityPanel != null) {
					if (targetList.getSelectedValue().isActive() || 
							abilityPanel.getSelectedCommand().getDamageType() == DmgType.REVIVE)
						takeTurn();
				} else if (abilityPanel == null && targetList.getSelectedValue().isActive())
					takeTurn();
			} else // Using groupTargetList
				takeTurn();
		}
	}
	
	/**
	 * 
	 * Provides a delay before executing the action.
	 *
	 */
	class ActionExecuter implements ActionListener {
		private BattleTarget[] targets;
		
		public ActionExecuter(BattleTarget[] targets) {
			this.targets = targets;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			executeAction(targets);
		}
		
	}
}
