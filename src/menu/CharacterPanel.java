package menu;

import items.Item;
import items.Usable;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

import battleComponents.BattleTarget;
import battleComponents.Character;

import javax.swing.*;

import field.GameState;

public class CharacterPanel extends JPanel {

	protected Profile p[];
	protected int num;
	protected Character Selected;
	protected int index;
	protected Item current;
	protected Menu menu;

	public CharacterPanel(Menu menu, Character chararray[]) {
		num = chararray.length;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		p = new Profile[num];
		for (int i = 0; i < num; i++) {
			p[i] = new Profile(chararray[i]);
			add(p[i]);
		}

		this.menu = menu;
		JPanel temp = new JPanel();
		temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));

		add(temp);
		revalidate();
		repaint();

		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("X"),
				"Use");

		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"),
				"Cancel");
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"),
				"Next");
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"),
				"Previous");

		getActionMap().put("Next", new Navigate("Next"));
		getActionMap().put("Previous", new Navigate("Previous"));

		getActionMap().put("Use", new Use(0));
		getActionMap().put("Cancel", new Use(1));
	}

	public void setItem(Item i) {
		current = i;
	}

	public CharacterPanel(Menu menu) { // TODO:Why doesn't this Selection Work?
		this.menu = menu;
		JPanel temp = new JPanel();
		temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));
		num = 3;
		p = new Profile[num];
		for (int i = 0; i < num; i++) {
			p[i] = new Profile();
			temp.add(p[i]);
		}
		add(temp);
		revalidate();
		repaint();

		getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke("X"), "Use");

		getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke("W"), "Cancel");

		getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke("DOWN"), "Next");
		getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke("UP"), "Previous");

		getActionMap().put("Next", new Navigate("Next"));
		getActionMap().put("Previous", new Navigate("Previous"));
		getActionMap().put("Use", new Use(0));
		getActionMap().put("Cancel", new Use(1));

	}

	class Navigate extends AbstractAction {
		String text;

		public Navigate(String text) {
			this.text = text;
		}

		public void actionPerformed(ActionEvent evt) {
			if (text == "Next") {
				int index = getSelectedIndex();
				if (index >= 2) {
					index = -1;
				}
				setSelected(index + 1);
			} else if (text == "Previous") {
				int index = getSelectedIndex();
				if (index <= 0) {
					index = 3;
				}
				setSelected(index - 1);
			}

			revalidate();
			repaint();
		}
	}

	class Use extends AbstractAction {

		int index;
		int choice;

		public Use(int x) {
			choice = x;
		}

		public void actionPerformed(ActionEvent evt) {
			if (choice == 0) {
				if (menu.tab.getSelectedIndex() == 1) {
					if (current instanceof Usable) {
						((Usable) current).use(new BattleTarget[] { Selected });
					}
				} else {
				}
			} else if (choice == 1) {
				if (menu.tab.getSelectedIndex() == 1) {
					// ((Item)current).cancelUse();
					System.out.println("Exit");
					menu.getInventoryPanel().getBackpack().getList()
							.grabFocus();
				} else {
					menu.getStatPanel().getEquipInventory().getList()
							.grabFocus();
				}
			}
		}

	}

	public void setSelected(int index) {
		if (index >= 0 && index < num) {
			for (int i = 0; i < num; i++) {
				p[i].setSelected(false);
			}
			p[index].setSelected(true);
			revalidate();
			repaint();
			Selected = p[index].getChar();
		}
	}

	public int getSelectedIndex() {
		int temp = -1;
		for (int i = 0; i < num; i++) {
			if (p[i].getSelected() == true) {
				temp = i;
			}
		}
		return temp;
	}

	public Character getChar() {
		return Selected;
	}

	static JFrame frame;

	public static void ShowScreen() throws Exception {
		frame = new JFrame("Rawr");// initializes frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// sets closing
																// upon click of
																// x
		frame.add(new CharacterPanel(new Menu(null)));
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
