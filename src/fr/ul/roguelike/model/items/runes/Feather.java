package fr.ul.roguelike.model.items.runes;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Feather extends Rune {
    /**
     * Creer une rune plume
     */
    public Feather() {
        super("feather",48, "Plume");
        this.setCriticalChance(5);
        this.setMana(20);
        this.setTexture(new Texture(Gdx.files.internal("images/" + getName() + ".png")));
    }
}
