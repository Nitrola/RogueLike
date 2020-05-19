package fr.ul.roguelike.model.monsters;

import fr.ul.roguelike.model.monsters.bosses.Arachnoide;
import fr.ul.roguelike.model.monsters.bosses.Griffin;
import fr.ul.roguelike.model.monsters.bosses.miniBosses.Hyppogriff;
import fr.ul.roguelike.model.monsters.bosses.miniBosses.Jose;
import fr.ul.roguelike.model.monsters.bosses.miniBosses.OldMan;
import fr.ul.roguelike.model.monsters.bosses.miniBosses.Vampire;
import fr.ul.roguelike.model.monsters.mobs.*;

public class MonsterFactory {

    /**
     * Creer un monstre
     */
    public static Monster create(String monsterName) {
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
        if(monsterName.equals("assassin")){
            return new Assassin(50, 0, 5f, 0, 10, 0, 5f, 5f);
        }
        if (monsterName.equals("cyclops")){
            return new DarkCyclops(100, 0, 8f, 0, 4, 1, 1f, 7f);
        }
        if(monsterName.equals("inquisitor")){
            return new Inquisitor(80, 0 , 4f, 0, 20, 5, 10f, 2f);
        }
        if(monsterName.equals("stag_knight")){
            return new StagKnight(100, 0, 5f, 0, 0, 40, 10f, 10f);
        }
        if(monsterName.equals("jose")){
            return new Jose(150, 0, 5f, 0, 50, 10, 40f, 40f, 0);
        }
        if(monsterName.equals("oldman")){
            return new OldMan(80, 0, 4f, 0, 0, 20, 10f, 10f, 0);
        }
        if(monsterName.equals("hyppogriff")){
            return new Hyppogriff(80, 0, 4f, 0, 0, 20, 10f, 10f, 0);
        }
        if(monsterName.equals("orcarcher")){
            return new OrcArcher(100, 0, 3f, 0, 10, 10, 10f, 10f);
        }
            return null;
    }
}
