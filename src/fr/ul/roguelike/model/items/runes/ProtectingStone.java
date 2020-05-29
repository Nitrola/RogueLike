package fr.ul.roguelike.model.items.runes;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class ProtectingStone extends Rune {
    /**
     * Creer une pierre protectrice
     */
    public ProtectingStone() {
        super("Stone_of_protection",39, "The protecting stone has a magic aura which improves your defense against sharp weapon");
        this.setArmor(5);
        this.setTexture(new Texture(Gdx.files.internal("images/" + getName() + ".png")));
    }
}