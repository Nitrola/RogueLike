package fr.ul.roguelike.model.heros;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.ul.roguelike.model.Player;
import fr.ul.roguelike.views.MainMenu;

import java.util.Random;

import static fr.ul.roguelike.model.heros.Hero.CombatState.*;

public abstract class Hero {
    private int hp;
    private int mana;
    private float manaRegen;
    private float criticChance;

    private int physicalDmg;
    private int magicalDmg;
    protected float posX, posY;

    //ranged from 0 to 1
    private float physicalDef;
    private float magicalDef;

    int nb_spell_slots;

    private boolean regen;

    public enum CombatState{
        ATTACKING,
        BLOCKING,
        HIT,
        DEAD,
        IDLE,
        RESSURECTING,
        WIN
    }

    protected CombatState combatState;
    Sound sound;

    Animation<Texture> animIdle;
    Animation<Texture> animBlock;
    Animation<Texture> animAttack;
    Animation<Texture> animDead;
    Animation<Texture> animHit;
    protected float stateTime;
    protected float animeTime;

    float width;
    float height;

    protected boolean hasAttack, degat, critic;;
    private boolean inInventory;


    /**
     * Creer un heros
     * @param hp Point de vie du joueur
     * @param mana Point de mana du joueur
     * @param mana_regen Regeneration de mana toutes les 50 ms
     * @param critic_chance Taux de coup critique
     * @param physical_dmg Degat physique
     * @param magical_dmg Degat magique
     * @param physical_def pourcentage de reduction des attaques physiques
     * @param magical_def pourcentage de reduction des attaques magiques
     */
    public Hero(int hp, int mana, float mana_regen, float critic_chance, int physical_dmg, int magical_dmg, float physical_def, float magical_def) {
        this.hp = hp;
        this.mana = mana;
        this.manaRegen = mana_regen;
        this.criticChance = critic_chance;
        this.physicalDmg = physical_dmg;
        this.magicalDmg = magical_dmg;
        this.physicalDef = physical_def;
        this.magicalDef = magical_def;
        this.nb_spell_slots = 0;
        combatState = CombatState.IDLE;
        animeTime = 0;
        inInventory = false;
        degat = false;
        hasAttack = false;
        critic = false;
        regen = false;
    }

    Texture[] loadFrames(int nb,String path){
        Texture[] frames = new Texture[nb];
        for(int i = 0; i < nb; i++) {
            frames[i] = new Texture(path + i + ".png");
        }
        return frames;
    }

    public void draw(SpriteBatch sb, float posX, float posY){
        this.posX = posX;
        this.posY = posY;

        stateTime += Gdx.graphics.getDeltaTime();
        Texture currentFrame = new Texture("images/combat/Def.png");

        if(combatState == CombatState.ATTACKING) {
            animeTime += Gdx.graphics.getDeltaTime();
            currentFrame = animAttack.getKeyFrame(animeTime, false);
            if(animAttack.getKeyFrameIndex(animeTime) == getHitFrame() && !hasAttack){
                degat = true;
                hasAttack = true;
                sound.play(0.3f);
                if (MainMenu.player.getManaLeft() > MainMenu.player.getPlayerCharacter().getAttackCost()){
                    MainMenu.player.useMana(MainMenu.player.getPlayerCharacter().getAttackCost());
                }
            }
        }

        if(combatState == CombatState.IDLE)
            currentFrame = animIdle.getKeyFrame(stateTime,true);

        if(combatState == CombatState.BLOCKING) {
            animeTime += Gdx.graphics.getDeltaTime();
            currentFrame = animBlock.getKeyFrame(animeTime, false);
        }
        if(combatState == CombatState.DEAD) {
            animeTime += Gdx.graphics.getDeltaTime();
            currentFrame = animDead.getKeyFrame(animeTime, false);
        }
        if(combatState == CombatState.HIT) {
            animeTime += Gdx.graphics.getDeltaTime();
            currentFrame = animHit.getKeyFrame(animeTime, false);
        }

        sb.draw(currentFrame,posX,posY,width , height);
    }

    public abstract int getAttackCost();
    public abstract int getBlockCost();

    public boolean shouldIdle(){
        if( combatState != CombatState.IDLE){
            if(combatState == ATTACKING && animAttack.isAnimationFinished(animeTime)) {
                animeTime = 0.0f;
                critic = false;
                return true;
            }
            if(combatState == BLOCKING && animBlock.isAnimationFinished(animeTime)) {
                animeTime = 0.0f;
                return true;
            }
            if(combatState == HIT && animHit.isAnimationFinished(animeTime)) {
                animeTime = 0.0f;
                return true;
            }
            if(combatState == DEAD && animDead.isAnimationFinished(animeTime)) {
                animeTime = 0.0f;
                return true;
            }
        }
        return false;
    }

    private void changeSize(){
        if(inInventory) {
            width = getWidth() / 1.5f;
            height = getHeight() / 1.5f;
        } else {
            width = getWidth() * 1.5f;
            height = getHeight() * 1.5f;
        }
    }

    public boolean isRegen() {
        return regen;
    }

    public void setRegen(boolean regen) {
        this.regen = regen;
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

    /**
     * Change les stats du heros
     */
    public void addStat(float hp, float mana, float mana_regen, float critic_chance, float physical_dmg, float magical_dmg, float physical_def, float magical_def){
        this.hp += hp;
        this.mana += mana;
        this.manaRegen += mana_regen;
        this.criticChance += critic_chance;
        this.physicalDmg += physical_dmg;
        this.magicalDmg += magical_dmg;
        this.physicalDef += physical_def;
        this.magicalDef += magical_def;
    }

    public int getHp() {
        return hp ;
    }

    public int getMana() {
        return mana;
    }

    public float getCriticChance() {
        return criticChance;
    }

    public float getPhysicalDmg(Player player) {
        float res = physicalDmg + player.getPhysicalAttackBonus();
        Random random = new Random();
        int alea = random.nextInt(100);
        if(alea < criticChance){
            critic = true;
            res *= 2;
        }
        return res;
    }

    public float getMagicalDmg(Player player) {
        float res = magicalDmg + player.getMagicalAttackBonus();
        Random random = new Random();
        int alea = random.nextInt(100);
        if(alea < criticChance){
            res *= 2;
        }
        return res;
    }

    public float getPhysicalDmgWithoutCrit(Player player) {
        return physicalDmg + player.getPhysicalAttackBonus();
    }

    public float getMagicalDmgWithoutCrit(Player player) {
        return  magicalDmg + player.getMagicalAttackBonus();
    }

    public float getPhysicalDef(Player player) {
        return physicalDef + player.getPhysicalDefenseBonus();
    }

    public float getMagicalDef(Player player) {
        return magicalDef + player.getMagicalDefenseBonus();
    }


    public float getManaRegenTime() {
        return 0.05f;
    }

    public float getManaRegen() {
        return manaRegen;
    }

    public Texture getTexture(){
        return animIdle.getKeyFrames()[0];
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }


    public void setInInventory(boolean inInventory) {
        this.inInventory = inInventory;
        changeSize();
    }

    public void setHasAttack(boolean hasAttack) {
        this.hasAttack = hasAttack;
    }

    public boolean isDegat() {
        return degat;
    }

    public void setDegat(boolean degat) {
        this.degat = degat;
    }

    public boolean isCritic() {
        return critic;
    }

    public abstract int getHitFrame();
}
