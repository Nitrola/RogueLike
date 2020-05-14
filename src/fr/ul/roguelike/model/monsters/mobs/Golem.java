package fr.ul.roguelike.model.monsters.mobs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.ul.roguelike.model.monsters.Monster;

public class Golem extends Monster {

    /**
     * Creer un Golem (Monstre)
     * @param hp Vie du monstre
     * @param mana Mana du monstre
     * @param attackSpeed Vitesse d'attaque du monstre
     * @param criticChance Taux de coup critique du monstre
     * @param physicalDmg Dommage physique du monstre
     * @param magicalDmg Dommage magique du monstre
     * @param physicalDef Defense physique du monstre
     * @param magicalDef Defense magique du monstre
     */
    public Golem(int hp, int mana, float attackSpeed, float criticChance, int physicalDmg, int magicalDmg, float physicalDef, float magicalDef) {
        super(hp, mana, attackSpeed, criticChance, physicalDmg, magicalDmg, physicalDef, magicalDef);
        Texture golemSheet = new Texture("images/combat/golemWalk.png");
        TextureRegion[][] tmp = TextureRegion.split(golemSheet,golemSheet.getWidth() / 7 , golemSheet.getHeight());
        TextureRegion[] walkFrames = new TextureRegion[7];
        int index = 0;
        for(int i = 0; i < 7; i++){
            walkFrames[index++] = tmp[0][i];
        }
        animIdleMonster = new Animation<TextureRegion>(0.1f*this.attackSpeed,walkFrames);
    }

    /**
     * Affiche un SpriteBatch à une certaine position
     */
    @Override
    public void draw(SpriteBatch sb,int posX,int posY){
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = this.animIdleMonster.getKeyFrame(stateTime,true);

        sb.draw(currentFrame,posX,posY,currentFrame.getRegionWidth()*6,currentFrame.getRegionHeight()*6);
    }

    @Override
    public int getHitFrame() {
        return (int)((animAttack.getAnimationDuration()/animAttack.getFrameDuration())/1.75);
    }
}
