package menu;

import java.awt.BorderLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dimension;
import javax.swing.border.CompoundBorder;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

import javax.swing.JScrollPane;

public class StatsPanel extends JPanel implements ChangeListener{

	/**
	 * Launch the application.
	 */
	/**
	 * Create the frame.
	 */

	private ImageIcon view = null;
	private EquipInventory e;

	public StatsPanel(Menu menu) {

		setBounds(100, 100, 800, 600);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		// frame.setContentPane(this);
		GridBagLayout gbl = new GridBagLayout();
		gbl.columnWidths = new int[] { 95, 0, 30, 17, 30, 30, 30, 30, 0, 30,
				30, 30, 30, 30, 30, 30 };
		gbl.rowHeights = new int[] { 28, 30, 30, 30, 30, 119, 33, 177, 0, 0 };
		gbl.columnWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0,
				0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl.rowWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		setLayout(gbl);

		JPanel Outer = new JPanel();
		Outer.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED,
				null, null, null, null), new BevelBorder(BevelBorder.LOWERED,
				null, null, null, null)));
		GridBagConstraints gbc_Outer = new GridBagConstraints();
		gbc_Outer.insets = new Insets(0, 0, 0, 5);
		gbc_Outer.gridheight = 9;
		gbc_Outer.gridwidth = 4;
		gbc_Outer.fill = GridBagConstraints.BOTH;
		gbc_Outer.gridx = 0;
		gbc_Outer.gridy = 0;
		add(Outer, gbc_Outer);
		GridBagLayout gbl_Outer = new GridBagLayout();
		gbl_Outer.columnWidths = new int[] { 0, 0 };
		gbl_Outer.rowHeights = new int[] { 192, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0 };
		gbl_Outer.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_Outer.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		Outer.setLayout(gbl_Outer);
		
		JPanel Title1 = new JPanel();
		Title1.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
		GridBagConstraints gbc_Title1 = new GridBagConstraints();
		gbc_Title1.gridwidth = 11;
		gbc_Title1.insets = new Insets(0, 0, 5, 0);
		gbc_Title1.fill = GridBagConstraints.BOTH;
		gbc_Title1.gridx = 4;
		gbc_Title1.gridy = 0;
		add(Title1, gbc_Title1);
		
		JLabel lblInventory = new JLabel("EQUIPMENT");
		Title1.add(lblInventory);

		JScrollPane EquipInventory = new JScrollPane();
		EquipInventory.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
		GridBagConstraints gbc_EquipInventory = new GridBagConstraints();
		gbc_EquipInventory.insets = new Insets(0, 0, 5, 0);
		gbc_EquipInventory.gridheight = 5;
		gbc_EquipInventory.gridwidth = 11;
		gbc_EquipInventory.fill = GridBagConstraints.BOTH;
		gbc_EquipInventory.gridx = 4;
		gbc_EquipInventory.gridy = 1;
		add(EquipInventory, gbc_EquipInventory);
		
		JPanel panel_2 = new JPanel();
		EquipInventory.setViewportView(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		e = new EquipInventory(menu);
		panel_2.add(e);
		e.setLayout(new GridLayout(1, 0, 0, 0));

		JPanel Preview = new JPanel();
		Preview.setMinimumSize(new Dimension(1, 1));
		GridBagConstraints gbc_Preview = new GridBagConstraints();
		gbc_Preview.gridwidth = 4;
		gbc_Preview.insets = new Insets(0, 0, 5, 0);
		gbc_Preview.fill = GridBagConstraints.BOTH;
		gbc_Preview.gridx = 0;
		gbc_Preview.gridy = 0;
		Outer.add(Preview, gbc_Preview);
		GridBagLayout gbl_Preview = new GridBagLayout();
		gbl_Preview.columnWidths = new int[] { 87, 0 };
		gbl_Preview.rowHeights = new int[] { 128, 30, 3, 10, 0 };
		gbl_Preview.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_Preview.rowWeights = new double[] { 1.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		Preview.setLayout(gbl_Preview);

		JLabel Picture = new JLabel(view);
		Picture.setMinimumSize(new Dimension(30, 12));
		Picture.setMaximumSize(new Dimension(50, 50));
		GridBagConstraints gbc_Picture = new GridBagConstraints();
		gbc_Picture.gridheight = 2;
		gbc_Picture.insets = new Insets(5, 0, 5, 0);
		gbc_Picture.gridx = 0;
		gbc_Picture.gridy = 0;
		Preview.add(Picture, gbc_Picture);

		JPanel border1 = new JPanel();
		border1.setPreferredSize(new Dimension(10, 3));
		border1.setMinimumSize(new Dimension(5, 3));
		border1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null,
				null));
		GridBagConstraints gbc_border1 = new GridBagConstraints();
		gbc_border1.fill = GridBagConstraints.BOTH;
		gbc_border1.insets = new Insets(0, 0, 5, 0);
		gbc_border1.gridx = 0;
		gbc_border1.gridy = 2;
		Preview.add(border1, gbc_border1);

		JLabel label_1 = new JLabel("Sample Name");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.gridx = 0;
		gbc_label_1.gridy = 3;
		Preview.add(label_1, gbc_label_1);

		JPanel Equipment = new JPanel();
		GridBagConstraints gbc_Equipment = new GridBagConstraints();
		gbc_Equipment.insets = new Insets(0, 0, 5, 0);
		gbc_Equipment.gridheight = 9;
		gbc_Equipment.gridwidth = 4;
		gbc_Equipment.fill = GridBagConstraints.BOTH;
		gbc_Equipment.gridx = 0;
		gbc_Equipment.gridy = 1;
		Outer.add(Equipment, gbc_Equipment);
		GridBagLayout gbl_Equipment = new GridBagLayout();
		gbl_Equipment.columnWidths = new int[] { 0, 0, 0 };
		gbl_Equipment.rowHeights = new int[] { 0, 53, 58, 56, 60, 0 };
		gbl_Equipment.columnWeights = new double[] { 1.0, 1.0, 1.0 };
		gbl_Equipment.rowWeights = new double[] { 0.0, 1.0, 1.0, 1.0, 1.0, 0.0 };
		Equipment.setLayout(gbl_Equipment);

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(10, 3));
		panel.setMinimumSize(new Dimension(5, 3));
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null,
				null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 3;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		Equipment.add(panel, gbc_panel);

		Head_1 = new JLabel();
		Head_1.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED,
				null, null, null, null), new BevelBorder(BevelBorder.LOWERED,
				null, null, null, null)));
		Head_1.setHorizontalAlignment(SwingConstants.CENTER);
		Head_1.setText("Head");
		GridBagConstraints gbc_Head_1 = new GridBagConstraints();
		gbc_Head_1.insets = new Insets(0, 0, 5, 5);
		gbc_Head_1.fill = GridBagConstraints.BOTH;
		gbc_Head_1.gridx = 1;
		gbc_Head_1.gridy = 1;
		Equipment.add(Head_1, gbc_Head_1);

		JLabel Headimg = new JLabel("Head");
		Head_1.add(Headimg);

		JLabel Lefthand = new JLabel();
		Lefthand.setBorder(new CompoundBorder(new BevelBorder(
				BevelBorder.RAISED, null, null, null, null), new BevelBorder(
				BevelBorder.LOWERED, null, null, null, null)));
		Lefthand.setHorizontalAlignment(SwingConstants.CENTER);
		Lefthand.setText("Left");
		GridBagConstraints gbc_Lefthand = new GridBagConstraints();
		gbc_Lefthand.insets = new Insets(0, 0, 5, 5);
		gbc_Lefthand.fill = GridBagConstraints.BOTH;
		gbc_Lefthand.gridx = 0;
		gbc_Lefthand.gridy = 2;
		Equipment.add(Lefthand, gbc_Lefthand);

		JLabel Body = new JLabel();
		Body.setHorizontalAlignment(SwingConstants.CENTER);
		Body.setText("Body");
		Body.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED,
				null, null, null, null), new BevelBorder(BevelBorder.LOWERED,
				null, null, null, null)));
		GridBagConstraints gbc_Body = new GridBagConstraints();
		gbc_Body.insets = new Insets(0, 0, 5, 5);
		gbc_Body.fill = GridBagConstraints.BOTH;
		gbc_Body.gridx = 1;
		gbc_Body.gridy = 2;
		Equipment.add(Body, gbc_Body);

		JLabel RightHand = new JLabel();
		RightHand.setBorder(new CompoundBorder(new BevelBorder(
				BevelBorder.RAISED, null, null, null, null), new BevelBorder(
				BevelBorder.LOWERED, null, null, null, null)));
		RightHand.setText("Right");
		RightHand.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_RightHand = new GridBagConstraints();
		gbc_RightHand.insets = new Insets(0, 0, 5, 0);
		gbc_RightHand.fill = GridBagConstraints.BOTH;
		gbc_RightHand.gridx = 2;
		gbc_RightHand.gridy = 2;
		Equipment.add(RightHand, gbc_RightHand);

		JLabel Legs = new JLabel();
		Legs.setHorizontalAlignment(SwingConstants.CENTER);
		Legs.setText("Legs");
		Legs.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED,
				null, null, null, null), new BevelBorder(BevelBorder.LOWERED,
				null, null, null, null)));
		GridBagConstraints gbc_Legs = new GridBagConstraints();
		gbc_Legs.insets = new Insets(0, 0, 5, 5);
		gbc_Legs.fill = GridBagConstraints.BOTH;
		gbc_Legs.gridx = 1;
		gbc_Legs.gridy = 3;
		Equipment.add(Legs, gbc_Legs);

		JLabel Feet = new JLabel();
		Feet.setHorizontalAlignment(SwingConstants.CENTER);
		Feet.setText("Feet");
		Feet.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED,
				null, null, null, null), new BevelBorder(BevelBorder.LOWERED,
				null, null, null, null)));
		GridBagConstraints gbc_Feet = new GridBagConstraints();
		gbc_Feet.insets = new Insets(0, 0, 5, 5);
		gbc_Feet.fill = GridBagConstraints.BOTH;
		gbc_Feet.gridx = 1;
		gbc_Feet.gridy = 4;
		Equipment.add(Feet, gbc_Feet);

		JLabel Accessories = new JLabel();
		Accessories.setHorizontalAlignment(SwingConstants.CENTER);
		Accessories.setText("Extra");
		Accessories.setBorder(new CompoundBorder(new BevelBorder(
				BevelBorder.RAISED, null, null, null, null), new BevelBorder(
				BevelBorder.LOWERED, null, null, null, null)));
		GridBagConstraints gbc_Accessories = new GridBagConstraints();
		gbc_Accessories.insets = new Insets(0, 0, 5, 0);
		gbc_Accessories.fill = GridBagConstraints.BOTH;
		gbc_Accessories.gridx = 2;
		gbc_Accessories.gridy = 4;
		Equipment.add(Accessories, gbc_Accessories);

		JPanel panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(10, 3));
		panel_1.setMinimumSize(new Dimension(5, 3));
		panel_1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null,
				null));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridwidth = 3;
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 5;
		Equipment.add(panel_1, gbc_panel_1);
		
		JPanel Title2 = new JPanel();
		Title2.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
		GridBagConstraints gbc_Title2 = new GridBagConstraints();
		gbc_Title2.gridwidth = 11;
		gbc_Title2.insets = new Insets(0, 0, 5, 0);
		gbc_Title2.fill = GridBagConstraints.BOTH;
		gbc_Title2.gridx = 4;
		gbc_Title2.gridy = 6;
		add(Title2, gbc_Title2);
		
		JLabel lblStats = new JLabel("STATS");
		Title2.add(lblStats);

		JPanel Stats = new JPanel();
		Stats.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
		GridBagConstraints gbc_Stats = new GridBagConstraints();
		gbc_Stats.gridheight = 2;
		gbc_Stats.gridwidth = 11;
		gbc_Stats.fill = GridBagConstraints.BOTH;
		gbc_Stats.gridx = 4;
		gbc_Stats.gridy = 7;
		add(Stats, gbc_Stats);
		Stats.setLayout(new GridLayout(0, 1, 0, 0));
		JPanel inside = new Stats();
		inside.setBorder(new EmptyBorder(0,20,10,0));
		Stats.add(inside);

		JPanel Options = new JPanel();
		GridBagConstraints gbc_Options = new GridBagConstraints();
		gbc_Options.gridheight = 5;
		gbc_Options.gridwidth = 4;
		gbc_Options.fill = GridBagConstraints.BOTH;
		gbc_Options.gridx = 0;
		gbc_Options.gridy = 10;
		Outer.add(Options, gbc_Options);
		Options.setLayout(new GridLayout(4, 2, 0, 0));

		ButtonGroup b = new ButtonGroup();

		JCheckBox chckbxAll = new JCheckBox("All");
		Options.add(chckbxAll);
		b.add(chckbxAll);

		JCheckBox chckbxWeapons = new JCheckBox("Weapons");
		Options.add(chckbxWeapons);
		b.add(chckbxWeapons);

		JCheckBox chckbxHead = new JCheckBox("Helmet");
		Options.add(chckbxHead);
		b.add(chckbxHead);

		JCheckBox chckbxArmor = new JCheckBox("Armor");
		Options.add(chckbxArmor);
		b.add(chckbxArmor);

		JCheckBox chckbxPants = new JCheckBox("Pants");
		Options.add(chckbxPants);
		b.add(chckbxPants);

		JCheckBox chckbxNewCheckBox = new JCheckBox("Boots");
		Options.add(chckbxNewCheckBox);
		b.add(chckbxNewCheckBox);

		JCheckBox chckbxOthers = new JCheckBox("Others");
		Options.add(chckbxOthers);
		b.add(chckbxOthers);

		// add equipped and stats
	}

	public StatsPanel(Menu menu, CharacterPanel c) {


		setBounds(100, 100, 800, 600);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		// frame.setContentPane(this);
		GridBagLayout gbl = new GridBagLayout();
		gbl.columnWidths = new int[] { 95, 0, 30, 17, 30, 30, 30, 30, 0, 30,
				30, 30, 30, 30, 30, 30 };
		gbl.rowHeights = new int[] { 28, 30, 30, 30, 30, 119, 33, 177, 0, 0 };
		gbl.columnWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0,
				0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl.rowWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		setLayout(gbl);

		JPanel Outer = new JPanel();
		Outer.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED,
				null, null, null, null), new BevelBorder(BevelBorder.LOWERED,
				null, null, null, null)));
		GridBagConstraints gbc_Outer = new GridBagConstraints();
		gbc_Outer.insets = new Insets(0, 0, 0, 5);
		gbc_Outer.gridheight = 9;
		gbc_Outer.gridwidth = 4;
		gbc_Outer.fill = GridBagConstraints.BOTH;
		gbc_Outer.gridx = 0;
		gbc_Outer.gridy = 0;
		add(Outer, gbc_Outer);
		GridBagLayout gbl_Outer = new GridBagLayout();
		gbl_Outer.columnWidths = new int[] { 0, 0 };
		gbl_Outer.rowHeights = new int[] { 192, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0 };
		gbl_Outer.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_Outer.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		Outer.setLayout(gbl_Outer);
		
		JPanel Title1 = new JPanel();
		Title1.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
		GridBagConstraints gbc_Title1 = new GridBagConstraints();
		gbc_Title1.gridwidth = 11;
		gbc_Title1.insets = new Insets(0, 0, 5, 0);
		gbc_Title1.fill = GridBagConstraints.BOTH;
		gbc_Title1.gridx = 4;
		gbc_Title1.gridy = 0;
		add(Title1, gbc_Title1);
		
		JLabel lblInventory = new JLabel("EQUIPMENT");
		Title1.add(lblInventory);

		JScrollPane EquipInventory = new JScrollPane();
		EquipInventory.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
		GridBagConstraints gbc_EquipInventory = new GridBagConstraints();
		gbc_EquipInventory.insets = new Insets(0, 0, 5, 0);
		gbc_EquipInventory.gridheight = 5;
		gbc_EquipInventory.gridwidth = 11;
		gbc_EquipInventory.fill = GridBagConstraints.BOTH;
		gbc_EquipInventory.gridx = 4;
		gbc_EquipInventory.gridy = 1;
		add(EquipInventory, gbc_EquipInventory);
		
		JPanel panel_2 = new JPanel();
		EquipInventory.setViewportView(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		EquipInventory e = new EquipInventory(menu);
		panel_2.add(e);
		e.setLayout(new GridLayout(1, 0, 0, 0));
		


		JPanel Preview = new JPanel();
		Preview.setMinimumSize(new Dimension(1, 1));
		GridBagConstraints gbc_Preview = new GridBagConstraints();
		gbc_Preview.gridwidth = 4;
		gbc_Preview.insets = new Insets(0, 0, 5, 0);
		gbc_Preview.fill = GridBagConstraints.BOTH;
		gbc_Preview.gridx = 0;
		gbc_Preview.gridy = 0;
		Outer.add(Preview, gbc_Preview);
		GridBagLayout gbl_Preview = new GridBagLayout();
		gbl_Preview.columnWidths = new int[] { 87, 0 };
		gbl_Preview.rowHeights = new int[] { 128, 30, 3, 10, 0 };
		gbl_Preview.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_Preview.rowWeights = new double[] { 1.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		Preview.setLayout(gbl_Preview);

		JLabel Picture = new JLabel(view);
		Picture.setMinimumSize(new Dimension(30, 12));
		Picture.setMaximumSize(new Dimension(50, 50));
		GridBagConstraints gbc_Picture = new GridBagConstraints();
		gbc_Picture.gridheight = 2;
		gbc_Picture.insets = new Insets(5, 0, 5, 0);
		gbc_Picture.gridx = 0;
		gbc_Picture.gridy = 0;
		Preview.add(Picture, gbc_Picture);

		JPanel border1 = new JPanel();
		border1.setPreferredSize(new Dimension(10, 3));
		border1.setMinimumSize(new Dimension(5, 3));
		border1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null,
				null));
		GridBagConstraints gbc_border1 = new GridBagConstraints();
		gbc_border1.fill = GridBagConstraints.BOTH;
		gbc_border1.insets = new Insets(0, 0, 5, 0);
		gbc_border1.gridx = 0;
		gbc_border1.gridy = 2;
		Preview.add(border1, gbc_border1);

		JLabel label_1 = new JLabel(c.getChar().toString());
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.gridx = 0;
		gbc_label_1.gridy = 3;
		Preview.add(label_1, gbc_label_1);

		JPanel Equipment = new JPanel();
		GridBagConstraints gbc_Equipment = new GridBagConstraints();
		gbc_Equipment.insets = new Insets(0, 0, 5, 0);
		gbc_Equipment.gridheight = 9;
		gbc_Equipment.gridwidth = 4;
		gbc_Equipment.fill = GridBagConstraints.BOTH;
		gbc_Equipment.gridx = 0;
		gbc_Equipment.gridy = 1;
		Outer.add(Equipment, gbc_Equipment);
		GridBagLayout gbl_Equipment = new GridBagLayout();
		gbl_Equipment.columnWidths = new int[] { 0, 0, 0 };
		gbl_Equipment.rowHeights = new int[] { 0, 53, 58, 56, 60, 0 };
		gbl_Equipment.columnWeights = new double[] { 1.0, 1.0, 1.0 };
		gbl_Equipment.rowWeights = new double[] { 0.0, 1.0, 1.0, 1.0, 1.0, 0.0 };
		Equipment.setLayout(gbl_Equipment);

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(10, 3));
		panel.setMinimumSize(new Dimension(5, 3));
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null,
				null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 3;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		Equipment.add(panel, gbc_panel);

		Equipment temp = c.getChar().getEquipment();
		Head_1 = new JLabel(temp.getHead().getIcon());
		Head_1.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED,
				null, null, null, null), new BevelBorder(BevelBorder.LOWERED,
				null, null, null, null)));
		Head_1.setHorizontalAlignment(SwingConstants.CENTER);
		Head_1.setText("Head");
		GridBagConstraints gbc_Head_1 = new GridBagConstraints();
		gbc_Head_1.insets = new Insets(0, 0, 5, 5);
		gbc_Head_1.fill = GridBagConstraints.BOTH;
		gbc_Head_1.gridx = 1;
		gbc_Head_1.gridy = 1;
		Equipment.add(Head_1, gbc_Head_1);


		JLabel Lefthand = new JLabel(temp.getLeft().getIcon());
		Lefthand.setBorder(new CompoundBorder(new BevelBorder(
				BevelBorder.RAISED, null, null, null, null), new BevelBorder(
				BevelBorder.LOWERED, null, null, null, null)));
		Lefthand.setHorizontalAlignment(SwingConstants.CENTER);
		Lefthand.setText("Left");
		GridBagConstraints gbc_Lefthand = new GridBagConstraints();
		gbc_Lefthand.insets = new Insets(0, 0, 5, 5);
		gbc_Lefthand.fill = GridBagConstraints.BOTH;
		gbc_Lefthand.gridx = 0;
		gbc_Lefthand.gridy = 2;
		Equipment.add(Lefthand, gbc_Lefthand);

		JLabel Body = new JLabel(temp.getBody().getIcon());
		Body.setHorizontalAlignment(SwingConstants.CENTER);
		Body.setText("Body");
		Body.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED,
				null, null, null, null), new BevelBorder(BevelBorder.LOWERED,
				null, null, null, null)));
		GridBagConstraints gbc_Body = new GridBagConstraints();
		gbc_Body.insets = new Insets(0, 0, 5, 5);
		gbc_Body.fill = GridBagConstraints.BOTH;
		gbc_Body.gridx = 1;
		gbc_Body.gridy = 2;
		Equipment.add(Body, gbc_Body);

		JLabel RightHand = new JLabel(temp.getRight().getIcon());
		RightHand.setBorder(new CompoundBorder(new BevelBorder(
				BevelBorder.RAISED, null, null, null, null), new BevelBorder(
				BevelBorder.LOWERED, null, null, null, null)));
		RightHand.setText("Right");
		RightHand.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_RightHand = new GridBagConstraints();
		gbc_RightHand.insets = new Insets(0, 0, 5, 0);
		gbc_RightHand.fill = GridBagConstraints.BOTH;
		gbc_RightHand.gridx = 2;
		gbc_RightHand.gridy = 2;
		Equipment.add(RightHand, gbc_RightHand);

		JLabel Legs = new JLabel(temp.getLegs().getIcon());
		Legs.setHorizontalAlignment(SwingConstants.CENTER);
		Legs.setText("Legs");
		Legs.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED,
				null, null, null, null), new BevelBorder(BevelBorder.LOWERED,
				null, null, null, null)));
		GridBagConstraints gbc_Legs = new GridBagConstraints();
		gbc_Legs.insets = new Insets(0, 0, 5, 5);
		gbc_Legs.fill = GridBagConstraints.BOTH;
		gbc_Legs.gridx = 1;
		gbc_Legs.gridy = 3;
		Equipment.add(Legs, gbc_Legs);

		JLabel Feet = new JLabel(temp.getFeet().getIcon());
		Feet.setHorizontalAlignment(SwingConstants.CENTER);
		Feet.setText("Feet");
		Feet.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED,
				null, null, null, null), new BevelBorder(BevelBorder.LOWERED,
				null, null, null, null)));
		GridBagConstraints gbc_Feet = new GridBagConstraints();
		gbc_Feet.insets = new Insets(0, 0, 5, 5);
		gbc_Feet.fill = GridBagConstraints.BOTH;
		gbc_Feet.gridx = 1;
		gbc_Feet.gridy = 4;
		Equipment.add(Feet, gbc_Feet);

		JLabel Accessories = new JLabel(temp.getAccessories().getIcon());
		Accessories.setHorizontalAlignment(SwingConstants.CENTER);
		Accessories.setText("Extra");
		Accessories.setBorder(new CompoundBorder(new BevelBorder(
				BevelBorder.RAISED, null, null, null, null), new BevelBorder(
				BevelBorder.LOWERED, null, null, null, null)));
		GridBagConstraints gbc_Accessories = new GridBagConstraints();
		gbc_Accessories.insets = new Insets(0, 0, 5, 0);
		gbc_Accessories.fill = GridBagConstraints.BOTH;
		gbc_Accessories.gridx = 2;
		gbc_Accessories.gridy = 4;
		Equipment.add(Accessories, gbc_Accessories);

		JPanel panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(10, 3));
		panel_1.setMinimumSize(new Dimension(5, 3));
		panel_1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null,
				null));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridwidth = 3;
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 5;
		Equipment.add(panel_1, gbc_panel_1);
		
		JPanel Title2 = new JPanel();
		Title2.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
		GridBagConstraints gbc_Title2 = new GridBagConstraints();
		gbc_Title2.gridwidth = 11;
		gbc_Title2.insets = new Insets(0, 0, 5, 0);
		gbc_Title2.fill = GridBagConstraints.BOTH;
		gbc_Title2.gridx = 4;
		gbc_Title2.gridy = 6;
		add(Title2, gbc_Title2);
		
		JLabel lblStats = new JLabel("STATS");
		Title2.add(lblStats);

		JPanel Stats = new JPanel();
		Stats.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
		GridBagConstraints gbc_Stats = new GridBagConstraints();
		gbc_Stats.gridheight = 2;
		gbc_Stats.gridwidth = 11;
		gbc_Stats.fill = GridBagConstraints.BOTH;
		gbc_Stats.gridx = 4;
		gbc_Stats.gridy = 7;
		add(Stats, gbc_Stats);
		Stats.setLayout(new GridLayout(0, 1, 0, 0));
		JPanel inside = new Stats();
		inside.setBorder(new EmptyBorder(0,20,10,0));
		Stats.add(inside);

		JPanel Options = new JPanel();
		GridBagConstraints gbc_Options = new GridBagConstraints();
		gbc_Options.gridheight = 5;
		gbc_Options.gridwidth = 4;
		gbc_Options.fill = GridBagConstraints.BOTH;
		gbc_Options.gridx = 0;
		gbc_Options.gridy = 10;
		Outer.add(Options, gbc_Options);
		Options.setLayout(new GridLayout(4, 2, 0, 0));

		ButtonGroup b = new ButtonGroup();

		JCheckBox chckbxAll = new JCheckBox("All");
		Options.add(chckbxAll);
		b.add(chckbxAll);

		JCheckBox chckbxWeapons = new JCheckBox("Weapons");
		Options.add(chckbxWeapons);
		b.add(chckbxWeapons);

		JCheckBox chckbxHead = new JCheckBox("Helmet");
		Options.add(chckbxHead);
		b.add(chckbxHead);

		JCheckBox chckbxArmor = new JCheckBox("Armor");
		Options.add(chckbxArmor);
		b.add(chckbxArmor);

		JCheckBox chckbxPants = new JCheckBox("Pants");
		Options.add(chckbxPants);
		b.add(chckbxPants);

		JCheckBox chckbxNewCheckBox = new JCheckBox("Boots");
		Options.add(chckbxNewCheckBox);
		b.add(chckbxNewCheckBox);

		JCheckBox chckbxOthers = new JCheckBox("Others");
		Options.add(chckbxOthers);
		b.add(chckbxOthers);


		// add equipped and stats
	}
	
	public EquipInventory getInv()
	{
		return e;
	}


	private JPanel contentPane;
	private JLabel Head_1;
	static JFrame frame;
	public static void ShowScreen() throws Exception {
		frame = new JFrame("Mapzzz");// initializes frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// sets closing
																// upon click of
																// x
		frame.getContentPane().add(new StatsPanel(new Menu(null)));
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

	@Override
	public void stateChanged(ChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public EquipInventory getEquipInventory() {
		return e;
	}
}
