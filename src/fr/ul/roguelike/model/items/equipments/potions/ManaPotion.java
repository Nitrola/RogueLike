package fr.ul.roguelike.model.items.equipments.potions;

public class ManaPotion extends ItemPotion {
    /**
     * Creer une potion de mana
     */
    public ManaPotion() {
        super("manaPotion",20, "Rend de la mana");
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
