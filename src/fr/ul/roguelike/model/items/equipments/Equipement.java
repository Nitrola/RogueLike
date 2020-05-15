package fr.ul.roguelike.model.items.equipments;

import com.badlogic.gdx.graphics.Texture;
import fr.ul.roguelike.model.items.Item;

public abstract class Equipement extends Item{

    public enum EquipementType{
        WEAPON,
        HEAD,
        PLATE
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

    public EquipementType getEquipementType() {
        return equipementType;
    }

    public float getArmor() {
        return equipement.getArmor();
    }

    public float getMagicResist() {
        return equipement.getMagicResist();
    }

    public int getPhysicalDamage() {
        return equipement.getPhysicalDamage();
    }

    public int getMagicDamage() {
        return equipement.getMagicalDamage();
    }

    public Texture getTexture() {
        return texture;

    }

    @Override
    public boolean isPotion() {
        return false;
    }
}
