package fr.ul.roguelike.model.heros;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static fr.ul.roguelike.RogueLike.screenWidth;
import static fr.ul.roguelike.RogueLike.screenHeight;

public class Alchimist extends Hero {
    private Animation<Texture> animWall;
    private float blockTime = 0;
    private boolean wall = false;

    public Alchimist() {
        super(175,100,0.20f,0.20f,10,30,7.5f,7.5f);
        nb_spell_slots = 2;

        animAttack = new Animation<Texture>(0.0675f,loadFrames(29,"images/combat/Alchimist/Attack/alchimist_attack_"));
        animBlock = new Animation<Texture>(0.0625f,loadFrames(36,"images/combat/Alchimist/Attack/alchimist_attack_"));
        animDead = new Animation<Texture>(0.1f,loadFrames(12,"images/combat/Alchimist/Death/alchimist_death_"));
        animIdle = new Animation<Texture>(0.1f,loadFrames(24,"images/combat/Alchimist/Idle/alchimist_idle_"));
        animHit = new Animation<Texture>(0.1f,loadFrames(4,"images/combat/Alchimist/Hit/alchimist_death_"));
        animWall = new Animation<Texture>(0.12f,loadFrames(30,"images/combat/Alchimist/Icewall/icewall_"));

        width = screenWidth/2f;
        height = screenHeight/1.5f;
    }

    public void draw(SpriteBatch sb, float posX, float posY){
        this.posX = posX;
        this.posY = posY;

        stateTime += Gdx.graphics.getDeltaTime();
        Texture currentFrame = new Texture("images/combat/Def.png");
        Texture block;

        if(combatState == CombatState.ATTACKING) {
            posX += screenWidth/17f;
            animeTime += Gdx.graphics.getDeltaTime();
            currentFrame = animAttack.getKeyFrame(animeTime, false);
            if(animAttack.getKeyFrameIndex(animeTime) == getHitFrame() && !hasAttack){
                degat = true;
                hasAttack = true;
            }
        }

        if(combatState == CombatState.IDLE) {
            currentFrame = animIdle.getKeyFrame(stateTime, true);
        }

        if(combatState == CombatState.BLOCKING) {
            posX += screenWidth/17f;
            animeTime += Gdx.graphics.getDeltaTime();
            currentFrame = animBlock.getKeyFrame(animeTime, false);
            if(animBlock.getKeyFrameIndex(animeTime) == getHitFrame()){
                wall = true;
            }
        }

        if(combatState == CombatState.DEAD) {
            animeTime += Gdx.graphics.getDeltaTime();
            currentFrame = animDead.getKeyFrame(animeTime, false);
        }
        if(combatState == CombatState.HIT) {
            animeTime += Gdx.graphics.getDeltaTime();
            currentFrame = animHit.getKeyFrame(animeTime, false);
        }

        sb.draw(currentFrame,posX,posY,width , height);
        if(wall){
            blockTime += Gdx.graphics.getDeltaTime();
            block = animWall.getKeyFrame(blockTime, false);
            sb.draw(block, screenWidth/2.5f, posY+screenHeight/15f);
            if(animWall.getKeyFrameIndex(blockTime) >= 29){
                wall = false;
                blockTime = 0;
            }
        }
    }

    @Override
    public int getAttackCost() {
        return 20;
    }

    @Override
    public int getBlockCost() {
        return 15;
    }

    @Override
    public int getHitFrame() {
        return 22;
    }

    public boolean isWall() {
        return wall;
    }
}
