package fr.ul.roguelike.model.items.runes;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Hourglass extends Rune {
    /**
     * Creer un sablier
     */
    public Hourglass() {
        super("hourglass",47, "Slow time to earn more mana per second");
        this.setManaRegen(0.10f);
        this.setTexture(new Texture(Gdx.files.internal("images/" + getName() + ".png")));
    }
}