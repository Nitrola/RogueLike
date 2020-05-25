package fr.ul.roguelike.model.heros;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import static fr.ul.roguelike.RogueLike.screenWidth;
import static fr.ul.roguelike.RogueLike.screenHeight;

public class Mage extends Hero {

    public Mage() {
        super(150,75,0.30f,20f,5,20,5f,10f);
        nb_spell_slots = 2;

        animAttack = new Animation<Texture>(0.075f,loadFrames(20,"images/combat/Mage/Attack/mage_attack_"));
        animBlock = new Animation<Texture>(0.075f,loadFrames(13,"images/combat/Mage/Block/mage_block_"));
        animDead = new Animation<Texture>(0.1f,loadFrames(14,"images/combat/Mage/Death/mage_death_"));
        animIdle = new Animation<Texture>(0.1f,loadFrames(11,"images/combat/Mage/Idle/mage_idle"));
        animHit = new Animation<Texture>(0.1f,loadFrames(4,"images/combat/Mage/Hit/mage_death_"));

        width = screenWidth/2f;
        height = screenHeight/1.5f;
    }

    @Override
    public int getAttackCost() {
        return 15;
    }

    @Override
    public int getBlockCost() {
        return 10;
    }

    @Override
    public int getHitFrame() {
        return 18;
    }
}
