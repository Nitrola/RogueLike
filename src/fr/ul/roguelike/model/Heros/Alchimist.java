package fr.ul.roguelike.model.Heros;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import static fr.ul.roguelike.RogueLike.screenWidth;
import static fr.ul.roguelike.RogueLike.screenHeight;

public class Alchimist extends Hero {
    public Alchimist() {
        super(100,100,1f,0.01f,10,1,0.5f,0.5f);
        nb_spell_slots = 2;

        animAttack = new Animation<Texture>(0.075f,loadFrames(36,"images/combat/Alchimist/Attack/alchimist_attack_"));
        animBlock = new Animation<Texture>(0.075f,loadFrames(13,"images/combat/Mage/Block/mage_block_"));
        animDead = new Animation<Texture>(0.1f,loadFrames(12,"images/combat/Alchimist/Death/alchimist_death_"));
        animIdle = new Animation<Texture>(0.1f,loadFrames(24,"images/combat/Alchimist/Idle/alchimist_idle_"));

        width = screenWidth/2f;
        height = screenHeight/1.5f;
    }

    @Override
    public int getHitFrame() {
        return 22;
    }
}
