package fr.ul.roguelike.model.items.runes;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Spatula extends Rune {
    /**
     * Pierre donnant des PV max supplémentaires
     */
    public Spatula() {
        super("spatula",99, "Seul les dieux connaissent son utilité");
        this.setCriticalChance(20);
        this.setTexture(new Texture(Gdx.files.internal("images/" + getName() + ".png")));
    }
}