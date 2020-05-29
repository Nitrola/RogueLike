package fr.ul.roguelike.model.items.runes;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Stone extends Rune {
    /**
     * Pierre donnant des PV max supplémentaires
     */
    public Stone() {
        super("pierre",45, "Une pierre. Si si, oui je sais, elle coûte cher");
        this.setHp(10);
        this.setTexture(new Texture(Gdx.files.internal("images/" + getName() + ".png")));
    }
}