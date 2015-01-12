package battleGUI;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

import battleComponents.BattleTarget;
import ui.StyleConstants;

/**
 * 
 * Represents a single BattleTarget's ATB bar, current HP, and current MP
 *
 */
public class ParticipantPanel extends JPanel {
	private JLabel nameLabel;
	private JLabel hpLabel;
	private JLabel mpLabel;
	private JProgressBar atb;
	
	private BattleTarget owner;
	
	public ParticipantPanel(BattleTarget b, BattleScreen screen) {
		owner = b;
		
		setBackground(StyleConstants.MENU_BACKGROUND);
		setBorder(new EmptyBorder(0, 5, 0, 5));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{100, 10, 60, 0, 40, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{Double.MIN_VALUE, 0.0, 0.0, 0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0};
		setLayout(gridBagLayout);
		
		nameLabel = new JLabel();
		nameLabel.setFont(StyleConstants.BATTLE_MENU_FONT);
		GridBagConstraints gbc_name = new GridBagConstraints();
		gbc_name.anchor = GridBagConstraints.WEST;
		gbc_name.insets = new Insets(0, 0, 5, 15);
		gbc_name.gridx = 0;
		gbc_name.gridy = 0;
		add(nameLabel, gbc_name);
		
		JLabel lblHP = new JLabel("HP");
		lblHP.setFont(StyleConstants.HP_MP_FONT);
		lblHP.setForeground(StyleConstants.HP_COLOUR);
		GridBagConstraints gbc_lblHP = new GridBagConstraints();
		gbc_lblHP.insets = new Insets(0, 0, 1, 10);
		gbc_lblHP.gridx = 1;
		gbc_lblHP.gridy = 0;
		add(lblHP, gbc_lblHP);
		
		hpLabel = new JLabel();
		hpLabel.setFont(StyleConstants.BOLD_BATTLE_MENU_FONT);
		GridBagConstraints gbc_hp = new GridBagConstraints();
		gbc_hp.anchor = GridBagConstraints.EAST;
		gbc_hp.insets = new Insets(0, 0, 5, 10);
		gbc_hp.gridx = 2;
		gbc_hp.gridy = 0;
		add(hpLabel, gbc_hp);
		
		JLabel lblMp = new JLabel("MP");
		lblMp.setFont(StyleConstants.HP_MP_FONT);
		lblMp.setForeground(StyleConstants.MP_COLOUR);
		GridBagConstraints gbc_lblMp = new GridBagConstraints();
		gbc_lblMp.insets = new Insets(0, 0, 1, 10);
		gbc_lblMp.gridx = 3;
		gbc_lblMp.gridy = 0;
		add(lblMp, gbc_lblMp);
		
		mpLabel = new JLabel();
		mpLabel.setFont(StyleConstants.BOLD_BATTLE_MENU_FONT);
		GridBagConstraints gbc_mp = new GridBagConstraints();
		gbc_mp.anchor = GridBagConstraints.EAST;
		gbc_mp.insets = new Insets(0, 0, 5, 0);
		gbc_mp.gridx = 4;
		gbc_mp.gridy = 0;
		add(mpLabel, gbc_mp);
		
		atb = new ATB(owner, screen);
		atb.setBackground(Color.WHITE);
		GridBagConstraints gbc_atb = new GridBagConstraints();
		gbc_atb.insets = new Insets(0, 0, 10, 0);
		gbc_atb.fill = GridBagConstraints.HORIZONTAL;
		gbc_atb.gridwidth = 5;
		gbc_atb.gridx = 0;
		gbc_atb.gridy = 1;
		add(atb, gbc_atb);
		
		// Set maxHP and maxMP
		setNameLabel(owner.toString());
		setHp(owner.getCurrHP());
		setMp(owner.getCurrMP());
	}
	
	public void update() {
		setNameLabel(owner.toString());
		setHp(owner.getCurrHP());
		setMp(owner.getCurrMP());
	}

	public String getNameLabelParticipantName() {
		return nameLabel.getText();
	}

	public void setNameLabel(String name) {
		nameLabel.setText(name);
	}

	public int getHp() {
		return Integer.parseInt(hpLabel.getText());
	}

	private void setHp(int hp) {
		if (owner.isScanned() || hp == 0)
			hpLabel.setText("" + hp);
		else
			hpLabel.setText("????");
		
		if (hp == 0)
			hpLabel.setForeground(StyleConstants.ZERO_HP_COLOUR);
		else if (owner.getStats().getMaxHP()*1.0 / hp >= 3)
			hpLabel.setForeground(StyleConstants.LOW_HP_COLOUR);
		else
			hpLabel.setForeground(StyleConstants.REG_HP_COLOUR);
			
	}

	public int getMp() {
		return Integer.parseInt(this.mpLabel.getText());
	}

	private void setMp(int mp) {
		if (owner.isScanned())
			mpLabel.setText("" + mp);
		else
			mpLabel.setText("????");
	}
	
	public int getATBValue() {
		return atb.getValue();
	}
	
	public void setATBValue(int value) {
		atb.setValue(value);
	}
}
