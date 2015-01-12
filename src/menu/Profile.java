package menu;

import javax.swing.JPanel;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.BoxLayout;

import java.awt.FlowLayout;

import battleComponents.Character;

import javax.swing.border.CompoundBorder;
import javax.swing.border.BevelBorder;

import java.awt.Dimension;
import java.awt.Color;

import javax.swing.border.EmptyBorder;

public class Profile extends JPanel {

	/**
	 * Create the panel.
	 */
	boolean Selected = false;
	private JPanel separator;
	private Character Char; 
	
	public Character getChar()
	{
		return Char; 
	}
	
	public boolean getSelected()
	{
		return Selected;
	}
	public Profile() {
		setMinimumSize(new Dimension(10, 3));
		setPreferredSize(new Dimension(115, 180));

			setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED,
					null, null, null, null), new BevelBorder(
					BevelBorder.LOWERED, null, null, null, null)));

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 69, -10, 0, 0, 0, 8, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel image = new JLabel("New label");
		image.setMinimumSize(new Dimension(30, 14));
		GridBagConstraints gbc_image = new GridBagConstraints();
		gbc_image.gridx = 0;
		gbc_image.gridy = 0;
		add(image, gbc_image);

		separator = new JPanel();
		separator.setMinimumSize(new Dimension(10, 5));
		separator.setPreferredSize(new Dimension(10, 5));
		
			separator.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null,
					null));

		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.fill = GridBagConstraints.BOTH;
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 1;
		add(separator, gbc_separator);

		JLabel name = new JLabel("Name");
		GridBagConstraints gbc_name = new GridBagConstraints();
		gbc_name.insets = new Insets(0, 10, 0, 0);
		gbc_name.anchor = GridBagConstraints.WEST;
		gbc_name.gridx = 0;
		gbc_name.gridy = 2;
		add(name, gbc_name);

		JLabel lvl = new JLabel("Lvl:");
		GridBagConstraints gbc_lvl = new GridBagConstraints();
		gbc_lvl.anchor = GridBagConstraints.WEST;
		gbc_lvl.insets = new Insets(0, 10, 0, 0);
		gbc_lvl.gridx = 0;
		gbc_lvl.gridy = 3;
		add(lvl, gbc_lvl);

		JLabel Health = new JLabel("HP:");
		GridBagConstraints gbc_Health = new GridBagConstraints();
		gbc_Health.anchor = GridBagConstraints.WEST;
		gbc_Health.insets = new Insets(0, 10, 0, 0);
		gbc_Health.gridx = 0;
		gbc_Health.gridy = 4;
		add(Health, gbc_Health);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_HealthBar = new GridBagConstraints();
		gbc_HealthBar.fill = GridBagConstraints.HORIZONTAL;
		gbc_HealthBar.gridx = 0;
		gbc_HealthBar.gridy = 5;
		add(panel, gbc_HealthBar);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		Bar panel_1 = new Bar();
		panel.add(panel_1);
		JLabel label = new JLabel("MP:");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 10, 0, 0);
		gbc_label.anchor = GridBagConstraints.WEST;
		gbc_label.gridx = 0;
		gbc_label.gridy = 6;
		add(label, gbc_label);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EmptyBorder(-3, 0, -10, 0));
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 7;
		add(panel_2, gbc_panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		Bar bar = new Bar();
		panel_2.add(bar);

	}
	
	public void setSelected(boolean x)
	{
		Selected = x;	
		if (Selected == true) {
			setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED,
					new Color(255, 255, 102), new Color(255, 255, 0),
					new Color(255, 153, 0), new Color(255, 204, 0)),
					new BevelBorder(BevelBorder.LOWERED,
							new Color(255, 255, 0), new Color(255, 255, 51),
							new Color(255, 204, 0), new Color(255, 153, 0))));
			separator.setBorder(new BevelBorder(BevelBorder.RAISED,
					new Color(255, 255, 102), new Color(255, 255, 0),
					new Color(255, 153, 0), new Color(255, 204, 0)));
		} else {
			setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED,
					null, null, null, null), new BevelBorder(
					BevelBorder.LOWERED, null, null, null, null)));

			separator.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null,
					null));
		}
		
	}

	public Profile(Character c) {
		this.Char = c;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel image = new JLabel(c.getImageIcon());
		GridBagConstraints gbc_image = new GridBagConstraints();
		gbc_image.insets = new Insets(0, 0, 5, 0);
		gbc_image.gridx = 0;
		gbc_image.gridy = 0;
		add(image, gbc_image);

		JLabel name = new JLabel(c.toString());
		GridBagConstraints gbc_name = new GridBagConstraints();
		gbc_name.insets = new Insets(0, 0, 5, 0);
		gbc_name.gridx = 0;
		gbc_name.gridy = 1;
		add(name, gbc_name);

		JLabel lvl = new JLabel("Lvl:" + c.getStats().getLevel());
		GridBagConstraints gbc_lvl = new GridBagConstraints();
		gbc_lvl.insets = new Insets(0, 0, 5, 0);
		gbc_lvl.gridx = 0;
		gbc_lvl.gridy = 2;
		add(lvl, gbc_lvl);

		JLabel Health = new JLabel("HP:" + c.getCurrHP() + "/"
				+ c.getStats().getMaxHP());
		GridBagConstraints gbc_Health = new GridBagConstraints();
		gbc_Health.insets = new Insets(0, 0, 5, 0);
		gbc_Health.gridx = 0;
		gbc_Health.gridy = 3;
		add(Health, gbc_Health);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_HealthBar = new GridBagConstraints();
		gbc_HealthBar.fill = GridBagConstraints.HORIZONTAL;
		gbc_HealthBar.insets = new Insets(0, 0, 5, 0);
		gbc_HealthBar.gridx = 0;
		gbc_HealthBar.gridy = 4;
		add(panel, gbc_HealthBar);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		Bar panel_1 = new Bar();
		panel.add(panel_1);

		JLabel Mana = new JLabel("MP:" + c.getCurrMP() + "/"
				+ c.getStats().getMaxMP());
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 0);
		gbc_label.gridx = 0;
		gbc_label.gridy = 5;
		add(Mana, gbc_label);

		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 6;
		add(panel_2, gbc_panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		Bar bar = new Bar();
		panel_2.add(bar);

	}

}
