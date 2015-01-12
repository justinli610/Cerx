package fieldCreator;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;

import field.Landscape;
import field.Map;
import field.MapUtilities;
import field.Terrain;

// The menu bar
public class NewMapMenuBar extends JMenuBar {
	private NewMapPanel mapPanel;
	
	// Constructor
	public NewMapMenuBar(NewMapPanel m) {
		mapPanel = m;
		
		initialise();
	}
	
    // Set up the menu bar
	private void initialise() {
		JMenu[] options = new JMenu[4];
		JMenuItem[] file = new JMenuItem[4];
		JMenuItem[] view = new JMenuItem[4];
		JMenuItem[] dimensions = new JMenuItem[4];
		JMenuItem help;
		
		MenuHandler fmh = new MenuHandler();
		
		//////////////////////////////////////////////////
		// Define all the menu options and attach handlers
		
		file[0] = new JMenuItem("New");
		file[1] = new JMenuItem("Save As...");
		file[2] = new JMenuItem("Load...");
		file[3] = new JMenuItem("Create image");
		file[0].addActionListener(fmh);
		file[1].addActionListener(fmh);
		file[2].addActionListener(fmh);
		file[3].addActionListener(fmh);
		
		view[0] = new JMenuItem("Toggle terrain");
		view[1] = new JMenuItem("Toggle landscape");
		view[2] = new JMenuItem("Toggle logicscape");
		view[3] = new JMenuItem("Toggle grid");
		view[0].addActionListener(fmh); 
		view[1].addActionListener(fmh);
		view[2].addActionListener(fmh);
		view[3].addActionListener(fmh);
		
		dimensions[0] = new JMenuItem("Add row");
		dimensions[1] = new JMenuItem("Remove row");
		dimensions[2] = new JMenuItem("Add column");
		dimensions[3] = new JMenuItem("Remove column");
		dimensions[0].addActionListener(fmh);
		dimensions[1].addActionListener(fmh);
		dimensions[2].addActionListener(fmh);
		dimensions[3].addActionListener(fmh);
		
		help = new JMenuItem("Help");
		help.addActionListener(fmh);
		
		options[0] = new JMenu("File"); // Save, load
		for (JMenuItem i : file)
			options[0].add(i);
		
		options[1] = new JMenu("View"); // Toggle options
		for (JMenuItem i : view)
			options[1].add(i);
		
		options[2] = new JMenu("Add/Remove"); // Add/remove rows/columns
		for (JMenuItem i : dimensions)
			options[2].add(i);
		
		options[3] = new JMenu("View Help"); // Help
		options[3].add(help);
		
		// Add the menu items to the menu bar
		add(options[0]);
		add(options[1]);
		add(options[2]);
		add(options[3]);
	}
    
    // Create a new map
	private void newMap() {
    	// Prompt user for dimensions
		NewMapWindow nmw = new NewMapWindow();
    	nmw.setVisible(true);
    	
    	// Only create a map if user pressed to confirm
    	if (nmw.getConfirmed()) {
			try {
				Map newMap = new Map(nmw.getRows(), nmw.getCols(), nmw.getTemplate());
				mapPanel.replaceMap(newMap);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
    }

	private void save() {
		MapUtilities.save(mapPanel.getMap());
    }
    
    private void load() {
    	Map m = MapUtilities.load();
    	
    	if (m != null) {
    		mapPanel.replaceMap(m);
    	}
    	
    	mapPanel.revalidate();
	}
    
    private void help() {
    	String message = "Press the mnemonic key corresponding to the desired tile and click or drag to add items. " +
    			"Terrain tiles use either the SHIFT key or no modifiers. Landscape tiles use the CTRL key. " +
    			"Logicscape tiles use the ALT key. CTRL+SPACE is the universal deletion combination.\n\n" +
    			"Save as a folder and load as a folder. Always include a folder name when saving (don't use " +
    			"an existing directory unless overwriting a file). \n\n" +
    			"To add or remove columns, click an option in the menu bar, and then click on the desired spot " +
    			"in the map where the action is to be performed. To add a row or column at the end of the map, " +
    			"select an add option in the menu bar and press the END key.";
    	
    	JTextArea area = new JTextArea(14, 32);
    	area.setBackground(new Color(238, 238, 238));
    	area.setText(message);
    	area.setLineWrap(true);
    	area.setWrapStyleWord(true);
    	area.setEditable(false);
    	
    	JOptionPane.showMessageDialog(null, area, "Help", JOptionPane.INFORMATION_MESSAGE);
    }
    
    class MenuHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("New"))
				newMap();
			else if (e.getActionCommand().equals("Save As..."))
				save();
			else if (e.getActionCommand().equals("Load..."))
				load();
			else if (e.getActionCommand().equals("Create image"))
				MapUtilities.writeImage(mapPanel.getMap());
			else if (e.getActionCommand().equals("Help"))
				help();
			else if (e.getActionCommand().equals("Toggle terrain"))
				mapPanel.toggleTerrain();
			else if (e.getActionCommand().equals("Toggle landscape"))
				mapPanel.toggleLandscape();
			else if (e.getActionCommand().equals("Toggle logicscape"))
				mapPanel.toggleLogicscape();
			else if (e.getActionCommand().equals("Toggle grid"))
				mapPanel.toggleGrid();
			else if (e.getActionCommand().equals("Add row")) {
				mapPanel.setMode(NewMapPanel.ADD_ROW);
				mapPanel.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
			} else if (e.getActionCommand().equals("Remove row")) {
				mapPanel.setMode(NewMapPanel.RMV_ROW);
				mapPanel.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
			} else if (e.getActionCommand().equals("Add column")) {
				mapPanel.setMode(NewMapPanel.ADD_COL);
				mapPanel.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
			} else if (e.getActionCommand().equals("Remove column")) {
				mapPanel.setMode(NewMapPanel.RMV_COL);
				mapPanel.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
			}
			
			mapPanel.repaint();
		}
    	
    }
}
