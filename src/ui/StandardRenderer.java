package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * 
 * A renderer that handles a single object per cell.
 *
 */
public class StandardRenderer extends JLabel implements ListCellRenderer<Object> {	
	@Override
	public Component getListCellRendererComponent(JList<? extends Object> list,
			Object obj, int index, boolean isSelected, boolean cellHasFocus) {
		setOpaque(true);
		setText(obj.toString());
		setFont(StyleConstants.BATTLE_MENU_FONT);
		
		if (isSelected)
			setBackground(StyleConstants.HIGHLIGHT);
		else
			setBackground(StyleConstants.LIST_BACKGROUND);
		
		return this;
	}

}
