package fr.ul.roguelike.model.Heros;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Mage extends Hero {
    public Mage() {
        super(100,100,5f,0.01f,10,1,5,5,1);
        nb_spell_slots = 2;


        animAttack = new Animation<Texture>(0.075f,loadFrames(19,"images/combat/Mage/Attack/mage_attack_"));
        animBlock = new Animation<Texture>(0.1f,loadFrames(4,"images/combat/Warrior/Block/HeroKnight_Block_"));
        animDead = new Animation<Texture>(0.1f,loadFrames(13,"images/combat/Mage/Death/mage_death_"));
        animIdle = new Animation<Texture>(0.1f,loadFrames(10,"images/combat/Mage/Idle/mage_idle"));

        width = animIdle.getKeyFrame(0,false).getWidth()/1.3f;
        height = animIdle.getKeyFrame(0,false).getHeight()/1.3f;
    }
}
