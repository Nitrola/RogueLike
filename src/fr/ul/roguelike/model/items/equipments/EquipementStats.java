package fr.ul.roguelike.model.items.equipments;

public class EquipementStats {
    //TODO mettre les champs en privés
    public int physicalDamage;
    public int magicalDamage;
    public float armor;
    public float magicResist;

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

    //TODO Faire des getters et des setters
}
