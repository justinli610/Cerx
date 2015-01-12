package ui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

import battleComponents.Character;
import battleComponents.CommandFormat;
import battleComponents.Magic;
import bestiary.Arkin;

public class AbilityRenderer extends JPanel implements ListCellRenderer<CommandFormat> {
	private Character owner;
	
	JLabel abilityName = new JLabel();
	JLabel mpCost = new JLabel();
	
	public AbilityRenderer(Character owner) {
		this.owner = owner;
	}
	
	/**
	 * Changes the owner this object references when rendering cells.
	 * @param owner - the Character whose turn it is
	 */
	public void setOwner(Character owner) {
		this.owner = owner;
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends CommandFormat> list,
			CommandFormat commandFormat, int index, boolean isSelected, boolean cellHasFocus) {
		// Create the labels
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{10, 0, 0, 10, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{0, 1.0, 0.0, 0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		abilityName.setFont(StyleConstants.BATTLE_MENU_FONT);
		GridBagConstraints gbc_itemName = new GridBagConstraints();
		gbc_itemName.anchor = GridBagConstraints.WEST;
		gbc_itemName.insets = new Insets(0, 0, 0, 5);
		gbc_itemName.gridx = 1;
		gbc_itemName.gridy = 0;
		add(abilityName, gbc_itemName);

		mpCost.setFont(StyleConstants.BATTLE_MENU_FONT);
		GridBagConstraints gbc_quantity = new GridBagConstraints();
		gbc_quantity.insets = new Insets(0, 0, 0, 5);
		gbc_quantity.anchor = GridBagConstraints.EAST;
		gbc_quantity.gridx = 2;
		gbc_quantity.gridy = 0;
		add(mpCost, gbc_quantity);

		// Change the labels to the specified object
		abilityName.setText(commandFormat.toString());
		if (commandFormat.getMPCost() != 0)
			mpCost.setText("" + commandFormat.getMPCost());
		else
			mpCost.setText("");

		// Highlight the currently selected item
		if (isSelected)
			setBackground(StyleConstants.HIGHLIGHT);
		else
			setBackground(StyleConstants.LIST_BACKGROUND);
		
		if (owner.getCurrMP() < commandFormat.getMPCost()) {
			abilityName.setEnabled(false);
			mpCost.setEnabled(false);
		} else {
			abilityName.setEnabled(true);
			mpCost.setEnabled(true);
		}

		return this;
	}
	
	public static void main(String[] args) {
		JFrame f = new JFrame();
		
		CommandFormat[] inventory = {Magic.BIO, Magic.BLIZZARD, Magic.FIRAJA, Arkin.ARKIN.new Blitz()};
		
		JList<CommandFormat> list = new JList<CommandFormat>(inventory);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setCellRenderer(new AbilityRenderer(Arkin.ARKIN));
		list.setSelectedIndex(0);
		
		f.getContentPane().add(list);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);
	}

}
