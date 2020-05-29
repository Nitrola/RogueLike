package fr.ul.roguelike.model.items.runes;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class PhilosopherStone extends Rune {
    /**
     * Creer une pierre philosphale
     */
    public PhilosopherStone() {
        super("philosopher's_stone",43, "Former Edward brother item. Increase greatly your magical attack");
        this.setMagicalDamage(6);
        this.setTexture(new Texture(Gdx.files.internal("images/" + getName() + ".png")));
    }
}