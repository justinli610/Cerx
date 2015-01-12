package menu;


import items.Item;
import items.Potion;
import items.Usable;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



import ui.InventoryRenderer;
import field.GameState;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.FlowLayout;
//Ver.1
public class InventoryPanel extends JPanel implements ChangeListener{

	//released boolean
	Backpack pack;
	/**
	 * @wbp.parser.constructor
	 */
	public InventoryPanel(Menu menu)
	{
		pack = new Backpack(menu);
		setBounds(100, 100, 800, 600);
	
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 30, 0, 30, 75, 30, 30, 30,
				30, 0, 30, 30, 30, 30, 30, 30, 30 };
		gbl_contentPane.rowHeights = new int[] { 0, 38, 137, 395, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0,
				0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gbl_contentPane);

		JPanel Outer = new JPanel();
		Outer.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED,
				null, null, null, null), new BevelBorder(BevelBorder.LOWERED,
				null, null, null, null)));
		GridBagConstraints gbc_Outer = new GridBagConstraints();
		gbc_Outer.insets = new Insets(0, 0, 0, 5);
		gbc_Outer.gridheight = 3;
		gbc_Outer.gridwidth = 4;
		gbc_Outer.fill = GridBagConstraints.BOTH;
		gbc_Outer.gridx = 0;
		gbc_Outer.gridy = 1;
		add(Outer, gbc_Outer);
		GridBagLayout gbl_Outer = new GridBagLayout();
		gbl_Outer.columnWidths = new int[] { 0, 0 };
		gbl_Outer.rowHeights = new int[] { 55, 166, 0, 0, 55, 68, 0, 22, -4,
				51 };
		gbl_Outer.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_Outer.rowWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		Outer.setLayout(gbl_Outer);
		
		JPanel Img = new JPanel();
		GridBagConstraints gbc_Img = new GridBagConstraints();
		gbc_Img.gridheight = 3;
		gbc_Img.insets = new Insets(0, 0, 5, 0);
		gbc_Img.fill = GridBagConstraints.BOTH;
		gbc_Img.gridx = 0;
		gbc_Img.gridy = 0;
		Outer.add(Img, gbc_Img);
		Img.setLayout(new BorderLayout(0, 0));
		
		JLabel lblImage = new JLabel("Sample Image");
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		Img.add(lblImage);
		
		JPanel Seperator1 = new JPanel();
		Seperator1.setPreferredSize(new Dimension(10, 3));
		Seperator1.setMinimumSize(new Dimension(10, 3));
		Seperator1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		GridBagConstraints gbc_Seperator1 = new GridBagConstraints();
		gbc_Seperator1.fill = GridBagConstraints.BOTH;
		gbc_Seperator1.gridx = 0;
		gbc_Seperator1.gridy = 3;
		Outer.add(Seperator1, gbc_Seperator1);
		
		JPanel Function = new JPanel();
		GridBagConstraints gbc_Function = new GridBagConstraints();
		gbc_Function.insets = new Insets(0, 0, 5, 0);
		gbc_Function.gridheight = 4;
		gbc_Function.fill = GridBagConstraints.BOTH;
		gbc_Function.gridx = 0;
		gbc_Function.gridy = 4;
		Outer.add(Function, gbc_Function);
		GridBagLayout gbl_Function = new GridBagLayout();
		gbl_Function.columnWidths = new int[]{258, 0};
		gbl_Function.rowHeights = new int[]{145, -17, 67, 0, 72, 0};
		gbl_Function.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_Function.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		Function.setLayout(gbl_Function);
		
		JTextArea ItemDescript = new JTextArea();
		ItemDescript.setWrapStyleWord(true);
		ItemDescript.setLineWrap(true);
		ItemDescript.setFont(new Font("Monospaced", Font.PLAIN, 13));
		ItemDescript.setEditable(false);
		ItemDescript.setText("Item Description");
		GridBagConstraints gbc_ItemDescript = new GridBagConstraints();
		gbc_ItemDescript.fill = GridBagConstraints.BOTH;
		gbc_ItemDescript.gridx = 0;
		gbc_ItemDescript.gridy = 0;
		Function.add(ItemDescript, gbc_ItemDescript);
		
		JPanel Seperator2 = new JPanel();
		Seperator2.setPreferredSize(new Dimension(10, 3));
		Seperator2.setMinimumSize(new Dimension(10, 3));
		Seperator2.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		GridBagConstraints gbc_Seperator2 = new GridBagConstraints();
		gbc_Seperator2.fill = GridBagConstraints.BOTH;
		gbc_Seperator2.gridx = 0;
		gbc_Seperator2.gridy = 1;
		Function.add(Seperator2, gbc_Seperator2);
		
		JButton Use = new JButton("Use(X)");
		GridBagConstraints gbc_Use = new GridBagConstraints();
		gbc_Use.fill = GridBagConstraints.BOTH;
		gbc_Use.gridx = 0;
		gbc_Use.gridy = 2;
		Function.add(Use, gbc_Use);
		
		JPanel Seperator3 = new JPanel();
		Seperator3.setPreferredSize(new Dimension(10, 3));
		Seperator3.setMinimumSize(new Dimension(10, 3));
		Seperator3.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		GridBagConstraints gbc_Seperator3 = new GridBagConstraints();
		gbc_Seperator3.fill = GridBagConstraints.BOTH;
		gbc_Seperator3.gridx = 0;
		gbc_Seperator3.gridy = 3;
		Function.add(Seperator3, gbc_Seperator3);
		
		JButton Destroy = new JButton("Destroy (W)");
		
		GridBagConstraints gbc_Destroy = new GridBagConstraints();
		gbc_Destroy.fill = GridBagConstraints.BOTH;
		gbc_Destroy.gridx = 0;
		gbc_Destroy.gridy = 4;
		Function.add(Destroy, gbc_Destroy);
		
		JPanel Seperator4 = new JPanel();
		Seperator4.setPreferredSize(new Dimension(10, 3));
		Seperator4.setMinimumSize(new Dimension(10, 3));
		Seperator4.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		GridBagConstraints gbc_Seperator4 = new GridBagConstraints();
		gbc_Seperator4.insets = new Insets(0, 0, 5, 0);
		gbc_Seperator4.fill = GridBagConstraints.BOTH;
		gbc_Seperator4.gridx = 0;
		gbc_Seperator4.gridy = 8;
		Outer.add(Seperator4, gbc_Seperator4);
		
		JPanel Sort = new JPanel();
		GridBagConstraints gbc_Sort = new GridBagConstraints();
		gbc_Sort.fill = GridBagConstraints.BOTH;
		gbc_Sort.gridx = 0;
		gbc_Sort.gridy = 9;
		Outer.add(Sort, gbc_Sort);
		GridBagLayout gbl_Sort = new GridBagLayout();
		gbl_Sort.columnWidths = new int[]{85, 85, 85, 0};
		gbl_Sort.rowHeights = new int[]{39, 0};
		gbl_Sort.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_Sort.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		Sort.setLayout(gbl_Sort);
		
		JCheckBox All = new JCheckBox("All (A)");
		GridBagConstraints gbc_All = new GridBagConstraints();
		gbc_All.fill = GridBagConstraints.BOTH;
		gbc_All.insets = new Insets(0, 0, 0, 5);
		gbc_All.gridx = 0;
		gbc_All.gridy = 0;
		Sort.add(All, gbc_All);
		
		JCheckBox Usable = new JCheckBox("Usable (S)");
		GridBagConstraints gbc_Usable = new GridBagConstraints();
		gbc_Usable.fill = GridBagConstraints.BOTH;
		gbc_Usable.insets = new Insets(0, 0, 0, 5);
		gbc_Usable.gridx = 1;
		gbc_Usable.gridy = 0;
		Sort.add(Usable, gbc_Usable);
		
		JCheckBox quest = new JCheckBox("Quest Items (D)");
		GridBagConstraints gbc_quest = new GridBagConstraints();
		gbc_quest.fill = GridBagConstraints.BOTH;
		gbc_quest.gridx = 2;
		gbc_quest.gridy = 0;
		Sort.add(quest, gbc_quest);
		
		ButtonGroup b = new ButtonGroup();
		b.add(quest);
		b.add(Usable);
		b.add(All);
		
		JPanel Title = new JPanel();
		Title.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
		GridBagConstraints gbc_Title = new GridBagConstraints();
		gbc_Title.gridwidth = 11;
		gbc_Title.insets = new Insets(0, 0, 5, 0);
		gbc_Title.fill = GridBagConstraints.BOTH;
		gbc_Title.gridx = 4;
		gbc_Title.gridy = 1;
		add(Title, gbc_Title);
		
		JLabel lblInventory = new JLabel("INVENTORY");
		Title.add(lblInventory);
	

		JScrollPane Inventory = new JScrollPane();
		Inventory.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
		GridBagConstraints gbc_Inventory = new GridBagConstraints();
		gbc_Inventory.gridheight = 2;
		gbc_Inventory.gridwidth = 11;
		gbc_Inventory.fill = GridBagConstraints.BOTH;
		gbc_Inventory.gridx = 4;
		gbc_Inventory.gridy = 2;
		add(Inventory, gbc_Inventory);
		

		
		JPanel Temp = new JPanel();
		Inventory.setViewportView(Temp);
		Temp.setLayout(new BorderLayout(0, 0));
		Temp.add(pack);
		pack.setLayout(new GridLayout(1, 0, 0, 0));

		//ArrayList a = g.getInventory;
		//ArrayList b = a.clone();
		//JSplit Panel
		
		//Left side, GridbagLayout
		//Add JLabel to top with border
		//Add JTextArea? to bottom
		//Add 2 JButtons beside TextArea "Use" and "Destroy"
		
		//Add JCheckbox to the bottom
		
		//Right Side JList
		//loop ArrayList b length, fill JList with Inventory Item names
		
		//Add Keybindings
	}
	
	public Backpack getBackpack()
	{
		return pack;
	}
	
	public InventoryPanel(Menu menu, GameState g)
	{
		pack = new Backpack(menu);
		setBounds(100, 100, 800, 600);
	
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 30, 0, 30, 75, 30, 30, 30,
				30, 0, 30, 30, 30, 30, 30, 30, 30 };
		gbl_contentPane.rowHeights = new int[] { 0, 38, 137, 395, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0,
				0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gbl_contentPane);

		JPanel Outer = new JPanel();
		Outer.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED,
				null, null, null, null), new BevelBorder(BevelBorder.LOWERED,
				null, null, null, null)));
		GridBagConstraints gbc_Outer = new GridBagConstraints();
		gbc_Outer.insets = new Insets(0, 0, 0, 5);
		gbc_Outer.gridheight = 3;
		gbc_Outer.gridwidth = 4;
		gbc_Outer.fill = GridBagConstraints.BOTH;
		gbc_Outer.gridx = 0;
		gbc_Outer.gridy = 1;
		add(Outer, gbc_Outer);
		GridBagLayout gbl_Outer = new GridBagLayout();
		gbl_Outer.columnWidths = new int[] { 0, 0 };
		gbl_Outer.rowHeights = new int[] { 55, 166, 0, 0, 55, 68, 0, 22, -4,
				51 };
		gbl_Outer.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_Outer.rowWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		Outer.setLayout(gbl_Outer);
		
		JPanel Img = new JPanel();
		GridBagConstraints gbc_Img = new GridBagConstraints();
		gbc_Img.gridheight = 3;
		gbc_Img.insets = new Insets(0, 0, 5, 0);
		gbc_Img.fill = GridBagConstraints.BOTH;
		gbc_Img.gridx = 0;
		gbc_Img.gridy = 0;
		Outer.add(Img, gbc_Img);
		Img.setLayout(new BorderLayout(0, 0));
		
		JLabel lblImage = new JLabel(pack.getCurrentItem().getIcon());
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		Img.add(lblImage);
		
		JPanel Seperator1 = new JPanel();
		Seperator1.setPreferredSize(new Dimension(10, 3));
		Seperator1.setMinimumSize(new Dimension(10, 3));
		Seperator1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		GridBagConstraints gbc_Seperator1 = new GridBagConstraints();
		gbc_Seperator1.fill = GridBagConstraints.BOTH;
		gbc_Seperator1.gridx = 0;
		gbc_Seperator1.gridy = 3;
		Outer.add(Seperator1, gbc_Seperator1);
		
		JPanel Function = new JPanel();
		GridBagConstraints gbc_Function = new GridBagConstraints();
		gbc_Function.insets = new Insets(0, 0, 5, 0);
		gbc_Function.gridheight = 4;
		gbc_Function.fill = GridBagConstraints.BOTH;
		gbc_Function.gridx = 0;
		gbc_Function.gridy = 4;
		Outer.add(Function, gbc_Function);
		GridBagLayout gbl_Function = new GridBagLayout();
		gbl_Function.columnWidths = new int[]{258, 0};
		gbl_Function.rowHeights = new int[]{145, -17, 67, 0, 72, 0};
		gbl_Function.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_Function.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		Function.setLayout(gbl_Function);
		
		JTextArea ItemDescript = new JTextArea();
		ItemDescript.setWrapStyleWord(true);
		ItemDescript.setLineWrap(true);
		ItemDescript.setFont(new Font("Monospaced", Font.PLAIN, 13));
		ItemDescript.setEditable(false);
		ItemDescript.setText(pack.getCurrentItem().getDescription());
		GridBagConstraints gbc_ItemDescript = new GridBagConstraints();
		gbc_ItemDescript.fill = GridBagConstraints.BOTH;
		gbc_ItemDescript.gridx = 0;
		gbc_ItemDescript.gridy = 0;
		Function.add(ItemDescript, gbc_ItemDescript);
		
		JPanel Seperator2 = new JPanel();
		Seperator2.setPreferredSize(new Dimension(10, 3));
		Seperator2.setMinimumSize(new Dimension(10, 3));
		Seperator2.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		GridBagConstraints gbc_Seperator2 = new GridBagConstraints();
		gbc_Seperator2.fill = GridBagConstraints.BOTH;
		gbc_Seperator2.gridx = 0;
		gbc_Seperator2.gridy = 1;
		Function.add(Seperator2, gbc_Seperator2);
		
		JButton Use = new JButton("Use(Q)");
		GridBagConstraints gbc_Use = new GridBagConstraints();
		gbc_Use.fill = GridBagConstraints.BOTH;
		gbc_Use.gridx = 0;
		gbc_Use.gridy = 2;
		Function.add(Use, gbc_Use);
		
		JPanel Seperator3 = new JPanel();
		Seperator3.setPreferredSize(new Dimension(10, 3));
		Seperator3.setMinimumSize(new Dimension(10, 3));
		Seperator3.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		GridBagConstraints gbc_Seperator3 = new GridBagConstraints();
		gbc_Seperator3.fill = GridBagConstraints.BOTH;
		gbc_Seperator3.gridx = 0;
		gbc_Seperator3.gridy = 3;
		Function.add(Seperator3, gbc_Seperator3);
		
		JButton Destroy = new JButton("Destroy (W)");
		
		GridBagConstraints gbc_Destroy = new GridBagConstraints();
		gbc_Destroy.fill = GridBagConstraints.BOTH;
		gbc_Destroy.gridx = 0;
		gbc_Destroy.gridy = 4;
		Function.add(Destroy, gbc_Destroy);
		
		JPanel Seperator4 = new JPanel();
		Seperator4.setPreferredSize(new Dimension(10, 3));
		Seperator4.setMinimumSize(new Dimension(10, 3));
		Seperator4.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		GridBagConstraints gbc_Seperator4 = new GridBagConstraints();
		gbc_Seperator4.insets = new Insets(0, 0, 5, 0);
		gbc_Seperator4.fill = GridBagConstraints.BOTH;
		gbc_Seperator4.gridx = 0;
		gbc_Seperator4.gridy = 8;
		Outer.add(Seperator4, gbc_Seperator4);
		
		JPanel Sort = new JPanel();
		GridBagConstraints gbc_Sort = new GridBagConstraints();
		gbc_Sort.fill = GridBagConstraints.BOTH;
		gbc_Sort.gridx = 0;
		gbc_Sort.gridy = 9;
		Outer.add(Sort, gbc_Sort);
		GridBagLayout gbl_Sort = new GridBagLayout();
		gbl_Sort.columnWidths = new int[]{85, 85, 85, 0};
		gbl_Sort.rowHeights = new int[]{39, 0};
		gbl_Sort.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_Sort.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		Sort.setLayout(gbl_Sort);
		
		JCheckBox All = new JCheckBox("All (A)");
		GridBagConstraints gbc_All = new GridBagConstraints();
		gbc_All.fill = GridBagConstraints.BOTH;
		gbc_All.insets = new Insets(0, 0, 0, 5);
		gbc_All.gridx = 0;
		gbc_All.gridy = 0;
		Sort.add(All, gbc_All);
		
		JCheckBox Usable = new JCheckBox("Usable (S)");
		GridBagConstraints gbc_Usable = new GridBagConstraints();
		gbc_Usable.fill = GridBagConstraints.BOTH;
		gbc_Usable.insets = new Insets(0, 0, 0, 5);
		gbc_Usable.gridx = 1;
		gbc_Usable.gridy = 0;
		Sort.add(Usable, gbc_Usable);
		
		JCheckBox quest = new JCheckBox("Quest Items (D)");
		GridBagConstraints gbc_quest = new GridBagConstraints();
		gbc_quest.fill = GridBagConstraints.BOTH;
		gbc_quest.gridx = 2;
		gbc_quest.gridy = 0;
		Sort.add(quest, gbc_quest);
		


		//getActionMap().put("Next", new Navigate("Next"));
		
		ButtonGroup b = new ButtonGroup();
		b.add(quest);
		b.add(Usable);
		b.add(All);
		
		JPanel Title = new JPanel();
		Title.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
		GridBagConstraints gbc_Title = new GridBagConstraints();
		gbc_Title.gridwidth = 11;
		gbc_Title.insets = new Insets(0, 0, 5, 0);
		gbc_Title.fill = GridBagConstraints.BOTH;
		gbc_Title.gridx = 4;
		gbc_Title.gridy = 1;
		add(Title, gbc_Title);
		
		JLabel lblInventory = new JLabel("INVENTORY");
		Title.add(lblInventory);
	

		JScrollPane Inventory = new JScrollPane();
		Inventory.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
		GridBagConstraints gbc_Inventory = new GridBagConstraints();
		gbc_Inventory.gridheight = 2;
		gbc_Inventory.gridwidth = 11;
		gbc_Inventory.fill = GridBagConstraints.BOTH;
		gbc_Inventory.gridx = 4;
		gbc_Inventory.gridy = 2;
		add(Inventory, gbc_Inventory);
		

		
		JPanel Temp = new JPanel();
		Inventory.setViewportView(Temp);
		Temp.setLayout(new BorderLayout(0, 0));
		Temp.add(pack);
		pack.setLayout(new GridLayout(1, 0, 0, 0));

		//ArrayList a = g.getInventory;
		//ArrayList b = a.clone();
		//JSplit Panel
		
		//Left side, GridbagLayout
		//Add JLabel to top with border
		//Add JTextArea? to bottom
		//Add 2 JButtons beside TextArea "Use" and "Destroy"
		
		//Add JCheckbox to the bottom
		
		//Right Side JList
		//loop ArrayList b length, fill JList with Inventory Item names
		
		//Add Keybindings
	}
//I'm going to figure out later if I can just use one action listener for everything =/


	
	static JFrame frame;

	public static void ShowScreen() throws Exception {
		frame = new JFrame("Rawr");// initializes frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// sets closing
																// upon click of
																// x
		frame.getContentPane().add(new InventoryPanel(new Menu(null), GameState.getGameState()));
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


	@Override
	public void stateChanged(ChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	} }
