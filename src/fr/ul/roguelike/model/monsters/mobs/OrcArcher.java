package fr.ul.roguelike.model.monsters.mobs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import fr.ul.roguelike.model.heros.Hero;

import static fr.ul.roguelike.RogueLike.screenHeight;
import static fr.ul.roguelike.RogueLike.screenWidth;

public class OrcArcher extends Mob {
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
    public OrcArcher(int hp, int mana, float attackSpeed, float criticChance, int physicalDmg, int magicalDmg, float physicalDef, float magicalDef) {
        super(hp, mana, attackSpeed, criticChance, physicalDmg, magicalDmg, physicalDef, magicalDef);

        animIdle = new Animation<Texture>(0.1f, loadFrames(11,"images/combat/OrcArcher/Idle/orc_idle_"));
        animAttack = new Animation<Texture>(0.07f, loadFrames(14,"images/combat/OrcArcher/Attack/orc_attack_"));
        animDead = new Animation<Texture>(0.07f, loadFrames(9,"images/combat/OrcArcher/Death/orc_death_"));


        combatState = Hero.CombatState.IDLE;
        width = screenWidth/7f*2;
        height = screenHeight/4.5f*2;
    }

    @Override
    public int getHitFrame() {
        return 10;
    }
}
