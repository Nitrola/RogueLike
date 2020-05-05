package fr.ul.roguelike.model.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class CombatStage extends Stage {

    public CombatStage(Vector2 pos){
        super(pos);
        texture = new Texture(Gdx.files.internal("images/combat.png"));
        texturePassed = new Texture(Gdx.files.internal("images/combat_passed.png"));
        sprite = new Sprite(texture);
        sprite.setPosition(this.getPosition().x - getRayon(), this.getPosition().y - getRayon());
        sprite.setSize(getRayon() * coeff, getRayon() * coeff);
    }

    public void draw(SpriteBatch spriteBatch){
        if(passed) {
            sprite.setTexture(texturePassed);
        }
        sprite.draw(spriteBatch);
    }
}
