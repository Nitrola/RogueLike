package fr.ul.roguelike.model.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import fr.ul.roguelike.model.GameWorld;

public class BossStage extends Stage {

    public BossStage(GameWorld gameWorld, Vector2 pos){
        super(gameWorld, pos);
        rayon = 100;
        this.gameWorld = gameWorld;
        position = pos;
        passed = false;
        actual = false;
    }

    public void draw(SpriteBatch spriteBatch){
        spriteBatch.draw(new Texture(Gdx.files.internal("images/boss.png"))
                , this.getPosition().x - getRayon(), this.getPosition().y - getRayon(),
                getRayon() * coeff, getRayon() * coeff);

    }
}
