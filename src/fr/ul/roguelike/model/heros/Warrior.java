package fr.ul.roguelike.model.heros;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import static fr.ul.roguelike.RogueLike.screenWidth;
import static fr.ul.roguelike.RogueLike.screenHeight;

public class Warrior extends Hero {


    public Warrior() {
        super(200,100,0.40f,0.01f,20,1,10f,5f); //Damage = 10
        nb_spell_slots = 2;
        Texture warriorSheet = new Texture("images/combat/warriorWalk.png");
        TextureRegion[][] tmp = TextureRegion.split(warriorSheet,warriorSheet.getWidth() / 3 , warriorSheet.getHeight());
        TextureRegion[] walkFrames = new TextureRegion[3*1];
        int index = 0;
        for(int i = 0; i < 3; i++){
            tmp[0][i].flip(true,false);
            walkFrames[index++] = tmp[0][i];
        }

        animAttack = new Animation<Texture>(0.1f,loadFrames(5,"images/combat/Warrior/Attack/HeroKnight_Attack1_"));
        animBlock = new Animation<Texture>(0.1f,loadFrames(4,"images/combat/Warrior/Block/HeroKnight_Block_"));
        animDead = new Animation<Texture>(0.1f,loadFrames(9,"images/combat/Warrior/Death/HeroKnight_Death_"));
        animIdle = new Animation<Texture>(0.1f,loadFrames(7,"images/combat/Warrior/Idle/HeroKnight_Idle_"));

        width = screenWidth/2.5f;
        height = screenHeight/2.5f;
    }

    @Override
    public int getHitFrame() {
        return 2;
    }
}
