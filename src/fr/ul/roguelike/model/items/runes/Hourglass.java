package fr.ul.roguelike.model.items.runes;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Hourglass extends Rune {
    /**
     * Creer une rune pierre
     */
    public Hourglass() {
        super("hourglass",47, "Une pierre. Si si, oui je sais, elle coûte cher");
        this.setManaRegen(0.10f);
        this.setTexture(new Texture(Gdx.files.internal("images/" + getName() + ".png")));
    }
}