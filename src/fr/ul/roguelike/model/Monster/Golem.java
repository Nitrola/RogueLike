package fr.ul.roguelike.model.Monster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Golem extends Monster {

    public Golem(int hp, int mana, int attackSpeed, float criticChance, int physicalDmg, int magicalDmg, float physicalDef, float magicalDef) {
        super(hp, mana, attackSpeed, criticChance, physicalDmg, magicalDmg, physicalDef, magicalDef);
        Texture golemSheet = new Texture("combat/golemWalk.png");
        TextureRegion[][] tmp = TextureRegion.split(golemSheet,golemSheet.getWidth() / 7 , golemSheet.getHeight()/1);
        TextureRegion[] walkFrames = new TextureRegion[7*1];
        int index = 0;
        for(int i = 0; i < 7; i++){
            walkFrames[index++] = tmp[0][i];
        }
        anim = new Animation<TextureRegion>(0.1f,walkFrames);
    }
    @Override
    public void draw(SpriteBatch sb,int posX,int posY){
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = this.anim.getKeyFrame(stateTime,true);

        //sb.begin();
        sb.draw(currentFrame,posX,posY,currentFrame.getRegionWidth()*6,currentFrame.getRegionHeight()*6);
        //sb.end();
    }
}
