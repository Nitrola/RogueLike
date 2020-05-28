package fr.ul.roguelike.model.items.runes;


public class Spatula extends Rune {
    /**
     * Pierre donnant des PV max supplémentaires
     */
    public Spatula() {
        super("spatula",99, "Seul les dieux connaissent son utilité");
        this.setCriticalChance(20);
    }
}