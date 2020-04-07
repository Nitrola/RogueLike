package fr.ul.roguelike.model.Monster;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Monster {
    private int hp;
    private int currentHp;
    private int mana;
    private int currentMana;

    protected int attackSpeed;
    private float timeSincePreviousAttack;
    private float criticChance;

    private int physicalDmg;
    private int magicalDmg;

    //ranged from 0 to 1
    private float physicalDef;
    private float magicalDef;

    protected Animation<TextureRegion> anim;
    protected float stateTime;

    public Monster(int hp, int mana, int attackSpeed, float criticChance, int physicalDmg, int magicalDmg, float physicalDef, float magicalDef) {
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
    }

    public void draw(SpriteBatch sb, int posX, int posY){
        return;
    }
    public void receiveHit(int hitpoint){
        currentHp -= hitpoint;
    }
    public float hpLeftRatio(){
        return 1.f* currentHp/hp;
    }
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

    public int getAttackSpeed() {
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

    public Animation<TextureRegion> getAnim() {
        return anim;
    }
}
