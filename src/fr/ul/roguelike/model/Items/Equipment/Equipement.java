package fr.ul.roguelike.model.Items.Equipment;

import com.badlogic.gdx.graphics.Texture;
import fr.ul.roguelike.model.Items.Item;

public abstract class Equipement extends Item{

    protected enum EquipementType{
        BOW,
        SWORD,
        STAFF,
        ARMOR,
        HELMET
    }
    protected EquipementStats equipement;
    protected Texture texture;
    protected EquipementType equipementType;

    public Equipement(String itemName, String itemDescription) {
        super(itemName, itemDescription);
    }

    public Equipement(String itemName, int price, String description) {
        super(itemName, price, description);
    }

    public EquipementStats getEquipement() {
        return equipement;
    }

    public String getEquipementType() {
        return equipementType.toString();
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
