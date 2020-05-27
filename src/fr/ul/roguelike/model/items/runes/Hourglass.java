package fr.ul.roguelike.model.items.runes;


public class Hourglass extends Rune {
    /**
     * Creer une rune pierre
     */
    public Hourglass() {
        super("hourglass",47, "Une pierre. Si si, oui je sais, elle coûte cher");
        this.setManaRegen(0.10f);
    }
}