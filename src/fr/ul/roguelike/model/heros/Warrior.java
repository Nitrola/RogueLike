package fr.ul.roguelike.model.heros;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.ul.roguelike.views.MainMenu;

import static fr.ul.roguelike.RogueLike.screenWidth;
import static fr.ul.roguelike.RogueLike.screenHeight;
import static fr.ul.roguelike.model.heros.Hero.CombatState.*;
import static fr.ul.roguelike.model.heros.Hero.CombatState.DEAD;

public class Warrior extends Hero {
    private Animation<Texture> animAttack1;
    private Animation<Texture> animAttack2;
    private Animation<Texture> animAttack3;

    public Warrior() {
        super(200,100,0.40f,20f,20,1,10f,5f); //Damage = 10
        nb_spell_slots = 2;
        Texture warriorSheet = new Texture("images/combat/warriorWalk.png");
        TextureRegion[][] tmp = TextureRegion.split(warriorSheet,warriorSheet.getWidth() / 3 , warriorSheet.getHeight());
        TextureRegion[] walkFrames = new TextureRegion[3];
        int index = 0;
        for(int i = 0; i < 3; i++){
            tmp[0][i].flip(true,false);
            walkFrames[index++] = tmp[0][i];
        }

        animAttack1 = new Animation<>(0.1f,loadFrames(6,"images/combat/Warrior/Attack/Warrior_Attack_"));
        animAttack = animAttack1;
        animAttack2 = new Animation<>(0.1f,loadFrames(5,"images/combat/Warrior/Attack2/Warrior_Attack_"));
        animAttack3 = new Animation<>(0.1f,loadFrames(9,"images/combat/Warrior/Attack3/Warrior_Attack_"));
        animBlock = new Animation<>(0.1f,loadFrames(4,"images/combat/Warrior/Block/Warrior_Block_"));
        animDead = new Animation<>(0.1f,loadFrames(9,"images/combat/Warrior/Death/Warrior_Death_"));
        animIdle = new Animation<>(0.1f,loadFrames(7,"images/combat/Warrior/Idle/Warrior_Idle_"));
        animHit = new Animation<>(0.1f,loadFrames(1,"images/combat/Warrior/Hit/Warrior_Hit_"));

        width = screenWidth/2f;
        height = screenHeight/1.5f;
        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/warrior_attack.mp3"));
    }

    public void draw(SpriteBatch sb, float posX, float posY){
        this.posX = posX;
        this.posY = posY;

        stateTime += Gdx.graphics.getDeltaTime();
        Texture currentFrame = new Texture("images/combat/Def.png");

        if(combatState == CombatState.ATTACKING) {
            animeTime += Gdx.graphics.getDeltaTime();
            currentFrame = animAttack.getKeyFrame(animeTime, false);
            if(animAttack.getKeyFrameIndex(animeTime) == getHitFrame(animAttack) && !hasAttack){
                degat = true;
                hasAttack = true;
                sound.play(0.3f);
                if (MainMenu.player.getManaLeft() > MainMenu.player.getPlayerCharacter().getAttackCost()){
                    MainMenu.player.useMana(MainMenu.player.getPlayerCharacter().getAttackCost());
                }
            }
        }

        if(combatState == CombatState.IDLE) {
            currentFrame = animIdle.getKeyFrame(stateTime, true);
        }

        if(combatState == CombatState.BLOCKING) {
            animeTime += Gdx.graphics.getDeltaTime();
            currentFrame = animBlock.getKeyFrame(animeTime, false);
        }

        if(combatState == CombatState.DEAD) {
            animeTime += Gdx.graphics.getDeltaTime();
            currentFrame = animDead.getKeyFrame(animeTime, false);
        }

        if(combatState == CombatState.HIT) {
            animeTime += Gdx.graphics.getDeltaTime();
            currentFrame = animHit.getKeyFrame(animeTime, false);
        }

        sb.draw(currentFrame, posX, posY, width, height);
    }

    @Override
    public int getAttackCost() {
        return 15;
    }

    @Override
    public int getBlockCost() {
        return 10;
    }

    @Override
    public int getHitFrame() {
        return 0;
    }

    public void changeAttack(){
        if(animAttack.getKeyFrameIndex(animeTime) >= getHitFrame(animAttack)) {
            if (animAttack.equals(animAttack1)) {
                animAttack = animAttack2;
            } else if (animAttack.equals(animAttack2)) {
                animAttack = animAttack3;
            } else {
                animAttack = animAttack1;
            }
            animeTime = 0;
            critic = false;
            degat = false;
            hasAttack = false;
        }
    }

    public int getHitFrame(Animation<Texture> animAttack) {
        if(animAttack.equals(animAttack1)) {
            return 2;
        }
        if(animAttack.equals(animAttack2)){
            return 2;
        }
        if(animAttack.equals(animAttack3)){
            return 4;
        }
        return 100;
    }

    public boolean shouldIdle(){
        if( combatState != CombatState.IDLE){
            if(combatState == ATTACKING && animAttack.isAnimationFinished(animeTime)) {
                animeTime = 0.0f;
                animAttack = animAttack1;
                return true;
            }
            if(combatState == BLOCKING && animBlock.isAnimationFinished(animeTime)) {
                animeTime = 0.0f;
                return true;
            }
            if(combatState == HIT && animHit.isAnimationFinished(animeTime)) {
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

}
