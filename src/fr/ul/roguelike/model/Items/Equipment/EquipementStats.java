package fr.ul.roguelike.model.Items.Equipment;

public class EquipementStats {
    public int physicalDamage;
    public int magicalDamage;
    public float armor;
    public float magicResist;

    public EquipementStats(int physicalDamage, int magicalDamage, float armor, float magicResist) {
        this.physicalDamage = physicalDamage;
        this.magicalDamage = magicalDamage;
        this.armor = armor;
        this.magicResist = magicResist;
    }

    public void upgrade() {
        System.out.println(physicalDamage);
        physicalDamage *= 1.4f;
        magicalDamage *= 1.4f;
    }
}
