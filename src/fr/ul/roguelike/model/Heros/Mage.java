package fr.ul.roguelike.model.Heros;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Mage extends Hero {
    public Mage() {
        super(100,100,5f,0.01f,10,1,5,5,1);
        nb_spell_slots = 2;


        animAttack = new Animation<Texture>(0.075f,loadFrames(20,"images/combat/Mage/Attack/mage_attack_"));
        animBlock = new Animation<Texture>(0.075f,loadFrames(13,"images/combat/Mage/Block/mage_block_"));
        animDead = new Animation<Texture>(0.1f,loadFrames(14,"images/combat/Mage/Death/mage_death_"));
        animIdle = new Animation<Texture>(0.1f,loadFrames(11,"images/combat/Mage/Idle/mage_idle"));

        width = Gdx.graphics.getWidth()/2f;
        height = Gdx.graphics.getHeight()/1.5f;
    }
}
