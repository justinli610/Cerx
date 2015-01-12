package battleGUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.JToggleButton;

import battleComponents.Character;
import battleComponents.Status;
import ui.StyleConstants;

public class BattleMenuPanel extends JPanel {
	// Currently unnecessary, since all characters have
	// same main options
	private Character owner;
	
	private OptionButton[] options;
	
	private AbilitySelectionPanel abilityPanel;
	
	private int selectedIndex;

	/**
	 * Create the panel.
	 */
	public BattleMenuPanel(BattleScreen screen) {
		options = new OptionButton[4];
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.1, 0.1, 0.1, 0.1, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		options[0] = new OptionButton("Attack");
		options[0].setFont(StyleConstants.BATTLE_MENU_FONT);
		GridBagConstraints gbc_btnAttack = new GridBagConstraints();
		gbc_btnAttack.fill = GridBagConstraints.BOTH;
		gbc_btnAttack.gridx = 0;
		gbc_btnAttack.gridy = 0;
		add(options[0], gbc_btnAttack);
		
		options[1] = new OptionButton("Magic");
		options[1].setFont(StyleConstants.BATTLE_MENU_FONT);
		GridBagConstraints gbc_btnMagic = new GridBagConstraints();
		gbc_btnMagic.fill = GridBagConstraints.BOTH;
		gbc_btnMagic.gridx = 0;
		gbc_btnMagic.gridy = 1;
		add(options[1], gbc_btnMagic);
		
		options[2] = new OptionButton("Ability");
		options[2].setFont(StyleConstants.BATTLE_MENU_FONT);
		GridBagConstraints gbc_btnAbility = new GridBagConstraints();
		gbc_btnAbility.fill = GridBagConstraints.BOTH;
		gbc_btnAbility.gridx = 0;
		gbc_btnAbility.gridy = 2;
		add(options[2], gbc_btnAbility);
		
		options[3] = new OptionButton("Item");
		options[3].setFont(StyleConstants.BATTLE_MENU_FONT);
		GridBagConstraints gbc_btnItem = new GridBagConstraints();
		gbc_btnItem.fill = GridBagConstraints.BOTH;
		gbc_btnItem.gridx = 0;
		gbc_btnItem.gridy = 3;
		add(options[3], gbc_btnItem);
		
		options[0].setSelected(true);
		selectedIndex = 0;
		
		abilityPanel = new AbilitySelectionPanel(screen);
		setComponentPopupMenu(abilityPanel);
	}
	
	public void showAbilityPanel(int mode) {
		abilityPanel.setMode(mode);
		
		abilityPanel.show(getParent(), 10, getParent().getHeight() - abilityPanel.getPreferredSize().height - 10);
	}
	
	/**
	 * Transfers focus to the JList.
	 */
	public void requestAbilityFocus() {
		abilityPanel.requestFocusInWindow();
	}
	
	/**
	 * The BattleMenuPanel reflects the state of the owner. If the owner has no magic or
	 * abilities, those buttons will be disabled. If the owner is silenced, magic will be
	 * disabled.
	 * @param owner - the Character whose turn it is. No changes are made if the argument is null
	 */
	public synchronized void setOwner(Character owner) {
		if (owner != null) {
			this.owner = owner;
			abilityPanel.setOwner(owner);
			
			// Enable all the buttons first
			for (OptionButton button: options)
				button.setEnabled(true);
			
			// Disable magic and abilities if none are present
			if (owner.getAbilities().length == 0)
				options[2].setEnabled(false);
			if (owner.getSpells().length == 0)
				options[1].setEnabled(false);
			
			// Disable item button
			options[3].setEnabled(false);
			
			// Check for silence status
			if (owner.getCurrentStatus(Status.SILENCE) > 0)
				options[1].setEnabled(false);
			
			// Place selection on closest enabled button
			if (!options[selectedIndex].isEnabled())
				increaseIndex();
		}
	}
	
	public Character getOwner() {
		return owner;
	}
	
	@Override
	public synchronized void setEnabled(boolean b) {
		super.setEnabled(b);
		
		if (b) { // Conditionally enable them
			setOwner(owner);
		} else // Disable them all
			for (OptionButton button: options)
				button.setEnabled(b);
	}
	
	/**
	 * Sets the currently selected button. Numbers out of range will be
	 * set to the boundaries.
	 * @param index - the position of the button.
	 */
	public void setSelectedIndex(int index) {
		this.selectedIndex = index;
		
		if (index < 0)
			index = 0;
		else if (index > options.length - 1)
			index = options.length - 1;
		
		// Clear current selection
		for (OptionButton b : options)
			b.setSelected(false);
		
		// Set the current selection as selected
		options[selectedIndex].setSelected(true);
	}
	
	public void decreaseIndex() {
		do {
			selectedIndex++;
			if (selectedIndex >= options.length)
				selectedIndex = 0;
			// Clear current selection
			for (OptionButton b : options)
				b.setSelected(false);
			// Set the current selection as selected
			options[selectedIndex].setSelected(true);
		} while (!options[selectedIndex].isEnabled());
	}
	
	public void increaseIndex() {
		do {
			selectedIndex--;
			if (selectedIndex < 0)
				selectedIndex = options.length - 1;
			// Clear current selection
			for (OptionButton b : options)
				b.setSelected(false);
			// Set the current selection as selected
			options[selectedIndex].setSelected(true);
		} while (!options[selectedIndex].isEnabled());
	}
	
	public int getSelectedIndex() {
		return selectedIndex;
	}
	
	/**
	 * Retrieves the text label on the selected button.
	 * @return - a String representing the button
	 */
	public String getSelectedText() {
		return options[selectedIndex].getText();
	}
}

/**
 * 
 * These buttons will not respond to mouse events.
 *
 */
class OptionButton extends JToggleButton {
	private boolean selected;
	
	public OptionButton(String text) {
		super(text);
	}
	
	/**
	 * If selected, the button will be rendered differently.
	 */
	public void setSelectedState(boolean b) {
		selected = b;
		
		if (selected)
			this.setSelected(true);
		else
			this.setSelected(false);
	}
	
	@Override
	public void addMouseListener(MouseListener m) {}
	
	@Override
	public void addMouseMotionListener(MouseMotionListener m) {}
}
