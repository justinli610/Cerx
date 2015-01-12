package menu;

import items.EquippableItem;
import items.Item;
import items.Sword;
import items.Usable;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;

import ui.InventoryRenderer;
import menu.Backpack.Buttons;

public class EquipInventory extends JPanel implements ListSelectionListener {

	Item current;
	ArrayList<EquippableItem> inventory = new ArrayList<EquippableItem>(4);
	ArrayList<EquippableItem> show;
	Menu menu;
	JList<EquippableItem> list;

	public JList<EquippableItem> getList() {
		return list;
	}

	public EquipInventory(Menu menu) {
		this.menu = menu;

		for (int i = 0; i < 1; i++) {
			inventory.add(new Sword());
		}
		show = (ArrayList<EquippableItem>) inventory.clone();// TODO: clone?
		list = new JList<EquippableItem>(show.toArray(new EquippableItem[] {}));
		ListModel<EquippableItem> m = new DefaultListModel<EquippableItem>();

		for (int i = 0; i < inventory.size(); i++) {
			((DefaultListModel<EquippableItem>) m).add(i, inventory.get(i));
		}
		list.setModel(m);

		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setCellRenderer(new EquipmentRenderer());
		list.setSelectedIndex(0);
		add(list);

		list.getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke("X"), "Use");
		list.getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke("F"),
				"Destroy");
		list.getActionMap().put("Exit", new Buttons("Exit"));
		
		list.getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke("W"), "Exit");
		list.getActionMap().put("Use", new Buttons("Use"));
		list.getActionMap().put("Destroy", new Buttons("Destroy"));
		list.setBackground(new Color(245, 255, 250));

		current = show.get(0);
	}

	public EquipInventory(Menu menu, ArrayList<EquippableItem> inv) {
		this.menu = menu;
		inventory = (ArrayList<EquippableItem>) inv.clone();
		show = (ArrayList<EquippableItem>) inventory.clone();
		list = new JList<EquippableItem>((EquippableItem[]) show.toArray());

		ListModel<EquippableItem> m = new DefaultListModel<EquippableItem>();

		for (int i = 0; i < inventory.size(); i++) {
			((DefaultListModel<EquippableItem>) m).add(i, inventory.get(i));
		}
		list.setModel(m);

		add(list);

		list.getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke("X"), "Use");
		list.getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke("W"),
				"Destroy");
		list.getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke("Z"), "Exit");

		list.getActionMap().put("Use", new Buttons("Use"));
		list.getActionMap().put("Destroy", new Buttons("Destroy"));
		list.getActionMap().put("Exit", new Buttons("Exit"));

		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setCellRenderer(new InventoryRenderer());
		list.setSelectedIndex(0);
		current = show.get(0);
	}

	// ArrayList stuff = new ArrayList (55);

	public Item getCurrentItem() {
		return current;
	}

	class Buttons extends AbstractAction {

		String text;

		public Buttons(String text) {
			this.text = text;
		}

		public void actionPerformed(ActionEvent evt) {

			if (text == "Use") {

				int x = list.getSelectedIndex();
				if (inventory.get(x).getCount() > 0) {
					if (inventory.get(x) instanceof Usable) {
						// ((Usable) inventory.get(x)).use(targets);
					}
				} else {
					if (inventory.size() > 0) {
						inventory.remove(x);
						show.remove(x);
						((DefaultListModel<EquippableItem>) list.getModel())
								.removeElementAt(x);

					}

					list.revalidate();
					if (x < list.getModel().getSize())
						list.setSelectedIndex(x);
					else
						list.setSelectedIndex(x - 1);
				}
				menu.getCharacterPanel().setItem(current);
				menu.getCharacterPanel().grabFocus();
				current.consume();
				
				menu.revalidate();
			} else if (text == "Destroy") {
				int x = list.getSelectedIndex();
				if (inventory.get(x).getCount() > 0) {
					inventory.get(x).consume();
				} else {
					if (inventory.size() > 0) {
						inventory.remove(x);
						show.remove(x);
						((DefaultListModel<EquippableItem>) list.getModel())
								.removeElementAt(x);
					}
					else{
						current.consume();
					}

					revalidate();
					if (x < list.getModel().getSize())
						list.setSelectedIndex(x);
					else
						list.setSelectedIndex(x - 1);
				}
				
				menu.revalidate();
			} else {
				menu.tab.grabFocus();
			}

		}
	}

	public void paintComponent(Graphics g) {
		// Draw button array w/ images.

		// Yellow border = currently at
	}

	class equip extends AbstractAction {

		public void actionPerformed(ActionEvent evt) {
			// Check Quantity of Object
			// Check Character
			// loop through Array list 1-5+5*CharacterIndex to find the position
			// that matches
			// Select ArrayList index 1
			// Check if it's null
			// if it is add 1 object from the Array Index 2 to the null place
			// else take object, append to array list, put object from Index
			// Array 2 in the position the object was formerly.
			// update graphics
		}
	}

	public void KeyBindings()// may just be in Equipped constructor

	{
		// All input map and action map key binding here. (not decided whether
		// added to buttons or JPanel yet)
		// add equip Abstract action
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		int x = list.getSelectedIndex();
		current = inventory.get(x);
		System.out.println(x);

	}

	static JFrame frame;

	public static void ShowScreen() throws Exception {
		frame = new JFrame("Mapzzz");// initializes frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// sets closing
																// upon click of
																// x
		frame.getContentPane().add(new EquipInventory(new Menu(null)));
		frame.pack();
		frame.setSize(800, 600);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Turn off metal's use of bold fonts
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				try {
					ShowScreen();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}
