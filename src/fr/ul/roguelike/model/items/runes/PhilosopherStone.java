package fr.ul.roguelike.model.items.runes;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class PhilosopherStone extends Rune {
    /**
     * Creer une rune pierre
     */
    public PhilosopherStone() {
        super("philosopher's_stone",43, "Une pierre. Si si, oui je sais, elle coûte cher");
        this.setMagicalDamage(6);
        this.setTexture(new Texture(Gdx.files.internal("images/" + getName() + ".png")));
    }
}