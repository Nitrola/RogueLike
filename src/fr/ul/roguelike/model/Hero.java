package fr.ul.roguelike.model;

public abstract class Hero {
    private int hp;
    private int mana;

    private int manaRegenTime;
    private float manaRegen;
    private float criticChance;

    private int physicalDmg;
    private int magicalDmg;

    //ranged from 0 to 1
    private float physicalDef;
    private float magicalDef;

    protected int nb_spell_slots;

    public Hero(int hp, int mana, float mana_regen, float critic_chance, int physical_dmg, int magical_dmg, float physical_def, float magical_def,int manaRegenTime) {
        this.hp = hp;
        this.mana = mana;
        this.manaRegen = mana_regen;
        this.criticChance = critic_chance;
        this.physicalDmg = physical_dmg;
        this.magicalDmg = magical_dmg;
        this.physicalDef = physical_def;
        this.magicalDef = magical_def;
        this.nb_spell_slots = 0;
        this.manaRegenTime = manaRegenTime;
    }

    public int getHp() {
        return hp;
    }

    public int getMana() {
        return mana;
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

    public int getNb_spell_slots() {
        return nb_spell_slots;
    }

    public int getManaRegenTime() {
        return manaRegenTime;
    }

    public float getManaRegen() {
        return manaRegen;
    }
}
