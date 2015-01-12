package fieldCreator;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;

// The new map creator dialog
public class NewMapWindow extends JDialog {
	private boolean okay = false; // Checks which button the user presses
	private int rows, cols;
	private JSpinner spinRows, spinCols;
	private JRadioButton[] buttons = new JRadioButton[3];
	
	// Constructor
	public NewMapWindow() {
		// Create the layout and set the panel properties
		BorderLayout borderLayout = (BorderLayout) getContentPane().getLayout();
		borderLayout.setVgap(10);
		getContentPane().setBackground(Color.WHITE);
		
		// Create an empty margin around the components
		JPanel p = (JPanel) getContentPane();
		p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		// The panel that holds buttons
		JPanel pnlButtons = new JPanel();
		pnlButtons.setBackground(Color.WHITE);
		getContentPane().add(pnlButtons, BorderLayout.SOUTH);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			// Reads from the two JSpinners
			public void actionPerformed(ActionEvent e) {
				rows = ((Integer) spinRows.getModel().getValue()).intValue();
				cols = ((Integer) spinCols.getModel().getValue()).intValue();
				
				// Update the button pressed and close this window
				okay = true;
				dispose();
			}
		});
		btnOk.setPreferredSize(new Dimension(80, 26));
		pnlButtons.add(btnOk);
		
		// The cancel button
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			// Close the window
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnCancel.setPreferredSize(new Dimension(80, 26));
		pnlButtons.add(btnCancel);
		
		// The instruction label
		JLabel lblEnterTheMap = new JLabel("Enter the map dimensions:");
		lblEnterTheMap.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblEnterTheMap.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblEnterTheMap, BorderLayout.NORTH);
		
		// All the non-button components go here
		JPanel pnlCentre = new JPanel();
		pnlCentre.setBackground(Color.WHITE);
		getContentPane().add(pnlCentre, BorderLayout.CENTER);
		pnlCentre.setLayout(new GridLayout(4, 2, 5, 5));
		
		// The label for the row JSpinner
		JLabel lblRows = new JLabel("Rows");
		lblRows.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		pnlCentre.add(lblRows);
		
		// This spinner represents the number of rows
		spinRows = new JSpinner();
		spinRows.setModel(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 5));
		pnlCentre.add(spinRows);
		
		// The label for the column JSpinner
		JLabel lblColumns = new JLabel("Columns");
		lblColumns.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		pnlCentre.add(lblColumns);
		
		// This spinner represents the number of rows
		spinCols = new JSpinner();
		spinCols.setModel(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 5));
		pnlCentre.add(spinCols);
		
		// These buttons provide options for new maps
		
		buttons[0] = new JRadioButton("Grass");
		buttons[0].setBackground(Color.WHITE);
		buttons[0].setSelected(true);
		pnlCentre.add(buttons[0]);
		
		buttons[1] = new JRadioButton("Pavement");
		buttons[1].setBackground(Color.WHITE);
		pnlCentre.add(buttons[1]);
		
		buttons[2] = new JRadioButton("Empty");
		buttons[2].setBackground(Color.WHITE);
		pnlCentre.add(buttons[2]);
		
		// Create a button group to ensure only one is selected
		ButtonGroup group = new ButtonGroup();
		group.add(buttons[0]);
		group.add(buttons[1]);
		group.add(buttons[2]);
		
		// Set the dialog properties
		setAlwaysOnTop(true);
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Create a Map");
		pack();
		setLocationRelativeTo(null);
	}

	// Return the number of rows
	public int getRows() {
		return rows;
	}

	// Return the number of columns
	public int getCols() {
		return cols;
	}
	
	/**
	 * Returns the selected new map template.
	 * 
	 * @return the template
	 */
	public int getTemplate() {
		for (int i = 0; i < buttons.length; i++)
			if (buttons[i].isSelected())
				return i+1;
		return 1;
	}
	
	// Check if the ok button was pressed
	public boolean getConfirmed() {
		return okay;
	}
}
