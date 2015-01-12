package field;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import main.GameFrameNew;

/**
 * 
 * Provides a rain effect to the screen.
 *
 */
public class RainPanel extends JPanel {
	private Timer rainTimer, lightningTimer;
	private int[] x, y; // Coordinates of the raindrops
	private double wind = 0;
	private boolean storm, flash;
	
	/**
	 * Constructs a new RainPanel.
	 * 
	 * @param map - the panel this will be laid over
	 * @param frequency - the number of raindrops on screen at once
	 * @param storm - turns lightning effects on and off
	 */
	public RainPanel(int frequency, boolean storm) {
		this.storm = storm;
		
		x = new int[frequency];
		y = new int[frequency];
		
		// Set panel properties
		setFocusable(false);
		setOpaque(false);
		//setPreferredSize(new Dimension(GameFrameNew.WIDTH, GameFrameNew.HEIGHT));
		setBounds(0, 0, GameFrameNew.WIDTH, GameFrameNew.HEIGHT);
		
		rainTimer = new Timer(200, new Repainter());
		
		if (storm) {
			lightningTimer = new Timer(1000, new LightningHandler());
		}
	}
	
	/**
	 * Creates a RainPanel with no rain and no lightning.
	 * 
	 * @param map - the 
	 */
	public RainPanel(MapPanel map) {
		this(0, false);
	}
	
	/**
	 * Starts the panel effects.
	 */
	public void start() {
		rainTimer.start();
		if (storm)
			lightningTimer.start();
		
		setVisible(true);
	}
	
	/**
	 * Stops the panel effects. 
	 */
	public void stop() {
		rainTimer.stop();
		if (storm)
			lightningTimer.stop();
		
		setVisible(false);
	}
	
	/**
	 * Sets the number of raindrops on the screen at any moment.
	 * 
	 * @param freq - the number of raindrops
	 */
	public void setFrequency(int freq) {
		x = new int[freq];
		y = new int[freq];
	}
	
	@Override
	public void paintComponent(Graphics g) {
		//super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(new Color(129, 168, 184, 100));
		g2.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
		
		for (int i = 0; i < x.length; i++) {
			g2.drawLine(x[i], y[i], x[i] + (int) wind, y[i] + (int) 60);
		}
		
		if (flash) {
			g2.setColor(new Color(255, 255, 255, 200));
			g2.fillRect(0, 0, GameFrameNew.WIDTH+40, GameFrameNew.HEIGHT+10);
		}
	}
	
	/**
	 * Turn the lightning effects on and off.
	 * 
	 * @param lightning - if true, lightning effects are enabled
	 */
	public void setStorm(boolean lightning) {
		if (lightning) {
			storm = true;
			lightningTimer = new Timer(2000, new LightningHandler());
			lightningTimer.start();
		} else {
			storm = false;
			lightningTimer.stop();
		}
	}
	
	/**
	 * Returns whether or not lightning effects are on.
	 * @return - true if lightning effects are on, otherwise false
	 */
	public boolean isStorm() {
		return storm;
	}

	/**
	 * 
	 * Refreshes the screen and repositions the rain.
	 *
	 */
	private class Repainter implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// Create new coordinates for raindrops
			for (int i = 0; i < x.length; i++) {
				x[i] = (int) (Math.random() * GameFrameNew.WIDTH);
				y[i] = (int) (Math.random() * GameFrameNew.HEIGHT);
			}
			
			// Change the wind speed
			wind += Math.random() * 6 - 3;
			if (wind > 60)
				wind = 60;
			else if (wind < -60)
				wind = -60;
			
			repaint();
		}
		
	}
	
	/**
	 * 
	 * Checks whether to show lightning
	 *
	 */
	private class LightningHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (Math.random() < 0.05) {
				flash = true;
				Timer t = new Timer(300, new LightningOff());
				t.setRepeats(false);
				t.start();
			}
		}
	}
	
	private class LightningOff implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			flash = false;
		}
	}
	
//	public static void main(String[] args) {
//		JFrame f = new JFrame();
//		MapPanel nmp = new MapPanel();
//		nmp.add(new RainPanel(nmp.getPreferredSize()));
//		
//		f.add(nmp);
//		f.setTitle("It's raining!");
//		
//		f.pack();
//		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		f.setVisible(true);
//	}
}
