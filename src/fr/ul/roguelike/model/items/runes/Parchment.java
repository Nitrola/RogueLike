package fr.ul.roguelike.model.items.runes;


public class Parchment extends Rune {
    /**
     * Pierre donnant des PV max supplémentaires
     */
    public Parchment() {
        super("parchment",14, "Donne +1 d'attaque magique");
        this.setMagicalDamage(1f);
    }
}
