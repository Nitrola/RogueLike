package fr.ul.roguelike.model.monsters.bosses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.ul.roguelike.RogueLike;
import fr.ul.roguelike.model.heros.Hero;

import static fr.ul.roguelike.RogueLike.screenHeight;
import static fr.ul.roguelike.RogueLike.screenWidth;
import static fr.ul.roguelike.model.heros.Hero.CombatState.*;
import static fr.ul.roguelike.model.heros.Hero.CombatState.BLOCKING;

public class FinalBossTwo extends Boss {
    private Animation<Texture> animResurrection;

    /**
     * Creer Boss de fin de niveau
     *
     * @param hp           Vie du monstre
     * @param mana         Mana du monstre
     * @param attackSpeed  Vitesse d'attaque du monstre
     * @param criticChance Taux de coup critique du monstre
     * @param physicalDmg  Dommage physique du monstre
     * @param magicalDmg   Dommage magique du monstre
     * @param physicalDef  Defense physique du monstre
     * @param magicalDef   Defense magique du monstre
     * @param dodge        Pourcentage de chance que le boss dodge ou parer
     */
    public FinalBossTwo(int hp, int mana, float attackSpeed, float criticChance, int physicalDmg, int magicalDmg, float physicalDef, float magicalDef, float dodge) {
        super(hp, mana, attackSpeed, criticChance, physicalDmg, magicalDmg, physicalDef, magicalDef, dodge);

        animIdle = new Animation<Texture>(0.1f, loadFrames(13,"images/combat/FinalBoss/Form2/Idle/FinalBoss_idle_"));
        animAttack = new Animation<Texture>(0.1f, loadFrames(25,"images/combat/FinalBoss/Form2/Attack/FinalBoss_attack_"));
        animAttack0 = new Animation<Texture>(0.1f, loadFrames(25,"images/combat/FinalBoss/Form2/Attack/FinalBoss_attack_"));
        animAttack1 = new Animation<Texture>(0.1f, loadFrames(25,"images/combat/FinalBoss/Form2/Attack/FinalBoss_attack_"));
        animDead = new Animation<Texture>(0.1f, loadFrames(21,"images/combat/FinalBoss/Form2/Death/FinalBoss_death_"));
        animResurrection = new Animation<Texture>(0.1f, loadFrames(21,"images/combat/FinalBoss/Form2/Resurrection/FinalBoss_res_"));

        combatState = RESSURECTING;
        width = screenWidth/2.5f;
        height = screenHeight/1.5f;
    }

    public void draw(SpriteBatch sb, int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        posY -= RogueLike.screenHeight/10;


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
            if(animAttack.getKeyFrameIndex(animeTime) == 12){
                hasAttack = false;
            }
            if(animAttack.getKeyFrameIndex(animeTime) == getHitFrame2() && !hasAttack){
                degat = true;
                hasAttack = true;
            }
        }

        if(combatState == Hero.CombatState.IDLE) {
            currentFrame = animIdle.getKeyFrame(stateTime, true);
        }

        if(combatState == Hero.CombatState.BLOCKING) {
            animeTime += Gdx.graphics.getDeltaTime();
            currentFrame = animBlock.getKeyFrame(animeTime, false);
        }
        if(combatState == DEAD) {
            animeTime += Gdx.graphics.getDeltaTime();
            currentFrame = animDead.getKeyFrame(animeTime, false);
        }
        if(combatState == RESSURECTING) {
            animeTime += Gdx.graphics.getDeltaTime();
            currentFrame = animResurrection.getKeyFrame(animeTime, false);
        }

        if(combatState != Hero.CombatState.WIN) {
            sb.draw(currentFrame, posX, posY, width, height);
        }

        update();
    }

    public boolean shouldIdle(){
        if( combatState != Hero.CombatState.IDLE){
            if(combatState == ATTACKING && animAttack.isAnimationFinished(animeTime)) {
                animeTime = 0.0f;
                return true;
            }
            if(combatState == BLOCKING && animBlock.isAnimationFinished(animeTime)) {
                animeTime = 0.0f;
                return true;
            }
            if(combatState == DEAD && animDead.isAnimationFinished(animeTime)) {
                animeTime = 0.0f;
                return true;
            }
            if(combatState == RESSURECTING && animResurrection.isAnimationFinished(animeTime)) {
                animeTime = 0.0f;
                return true;
            }
        }
        return false;
    }

    @Override
    public int getHitFrame() {
        return 10;
    }

    public int getHitFrame2(){
        return 19;
    }

    @Override
    public boolean isBlocking() {
        return false;
    }
}
