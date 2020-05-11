package fr.ul.roguelike.model.Monster;

import fr.ul.roguelike.model.Monster.Boss.Arachnoide;
import fr.ul.roguelike.model.Monster.Boss.Griffin;

public class MonsterFactory {

    /**
     * Creer un monstre
     */
    public static Monster create(String monsterName) {
        if(monsterName.equals("golem")){
            return new Golem(100, 0, 2.5f, 0.0f, 5, 0, 0.2f, 0.5f);
        }
        if(monsterName.equals("arachnoide")){
            return new Arachnoide(100, 0, 5f, 0.0f, 20, 0, 0.2f, 0.5f, 0.2f);
        }
        if(monsterName.equals("griffin")){
            return new Griffin(100, 0, 3f, 0.0f, 10, 0, 0.2f, 0.5f, 0.2f);
        }
        if(monsterName.equals("skeleton")){
            return new Skeleton(100, 0, 2.5f, 0.0f, 5, 0, 0.2f, 0.5f);
        }
            return null;
    }
}
