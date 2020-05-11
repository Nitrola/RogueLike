package fr.ul.roguelike.model.Monster.Boss.MiniBoss;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import fr.ul.roguelike.model.Heros.Hero;

import static fr.ul.roguelike.RogueLike.screenHeight;
import static fr.ul.roguelike.RogueLike.screenWidth;

public class Vampire extends MiniBoss {
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
}
