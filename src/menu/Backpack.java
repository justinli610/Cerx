package menu;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import items.Item;
import items.Potion;
import items.Usable;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ui.InventoryRenderer;
import menu.EquipInventory.Buttons;

public class Backpack extends JPanel implements ListSelectionListener {

	/**
	 * Create the panel.
	 */
	Item current;
	ArrayList<Item> inventory = new ArrayList<Item>(4);
	ArrayList<Item> show;
	Menu menu;
	JList<Item> list;

	public Backpack(Menu menu) {
		this.menu = menu;
		for (int i = 0; i < 1; i++) {
			inventory.add(new Potion());
		}
		show = (ArrayList<Item>) inventory.clone();// TODO: clone?
		list = new JList<Item>(); // show.toArray(new Item[] {})
		DefaultListModel<Item> m = new DefaultListModel<Item>();

		for (int i = 0; i < inventory.size(); i++) {
			m.add(i, inventory.get(i));
		}
		list.setModel(m);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setCellRenderer(new InventoryRenderer());
		list.setSelectedIndex(0);
		list.addListSelectionListener(this);
		list.setBackground(new Color(245, 255, 250));

		add(list);
		
		list.getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke("A"), "All");
		list.getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke("S"), "Usable");
		list.getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke("D"), "Quest");
		list.getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke("X"), "Use");
		list.getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke("F"), "Destroy");
		list.getActionMap().put("Exit", new Buttons("Exit"));
		list.getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke("W"), "Exit");
	
		list.getActionMap().put("Use", new Buttons("Use"));
		list.getActionMap().put("Destroy", new Buttons("Destroy"));
		list.getActionMap().put("All", new Sort("All"));
		list.getActionMap().put("Usable", new Sort("Usable"));
		list.getActionMap().put("Quest", new Sort("Quest"));
		
		current = show.get(0);

	}
	
	public Backpack(ArrayList<Item> inv, Menu menu) {
		inventory = inv;
		show = (ArrayList<Item>) inventory.clone();// TODO: clone?
		list = new JList<Item>(); // show.toArray(new Item[] {})
		DefaultListModel<Item> m = new DefaultListModel<Item>();

		for (int i = 0; i < inventory.size(); i++) {
			m.add(i, inventory.get(i));
		}
		list.setModel(m);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setCellRenderer(new InventoryRenderer());
		list.setSelectedIndex(0);
		list.addListSelectionListener(this);
		
		list.getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke("A"), "All");
		list.getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke("S"), "Usable");
		list.getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke("D"), "Quest");
		list.getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke("X"), "Use");
		list.getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke("W"), "Destroy");
		list.getActionMap().put("Exit", new Buttons("Exit"));
		list.getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke("Z"), "Exit");
	
		list.getActionMap().put("Use", new Buttons("Use"));
		list.getActionMap().put("Destroy", new Buttons("Destroy"));
		list.getActionMap().put("All", new Sort("All"));
		list.getActionMap().put("Usable", new Sort("Usable"));
		list.getActionMap().put("Quest", new Sort("Quest"));

		add(list);
		current = show.get(0);
	}
	
	class Sort extends AbstractAction {
		String text = "";
		public Sort(String text)
		{
			super (text);
			this.text = text;
		}

		public void actionPerformed(ActionEvent evt) {
			if(text == "Usable")
			{
				viewUsable();
				System.out.println("Use");
			}else if(text == "Quest")
			{
				viewQuest();
				System.out.println("Quest");
			}
			else
			{
				viewAll();
				System.out.println("All");
			}
			revalidate();
			repaint();
		}


	}
	
	class Buttons extends AbstractAction {

		String text;
		public Buttons (String text)
		{
			this.text = text;
		}
		public void actionPerformed(ActionEvent evt) {
			if(text == "Use")
			{
				if(getCurrentItem() instanceof Usable)
				{
					System.out.println("?");
					int x = list.getSelectedIndex();
					if (inventory.get(x).getCount() > 0) {
						if (inventory.get(x) instanceof Usable) {
							// ((Usable) inventory.get(x)).use(targets);
						}
					} else {
						if (inventory.size() > 0) {
							inventory.remove(x);
							show.remove(x);
							((DefaultListModel<Item>) list.getModel()).removeElementAt(x);

						}

						list.revalidate();
						if (x < list.getModel().getSize())
							list.setSelectedIndex(x);
						else
							list.setSelectedIndex(x - 1);
					}
					menu.getCharacterPanel().setItem(current);
					menu.getCharacterPanel().grabFocus();
				}
			}
			else if( text == "Destroy")
			{
				int x = list.getSelectedIndex();
				if (inventory.get(x).getCount() > 0) {
					inventory.get(x).consume();
				} else {
					if (inventory.size() > 0) {
						inventory.remove(x);
						show.remove(x);
						((DefaultListModel<Item>) list.getModel()).removeElementAt(x);

					}

					list.revalidate();
					if (x < list.getModel().getSize())
						list.setSelectedIndex(x);
					else
						list.setSelectedIndex(x - 1);
				}
			}else {
				menu.tab.grabFocus();
			}
			
			//if and else
			
			//Use
			//Detect if object use
			//Use Object
			
			//Destroy
			//Remove item from ArrayList
			//Remove item from ArrayList in Gamestate
			//Update			

			//Select
			//Update Image and Text box

		}
	}

	public JList<Item> getList() {
		return list;
	}

	

	public Item getCurrentItem() {
		return current;
	}

	public void viewUsable() {

		ArrayList<Item> temp = new ArrayList<Item>(inventory.size());
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i) instanceof Usable) {
				temp.add(inventory.get(i));
			}
		}
		show = temp;
	}

	public void viewQuest() {
		ArrayList<Item> temp = new ArrayList<Item>(inventory.size());
		for (int i = 0; i < inventory.size(); i++) {
			if (!(inventory.get(i) instanceof Usable)) {
				temp.add(inventory.get(i));
			}
		}
		show = temp;
	}

	public void viewAll() {
		show = (ArrayList<Item>) inventory.clone();// TODO: clone?
	}



	/*
	 * public void paintComponent(Graphics g) {
	 * 
	 * setBackground(new Color(255, 215, 50)); }
	 */

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (list.getSelectedIndex() >= 0) {
			int x = list.getSelectedIndex();

			current = inventory.get(x);
		}
	}

}
