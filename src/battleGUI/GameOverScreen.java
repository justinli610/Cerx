package battleGUI;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import ui.StyleConstants;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Font;

public class GameOverScreen extends JPanel {
	public GameOverScreen() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblGameOver = new JLabel("Game Over");
		lblGameOver.setFont(StyleConstants.GAME_OVER);
		lblGameOver.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblGameOver = new GridBagConstraints();
		gbc_lblGameOver.gridx = 0;
		gbc_lblGameOver.gridy = 0;
		add(lblGameOver, gbc_lblGameOver);
		
		setBackground(Color.black);
	}
}
