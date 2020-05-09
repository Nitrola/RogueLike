package fr.ul.roguelike.model.Monster.Boss;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import fr.ul.roguelike.model.Heros.Hero;

import static fr.ul.roguelike.RogueLike.screenHeight;
import static fr.ul.roguelike.RogueLike.screenWidth;

public class Arachnoide extends Boss {

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
    public Arachnoide(int hp, int mana, float attackSpeed, float criticChance, int physicalDmg, int magicalDmg, float physicalDef, float magicalDef, float dodge) {
        super(hp, mana, attackSpeed, criticChance, physicalDmg, magicalDmg, physicalDef, magicalDef, dodge);

        animIdle = new Animation<Texture>(0.1f, loadFrames(6,"images/combat/Arachnoide/Idle/arachnoide_idle_"));
        animAttack = new Animation<Texture>(0.1f, loadFrames(15,"images/combat/Arachnoide/Attack/arachnoide_attack_"));
        animAttack0 = new Animation<Texture>(0.09f, loadFrames(15,"images/combat/Arachnoide/Attack/arachnoide_attack_"));
        animAttack1 = new Animation<Texture>(0.11f, loadFrames(15,"images/combat/Arachnoide/Attack2/arachnoide_attack_"));
        animBlock = new Animation<Texture>(0.1f, loadFrames(15,"images/combat/Arachnoide/Block/arachnoide_block_"));
        animDead = new Animation<Texture>(0.1f, loadFrames(9,"images/combat/Arachnoide/Death/arachnoide_death_"));


        combatState = Hero.CombatState.IDLE;
        width = screenWidth/2f;
        height = screenHeight/1.5f;
    }
}
