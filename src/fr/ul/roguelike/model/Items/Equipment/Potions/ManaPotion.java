package fr.ul.roguelike.model.Items.Equipment.Potions;

public class ManaPotion extends ItemPotion {
    /**
     * Creer un Item
     * @param name  Nom de l'Item
     * @param price Prix de l'Item
     * @param d     Description de l'Item
     */
    public ManaPotion(String name, int price, String d) {
        super(name, price, d);
    }

    /**
     * Creer un Item
     * @param name  Nom de l'Item
     * @param descr Description de l'Item
     */
    public ManaPotion(String name, String descr) {
        super(name, descr);
    }

    @Override
    public boolean isHealthPotion() {
        return false;
    }

    @Override
    public boolean isManaPotion() {
        return true;
    }
}
