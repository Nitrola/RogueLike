package fr.ul.roguelike.model.Heros;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

public class Alchimist extends Hero {
    public Alchimist() {
        super(100,100,5f,0.01f,10,1,5,5,1);
        nb_spell_slots = 2;

        animAttack = new Animation<Texture>(0.075f,loadFrames(20,"images/combat/Mage/Attack/mage_attack_"));
        animBlock = new Animation<Texture>(0.075f,loadFrames(13,"images/combat/Mage/Block/mage_block_"));
        animDead = new Animation<Texture>(0.1f,loadFrames(12,"images/combat/Alchimist/Death/alchimist_death_"));
        animIdle = new Animation<Texture>(0.1f,loadFrames(24,"images/combat/Alchimist/Idle/alchimist_idle_"));

        width = animIdle.getKeyFrame(0,false).getWidth()/1.3f;
        height = animIdle.getKeyFrame(0,false).getHeight()/1.3f;
    }
}
