package menu;

import items.EquippableItem;
import items.Item;
import items.Potion;
import items.Sword;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

import ui.InventoryRenderer;

public class EquipmentRenderer extends JPanel implements ListCellRenderer<EquippableItem>  {
		JLabel itemName = new JLabel("itemName");
		JLabel quantity = new JLabel("Qty");
		
		@Override
		public Component getListCellRendererComponent(JList<? extends EquippableItem> list,
				EquippableItem item, int index, boolean isSelected, boolean cellHasFocus) {		
			// Create the labels
			setBackground(new Color(245, 255, 250));
			GridBagLayout gridBagLayout = new GridBagLayout();
			gridBagLayout.columnWidths = new int[]{10, 0, 0, 10, 0};
			gridBagLayout.rowHeights = new int[]{0, 0};
			gridBagLayout.columnWeights = new double[]{0, 1.0, 0.0, 0, Double.MIN_VALUE};
			gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			setLayout(gridBagLayout);
			
			itemName.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 16));
			GridBagConstraints gbc_itemName = new GridBagConstraints();
			gbc_itemName.anchor = GridBagConstraints.WEST;
			gbc_itemName.insets = new Insets(0, 0, 0, 5);
			gbc_itemName.gridx = 1;
			gbc_itemName.gridy = 0;
			add(itemName, gbc_itemName);
			
			quantity.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 16));
			GridBagConstraints gbc_quantity = new GridBagConstraints();
			gbc_quantity.insets = new Insets(0, 0, 0, 5);
			gbc_quantity.anchor = GridBagConstraints.EAST;
			gbc_quantity.gridx = 2;
			gbc_quantity.gridy = 0;
			add(quantity, gbc_quantity);
			
			// Change the labels to the specified object
			itemName.setText(item.toString());
			quantity.setText("" + item.getCount());
			
			if (isSelected){
				setBorder(BorderFactory.createLineBorder(Color.yellow));
			}
			else{
				setBorder(BorderFactory.createLineBorder(getBackground()));
			}
			
			return this;
		}
		
		public static void main(String[] args) {
			JFrame f = new JFrame();
			
			EquippableItem[] inventory = {new Sword(), new Sword(), new Sword(), new Sword()};
			
			JList<Item> list = new JList<Item>(inventory);
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list.setCellRenderer(new InventoryRenderer());
			list.setSelectedIndex(0);
			
			f.getContentPane().add(list);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.pack();
			f.setVisible(true);
		}
	}


