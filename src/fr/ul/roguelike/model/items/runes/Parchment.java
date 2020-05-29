package fr.ul.roguelike.model.items.runes;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Parchment extends Rune {
    /**
     * Pierre donnant des PV max supplémentaires
     */
    public Parchment() {
        super("parchment",14, "Donne +1 d'attaque magique");
        this.setMagicalDamage(1f);
        this.setTexture(new Texture(Gdx.files.internal("images/" + getName() + ".png")));
    }
}
