package fr.ul.roguelike.model.Heros;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.ul.roguelike.model.Hero;

public class Warrior extends Hero {


    public Warrior() {
        super(100,100,5f,0.01f,10,1,5,5,1);
        nb_spell_slots = 2;
        Texture warriorSheet = new Texture("combat/WarriorWalk.png");
        TextureRegion[][] tmp = TextureRegion.split(warriorSheet,warriorSheet.getWidth() / 3 , warriorSheet.getHeight()/1);
        TextureRegion[] walkFrames = new TextureRegion[3*1];
        int index = 0;
        for(int i = 0; i < 3; i++){
            tmp[0][i].flip(true,false);
            walkFrames[index++] = tmp[0][i];
        }

        animAttack = new Animation<Texture>(0.1f,loadFrames(5,"combat/Warrior/Attack/HeroKnight_Attack1_"));
        animBlock = new Animation<Texture>(0.1f,loadFrames(4,"combat/Warrior/Block/HeroKnight_Block_"));
        animDead = new Animation<Texture>(0.1f,loadFrames(9,"combat/Warrior/Death/HeroKnight_Death_"));
        animIdle = new Animation<Texture>(0.1f,loadFrames(7,"combat/Warrior/Idle/HeroKnight_Idle_"));
    }

}
