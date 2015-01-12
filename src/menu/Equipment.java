package menu;

import java.io.Serializable;

import javax.swing.*;

import fieldComponents.EquipConstants;
import items.EquippableItem;
import items.Item;

public class Equipment implements Serializable {
	private static final long serialVersionUID = 8327081605891825591L;
	
	protected EquippableItem head = null;
	protected EquippableItem right = null;
	protected EquippableItem left = null;
	protected EquippableItem body = null;
	protected EquippableItem legs = null;
	protected EquippableItem feet = null;
	protected EquippableItem accessories = null;

	public Equipment() {
		head = null;
		body = null;
		right = null;
		left = null;
		legs = null;
		feet = null;
		accessories = null;
	}

	public EquippableItem getHead() {
		return head;
	}

	public EquippableItem getBody() {
		return body;
	}

	public EquippableItem getRight() {
		return right;
	}

	public EquippableItem getLeft() {
		return left;
	}

	public EquippableItem getLegs() {
		return legs;
	}

	public EquippableItem getFeet() {
		return feet;
	}

	public EquippableItem getAccessories() {
		return head;
	}

	public EquippableItem setItem(EquippableItem i) {
		EquippableItem temp = null;
		if (i.getEquipConstant() == EquipConstants.HEAD) {
			temp = head;
			head = i;
		} else if (i.getEquipConstant() == EquipConstants.RIGHT) {
			temp = right;
			right = i;
		} else if (i.getEquipConstant() == EquipConstants.BODY) {
			temp = body;
			body = i;
		} else if (i.getEquipConstant() == EquipConstants.LEFT) {
			temp = left;
			left = i;
		} else if (i.getEquipConstant() == EquipConstants.LEGS) {
			temp = right;
			right = i;
		} else if (i.getEquipConstant() == EquipConstants.FEET) {
			temp = feet;
			feet = i;
		} else if (i.getEquipConstant() == EquipConstants.ACCESSORIES) {
			temp = accessories;
			accessories = i;
		}
		return temp;
	}

	public int getAgility() {
		int temp = head.getModifiers().getAgility() + left.getModifiers().getAgility()
				+ right.getModifiers().getAgility() + body.getModifiers().getAgility()
				+ legs.getModifiers().getAgility() + feet.getModifiers().getAgility()
				+ accessories.getModifiers().getAgility();
		return temp;
	}

	public int getVitality() {
		int temp = head.getModifiers().getVitality()
				+ left.getModifiers().getVitality()
				+ right.getModifiers().getVitality()
				+ body.getModifiers().getVitality() + legs.getModifiers().getVitality()
				+ feet.getModifiers().getVitality()
				+ accessories.getModifiers().getVitality();
		return temp;
	}

	public int getStrength() {
		int temp = head.getModifiers().getStrength()
				+ left.getModifiers().getStrength()
				+ right.getModifiers().getStrength()
				+ body.getModifiers().getStrength() + legs.getModifiers().getStrength()
				+ feet.getModifiers().getStrength()
				+ accessories.getModifiers().getStrength();
		return temp;
	}

	public int getMagic() {
		int temp = head.getModifiers().getMagic() + left.getModifiers().getMagic()
				+ right.getModifiers().getMagic() + body.getModifiers().getMagic()
				+ legs.getModifiers().getMagic() + feet.getModifiers().getMagic()
				+ accessories.getModifiers().getMagic();
		return temp;
	}

	public int getSpirit() {
		int temp = head.getModifiers().getSpirit() + left.getModifiers().getSpirit()
				+ right.getModifiers().getSpirit() + body.getModifiers().getSpirit()
				+ legs.getModifiers().getSpirit() + feet.getModifiers().getSpirit()
				+ accessories.getModifiers().getSpirit();
		return temp;
	}
}
