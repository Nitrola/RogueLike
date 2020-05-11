package fr.ul.roguelike.model.Monster.Mob;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.ul.roguelike.RogueLike;
import fr.ul.roguelike.model.Heros.Hero;

import static fr.ul.roguelike.RogueLike.screenHeight;
import static fr.ul.roguelike.RogueLike.screenWidth;

public class Knight extends Mob {
    private boolean havehit;

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
    public Knight(int hp, int mana, float attackSpeed, float criticChance, int physicalDmg, int magicalDmg, float physicalDef, float magicalDef) {
        super(hp, mana, attackSpeed, criticChance, physicalDmg, magicalDmg, physicalDef, magicalDef);
        this.havehit = false;

        animIdle = new Animation<Texture>(0.1f, loadFrames(18,"images/combat/Knight/Idle/knight_idle_"));
        animAttack = new Animation<Texture>(0.1f, loadFrames(37,"images/combat/Knight/Attack/knight_attack_"));


        combatState = Hero.CombatState.IDLE;
        width = screenWidth/7f*3;
        height = screenHeight/4.5f*3;
    }

    @Override
    public void draw(SpriteBatch sb, int posX, int posY) {
        this.posX = posX;
        this.posY = posY;

        posX += RogueLike.screenWidth/10;
        posY -= RogueLike.screenHeight/14;

        posX -= width/4;
        stateTime += Gdx.graphics.getDeltaTime();
        Texture currentFrame = null;

        if(combatState == Hero.CombatState.ATTACKING) {
            animeTime += Gdx.graphics.getDeltaTime();
            currentFrame = animAttack.getKeyFrame(animeTime, false);
            if(animAttack.getKeyFrameIndex(animeTime) >= getHitFrame() && animAttack.getKeyFrameIndex(animeTime) <= getHitFrame2()){
                if(havehit) {
                    degat = true;
                    hasAttack = true;
                    havehit = false;
                } else {
                    havehit = true;
                }
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

    @Override
    public int getHitFrame() {
        return 22;
    }

    public int getHitFrame2() {
        return 30;
    }
}
