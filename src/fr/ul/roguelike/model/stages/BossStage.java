package fr.ul.roguelike.model.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import static fr.ul.roguelike.RogueLike.screenWidth;

public class BossStage extends Stage {

    /**
     * Creer un stage de Boss
     * @param pos Position du stage
     */
    public BossStage(Vector2 pos){
        super(pos);
        rayon = screenWidth/16;
        texture = new Texture(Gdx.files.internal("images/boss.png"));
        texturePassed = new Texture(Gdx.files.internal("images/skull_passed.png"));
        sprite = new Sprite(texture);
        sprite.setPosition(this.getPosition().x - getRayon(), this.getPosition().y - getRayon()/1.5f);
        sprite.setSize(getRayon() * coeff, getRayon() * coeff);
    }

    /**
     * Affiche de SpriteBatch
     */
    public void draw(SpriteBatch spriteBatch){
        sprite.draw(spriteBatch);
    }
}
