package fr.ul.roguelike.model.Items.Equipment.Potions;

public class HealthPotion extends ItemPotion {
    /**
     * Creer un Item
     * @param name  Nom de l'Item
     * @param price Prix de l'Item
     * @param d     Description de l'Item
     */
    public HealthPotion(String name, int price, String d) {
        super(name, price, d);
    }

    /**
     * Creer un Item
     * @param name  Nom de l'Item
     * @param descr Description de l'Item
     */
    public HealthPotion(String name, String descr) {
        super(name, descr);
    }

    @Override
    public boolean isHealthPotion() {
        return true;
    }

    @Override
    public boolean isManaPotion() {
        return false;
    }
}
