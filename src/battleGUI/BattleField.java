package battleGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import ui.StyleConstants;
import battleComponents.BattleTarget;
import battleComponents.Character;
import battleComponents.DmgType;
import battleComponents.Status;
import bestiary.BabyBehemoth;
import bestiary.Monster;

/**
 * 
 * The panel onto which all battle graphics are drawn.
 *
 */
public class BattleField extends JPanel {
	private Infobar infoBar;
	
	private Character[] party;
	private Monster[] enemies;
	private BattleTarget[] participants;
	
	private Point[] partyLocations;
	private Point[] enemyLocations;
	private Point[] participantLocations;
	
	private DamageLabel[] partyDamage;
	private DamageLabel[] enemyDamage;
	private DamageLabel[] participantDamage;
	
	private BufferedImage background;
	
//	private JLabel[] partyLabel;
//	private JLabel[] enemyLabel;
//	private JLabel[] participantLabel;
	
	private volatile boolean[] participantMoving;
	
	
	/**
	 * Create the panel.
	 */
	public BattleField(Character[] party, Monster[] enemies) {
		BattleTarget[] participants = new BattleTarget[party.length + enemies.length];
		
		for (int i = 0; i < party.length; i++) {
			participants[i] = party[i];
		}
		for (int i = 0; i < enemies.length; i++) {
			participants[party.length + i] = enemies[i];
		}
		
		this.participants = participants;
		this.party = party;
		this.enemies = enemies;
		
		partyLocations = new Point[party.length];
		enemyLocations = new Point[enemies.length];
		participantLocations = new Point[party.length + enemies.length];
		
		partyDamage = new DamageLabel[party.length];
		enemyDamage = new DamageLabel[enemies.length];
		participantDamage = new DamageLabel[participants.length];
		
		// Set up the DamageLabels
		for (int i = 0; i < partyDamage.length; i++) {
			partyDamage[i] = new DamageLabel();
			participantDamage[i] = partyDamage[i];
		}
		for (int i = 0; i < enemyDamage.length; i++) {
			enemyDamage[i] = new DamageLabel();
			participantDamage[i + party.length] = enemyDamage[i];
		}
		
		// Create participantMoving
		participantMoving = new boolean[party.length + enemies.length];
		
//		partyLabel = new JLabel[party.length];
//		enemyLabel = new JLabel[enemies.length];
//		participantLabel = new JLabel[party.length + enemies.length];
//		
//		for (int i = 0; i < party.length; i++) {
//			partyLabel[i] = new JLabel();
//			participantLabel[i] = partyLabel[i];
//			add(partyLabel[i]);
//		}
//		for (int i = 0; i < enemies.length; i++) {
//			enemyLabel[i] = new JLabel();
//			participantLabel[party.length + i] = enemyLabel[i];
//			add(enemyLabel[i]);
//		}
		
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(800, 600));
		setLayout(new BorderLayout());
		
		try {
			background = ImageIO.read(new File("Misc\\background.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g.create();
		g2.drawImage(background, 0, -100, getWidth(), getHeight() + 100, null);
		g2.translate(0, 100);
		
		int height;
		
		// Figure out how to distribute screen estate
		height = (getHeight() - 100) / (party.length + 1);
		
		// Draw each character
		for (int i = 0; i < party.length; i++) {
			if (!participantMoving[i]) {
				double posY = height
						* (i + 1) - (0.5 * party[i].getBattleModel().getImage()
								.getHeight(null));
				double posX = getWidth() / 20.0 + 80 * (i % 2);
				
				partyLocations[i] = new Point((int) posX, (int) posY);
				participantLocations[i] = partyLocations[i];
				g2.drawImage(party[i].getBattleModel().getImage(), (int) posX,
						(int) (posY), null);
			} else {
				g2.drawImage(party[i].getBattleModel().getImage(), partyLocations[i].x,
						partyLocations[i].y, null);
			}
		}
		
		// Divide up the screen estate for enemies
		height = (getHeight() - 100) / (enemies.length + 1);
		
		for (int i = 0; i < enemies.length; i++) {
			if (!participantMoving[i + party.length]) {
				double posY = height * (i + 1) - (0.5 * enemies[i].getBattleModel().getImage()
								.getHeight(null));
				double posX = getWidth() - (getWidth() / 20 + 120)
						- enemies[i].getBattleModel().getImage().getWidth(null) + 120 * (i % 2);
				
				enemyLocations[i] = new Point((int) posX, (int) posY);
				participantLocations[i + party.length] = enemyLocations[i];
				g2.drawImage(enemies[i].getBattleModel().getImage(),
						(int) posX, (int) (posY), null);
			} else {
				g2.drawImage(enemies[i].getBattleModel().getImage(), enemyLocations[i].x,
						enemyLocations[i].y, null);
			}
		}
		
		drawStatuses2(g2);
		drawDamage(g2);
	}

	/**
	 * Draws status effects on the screen.
	 * @param g - the current Graphics object
	 */
	private void drawStatuses2(Graphics g) {
		for (int i = 0; i < participants.length; i++) {
			double posY = participantLocations[i].getY() + participants[i].getBattleModel().getHitbox().getY();
			double posX = participantLocations[i].getX() + participants[i].getBattleModel().getHitbox().getX();
			
			if (participants[i].getCurrentStatus(Status.BLIND) > 0) {
				g.drawImage(StatusImage.blind.getImage(), (int) posX, (int) posY, null);
				posY -= 16;
			}
			if (participants[i].getCurrentStatus(Status.SILENCE) > 0) {
				g.drawImage(StatusImage.silence.getImage(), (int) posX, (int) posY, null);
				posY -= 16;
			}
			if (participants[i].getCurrentStatus(Status.POISON) > 0) {
				g.drawImage(StatusImage.poison.getImage(), (int) posX, (int) posY, null);
				posY -= 16;
			}
			if (participants[i].getCurrentStatus(Status.SLEEP) > 0) {
				g.drawImage(StatusImage.sleep.getImage(), (int) posX, (int) posY, null);
				posY -= 16;
			}
		}
	}

	JLabel status;
	@Deprecated
	private void drawStatuses() {
		for (int i = 0; i < enemies.length; i++) {
			double posY = enemyLocations[i].getY() + enemies[i].getBattleModel().getHitbox().getY();
			double posX = enemyLocations[i].getX() + enemies[i].getBattleModel().getHitbox().getX();
			
			if (enemies[i].getCurrentStatus(Status.BLIND) > 0) {
				status = new JLabel(StatusImage.blind.getImageIcon());
				status.setBounds((int) posX, (int) posY, 
						(int) status.getPreferredSize().getWidth(), (int) status.getPreferredSize().getHeight());
				infoBar.add(status);
				posY -= 16;
			}
			if (enemies[i].getCurrentStatus(Status.SILENCE) > 0) {
				status = new JLabel(StatusImage.silence.getImageIcon());
				status.setBounds((int) posX, (int) posY, 
						(int) status.getPreferredSize().getWidth(), (int) status.getPreferredSize().getHeight());
				infoBar.add(status);
				posY -= 16;
			}
			if (enemies[i].getCurrentStatus(Status.POISON) > 0) {
				status = new JLabel(StatusImage.poison.getImageIcon());
				status.setBounds((int) posX, (int) posY, 
						(int) status.getPreferredSize().getWidth(), (int) status.getPreferredSize().getHeight());
				infoBar.add(status);
				posY -= 16;
			}
			if (enemies[i].getCurrentStatus(Status.SLEEP) > 0) {
				status = new JLabel(StatusImage.sleep.getImageIcon());
				status.setBounds((int) posX, (int) posY, 
						(int) status.getPreferredSize().getWidth(), (int) status.getPreferredSize().getHeight());
				infoBar.add(status);
				posY -= 16;
			}
		}
		//statusPanel.repaint();
	}
	
	/**
	 * Temporarily displays the amount of damage a character has taken.
	 * @param g2 - the Graphics object used to render this panel
	 */
	private void drawDamage(Graphics2D g2) {
		String damage;
		DmgType t;
		
		double posY, posX;
		int damageAmount, xOffset;
		FontMetrics fontMetrics;
		
		g2.setFont(StyleConstants.DAMAGE_FONT);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		
		// Check each participant's damage label.
		for (int i = 0; i < participants.length; i++) {
			if (participantDamage[i].isVisible()) {
				t = participantDamage[i].getType();
				damageAmount = participantDamage[i].getDamage();
				
				if (damageAmount < 0 && !t.isTargetsMP()) { // Heals HP
					g2.setColor(StyleConstants.HP_COLOUR);
					damageAmount *= -1;
				} else if (damageAmount < 0 && t.isTargetsMP()) { // Heals MP
					g2.setColor(StyleConstants.MP_COLOUR);
					damageAmount *= -1;
				} else if (damageAmount > 0 && t.isTargetsMP()) { // Damages MP
					g2.setColor(StyleConstants.MP_DAMAGE_COLOUR);
				} else
					g2.setColor(StyleConstants.DAMAGE_COLOUR);
				
				posY = participantLocations[i].getY() - 5; // A little higher than the top of image
				posX = participantLocations[i].getX()
						+ participants[i].getBattleModel().getImage().getWidth(null) / 2;

				damage = "" + damageAmount;
				
				if (t.isTargetsMP())
					damage += " MP";
				
				// Centre the text
				fontMetrics = g2.getFontMetrics();
				xOffset = fontMetrics.stringWidth(damage) / 2;
				
				g2.drawString(damage, (int) posX - xOffset, (int) posY);
			}
		}
	}

	/**
	 * Instructs the panel to draw the amount of damage indicated for the given BattleTarget.
	 * @param target - the BattleTarget that took damage
	 * @param damage - the amount of damage taken (negative numbers represent healing)
	 * @param type - the type of damage inflicted
	 */
	public void displayDamage(BattleTarget target, int damage, DmgType type) {
		int index = -1;
		
		if (type != DmgType.NONE_NEG && type != DmgType.NONE_POS) {
			// Figure out which index the target is stored in
			for (int i = 0; i < participants.length; i++) {
				if (participants[i] == target)
					index = i;
			}
		
			participantDamage[index].setDamage(damage);
			participantDamage[index].setType(type);
			participantDamage[index].setVisible(true);
			
			Timer t = new Timer(1500, new DamageLabelRemover(index));
			t.setRepeats(false);
			t.start();
		}
		
		repaint();
	}
	
	private final int STEPAMOUNT = 40;
	
	public void stepForward(BattleTarget target) {
		ActionListener a;
		Timer t;
		int index = -1; // The index of the BattleTarget
		
		// Figure out which BattleTarget this is
		for (int i = 0; i < participants.length; i++) {
			if (participants[i] == target)
				index = i;
		}
		
		t = new Timer(50, null);
		
		if (target instanceof Monster) {
			// Move to the left
			a = new ImageMover(t, index, -1, true);
		} else
			a = new ImageMover(t, index, 1, true);
		
		t.addActionListener(a);
		t.start();
	}
	
	public void stepBackward(BattleTarget target) {
		ActionListener a;
		Timer t;
		int index = -1; // The index of the BattleTarget
		
		// Figure out which BattleTarget this is
		for (int i = 0; i < participants.length; i++) {
			if (participants[i] == target)
				index = i;
		}
		
		t = new Timer(50, null);
		
		if (target instanceof Monster) {
			// Move to the right
			a = new ImageMover(t, index, 1, false);
		} else
			a = new ImageMover(t, index, -1, false);
		
		t.addActionListener(a);
		t.start();
	}

	public Infobar getInfoBar() {
		return infoBar;
	}

	public void setInfoBar(Infobar infoBar) {
		this.infoBar = infoBar;
		add(infoBar, BorderLayout.CENTER);
	}
	
	public void setBackgroundColour(Color c) {
		setBackground(c);
	}
	
	/**
	 * 
	 * Instructs the panel to stop drawing the damage label.
	 *
	 */
	class DamageLabelRemover implements ActionListener {
		private int index;
		
		public DamageLabelRemover(int index) {
			this.index = index;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			participantDamage[index].setVisible(false);
			repaint();
		}
	}
	
	class DamageLabel {
		private boolean visible;
		private int damage;
		private DmgType type;
		
		public void setVisible(boolean b) {
			visible = b;
		}
		
		public boolean isVisible() {
			return visible;
		}
		
		public void setDamage(int damage) {
			this.damage = damage;
		}
		
		public int getDamage() {
			return damage;
		}

		public DmgType getType() {
			return type;
		}

		public void setType(DmgType type) {
			this.type = type;
		}
	}
	
	/**
	 * 
	 * Handles BattleTarget movement before and after they
	 * take action.
	 *
	 */
	class ImageMover implements ActionListener {
		private Timer t;
		private int index, sign;
		private int total;
		private boolean forward;
		
		/**
		 * Constructs an ImageMover with the given parameters.
		 * @param t - the Timer associated with this object
		 * @param index - the index in the participant arrays of the BattleTarget
		 * @param sign - Must be -1 or +1. -1 moves to the left, +1 moves to the right
		 * @param forward - if true, the BattleTarget is beginning its turn
		 */
		public ImageMover(Timer t, int index, int sign, boolean forward) {
			this.index = index;
			this.sign = sign;
			this.t = t;
			this.forward = forward;
			
			if (forward)
				participantMoving[index] = true; // Don't recalculate the position when drawing
		}
		
		@Override
		public synchronized void actionPerformed(ActionEvent e) {			
			participantLocations[index].x += 6 * sign;
			total += 6;
			
			if (total >= STEPAMOUNT) {
				t.stop();
				
				if (!forward)
					participantMoving[index] = false;
			}

			repaint();
		}
		
	}
}
