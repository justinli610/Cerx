package battleGUI;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

/**
 * 
 * Handles the messages that come up in battle.
 *
 */
public class Infobar extends JPanel {
	private static final Color brown = new Color(182,83,35);
	private static final Color brown2 = new Color(105,48,20);
	
	// The timers currently requesting the info bar
	private static ArrayList<Timer> timers = new ArrayList<Timer>(2);
	
	private JTextPane dialog;
	private JPanel dPanel;
	/**
	 * Create the panel.
	 */
	public Infobar() {
		dialog = new JTextPane() { // Prevents user from selecting text
			@Override
			public void addMouseMotionListener(MouseMotionListener m) {} // Do nothing
			@Override
			public void addMouseListener(MouseListener m) {} // Do nothing
		};
		StyledDocument document = new DefaultStyledDocument();
		Style defaultStyle = document.getStyle(StyleContext.DEFAULT_STYLE);
		StyleConstants.setAlignment(defaultStyle, StyleConstants.ALIGN_CENTER);
		
		dialog.setDocument(document);
		dialog.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 24));
		dialog.setOpaque(false);
		dialog.setForeground(Color.white);
		dialog.setEditable(false);
		dialog.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		CompoundBorder compound = BorderFactory.createCompoundBorder(
				BorderFactory.createBevelBorder(BevelBorder.RAISED,brown,brown2), 
				BorderFactory.createBevelBorder(BevelBorder.LOWERED,brown,brown2));
		
		dPanel = new JPanel();
		dPanel.setLayout(new BoxLayout(dPanel,BoxLayout.Y_AXIS));
		dPanel.setBackground(new Color(0, 0, 0, 150));		
		dPanel.setBorder(BorderFactory.createCompoundBorder(
				compound, BorderFactory.createEmptyBorder(10,20,10,20)));
		
		dPanel.add(dialog);
		dPanel.setMinimumSize(new Dimension(700, 300));
		dPanel.setMaximumSize(new Dimension(700, 300));
		dPanel.setAlignmentX(CENTER_ALIGNMENT);
		dPanel.setVisible(false);
		
		// Panel properties
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(new Color(0, 0, 0, 0));
		setOpaque(false);
		setFocusable(true);
		
		// Add components
		add(Box.createVerticalStrut(20));
		add(dPanel);
		add(Box.createVerticalGlue());
	}
	
	private ExecutorService executor = Executors.newSingleThreadExecutor();

	/**
	 * Sets the text to be displayed by the infobar. The 
	 * @param text - the String to be displayed
	 */
	public void setText(String text) {
		executor.execute(new InfobarTimer(text));
	}
	
	/**
	 * Turns the infobar on and off.
	 * @param b - if true, the dialog box will be shown
	 */
	public void setDialogVisible(boolean b) {
		dPanel.setVisible(b);
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setComposite(AlphaComposite.DstAtop);
		g2.setColor(dPanel.getBackground());
		g2.fillRect(dPanel.getX(), dPanel.getY(), dPanel.getWidth(), dPanel.getHeight());
		g2.setComposite(AlphaComposite.SrcOver);
		super.paintComponent(g2);
	}
	
	/**
	 * 
	 * Hides the info bar after a certain amount of time has elapsed
	 *
	 */
	class InfobarTimer implements Runnable {
		private String text;
		
		public InfobarTimer(String text) {
			this.text = text;
		}

		@Override
		public void run() {
			dialog.setText(text);
			dPanel.setVisible(true);
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			dPanel.setVisible(false);
		}
	}
	
	public static void main(String[] args) {
		JFrame f = new JFrame();
		Infobar info = new Infobar();
		
		f.add(info);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);
		
//		Thread t = new Thread(info.new InfobarTimer("Hello"));
//		t.start();
		
		info.setText("Hell's Judgment");
		info.setText("Bad Breath");
		info.setText("Devour");
	}
}
