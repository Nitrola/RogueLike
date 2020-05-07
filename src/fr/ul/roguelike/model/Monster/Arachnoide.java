package fr.ul.roguelike.model.Monster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.ul.roguelike.model.Heros.Alchimist;
import fr.ul.roguelike.model.Heros.Hero;

import static fr.ul.roguelike.RogueLike.screenHeight;
import static fr.ul.roguelike.RogueLike.screenWidth;

public class Arachnoide extends Monster {

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
    public Arachnoide(int hp, int mana, float attackSpeed, float criticChance, int physicalDmg, int magicalDmg, float physicalDef, float magicalDef) {
        super(hp, mana, attackSpeed, criticChance, physicalDmg, magicalDmg, physicalDef, magicalDef);
        Texture golemSheet = new Texture("images/combat/golemWalk.png");
        TextureRegion[][] tmp = TextureRegion.split(golemSheet,golemSheet.getWidth() / 7 , golemSheet.getHeight());
        TextureRegion[] walkFrames = new TextureRegion[7];
        int index = 0;
        for(int i = 0; i < 7; i++){
            walkFrames[index++] = tmp[0][i];
        }
        animIdleMonster = new Animation<TextureRegion>(0.1f*this.attackSpeed, walkFrames);

        animIdle = new Animation<Texture>(0.1f, loadFrames(6,"images/combat/Arachnoide/Idle/arachnoide_idle_"));
        animAttack = new Animation<Texture>(0.1f, loadFrames(15,"images/combat/Arachnoide/Attack/arachnoide_attack_"));
        animBlock = new Animation<Texture>(0.1f, loadFrames(15,"images/combat/Arachnoide/Block/arachnoide_block_"));
        animDead = new Animation<Texture>(0.1f, loadFrames(9,"images/combat/Arachnoide/Death/arachnoide_death_"));


        combatState = Hero.CombatState.IDLE;
        width = screenWidth/2f;
        height = screenHeight/1.5f;
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
        }

        if(combatState == Hero.CombatState.IDLE) {
            currentFrame = animIdle.getKeyFrame(stateTime, true);
        }

        if(combatState == Hero.CombatState.BLOCKING) {
            animeTime += Gdx.graphics.getDeltaTime();
            currentFrame = animBlock.getKeyFrame(animeTime, false);
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

    private void update(){
        if(shouldIdle() && combatState == Hero.CombatState.DEAD){
            combatState = Hero.CombatState.WIN;
        }
        if(shouldIdle()){
            combatState = Hero.CombatState.IDLE;
        }
    }
}
