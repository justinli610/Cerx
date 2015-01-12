package menu;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class TestPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public TestPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {30, 30, 30, 30, 0, 0, 30, 30, 30, 30, 30, 30, 30};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JButton btnButton = new JButton("Button");
		GridBagConstraints gbc_btnButton = new GridBagConstraints();
		gbc_btnButton.gridheight = 2;
		gbc_btnButton.insets = new Insets(20, 0, 5, 0);
		gbc_btnButton.gridwidth = 5;
		gbc_btnButton.gridx = 0;
		gbc_btnButton.gridy = 0;
		add(btnButton, gbc_btnButton);

	}

}
