package menu;

import java.awt.event.ActionEvent;

import javax.swing.*;

import field.GameState;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class Options extends JPanel {

	GameState g;

	public Options(GameState g) {
		this.g = g;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{164, 53, 161, 0};
		gridBagLayout.rowHeights = new int[]{86, 23, 0, 0, 99, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JButton Quit = new JButton("Quit");
		
				GridBagConstraints gbc_Quit = new GridBagConstraints();
				gbc_Quit.anchor = GridBagConstraints.NORTH;
				gbc_Quit.insets = new Insets(0, 0, 5, 5);
				gbc_Quit.gridx = 1;
				gbc_Quit.gridy = 1;
				add(Quit, gbc_Quit);
		JButton SQ = new JButton("Save & Quit");
		GridBagConstraints gbc_SQ = new GridBagConstraints();
		gbc_SQ.anchor = GridBagConstraints.NORTH;
		gbc_SQ.insets = new Insets(0, 0, 5, 5);
		gbc_SQ.gridx = 1;
		gbc_SQ.gridy = 2;
		add(SQ, gbc_SQ);
		JButton Save = new JButton("Save");
		GridBagConstraints gbc_Save = new GridBagConstraints();
		gbc_Save.insets = new Insets(0, 0, 5, 5);
		gbc_Save.anchor = GridBagConstraints.NORTH;
		gbc_Save.gridx = 1;
		gbc_Save.gridy = 3;
		add(Save, gbc_Save);
		
		// Button for Quit
		// Button for Save
		// Button for Save and Quit
		// Button for increase/decrease/mute music volume

		// Add input map and action map
	}

	class Quit extends AbstractAction {

		public void actionPerformed(ActionEvent evt) {

		}
	}

	class Gamemod extends AbstractAction {
		String text;

		public Gamemod(String text) {
			super(text);
			this.text = text;
		}

		public void actionPerformed(ActionEvent evt) {
			if (this.text == "Close") {
				Quit();
			} else if (this.text == "Save&Quit") {
				Save();
				Quit();
			} else {
				Save();
			}

		}

		public void Save() {
			// g.saveGame();
		}

		public void Quit() {
			System.out.close();
		}
	}



}
