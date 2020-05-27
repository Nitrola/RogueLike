package fr.ul.roguelike.model.items.runes;


public class Dumbbell extends Rune {
    /**
     * Creer une rune pierre
     */
    public Dumbbell() {
        super("dumbbell",42, "Une pierre. Si si, oui je sais, elle coûte cher");
        this.setPhysicalDamage(6);
    }
}