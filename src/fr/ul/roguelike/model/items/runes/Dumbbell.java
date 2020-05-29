package fr.ul.roguelike.model.items.runes;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Dumbbell extends Rune {
    /**
     * Creer une rune poids
     */
    public Dumbbell() {
        super("dumbbell",42, "Une pierre. Si si, oui je sais, elle coûte cher");
        this.setPhysicalDamage(6);
        this.setTexture(new Texture(Gdx.files.internal("images/" + getName() + ".png")));
    }
}