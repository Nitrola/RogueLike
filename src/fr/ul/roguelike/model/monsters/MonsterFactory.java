package fr.ul.roguelike.model.monsters;

import fr.ul.roguelike.model.monsters.bosses.Arachnoide;
import fr.ul.roguelike.model.monsters.bosses.Griffin;
import fr.ul.roguelike.model.monsters.bosses.miniBosses.Vampire;
import fr.ul.roguelike.model.monsters.mobs.Dragon;
import fr.ul.roguelike.model.monsters.mobs.Golem;
import fr.ul.roguelike.model.monsters.mobs.Knight;
import fr.ul.roguelike.model.monsters.mobs.Skeleton;

public class MonsterFactory {

    /**
     * Creer un monstre
     */
    public static Monster create(String monsterName) {
        if(monsterName.equals("golem")){
            return new Golem(100, 0, 3.5f, 0.0f, 5, 0, 0.2f, 0.5f);
        }
        if(monsterName.equals("arachnoide")){
            return new Arachnoide(200, 0, 5f, 0.0f, 50, 0, 15f, 50f, 20f);
        }
        if(monsterName.equals("griffin")){
            return new Griffin(150, 0, 5f, 0.0f, 25, 0, 18f, 10f, 20f);
        }
        if(monsterName.equals("skeleton")){
            return new Skeleton(70, 0, 4f, 0.0f, 15, 0, 5f, 5f);
        }
        if (monsterName.equals("vampire")){
            return new Vampire(60, 0, 3f, 0.0f, 8, 4, 5f, 2.5f, 25f);
        }
        if(monsterName.equals("dragon")){
            return new Dragon(80, 0, 6f, 0.0f, 7, 2, 10f, 10f);
        }
        if (monsterName.equals("knight")){
            return new Knight(60, 0, 7f, 0.0f, 0, 2, 8f, 1f);
        }
            return null;
    }
}
