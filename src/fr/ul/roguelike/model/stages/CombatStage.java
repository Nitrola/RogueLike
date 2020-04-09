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
        if(actual){
            coeff = 3;
        }
        if(passed) {
            spriteBatch.draw(new Texture(Gdx.files.internal("images/combat_passed.png"))
                    , this.getPosition().x - getRayon(), this.getPosition().y - getRayon(),
                    getRayon() * coeff, getRayon() * coeff);
        }else{
            spriteBatch.draw(new Texture(Gdx.files.internal("images/combat.png"))
                    , this.getPosition().x - getRayon(), this.getPosition().y - getRayon(),
                    getRayon() * coeff, getRayon() * coeff);
        }
    }
}
