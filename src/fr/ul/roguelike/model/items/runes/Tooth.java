package fr.ul.roguelike.model.items.runes;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import fr.ul.roguelike.views.MainMenu;

public class Tooth extends Rune {
    /**
     * Creer une rune pierre
     */
    public Tooth() {
        super("tooth",64, "Une pierre. Si si, oui je sais, elle coûte cher");
        MainMenu.player.getPlayerCharacter().setRegen(true);
        this.setTexture(new Texture(Gdx.files.internal("images/" + getName() + ".png")));
    }
}