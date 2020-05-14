package fr.ul.roguelike.model.items.equipment.potions;

public class HealthPotion extends ItemPotion {
    /**
     * Creer une potion de vie
     */
    public HealthPotion() {
        super("healingPotion",20, "Rend des PV");
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
