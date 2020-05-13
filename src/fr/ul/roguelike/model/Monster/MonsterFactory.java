package fr.ul.roguelike.model.Monster;

import fr.ul.roguelike.model.Monster.Boss.Arachnoide;
import fr.ul.roguelike.model.Monster.Boss.Griffin;
import fr.ul.roguelike.model.Monster.Boss.MiniBoss.Vampire;
import fr.ul.roguelike.model.Monster.Mob.Dragon;
import fr.ul.roguelike.model.Monster.Mob.Golem;
import fr.ul.roguelike.model.Monster.Mob.Knight;
import fr.ul.roguelike.model.Monster.Mob.Skeleton;

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
            return new Griffin(200, 0, 3f, 0.0f, 30, 0, 20f, 10f, 20f);
        }
        if(monsterName.equals("skeleton")){
            return new Skeleton(60, 0, 3.5f, 0.0f, 15, 0, 5f, 5f);
        }
        if (monsterName.equals("vampire")){
            return new Vampire(60, 0, 3f, 0.0f, 10, 10, 5f, 2.5f, 25f);
        }
        if(monsterName.equals("dragon")){
            return new Dragon(80, 0, 3.5f, 0.0f, 10, 5, 10f, 10f);
        }
        if (monsterName.equals("knight")){
            return new Knight(75, 0, 8f, 0.0f, 0, 1, 8f, 1f);
        }
            return null;
    }
}
