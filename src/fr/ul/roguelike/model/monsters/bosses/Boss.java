package fr.ul.roguelike.model.monsters.bosses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.ul.roguelike.RogueLike;
import fr.ul.roguelike.model.heros.Hero;
import fr.ul.roguelike.model.monsters.Monster;

import java.util.Random;

import static fr.ul.roguelike.model.heros.Hero.CombatState.*;
import static fr.ul.roguelike.model.heros.Hero.CombatState.HIT;

public abstract class Boss extends Monster {
    protected Animation<Texture> animBlock;
    protected Animation<Texture> animAttack0;
    protected Animation<Texture> animAttack1;
    float dodgeChance;


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
    public Boss(int hp, int mana, float attackSpeed, float criticChance, int physicalDmg, int magicalDmg, float physicalDef, float magicalDef, float dodge) {
        super(hp, mana, attackSpeed, criticChance, physicalDmg, magicalDmg, physicalDef, magicalDef);
        dodgeChance = dodge;
    }


    @Override
    public void draw(SpriteBatch sb, int posX, int posY) {
        this.posX = posX;
        this.posY = posY;

        if(this instanceof Griffin || this instanceof FinalBossOne){
            posY -= RogueLike.screenHeight/10;
        }

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

        if(combatState == Hero.CombatState.BLOCKING) {
            animeTime += Gdx.graphics.getDeltaTime();
            currentFrame = animBlock.getKeyFrame(animeTime, false);
        }
        if(combatState == DEAD) {
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
        if(res && combatState == DEAD){
            combatState = Hero.CombatState.WIN;
        }else {
            if (res) {
                combatState = Hero.CombatState.IDLE;
            }
        }
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
        }
        return false;
    }

    public void selectRandomAttack(){
        Random r = new Random();
        int res = r.nextInt(2);
        if(res == 0){
            animAttack = animAttack0;
        }else{
            animAttack = animAttack1;
        }
    }

    public boolean isBlocking(){
        Random random = new Random();
        int alea = random.nextInt(100);
        if(this.combatState == DEAD){
            return false;
        }
        if(alea > dodgeChance){
            dodgeChance += 10;
            return false;
        }else{
            combatState = Hero.CombatState.BLOCKING;
            dodgeChance = 20;
        }
        return true;
    }
}
