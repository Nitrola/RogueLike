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

    /**
     * Creer un equipement avec sa description
     */
    public Equipement(String itemName, String itemDescription) {
        super(itemName, itemDescription);
    }

    /**
     * Creer un equipement avec un prix et une description
     */
    public Equipement(String itemName, int price, String description) {
        super(itemName, price, description);
    }


    //////////////////////////////////
    ///////Getters and Setters///////
    /////////////////////////////////


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

    @Override
    public boolean isPotion() {
        return false;
    }
}
