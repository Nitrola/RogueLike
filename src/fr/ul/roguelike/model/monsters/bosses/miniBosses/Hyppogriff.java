package fr.ul.roguelike.model.monsters.bosses.miniBosses;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import fr.ul.roguelike.model.heros.Hero;
import fr.ul.roguelike.model.items.runes.Feather;
import fr.ul.roguelike.model.items.runes.Rune;
import fr.ul.roguelike.views.MainMenu;

import static fr.ul.roguelike.RogueLike.screenHeight;
import static fr.ul.roguelike.RogueLike.screenWidth;
import static fr.ul.roguelike.model.heros.Hero.CombatState.DEAD;

public class Hyppogriff extends MiniBoss {

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
    public Hyppogriff(int hp, int mana, float attackSpeed, float criticChance, int physicalDmg, int magicalDmg, float physicalDef, float magicalDef, float dodge) {
        super(hp, mana, attackSpeed, criticChance, physicalDmg, magicalDmg, physicalDef, magicalDef, dodge);

        animIdle = new Animation<Texture>(0.1f, loadFrames(10,"images/combat/Hyppogriff/Idle/hyppogriff_idle_"));
        animAttack = new Animation<Texture>(0.1f, loadFrames(24,"images/combat/Hyppogriff/Attack/hyppogriff_attack_"));
        animAttack0 = new Animation<Texture>(0.1f, loadFrames(24,"images/combat/Hyppogriff/Attack/hyppogriff_attack_"));
        animAttack1 = new Animation<Texture>(0.1f, loadFrames(24,"images/combat/Hyppogriff/Attack/hyppogriff_attack_"));
        animDead = new Animation<Texture>(0.1f, loadFrames(20,"images/combat/Hyppogriff/Death/hyppogriff_death_"));


        combatState = Hero.CombatState.IDLE;
        width = screenWidth/2.5f;
        height = screenHeight/1.5f;
    }

    protected void update(){
        boolean res = shouldIdle();
        if(res && combatState == DEAD){
            combatState = Hero.CombatState.WIN;
            Rune item = new Feather();
            MainMenu.player.addItem(item);
        }else {
            if (res) {
                combatState = Hero.CombatState.IDLE;
            }
        }
    }

    @Override
    public int getHitFrame() {
        return 17;
    }

    @Override
    public boolean isBlocking() {
        return false;
    }
}
