package menu;

import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;

import menu.KeybindTest.SampleAction;
import field.GameState;

public class Menu extends JPanel {
	protected JTabbedPane tab;
	protected InventoryPanel Inv;
	private StatsPanel Stats;
	private CharacterPanel test;

	public Menu(GameState g) {
		int tabheight = 250 / 3;
		this.setLayout(new BorderLayout());
		tab = new JTabbedPane(JTabbedPane.LEFT, JTabbedPane.SCROLL_TAB_LAYOUT);

		JComponent Options = new Options(g);
		tab.addTab(
				"<html><body leftmargin=15 topmargin=8 marginwidth=10 marginheight="
						+ tabheight + ">Options</body></html>", null, Options,
				null);

		Inv = new InventoryPanel(this);
		tab.addTab(
				"<html><body leftmargin=15 topmargin=8 marginwidth=10 marginheight="
						+ tabheight + ">Inventory</body></html>", null,
				Inv, null);

		Stats = new StatsPanel(this);
		tab.addTab(
				"<html><body leftmargin=15 topmargin=8 marginwidth=10 marginheight="
						+ tabheight + ">Stats</body></html>", null, Stats, null);

		 test = new CharacterPanel(this);



		tab.getInputMap(WHEN_FOCUSED).put(
				KeyStroke.getKeyStroke("X"), "focus");

		tab.getActionMap().put("focus", new SetFocus());
		
		tab.getInputMap(this.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("W"), "Exit");
		tab.getActionMap().put("Exit", new ExitMenu());

		// add options pane
		// add inventory pane
		// add stats pane
		add(tab, BorderLayout.CENTER);
		add(test, BorderLayout.EAST);
	}
	
	public InventoryPanel getInventoryPanel()
	{
		return Inv;
	}
	
	public CharacterPanel getCharacterPanel()
	{
		return test;
	}
	class ExitMenu extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			GameState.getGameState().getGameFrame().setContentPane(GameState.getGameState().getMapPanel());
			GameState.getGameState().setRainOn(true);
			GameState.getGameState().getMapPanel().grabFocus();
		}
	}

	class SetFocus extends AbstractAction {

		int index;

		public SetFocus() {

			this.index = tab.getSelectedIndex();
		}

		public void actionPerformed(ActionEvent evt) {
			System.out.println(tab.getSelectedIndex());
			if (tab.getSelectedIndex() == 0)
				{}
			else if (tab.getSelectedIndex() == 1) {
				Inv.getBackpack().getList().grabFocus();
			}
			else {
				Stats.getInv().getList().grabFocus();
			}
		}

	}

	static JFrame frame;

	public static void ShowScreen() throws Exception {
		frame = new JFrame("Rawr");// initializes frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// sets closing
		frame.add(new Menu(null));
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
					e.printStackTrace();
				}
			}
		});
	}

	public StatsPanel getStatPanel() {
		// TODO Auto-generated method stub
		return Stats;
	}
	
	public void requestTabFocus() {
		tab.grabFocus();
	}
}
