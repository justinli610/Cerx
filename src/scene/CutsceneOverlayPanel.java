package scene;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;

import ui.StyleConstants;

/**
 * 
 * The Dialogbox is shown on screen whenever a Cutscene occurs. It blocks user input
 * to prevent Characters from moving, and allows the user to advance dialogue by pressing
 * the X key. It also displays an icon to alert the user that a Cutscene is taking place.
 *
 */
public class CutsceneOverlayPanel extends JPanel implements KeyListener, FocusListener {
	private static final Color brown = new Color(182,83,35);
	private static final Color brown2 = new Color(105,48,20);
	private Color background; // Controls fading colour
	private JTextArea dialog;
	private JLabel image;
	private JPanel dPanel;
	
	private int opacity = 255;
	
	// Keep a reference to the current cutscene
	private Cutscene cutscene;
	
	public CutsceneOverlayPanel() {
		JPanel iconPanel;
		ImageIcon sceneIcon;
		
		background = new Color(0, 0, 0, opacity);
		
		//If image
		image = new JLabel();
		
		dialog = new JTextArea() { // Prevents user from selecting text
			@Override
			public void addMouseMotionListener(MouseMotionListener m) {} // Do nothing
			@Override
			public void addMouseListener(MouseListener m) {} // Do nothing
		};
		dialog.setLineWrap(true);
		dialog.setWrapStyleWord(true);
		dialog.setFont(new Font("Garamond", Font.PLAIN, 20));
		dialog.setOpaque(false);
		dialog.setForeground(Color.white);
		dialog.setEditable(false);
		dialog.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		CompoundBorder compound = BorderFactory.createCompoundBorder(
				BorderFactory.createBevelBorder(BevelBorder.RAISED,brown,brown2), 
				BorderFactory.createBevelBorder(BevelBorder.LOWERED,brown,brown2));
		
		image.setAlignmentX(Component.TOP_ALIGNMENT);
		image.setFont(StyleConstants.DIALOGUE_FONT);
		image.setForeground(Color.white);
		image.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(0, 0, 10, 0),
				BorderFactory.createMatteBorder(0,0,2,0, brown2)));
		
		sceneIcon = new ImageIcon("Misc\\loading.png");
		
		iconPanel = new JPanel();
		iconPanel.setLayout(new BoxLayout(iconPanel, BoxLayout.X_AXIS));
		iconPanel.setOpaque(false);
		iconPanel.add(Box.createHorizontalGlue());
		iconPanel.add(new JLabel(sceneIcon));
		
		dPanel = new JPanel();
		dPanel.setLayout(new BoxLayout(dPanel,BoxLayout.Y_AXIS));
		dPanel.setBackground(new Color(0, 0, 0, 150));		
		dPanel.setBorder(BorderFactory.createCompoundBorder(
				compound, BorderFactory.createEmptyBorder(10,20,10,20)));
		
		dPanel.add(image);
		dPanel.add(dialog);
		dPanel.setMinimumSize(new Dimension(700, 300));
		dPanel.setMaximumSize(new Dimension(700, 300));
		dPanel.setAlignmentX(CENTER_ALIGNMENT);
		
		// Panel properties
		addKeyListener(this);
		addFocusListener(this);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(new Color(0, 0, 0, 0));
		setOpaque(false);
		setFocusable(true);
		
		// Add components
		// TODO: Add an icon that shows the user they're not in control at the moment
		add(iconPanel);
		add(Box.createVerticalGlue());
		add(dPanel);
		add(Box.createVerticalStrut(10));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		getParent().repaint();
		g.setColor(new Color(background.getRed(), background.getGreen(), background.getBlue(), opacity));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(dPanel.getBackground());
		g.fillRect(dPanel.getX(), dPanel.getY(), dPanel.getWidth(), dPanel.getHeight());
	}
	
	/**
	 * Provide the Dialogbox with a reference to the currently executing cut scene.
	 * 
	 * @param scene - the Cutscene to register
	 */
	public void registerCutscene(Cutscene scene) {
		cutscene = scene;
	}
	
	/**
	 * Replaces the name and body text of the dialog box.
	 * 
	 * @param name - the name of the speaker
	 * @param text - the text to speak
	 */
	public void set(String name, String text) {
		image.setText(name);
		dialog.setText(text);
	}
	
	public void setDialogVisible(boolean b) {
		dPanel.setVisible(b);
	}
	
	public int getOpacity() {
		return opacity;
	}

	public void setOpacity(int opacity) {
		if (opacity > 255)
			this.opacity = 255;
		else if (opacity < 0)
			this.opacity = 0;
		else
			this.opacity = opacity;
	}
	
	public void setColor(Color c) {
		background = new Color (c.getRed(), c.getGreen(), c.getBlue(), opacity);
	}

//	public static void main(String[] args) {
//		// Schedule a job for the event dispatch thread:
//		// creating and showing this application's GUI.
//		SwingUtilities.invokeLater(new Runnable() {
//			public void run() {
//				// Turn off metal's use of bold fonts
//				//UIManager.put("swing.boldMetal", Boolean.FALSE);
//				ShowScreen();
//			}
//		});
//	}


	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == 'x') {
			// Hide the Dialogbox
			setDialogVisible(false);
			
			// Activate the next event
			if (cutscene.isReady())
				cutscene.next();
		}
	}

	@Override
	public void focusGained(FocusEvent e) {}

	@Override
	public void focusLost(FocusEvent e) {
		// Pass focus to the text field
		if (isVisible())
			grabFocus();
	}	
}
