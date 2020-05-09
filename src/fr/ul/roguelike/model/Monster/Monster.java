package fr.ul.roguelike.model.Monster;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.ul.roguelike.model.Heros.Hero;

public abstract class Monster {
    private int hp;
    private int currentHp;
    private int mana;
    private int currentMana;

    protected float attackSpeed;
    private float timeSincePreviousAttack;
    private float criticChance;

    private int physicalDmg;
    private int magicalDmg;

    //ranged from 0 to 1
    private float physicalDef;
    private float magicalDef;

    protected Animation<TextureRegion> animIdleMonster;
    protected Animation<Texture> animIdle;
    protected Animation<Texture> animAttack;
    protected Animation<Texture> animDead;
    protected float stateTime;

    protected float posX, posY, height, width;
    protected float animeTime;

    protected Hero.CombatState combatState;
    protected boolean degat;
    protected boolean hasAttack;

    /**
     * Creer Monstre
     * @param hp Vie du monstre
     * @param mana Mana du monstre
     * @param attackSpeed Vitesse d'attaque du monstre
     * @param criticChance Taux de coup critique du monstre
     * @param physicalDmg Dommage physique du monstre
     * @param magicalDmg Dommage magique du monstre
     * @param physicalDef Defense physique du monstre
     * @param magicalDef Defense magique du monstre
     */
    public Monster(int hp, int mana, float attackSpeed, float criticChance, int physicalDmg, int magicalDmg, float physicalDef, float magicalDef) {
        this.hp = hp;
        this.currentHp = hp;
        this.mana = mana;
        this.currentMana = mana;
        this.attackSpeed = attackSpeed;
        this.criticChance = criticChance;
        this.physicalDmg = physicalDmg;
        this.magicalDmg = magicalDmg;
        this.physicalDef = physicalDef;
        this.magicalDef = magicalDef;
        this.timeSincePreviousAttack = 0.0f;
        degat = false;
        hasAttack = false;
    }

    /**
     * Affiche le monstre
     */
    public abstract void draw(SpriteBatch sb, int posX, int posY);

    /**
     * Retire de la vie au monstre
     */
    public void receiveHit(int hitpoint){
        currentHp -= hitpoint;
    }

    /**
     * Calcul le ration de point de vie restant
     */
    public float hpLeftRatio(){
        return 1.f* currentHp/hp;
    }

    protected Texture[] loadFrames(int nb, String path){
        Texture[] frames = new Texture[nb];
        for(int i = 0; i < nb; i++) {
            frames[i] = new Texture(path + i + ".png");
        }
        return frames;
    }

    //////////////////////////////////
    ///////Getters and Setters///////
    /////////////////////////////////

    public void updateLastAttackTimer(float newTime){
        timeSincePreviousAttack = newTime;
    }

    public float getTimeSincePreviousAttack() {
        return timeSincePreviousAttack;
    }


    public int getCurrentHp() {
        return currentHp;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public int getHp() {
        return hp;
    }

    public int getMana() {
        return mana;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    public float getCriticChance() {
        return criticChance;
    }

    public int getPhysicalDmg() {
        return physicalDmg;
    }

    public int getMagicalDmg() {
        return magicalDmg;
    }

    public float getPhysicalDef() {
        return physicalDef;
    }

    public float getMagicalDef() {
        return magicalDef;
    }

    public Animation<TextureRegion> getAnimIdle() {
        return animIdleMonster;
    }

    public float getHeight() {
        return height;
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setCombatState(Hero.CombatState combatState) {
        this.combatState = combatState;
    }

    public Hero.CombatState getCombatState() {
        return combatState;
    }


    public boolean isDegat() {
        return degat;
    }

    public void setDegat(boolean degat) {
        this.degat = degat;
    }

    public void setHasAttack(boolean hasAttack) {
        this.hasAttack = hasAttack;
    }
}
