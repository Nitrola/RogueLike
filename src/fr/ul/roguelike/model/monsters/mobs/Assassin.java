package fr.ul.roguelike.model.monsters.mobs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import fr.ul.roguelike.model.heros.Hero;

import static fr.ul.roguelike.RogueLike.screenHeight;
import static fr.ul.roguelike.RogueLike.screenWidth;

public class Assassin extends Mob {
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
    public Assassin(int hp, int mana, float attackSpeed, float criticChance, int physicalDmg, int magicalDmg, float physicalDef, float magicalDef) {
        super(hp, mana, attackSpeed, criticChance, physicalDmg, magicalDmg, physicalDef, magicalDef);
        animIdle = new Animation<Texture>(0.1f, loadFrames(16,"images/combat/Assassin/Idle/assassin_idle_"));
        animAttack = new Animation<Texture>(0.07f, loadFrames(23,"images/combat/Assassin/Attack/assassin_attack_"));


        combatState = Hero.CombatState.IDLE;
        width = screenWidth/7f*3;
        height = screenHeight/4.5f*3;
    }

    @Override
    public int getHitFrame() {
        return 13;
    }
}
