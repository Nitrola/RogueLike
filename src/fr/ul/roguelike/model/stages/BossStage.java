package fr.ul.roguelike.model.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class BossStage extends Stage {

    public BossStage(Vector2 pos){
        super(pos);
        rayon = 100;
        position = pos;
        passed = false;
        actual = false;
    }

    public void draw(SpriteBatch spriteBatch){
        spriteBatch.draw(new Texture(Gdx.files.internal("images/boss.png"))
                , this.getPosition().x - getRayon(), this.getPosition().y - getRayon()/1.5f,
                getRayon() * coeff, getRayon() * coeff);

    }
}
