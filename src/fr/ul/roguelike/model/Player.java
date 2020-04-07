package fr.ul.roguelike.model;

import fr.ul.roguelike.model.Heros.Warrior;
import fr.ul.roguelike.model.Items.Item;
import fr.ul.roguelike.model.Spells.Spell;

import java.util.ArrayList;

public class Player {
    private Hero playerCharacter;

    private ArrayList<Spell> spells;
    private ArrayList<Item> items;

    private int current_level;
    private int healthLeft;
    private float manaLeft;


    public Player(){

        playerCharacter = new Warrior();
        spells = new ArrayList<>(playerCharacter.getNb_spell_slots());
        items = new ArrayList<>();

        healthLeft = playerCharacter.getHp();
        manaLeft = 0.0f;

        current_level = 0;
    }

    public void regenMana(){
        System.out.println(manaLeft);
        System.out.println(playerCharacter.getMana());
        if(manaLeft < playerCharacter.getMana()){
            manaLeft += playerCharacter.getManaRegen();
        }
        if(manaLeft > playerCharacter.getMana()){
            manaLeft = playerCharacter.getMana();
        }

    }

    public void receiveHit(int dmg){
        this.healthLeft -= dmg;
    }

    public Hero getPlayerCharacter() {
        return playerCharacter;
    }

    public ArrayList<Spell> getSpells() {
        return spells;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public int getHealthLeft() {
        return healthLeft;
    }

    public float getManaLeft() {
        return manaLeft;
    }

    public int getCurrent_level() {
        return current_level;
    }

}
