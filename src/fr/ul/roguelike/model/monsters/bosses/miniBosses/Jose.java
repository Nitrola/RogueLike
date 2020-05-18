package fr.ul.roguelike.model.monsters.bosses.miniBosses;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import fr.ul.roguelike.model.heros.Hero;

import static fr.ul.roguelike.RogueLike.screenHeight;
import static fr.ul.roguelike.RogueLike.screenWidth;

public class Jose extends MiniBoss {
    /**
     * Creer MiniBoss de fin de niveau
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
    public Jose(int hp, int mana, float attackSpeed, float criticChance, int physicalDmg, int magicalDmg, float physicalDef, float magicalDef, float dodge) {
        super(hp, mana, attackSpeed, criticChance, physicalDmg, magicalDmg, physicalDef, magicalDef, dodge);

        animIdle = new Animation<Texture>(0.1f, loadFrames(13,"images/combat/Jose/Idle/jose_idle_"));
        animAttack = new Animation<Texture>(0.1f, loadFrames(22,"images/combat/Jose/Attack/jose_attack_"));
        animAttack1 = new Animation<Texture>(0.1f, loadFrames(22,"images/combat/Jose/Attack/jose_attack_"));
        animAttack0 = new Animation<Texture>(0.1f, loadFrames(22,"images/combat/Jose/Attack/jose_attack_"));
        animDead = new Animation<Texture>(0.1f, loadFrames(24,"images/combat/Jose/Death/jose_death_"));


        combatState = Hero.CombatState.IDLE;
        width = screenWidth/2.5f;
        height = screenHeight/1.5f;
    }

    @Override
    public int getHitFrame() {
        return 15;
    }

    @Override
    public boolean isBlocking() {
        return false;
    }
}
