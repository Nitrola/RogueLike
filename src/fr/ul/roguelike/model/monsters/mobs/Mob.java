package fr.ul.roguelike.model.monsters.mobs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.ul.roguelike.model.heros.Hero;
import fr.ul.roguelike.model.monsters.Monster;

public abstract class Mob extends Monster {
    /**
     * Creer Monstre
     *
     * @param hp           Vie du monstre
     * @param mana         Mana du monstre
     * @param attackSpeed  Vitesse d'attaque du monstre
     * @param criticChance Taux de coup critique du monstre
     * @param physicalDmg  Dommage physique du monstre
     * @param magicalDmg   Dommage magique du monstre
     * @param physicalDef  Defense physique du monstre
     * @param magicalDef   Defense magique du monstre
     */
    public Mob(int hp, int mana, float attackSpeed, float criticChance, int physicalDmg, int magicalDmg, float physicalDef, float magicalDef) {
        super(hp, mana, attackSpeed, criticChance, physicalDmg, magicalDmg, physicalDef, magicalDef);
        animDead = new Animation<Texture>(0.1f, loadFrames(18,"images/combat/Smoke/smoke_"));
    }

    @Override
    public void draw(SpriteBatch sb, int posX, int posY) {
        this.posX = posX;
        this.posY = posY;

        posX -= width/4;
        stateTime += Gdx.graphics.getDeltaTime();
        Texture currentFrame = null;

        if(combatState == Hero.CombatState.ATTACKING) {
            animeTime += Gdx.graphics.getDeltaTime();
            currentFrame = animAttack.getKeyFrame(animeTime, false);
            if(animAttack.getKeyFrameIndex(animeTime) == getHitFrame() && !hasAttack){
                degat = true;
                hasAttack = true;
            }
        }

        if(combatState == Hero.CombatState.IDLE) {
            currentFrame = animIdle.getKeyFrame(stateTime, true);
        }

        if(combatState == Hero.CombatState.DEAD) {
            animeTime += Gdx.graphics.getDeltaTime();
            currentFrame = animDead.getKeyFrame(animeTime, false);
        }

        if(combatState != Hero.CombatState.WIN) {
            sb.draw(currentFrame, posX, posY, width, height);
        }

        update();
    }

    protected void update(){
        boolean res = shouldIdle();
        if(res && combatState == Hero.CombatState.DEAD){
            combatState = Hero.CombatState.WIN;
        }else {
            if (res) {
                combatState = Hero.CombatState.IDLE;
            }
        }
    }

    private boolean shouldIdle(){
        if(combatState != Hero.CombatState.IDLE && animAttack.isAnimationFinished(animeTime) && animDead.isAnimationFinished(animeTime)){
            animeTime = 0.0f;
            return true;
        }
        return false;
    }
}
