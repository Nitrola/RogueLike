package fr.ul.roguelike.model.items.runes;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Parchment extends Rune {
    /**
     * Créer rune parchemin
     */
    public Parchment() {
        super("parchment",14, "This old writing reaveals you the truth of this world, improving your magical skill by 1");
        this.setMagicalDamage(1f);
        this.setTexture(new Texture(Gdx.files.internal("images/" + getName() + ".png")));
    }
}
