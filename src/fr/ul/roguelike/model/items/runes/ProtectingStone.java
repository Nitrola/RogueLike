package fr.ul.roguelike.model.items.runes;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class ProtectingStone extends Rune {
    /**
     * Creer une rune pierre
     */
    public ProtectingStone() {
        super("Stone_of_protection",39, "Une pierre. Si si, oui je sais, elle coûte cher");
        this.setArmor(5);
        this.setTexture(new Texture(Gdx.files.internal("images/" + getName() + ".png")));
    }
}