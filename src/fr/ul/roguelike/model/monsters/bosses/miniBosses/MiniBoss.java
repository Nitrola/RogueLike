package fr.ul.roguelike.model.monsters.bosses.miniBosses;

import fr.ul.roguelike.model.monsters.bosses.Boss;

public abstract class MiniBoss extends Boss {
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
    public MiniBoss(int hp, int mana, float attackSpeed, float criticChance, int physicalDmg, int magicalDmg, float physicalDef, float magicalDef, float dodge) {
        super(hp, mana, attackSpeed, criticChance, physicalDmg, magicalDmg, physicalDef, magicalDef, dodge);
    }
}
