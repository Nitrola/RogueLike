package fr.ul.roguelike.model.items.runes;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Dumbbell extends Rune {
    /**
     * Creer une rune poids
     */
    public Dumbbell() {
        super("dumbbell",42, "Train harder to improve your strength by 6");
        this.setPhysicalDamage(6);
        this.setTexture(new Texture(Gdx.files.internal("images/" + getName() + ".png")));
    }
}