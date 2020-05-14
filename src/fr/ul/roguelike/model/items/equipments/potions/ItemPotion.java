package fr.ul.roguelike.model.items.equipment.potions;

import fr.ul.roguelike.model.items.Item;

public abstract class ItemPotion extends Item {
    /**
     * Creer un Item
     * @param name  Nom de l'Item
     * @param price Prix de l'Item
     * @param d     Description de l'Item
     */
    public ItemPotion(String name, int price, String d) {
        super(name, price, d);
    }

    /**
     * Creer un Item
     * @param name  Nom de l'Item
     * @param descr Description de l'Item
     */
    public ItemPotion(String name, String descr) {
        super(name, descr);
    }

    public abstract boolean isHealthPotion();
    public abstract boolean isManaPotion();

    @Override
    public boolean isPotion() {
        return true;
    }
}
