package fr.ul.roguelike.model.Heros;

import fr.ul.roguelike.model.Hero;

public class Warrior extends Hero {

    public Warrior() {
        super(100,100,1f,0.01f,10,1,5,5,2);
        nb_spell_slots = 2;
    }
}
