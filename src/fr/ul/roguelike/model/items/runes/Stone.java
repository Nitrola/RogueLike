package fr.ul.roguelike.model.items.runes;


public class Stone extends Rune {
    /**
     * Pierre donnant des PV max supplémentaires
     */
    public Stone() {
        super("pierre",45, "Une pierre. Si si, oui je sais, elle coûte cher");
        this.setHp(10);
    }
}