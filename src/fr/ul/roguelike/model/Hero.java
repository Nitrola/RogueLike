package fr.ul.roguelike.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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

    public enum CombatState{
        ATTACKING,
        BLOCKING,
        DEAD,
        IDLE,
        WIN
    }

    private CombatState combatState;

    protected Animation<Texture> animIdle;
    protected Animation<Texture> animBlock;
    protected Animation<Texture> animAttack;
    protected Animation<Texture> animDead;
    private float stateTime;
    private float animeTime;

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
        combatState = CombatState.IDLE;
        animeTime = 0;
    }

    protected Texture[] loadFrames(int nb,String path){
        Texture[] frames = new Texture[nb];
        for(int i = 0; i < nb; i++) {
            frames[i] = new Texture(path + i + ".png");
        }
        return frames;
    }

    public void draw(SpriteBatch sb, int posX, int posY){

        stateTime += Gdx.graphics.getDeltaTime();
        Texture currentFrame = new Texture("combat/Def.png");

        if(combatState == CombatState.ATTACKING) {
            animeTime += Gdx.graphics.getDeltaTime();
            currentFrame = animAttack.getKeyFrame(animeTime, false);
        }

        if(combatState == CombatState.IDLE)
            currentFrame = animIdle.getKeyFrame(stateTime,true);

        if(combatState == CombatState.BLOCKING) {
            animeTime += Gdx.graphics.getDeltaTime();
            currentFrame = animBlock.getKeyFrame(animeTime, false);
        }
        if(combatState == CombatState.DEAD)
            currentFrame = animDead.getKeyFrame(stateTime,false);

        sb.draw(currentFrame,posX,posY,currentFrame.getWidth()*5,currentFrame.getHeight()*5);
    }

    public boolean shouldIdle(){
        if( combatState != CombatState.IDLE && animAttack.isAnimationFinished(animeTime) && animBlock.isAnimationFinished(animeTime)){
            animeTime = 0.0f;
            return true;
        }
        return false;
    }

    public void setAnimeTime(float animeTime) {
        this.animeTime = animeTime;
    }

    public void setCombatState(CombatState combatState) {
        this.combatState = combatState;
    }

    public CombatState getCombatState() {
        return combatState;
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

    public Texture getTexture(){
        return animIdle.getKeyFrames()[0];
    }
}
