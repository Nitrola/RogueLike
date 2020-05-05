package fr.ul.roguelike.model;

import fr.ul.roguelike.model.Heros.Hero;
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

    /**
     * Creer un joueur
     */
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

    /**
     * Choix de l'etat du personnage
     */
    public void updateState(){
        if(healthLeft <= 0){
            playerCharacter.setCombatState(Hero.CombatState.DEAD);
        }
        else if(playerCharacter.shouldIdle()){
            playerCharacter.setCombatState(Hero.CombatState.IDLE);
        }

    }

    /**
     * Regenere la mana du personnage
     */
    public void regenMana(){
        if(playerCharacter.getCombatState() != Hero.CombatState.DEAD) {
            if (manaLeft < playerCharacter.getMana()) {
                manaLeft += playerCharacter.getManaRegen();
            } else {
                manaLeft = playerCharacter.getMana();
            }
        }
    }

    /**
     * Determine l'etat du personnage comme etant en train d'effectuer une parade
     */
    public void parry(){
        playerCharacter.setAnimeTime(0.0f);
        playerCharacter.setCombatState(Hero.CombatState.BLOCKING);
    }

    /**
     * Inflige des damages au personnage
     */
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

    public void heal(int heal){

        healthLeft+=heal;
        if(healthLeft > playerCharacter.getHp()){
            healthLeft = playerCharacter.getHp();
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

    //////////////////////////////////
    ///////Getters and Setters///////
    /////////////////////////////////

    public PlayerGear getPlayerGear() {
        return playerGear;
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

    public void setPlayerCharacter(Hero playerCharacter) {
        this.playerCharacter = playerCharacter;
    }
}
