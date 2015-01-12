package battleGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.DefaultListModel;
import javax.swing.InputMap;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;

import battleComponents.Character;
import battleComponents.CommandFormat;
import ui.AbilityRenderer;

/**
 * 
 * Renders the menus for selection of CommandFormat objects. Cannot
 * render Items.
 *
 */
public class AbilitySelectionPanel extends JPopupMenu {
	private static final int ROW_COUNT = 5;
	public static final int MAGIC = 1, ABILITY = 2;
	
	private Character owner;
	private AbilityRenderer renderer;
	private int mode;
	private JList<CommandFormat> commandList;
	
	private TargetSelectionMenu targetSelection;
	
	private BattleScreen screen;
	
	/**
	 * Create the selection menu with an owner set.
	 * @param owner - the Character whose turn it is
	 */
	public AbilitySelectionPanel(BattleScreen screen, Character owner) {
		this(screen);
		
		setOwner(owner);
	}
	
	/**
	 * Create the selection menu. The owner and mode must be defined before this panel is displayed.
	 */
	public AbilitySelectionPanel(BattleScreen screen) {
		this.screen = screen;
		
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(160, 82, 45), new Color(128, 0, 0)));
		setBackground(Color.WHITE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblAbility = new JLabel("Ability");
		lblAbility.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
		GridBagConstraints gbc_lblAbility = new GridBagConstraints();
		gbc_lblAbility.anchor = GridBagConstraints.WEST;
		gbc_lblAbility.insets = new Insets(0, 10, 5, 5);
		gbc_lblAbility.gridx = 0;
		gbc_lblAbility.gridy = 0;
		add(lblAbility, gbc_lblAbility);
		
		JLabel lblMpCost = new JLabel("MP Cost");
		lblMpCost.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
		GridBagConstraints gbc_lblMpCost = new GridBagConstraints();
		gbc_lblMpCost.insets = new Insets(0, 0, 5, 10);
		gbc_lblMpCost.gridx = 1;
		gbc_lblMpCost.gridy = 0;
		add(lblMpCost, gbc_lblMpCost);
		
		renderer = new AbilityRenderer(null);
		
		commandList = new JList<CommandFormat>() {
			@Override
			public void addMouseListener(MouseListener m) {}
			@Override
			public void addMouseMotionListener(MouseMotionListener m) {}
		};
		commandList.setCellRenderer(renderer);
		commandList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		commandList.setVisibleRowCount(ROW_COUNT);
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.gridwidth = 2;
		gbc_list.insets = new Insets(0, 5, 5, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 0;
		gbc_list.gridy = 1;
		
		JScrollPane listScroller = new JScrollPane(commandList);
		listScroller.getViewport().setBackground(new Color(255, 244, 215));
		add(listScroller, gbc_list);
		
		targetSelection = new TargetSelectionMenu(this, screen, owner);
		
		setPreferredSize(new Dimension(250, 200));
		
		// Key bindings
		addKeyBindings();

	}
	
	/**
	 * Associates a new Character with this menu.
	 * @param owner
	 */
	public void setOwner(Character owner) {
		this.owner = owner;
		renderer.setOwner(owner);
	}
	
	/**
	 * Instructs the menu which abilities to show.
	 * @param mode - one of the class's integer constants
	 */
	public void setMode(int mode) {
		DefaultListModel<CommandFormat> model = new DefaultListModel<CommandFormat>();
		
		if (mode == ABILITY) {
			// Ensure list size is sufficient
			model.ensureCapacity(owner.getAbilities().length);
			
			// Add abilities in
			for (int i = 0; i < owner.getAbilities().length; i++)
				model.add(i, owner.getAbilities()[i]);
		} else if (mode == MAGIC) {
			// Ensure list size is sufficient
			model.ensureCapacity(owner.getSpells().length);

			// Add magic in
			for (int i = 0; i < owner.getSpells().length; i++)
				model.add(i, owner.getSpells()[i]);
		}
		
		commandList.setModel(model);
		commandList.setSelectedIndex(0);
	}
	
	public CommandFormat getSelectedCommand() {
		return commandList.getSelectedValue();
	}
	
	/**
	 * Makes the list request focus instead of the window.
	 */
	@Override
	public boolean requestFocusInWindow() {
		return commandList.requestFocusInWindow();
	}
	
	public void addKeyBindings() {
		InputMap input = commandList.getInputMap(WHEN_IN_FOCUSED_WINDOW);
		ActionMap action = commandList.getActionMap();
		
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "Cancel");
		action.put("Cancel", new CancelAction());
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, 0), "Accept");
		action.put("Accept", new AcceptAction());
	}
	
	/**
	 * 
	 * Hides the AbilitySelectionPanel.
	 *
	 */
	class CancelAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			AbilitySelectionPanel.this.setVisible(false);
		}
		
	}
	
	/**
	 * 
	 * Forwards the user to the target selection menu.
	 *
	 */
	class AcceptAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (getSelectedCommand().getMPCost() <= owner.getCurrMP()) {
				targetSelection.setOwner(owner);
				targetSelection.setAction(commandList.getSelectedValue());
				targetSelection.displayMenu(commandList.getSelectedValue());
				targetSelection.show(screen, 10, screen.getHeight() - targetSelection.getPreferredSize().height - 10);
				targetSelection.requestFocusInWindow();
			}
		}
		
	}
}
