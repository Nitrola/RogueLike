package fr.ul.roguelike.model.monsters.bosses.miniBosses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.ul.roguelike.model.heros.Hero;
import fr.ul.roguelike.views.MainMenu;

import static fr.ul.roguelike.RogueLike.screenHeight;
import static fr.ul.roguelike.RogueLike.screenWidth;
import static fr.ul.roguelike.model.heros.Hero.CombatState.DEAD;

public class Vampire extends MiniBoss {
    private int ratioRegen;
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
    public Vampire(int hp, int mana, float attackSpeed, float criticChance, int physicalDmg, int magicalDmg, float physicalDef, float magicalDef, float dodge) {
        super(hp, mana, attackSpeed, criticChance, physicalDmg, magicalDmg, physicalDef, magicalDef, dodge);
        this.ratioRegen = 3;
        animIdle = new Animation<Texture>(0.1f, loadFrames(6,"images/combat/Vampire/Idle/vampire_idle_"));
        animAttack = new Animation<Texture>(0.1f, loadFrames(10,"images/combat/Vampire/Attack/vampire_attack_"));
        animAttack0 = new Animation<Texture>(0.1f, loadFrames(10,"images/combat/Vampire/Attack/vampire_attack_"));
        animAttack1 = new Animation<Texture>(0.1f, loadFrames(10,"images/combat/Vampire/Attack2/vampire_attack_"));
        animBlock = new Animation<Texture>(0.1f, loadFrames(10,"images/combat/Vampire/Dodge/vampire_dodge_"));
        animDead = new Animation<Texture>(0.1f, loadFrames(17,"images/combat/Vampire/Death/vampire_death_"));


        combatState = Hero.CombatState.IDLE;
        width = screenWidth/2.5f;
        height = screenHeight/1.5f;
    }

    @Override
    public int getHitFrame() {
        if(animAttack.equals(animAttack0)) {
            return 5;
        }else{
            return 2;
        }
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

    private void update(){
        boolean res = shouldIdle();
        if(res && combatState == DEAD){
            combatState = Hero.CombatState.WIN;
        }else {
            if (res) {
                combatState = Hero.CombatState.IDLE;
            }
        }
        if(degat){
            currentHp += MainMenu.player.getDamage(this)/ratioRegen;
            if(currentHp > getHp()){
                currentHp = getHp();
            }
        }
    }
}
