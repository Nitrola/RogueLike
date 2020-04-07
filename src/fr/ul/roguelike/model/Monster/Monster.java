package fr.ul.roguelike.model.Monster;

public abstract class Monster {
    private int hp;
    private int mana;

    protected int attackSpeed;
    private float timeSincePreviousAttack;
    private float criticChance;

    private int physicalDmg;
    private int magicalDmg;

    //ranged from 0 to 1
    private float physicalDef;
    private float magicalDef;

    public Monster(int hp, int mana, int attackSpeed, float criticChance, int physicalDmg, int magicalDmg, float physicalDef, float magicalDef) {
        this.hp = hp;
        this.mana = mana;
        this.attackSpeed = attackSpeed;
        this.criticChance = criticChance;
        this.physicalDmg = physicalDmg;
        this.magicalDmg = magicalDmg;
        this.physicalDef = physicalDef;
        this.magicalDef = magicalDef;
        this.timeSincePreviousAttack = 0.0f;
    }

    public void updateLastAttackTimer(float newTime){
        timeSincePreviousAttack = newTime;
    }
    public float getTimeSincePreviousAttack() {
        return timeSincePreviousAttack;
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
}
