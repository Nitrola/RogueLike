package fr.ul.roguelike.model.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import fr.ul.roguelike.model.GameWorld;

public class CombatStage extends Stage {

    public CombatStage(GameWorld gameWorld, Vector2 pos){
        super(gameWorld, pos);

    }

    public void draw(SpriteBatch spriteBatch){
        if(!passed) {
            spriteBatch.draw(new Texture(Gdx.files.internal("combat_passed.png"))
                    , this.getPosition().x - getRayon(), this.getPosition().y - getRayon(),
                    getRayon() * 2, getRayon() * 2);
        }else{
            spriteBatch.draw(new Texture(Gdx.files.internal("combat.png"))
                    , this.getPosition().x - getRayon(), this.getPosition().y - getRayon(),
                    getRayon() * 2, getRayon() * 2);
        }
    }
}
