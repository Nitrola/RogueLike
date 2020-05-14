package fr.ul.roguelike.model;

import fr.ul.roguelike.model.heros.Hero;
import fr.ul.roguelike.model.heros.Warrior;
import fr.ul.roguelike.model.items.equipments.Equipement;
import fr.ul.roguelike.model.items.Item;
import fr.ul.roguelike.model.items.runes.Rune;
import fr.ul.roguelike.model.monsters.Monster;
import fr.ul.roguelike.model.spells.Spell;
import java.util.ArrayList;
import java.util.Random;

public class Player {
    private Hero playerCharacter;

    private ArrayList<Spell> spells;
    private ArrayList<Item> items;

    private int currentLevel;
    private int healthLeft;
    private float manaLeft;
    private int currentGold;
    private PlayerGear playerGear;

    private int[] potionsTable; //Premiere case: potion de soin - Seconde case: potion de mana
    private Equipement[] inventoryEquipements;

    private int cpt = 1; //Niveau de la map
    private int cptStage = 1; //Niveau du stage

    /**
     * Creer un joueur
     */
    public Player(){
        playerCharacter = new Warrior();
        playerGear = new PlayerGear();
        spells = new ArrayList<>(playerCharacter.getNb_spell_slots());
        items = new ArrayList<>();
        //0 = head, 1 = right arm, 2 = left arm, 3 = plate
        inventoryEquipements = new Equipement[4];

        healthLeft = playerCharacter.getHp();
        manaLeft = 0.0f;

        currentLevel = 0;
        currentGold = 999;

        potionsTable = new int[2];
        potionsTable[0] = 100; //Ca va degager
        potionsTable[1] = 100; //Ca va degager

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
     * Regenere la mana du personnage
     */
    public void regenMana(float mana){
        if(playerCharacter.getCombatState() != Hero.CombatState.DEAD) {
            if (manaLeft + mana < playerCharacter.getMana()) {
                manaLeft += mana;
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

    /**
     * Donne une somme aléatoire d'argent
     * @param nb
     */
    public void giveMoney(int nb) {
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt((int) (nb*1.20) - (int)(nb*0.80)) + (int)(nb*0.80);
        this.currentGold += randomInt;
    }

    /**
     * Remplit les slots
     * @param cpt le slot à remplir
     * @param equipement l'équipement à équiper
     */
    public void fillInventary(int cpt, Equipement equipement){
        inventoryEquipements[cpt] = equipement;
    }

    public Equipement getEquipement(int cpt){
        return inventoryEquipements[cpt];
    }

    //////////////////////////////////
    ///////Getters and Setters///////
    /////////////////////////////////

    /**
     * Degat infligé au monstre
     */
    public float getDamage(Monster monster){
        float physicalDamage = (float) (getPlayerCharacter().getPhysicalDmg(this) / (1+(Math.pow(monster.getPhysicalDef(),2))/100));
        float magicalDamage = (float) (getPlayerCharacter().getMagicalDmg(this) / (1+(Math.pow(monster.getMagicalDef(),2))/100));
        return (float)((physicalDamage + magicalDamage)/(Math.sqrt(cpt)+(Math.log(cptStage))/10));
    }

    public float getPhysicalAttackBonus(){
        int bonus = 0;
        for(Equipement e :inventoryEquipements){
            if(e != null) {
                bonus += e.getPhysicalDamage();
            }
        }
        return bonus;
    }

    public float getMagicalAttackBonus(){
        int bonus = 0;
        for(Equipement e : inventoryEquipements){
            if(e != null){
                bonus+= e.getMagicDamage();
            }
        }
        return bonus;
    }

    public float getPhysicalDefenseBonus() {
        int bonus = 0;
        for(Equipement e : inventoryEquipements){
            if(e != null){
                bonus+= e.getArmor();
            }
        }
        return bonus;
    }

    public float getMagicalDefenseBonus() {
        int bonus = 0;
        for(Equipement e : inventoryEquipements){
            if(e != null){
                bonus+= e.getMagicResist();
            }
        }
        return bonus;
    }

    public void addStat(float hp, float mana, float mana_regen, float critic_chance, float physical_dmg, float magical_dmg, float physical_def, float magical_def){
        getPlayerCharacter().addStat(hp,mana,mana_regen,critic_chance,physical_dmg,magical_dmg,physical_def,magical_def);
    }

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

    public int getNbPotionHealth(){
        return potionsTable[0];
    }

    public int getNbPotionMana(){
        return potionsTable[1];
    }

    public void receivePotionHealth(){
        potionsTable[0]++;
    }

    public void usePotionHealth(){
        potionsTable[0]--;
    }

    public void receivePotionMana(){
        potionsTable[1]++;
    }

    public void usePotionMana(){
        potionsTable[1]--;
    }

    public void resetMana() {
        manaLeft=0;
    }
    public int getCurrentGold() {
        return currentGold;
    }
    public void setCurrentGold(int currentGold) {
        this.currentGold = currentGold;
    }
    public int getCpt() {
        return cpt;
    }
    public void increaseCpt(){
        cpt++;
        cptStage = 1;
    }
    public void increaseCptStage(){
        cptStage++;
    }

    public int getCptStage() {
        return cptStage;
    }
}
