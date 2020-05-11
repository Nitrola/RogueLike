package fr.ul.roguelike.model.Monster;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import fr.ul.roguelike.model.Heros.Hero;

import static fr.ul.roguelike.RogueLike.screenHeight;
import static fr.ul.roguelike.RogueLike.screenWidth;

public class Skeleton extends Mob{

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
    public Skeleton(int hp, int mana, float attackSpeed, float criticChance, int physicalDmg, int magicalDmg, float physicalDef, float magicalDef) {
        super(hp, mana, attackSpeed, criticChance, physicalDmg, magicalDmg, physicalDef, magicalDef);
        animIdle = new Animation<Texture>(0.1f, loadFrames(15,"images/combat/Griffin/Idle/griffin_idle_"));
        animAttack = new Animation<Texture>(0.1f, loadFrames(13,"images/combat/Griffin/Attack/griffin_attack_"));
        animDead = new Animation<Texture>(0.1f, loadFrames(13,"images/combat/Griffin/Death/griffin_death_"));


        combatState = Hero.CombatState.IDLE;
        width = screenWidth/1.7f;
        height = screenHeight/1.2f;
    }

    @Override
    public int getHitFrame() {
        return 0;
    }
}
