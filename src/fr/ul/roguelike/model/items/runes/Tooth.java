package fr.ul.roguelike.model.items.runes;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import fr.ul.roguelike.views.MainMenu;

public class Tooth extends Rune {
    /**
     * Creer une dent
     */
    public Tooth() {
        super("tooth",64, "(Unique) Your attack heal you from a percentage of damage you're dealing");
        MainMenu.player.getPlayerCharacter().setRegen(true);
        this.setTexture(new Texture(Gdx.files.internal("images/" + getName() + ".png")));
    }
}