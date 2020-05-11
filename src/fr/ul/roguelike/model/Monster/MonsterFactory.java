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
            return new Arachnoide(100, 0, 5f, 0.0f, 20, 0, 0.2f, 0.5f, 20f);
        }
        if(monsterName.equals("griffin")){
            return new Griffin(100, 0, 3f, 0.0f, 10, 0, 0.2f, 0.5f, 20f);
        }
        if(monsterName.equals("skeleton")){
            return new Skeleton(100, 0, 3.5f, 0.0f, 5, 0, 0.2f, 0.5f);
        }
        if (monsterName.equals("vampire")){
            return new Vampire(100, 0, 3f, 0.0f, 10, 0, 0.2f, 0.5f, 20f);
        }
        if(monsterName.equals("dragon")){
            return new Dragon(100, 0, 3.5f, 0.0f, 5, 0, 0.2f, 0.5f);
        }
        if (monsterName.equals("knight")){
            return new Knight(100, 0, 8f, 0.0f, 1, 0, 0.2f, 0.5f);
        }
            return null;
    }
}
