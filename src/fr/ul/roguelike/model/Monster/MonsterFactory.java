package fr.ul.roguelike.model.Monster;

public class MonsterFactory {

    /**
     * Creer un monstre
     */
    public static Monster create(String monsterName) {
            if(monsterName.equals("golem")){
                return new Golem(100, 0, 2.5f, 0.0f, 15, 0, 0.2f, 0.5f);
            }
        if(monsterName.equals("arachnoide")){
            return new Arachnoide(100, 0, 2.5f, 0.0f, 15, 0, 0.2f, 0.5f);
        }
            return null;
    }
}
