package fr.ul.roguelike.model.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class MiniBossStage extends Stage {

    /**
     * Creer un stage de mini Boss
     */
    public MiniBossStage(Vector2 pos){
        super(pos);
        texture = new Texture(Gdx.files.internal("images/skull.png"));
        texturePassed = new Texture(Gdx.files.internal("images/skull_passed.png"));
        sprite = new Sprite(texture);
        sprite.setPosition(this.getPosition().x - getRayon(), this.getPosition().y - getRayon());
        sprite.setSize(getRayon() * coeff, getRayon() * coeff);
    }

    /**
     * Affiche le SpriteBatch
     */
    public void draw(SpriteBatch spriteBatch){
        if (actual) {
            coeff = 3;
        }
        if (passed) {
            sprite.setTexture(texturePassed);
        }
        sprite.draw(spriteBatch);
    }
}

