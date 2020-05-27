package fr.ul.roguelike.model.items.runes;

import fr.ul.roguelike.model.items.Item;

public abstract class Rune extends Item {
    private float hp;
    private float mana;
    private float manaRegen;
    private float criticalChance;
    private float physicalDamage;
    private float magicalDamage;
    private float armor;
    private float magicResist;


    /**
     * Creer une Rune
     */
    public Rune(String name, int price, String descr){
        super(name,price,descr);

    }

    @Override
    public boolean isPotion() {
        return false;
    }

    //////////////////////////////////
    ///////Getters and Setters///////
    /////////////////////////////////


    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }

    public float getMana() {
        return mana;
    }
    public void setMana(float mana) {
        this.mana = mana;
    }
    public float getManaRegen() {
        return manaRegen;
    }
    public void setManaRegen(float manaRegen) {
        this.manaRegen = manaRegen;
    }
    public float getCriticalChance() {
        return criticalChance;
    }

    public void setCriticalChance(float criticalChance) {
        this.criticalChance = criticalChance;
    }
    public float getPhysicalDamage() {
        return physicalDamage;
    }
    public void setPhysicalDamage(float physicalDamage) {
        this.physicalDamage = physicalDamage;
    }
    public float getMagicalDamage() {
        return magicalDamage;
    }
    public void setMagicalDamage(float magicalDamage) {
        this.magicalDamage = magicalDamage;
    }
    public float getArmor() {
        return armor;
    }
    public void setArmor(float armor) {
        this.armor = armor;
    }
    public float getMagicResist() {
        return magicResist;
    }
    public void setMagicResist(float magicResist) {
        this.magicResist = magicResist;
    }
}
