package fr.ul.roguelike.model.items.runes;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Spatula extends Rune {
    /**
     * Créer spatule
     */
    public Spatula() {
        super("spatula",99, "The legend said it comes from Runeterra. Raise your critical chance by 20%");
        this.setCriticalChance(20);
        this.setTexture(new Texture(Gdx.files.internal("images/" + getName() + ".png")));
    }
}