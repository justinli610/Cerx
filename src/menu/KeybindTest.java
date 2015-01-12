package menu;

//KeyBindings Test.
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class KeybindTest extends JPanel {

	public KeybindTest() throws Exception {
		JButton component = new JButton("Button");
		getInputMap().put(KeyStroke.getKeyStroke("F2"), "actionName2");
		getActionMap().put("actionName", new SampleAction("actionName"));
		getActionMap().put("actionName2", new SampleAction2());

		component.getInputMap().put(KeyStroke.getKeyStroke("F3"), "actionName");
		component.getInputMap().put(KeyStroke.getKeyStroke("F4"), "actionName");
		component.getInputMap().put(KeyStroke.getKeyStroke("F5"), "actionName");
		component.getInputMap().put(KeyStroke.getKeyStroke("F6"), "actionName");
		component.getInputMap().put(KeyStroke.getKeyStroke("F7"), "actionName");
		component.getInputMap().put(KeyStroke.getKeyStroke("F8"), "actionName");

		component.getInputMap().put(KeyStroke.getKeyStroke("control A"),
				"actionName");
		component.getInputMap().put(KeyStroke.getKeyStroke("shift F2"),
				"actionName");
		component.getInputMap().put(KeyStroke.getKeyStroke('('), "actionName");
		component.getInputMap().put(KeyStroke.getKeyStroke("button3 F"),
				"actionName");
		component.getInputMap().put(KeyStroke.getKeyStroke("typed x"),
				"actionName");
		component.getInputMap().put(KeyStroke.getKeyStroke("DOWN"),
				"actionName");
		component.getInputMap().put(KeyStroke.getKeyStroke("released DELETE"),
				"actionName");
		component.getInputMap().put(KeyStroke.getKeyStroke("UP"),
				"actionName");

		this.add(component);

		component.getActionMap().put("actionName",
				new SampleAction("actionName"));
		component.getActionMap().put("actionName2",
				new SampleAction2());

	}

	class SampleAction extends AbstractAction {

		public SampleAction(String text) {
			super(text);
		}

		public void actionPerformed(ActionEvent evt) {
			System.out.println("Ha");
		}
	}

	class SampleAction2 extends AbstractAction {

		public void actionPerformed(ActionEvent evt) {
			System.out.println("Ha2");
		}
	}

	static JFrame frame;

	public static void ShowScreen() throws Exception {
		frame = new JFrame("Mapzzz");// initializes frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// sets closing
																// upon click of
																// x
		frame.add(new KeybindTest());
		frame.pack();
		frame.setSize(800, 385);
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
}