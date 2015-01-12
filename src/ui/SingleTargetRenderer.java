package ui;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import battleComponents.BattleTarget;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

/**
 * 
 * Only used to render JLists when the target selection mode is set to single.
 *
 */
public class SingleTargetRenderer extends JPanel implements ListCellRenderer<BattleTarget> {
	JLabel targetName = new JLabel();
	
	@Override
	public Component getListCellRendererComponent(JList<? extends BattleTarget> list,
			BattleTarget target, int index, boolean isSelected, boolean cellHasFocus) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		targetName.setText(target.getName());
		targetName.setFont(StyleConstants.BATTLE_MENU_FONT);
		GridBagConstraints gbc_targetName = new GridBagConstraints();
		gbc_targetName.gridx = 0;
		gbc_targetName.gridy = 0;
		add(targetName, gbc_targetName);

		// Highlight the currently selected item
		if (isSelected)
			setBackground(StyleConstants.HIGHLIGHT);
		else
			setBackground(StyleConstants.LIST_BACKGROUND);
		
		if (!target.isActive()) {
			setEnabled(false);
			targetName.setEnabled(false);
		} else {
			setEnabled(true);
			targetName.setEnabled(true);
		}
		
		return this;
	}

}
