package fr.ul.roguelike.model.items.equipments;

public class EquipementStats {
    //TODO mettre les champs en privés
    private int physicalDamage;
    private int magicalDamage;
    private float armor;
    private float magicResist;

    /**
     * Creer un objet avec des bonus
     */
    public EquipementStats(int physicalDamage, int magicalDamage, float armor, float magicResist) {
        this.physicalDamage = physicalDamage;
        this.magicalDamage = magicalDamage;
        this.armor = armor;
        this.magicResist = magicResist;
    }

    /**
     * Permet d'ameliorer un objet
     */
    public void upgrade() {
        physicalDamage *= 1.5f;
        magicalDamage *= 1.5f;
    }

    //////////////////////////////////
    ///////Getters and Setters///////
    /////////////////////////////////


    public int getPhysicalDamage() {
        return physicalDamage;
    }
    public int getMagicalDamage() {
        return magicalDamage;
    }
    public float getArmor() {
        return armor;
    }
    public float getMagicResist() {
        return magicResist;
    }
}
