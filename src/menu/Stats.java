package menu;

import javax.swing.*;

import battleComponents.Character;

import java.awt.*;
import java.awt.event.ActionEvent;

public class Stats extends JPanel{
	
	public Stats(Character c)
	{
		JLabel Char = new JLabel(c.getName());
		JPanel panel = new JPanel();
		
		panel.setLayout(new GridLayout(2,3));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		int[] ER = c.getElementResist();
			
		JLabel str = new JLabel ("Strength:" + c.getStats().getStrength()+c.getEquipment().getStrength() +"("+")");
		panel.add(str);
		
		JLabel vit = new JLabel ("Vitality:" + c.getStats().getVitality() +c.getEquipment().getVitality()+"("+")");
		panel.add(vit);
		
		JLabel mag = new JLabel ("Magic:" + c.getStats().getMagic()+c.getEquipment().getVitality()+"("+")");
		panel.add(mag);
		
		JLabel spr = new JLabel ("Spirit:" + c.getStats().getSpirit()+c.getEquipment().getSpirit()+"("+")");
		panel.add(spr);
		
		JLabel agi = new JLabel ("Agility:" + c.getStats().getAgility()+c.getEquipment().getAgility()+"("+")");
		panel.add(agi);

		JLabel placeholder = new JLabel();
		panel.add(placeholder);
		
		JLabel fire = new JLabel ("Fire: %"+ER[0]);
		panel.add(fire);

		JLabel ice = new JLabel ("Ice: %"+ER[1]);
		panel.add(ice);
		
		JLabel lightning = new JLabel ("Lightning:%" +ER[2]);
		panel.add(lightning);
		
		JLabel water = new JLabel ("Water:%"+ER[3]);
		panel.add(water);
		
		JLabel wind = new JLabel ("Wind:%"+ER[4]);
		panel.add(wind);
		
		JLabel earth = new JLabel ("Earth:%"+ER[5]);
		panel.add(earth);
		
		//		FIRE("Fire", 0), ICE("Ice", 1), LIGHTNING("Lightning", 2), 
//		WATER("Water", 3), WIND("Wind", 4), EARTH("Earth", 5);
		
	}
	
	public Stats()
	{
		
		JPanel panel = new JPanel();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{150, 150, 150, 0};
		gbl_panel.rowHeights = new int[]{39, 71, 71, 71, 71, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel label = new JLabel();
		label.setText("Name");
		label.setAlignmentY(0.0f);
		label.setAlignmentX(0.5f);
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.WEST;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		panel.add(label, gbc_label);
		
		JLabel str = new JLabel ("Strength:0");
		GridBagConstraints gbc_str = new GridBagConstraints();
		gbc_str.fill = GridBagConstraints.BOTH;
		gbc_str.insets = new Insets(0, 0, 5, 5);
		gbc_str.gridx = 0;
		gbc_str.gridy = 1;
		panel.add(str, gbc_str);
		
		JLabel vit = new JLabel ("Vitality:0");
		GridBagConstraints gbc_vit = new GridBagConstraints();
		gbc_vit.fill = GridBagConstraints.BOTH;
		gbc_vit.insets = new Insets(0, 0, 5, 5);
		gbc_vit.gridx = 1;
		gbc_vit.gridy = 1;
		panel.add(vit, gbc_vit);
		
		JLabel mag = new JLabel ("Ability Power:0");
		GridBagConstraints gbc_mag = new GridBagConstraints();
		gbc_mag.fill = GridBagConstraints.BOTH;
		gbc_mag.insets = new Insets(0, 0, 5, 0);
		gbc_mag.gridx = 2;
		gbc_mag.gridy = 1;
		panel.add(mag, gbc_mag);
		
		JLabel spr = new JLabel ("Spirit:0");
		GridBagConstraints gbc_spr = new GridBagConstraints();
		gbc_spr.fill = GridBagConstraints.BOTH;
		gbc_spr.insets = new Insets(0, 0, 5, 5);
		gbc_spr.gridx = 0;
		gbc_spr.gridy = 2;
		panel.add(spr, gbc_spr);
		
		JLabel agi = new JLabel ("Magic:0");
		GridBagConstraints gbc_agi = new GridBagConstraints();
		gbc_agi.fill = GridBagConstraints.BOTH;
		gbc_agi.insets = new Insets(0, 0, 5, 5);
		gbc_agi.gridx = 1;
		gbc_agi.gridy = 2;
		panel.add(agi, gbc_agi);
		
		JLabel placeholder = new JLabel();
		GridBagConstraints gbc_placeholder = new GridBagConstraints();
		gbc_placeholder.fill = GridBagConstraints.BOTH;
		gbc_placeholder.insets = new Insets(0, 0, 5, 0);
		gbc_placeholder.gridx = 2;
		gbc_placeholder.gridy = 2;
		panel.add(placeholder, gbc_placeholder);
		
		JLabel fire = new JLabel ("Fire: %0");
		GridBagConstraints gbc_fire = new GridBagConstraints();
		gbc_fire.fill = GridBagConstraints.BOTH;
		gbc_fire.insets = new Insets(0, 0, 5, 5);
		gbc_fire.gridx = 0;
		gbc_fire.gridy = 3;
		panel.add(fire, gbc_fire);
		
				JLabel ice = new JLabel ("Ice:%0");
				GridBagConstraints gbc_ice = new GridBagConstraints();
				gbc_ice.fill = GridBagConstraints.BOTH;
				gbc_ice.insets = new Insets(0, 0, 5, 5);
				gbc_ice.gridx = 1;
				gbc_ice.gridy = 3;
				panel.add(ice, gbc_ice);
		
		JLabel lightning = new JLabel ("Lightning:%0");
		GridBagConstraints gbc_lightning = new GridBagConstraints();
		gbc_lightning.fill = GridBagConstraints.BOTH;
		gbc_lightning.insets = new Insets(0, 0, 5, 0);
		gbc_lightning.gridx = 2;
		gbc_lightning.gridy = 3;
		panel.add(lightning, gbc_lightning);
		
		JLabel water = new JLabel ("Water:%0");
		GridBagConstraints gbc_water = new GridBagConstraints();
		gbc_water.fill = GridBagConstraints.BOTH;
		gbc_water.insets = new Insets(0, 0, 0, 5);
		gbc_water.gridx = 0;
		gbc_water.gridy = 4;
		panel.add(water, gbc_water);
		
		JLabel wind = new JLabel ("Wind:%0");
		GridBagConstraints gbc_wind = new GridBagConstraints();
		gbc_wind.fill = GridBagConstraints.BOTH;
		gbc_wind.insets = new Insets(0, 0, 0, 5);
		gbc_wind.gridx = 1;
		gbc_wind.gridy = 4;
		panel.add(wind, gbc_wind);
		
		JLabel earth = new JLabel ("Earth:%0");
		GridBagConstraints gbc_earth = new GridBagConstraints();
		gbc_earth.fill = GridBagConstraints.BOTH;
		gbc_earth.gridx = 2;
		gbc_earth.gridy = 4;
		panel.add(earth, gbc_earth);
		add(panel);

		
	}

}
