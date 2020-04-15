package fr.ul.roguelike.model;

import com.badlogic.gdx.graphics.Texture;
import fr.ul.roguelike.model.Heros.Warrior;
import fr.ul.roguelike.model.Items.Equipment.Equipement;
import fr.ul.roguelike.model.Items.Item;
import fr.ul.roguelike.model.Spells.Spell;

import java.util.ArrayList;

public class Player {
    private Hero playerCharacter;

    private ArrayList<Spell> spells;
    private ArrayList<Item> items;

    private int currentLevel;
    private int healthLeft;
    private float manaLeft;
    private int currentGold;
    private PlayerGear playerGear;

    private ArrayList<Item> inventory;
    private ArrayList<Equipement> inventoryEquipements;

    public Player(){

        playerCharacter = new Warrior();
        playerGear = new PlayerGear();
        spells = new ArrayList<>(playerCharacter.getNb_spell_slots());
        items = new ArrayList<>();

        healthLeft = playerCharacter.getHp();
        manaLeft = 0.0f;

        currentLevel = 0;
        currentGold = 0;

        inventory = new ArrayList<>();
        inventoryEquipements = new ArrayList<>();

    }

    public PlayerGear getPlayerGear() {
        return playerGear;
    }

    public void updateState(){
        //System.out.println(playerCharacter.getCombatState());
        if(healthLeft <= 0){
            playerCharacter.setCombatState(Hero.CombatState.DEAD);
        }
        else if(playerCharacter.shouldIdle()){
            playerCharacter.setCombatState(Hero.CombatState.IDLE);
        }

    }

    public void regenMana(){
        if(playerCharacter.getCombatState() != Hero.CombatState.DEAD) {
            if (manaLeft < playerCharacter.getMana()) {
                manaLeft += playerCharacter.getManaRegen();
            } else {
                manaLeft = playerCharacter.getMana();
            }
        }
    }

    public void parry(){
        playerCharacter.setAnimeTime(0.0f);
        playerCharacter.setCombatState(Hero.CombatState.BLOCKING);
    }
    public void receiveHit(int dmg){
        if(playerCharacter.getCombatState() != Hero.CombatState.DEAD && playerCharacter.getCombatState() != Hero.CombatState.BLOCKING){

            if (healthLeft - dmg > 0 && healthLeft - dmg < playerCharacter.getHp()) {
                this.healthLeft -= dmg;
            } else if(healthLeft - dmg <= 0) {
                healthLeft = 0;
            }
            else if(healthLeft - dmg >= playerCharacter.getHp()){
                healthLeft = playerCharacter.getHp();
            }
        }
    }

    public void useMana(int manaUsed){
        if(playerCharacter.getCombatState() != Hero.CombatState.DEAD) {
            if (manaLeft > manaUsed) {
                manaLeft -= manaUsed;
                playerCharacter.setCombatState(Hero.CombatState.ATTACKING);
                playerCharacter.setAnimeTime(0.0f);
            }
            else {
                manaLeft = 0.0f;
            }

        }
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

    public int getCurrentLevel() {
        return currentLevel;
    }

}
