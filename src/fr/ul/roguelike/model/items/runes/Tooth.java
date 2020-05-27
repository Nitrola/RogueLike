package fr.ul.roguelike.model.items.runes;


import fr.ul.roguelike.views.MainMenu;

public class Tooth extends Rune {
    /**
     * Creer une rune pierre
     */
    public Tooth() {
        super("tooth",64, "Une pierre. Si si, oui je sais, elle coûte cher");
        MainMenu.player.getPlayerCharacter().setRegen(true);
    }
}