package fr.ul.roguelike.model.Monster;

public class MonsterFactory {

    public static Monster create(String monsterName) {
            if(monsterName == "golem"){
                return new Golem(100, 0, 2.5f, 0.0f, 15, 0, 0.2f, 0.5f);
            }

            return null;
    }
}
