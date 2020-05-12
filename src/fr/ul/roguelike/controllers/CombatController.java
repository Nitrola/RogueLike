package fr.ul.roguelike.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import fr.ul.roguelike.model.Heros.Hero;
import fr.ul.roguelike.model.Monster.Boss.Boss;
import fr.ul.roguelike.model.Monster.Boss.MiniBoss.MiniBoss;
import fr.ul.roguelike.model.Monster.Mob.Golem;
import fr.ul.roguelike.model.Monster.Monster;
import fr.ul.roguelike.model.Player;

import static fr.ul.roguelike.RogueLike.screenHeight;

import java.util.ArrayList;

public class CombatController {

    private Rectangle attack,block,healthPotion,manaPotion;
    private Player player;
    private ArrayList<Monster> monsters;

    public CombatController(Rectangle attack, Rectangle block, Rectangle healthPotion, Rectangle manaPotion, Player player, ArrayList<Monster> monsters) {
        this.attack = attack;
        this.block = block;
        this.healthPotion = healthPotion;
        this.manaPotion = manaPotion;
        this.player = player;
        this.monsters = monsters;
    }

    public void checkInput() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) ) {
            int posX = Gdx.input.getX();
            int posY = screenHeight - Gdx.input.getY();

            //Le joueur attaque
            if (attack.contains(posX, posY) && !monsters.isEmpty()) {
                if(!monsters.get(0).isBlocking() && monsters.get(0).getCurrentHp() > 0) {
                    monsters.get(0).receiveHit(player.getPlayerCharacter().getPhysicalDmg());
                }
                player.useMana(20);
                if (monsters.get(0).getCurrentHp() <= 0 && monsters.get(0).getCombatState() != Hero.CombatState.DEAD) {
                    System.out.println(monsters.get(0).getCombatState());
                    monsters.get(0).setCombatState(Hero.CombatState.DEAD);
                }
            }

            //Le joueur bloque
            if (block.contains(posX, posY)) {
                player.useMana(10);
                player.parry();
            }

            //Le joueur prend une potion de soin
            if (healthPotion.contains(posX, posY)) {
                if(player.getNbPotionHealth() > 0) {
                    player.heal(10);
                    player.usePotionHealth();
                }
            }

            //Le joueur prend une potion de mana
            if (manaPotion.contains(posX, posY)) {
                if(player.getNbPotionMana() > 0) {
                    player.regenMana(10f);
                    player.usePotionMana();
                }
            }
        }
    }
}
