package fr.ul.roguelike.model.items.runes;


public class PhilosopherStone extends Rune {
    /**
     * Creer une rune pierre
     */
    public PhilosopherStone() {
        super("philosopher's_stone",43, "Une pierre. Si si, oui je sais, elle coûte cher");
        this.setMagicalDamage(6);
    }
}