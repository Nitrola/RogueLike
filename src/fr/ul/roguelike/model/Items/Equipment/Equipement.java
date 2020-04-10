package fr.ul.roguelike.model.Items.Equipment;

import com.badlogic.gdx.graphics.Texture;
import fr.ul.roguelike.model.Items.Item;

public abstract class Equipement extends Item{

    protected EquipementStats equipement;
    protected Texture texture;

    public Equipement(String itemName, String itemDescription) {
        super(itemName, itemDescription);
    }


    public float getArmor() {
        return equipement.armor;
    }

    public float getMagicResist() {
        return equipement.magicResist;
    }

    public int getPhysicalDamage() {
        return equipement.physicalDamage;
    }

    public int getMagicDamage() {
        return equipement.magicalDamage;
    }

    public Texture getTexture() {
        return texture;

    }
}
