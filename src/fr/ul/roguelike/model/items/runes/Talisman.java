package fr.ul.roguelike.model.items.runes;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Talisman extends Rune {
    /**
     * Creer une rune pierre
     */
    public Talisman() {
        super("talisman",51, "Allows the possessor to be protected to magical spells");
        this.setMagicResist(5);
        this.setTexture(new Texture(Gdx.files.internal("images/" + getName() + ".png")));
    }
}