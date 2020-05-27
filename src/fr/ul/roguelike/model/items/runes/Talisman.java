package fr.ul.roguelike.model.items.runes;


public class Talisman extends Rune {
    /**
     * Creer une rune pierre
     */
    public Talisman() {
        super("talisman",51, "Une pierre. Si si, oui je sais, elle coûte cher");
        this.setMagicResist(5);
    }
}