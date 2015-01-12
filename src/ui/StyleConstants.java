package ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.UIManager;

public class StyleConstants {
	// Battle menus
	public static final Color MENU_BACKGROUND = new Color(245, 255, 250); // Mint green
	public static final Color LIST_BACKGROUND = new Color(255, 244, 215);
	public static final Color HIGHLIGHT = new Color(255, 215, 50);
	public static final Font BATTLE_MENU_FONT = new Font("Segoe UI Symbol", Font.PLAIN, 16);
	public static final Font BOLD_BATTLE_MENU_FONT = new Font("Segoe UI Symbol", Font.BOLD, 16);
	public static final Font HP_MP_FONT = new Font("Dialog", Font.BOLD, 14);
	
	// Game over
	public static final Font GAME_OVER = new Font("Garamond", Font.PLAIN, 96);
	
	// Battlefield
	public static final Font DAMAGE_FONT = new Font("Segoe UI Symbol", Font.PLAIN, 24);
	
	// Labels
	public static final Color HP_COLOUR = new Color(0, 128, 0);
	public static final Color REG_HP_COLOUR = Color.BLACK;
	public static final Color LOW_HP_COLOUR = Color.ORANGE;
	public static final Color ZERO_HP_COLOUR = Color.RED;
	public static final Color MP_COLOUR = new Color(218, 112, 214);
	public static final Color DAMAGE_COLOUR = Color.WHITE;
	public static final Color MP_DAMAGE_COLOUR = new Color(51, 136, 255); // 10, 61, 108
	
	// ATB
	public static final Color ATB_ACTIVE = UIManager.getColor("ProgressBar.foreground");
	public static final Color ATB_FULL = new Color(184, 204, 163);
	public static final Color ATB_SLOW = new Color(76, 108, 138);
	
	// Dialogue
	public static final Font DIALOGUE_FONT = new Font("Garamond", Font.PLAIN, 28);
}
