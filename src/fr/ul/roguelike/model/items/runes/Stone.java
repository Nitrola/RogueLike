package fr.ul.roguelike.model.items.runes;


public class Stone extends Rune {
    /**
     * Creer une rune pierre
     */
    public Stone() {
        super("pierre",45, "Une pierre. Si si, oui je sais, elle coûte cher");
        this.setHp(10);
    }
}
