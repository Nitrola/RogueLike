package fr.ul.roguelike.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import fr.ul.roguelike.model.Monster.Monster;
import fr.ul.roguelike.model.Player;

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
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            int posX = Gdx.input.getX();
            int posY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (attack.contains(posX, posY)) {
                monsters.get(0).receiveHit(player.getPlayerCharacter().getPhysicalDmg());
                player.useMana(1);
                if (monsters.get(0).getCurrentHp() <= 0) {
                    monsters.remove(0);
                }
            }

            if (block.contains(posX, posY)) {

            }

            if (healthPotion.contains(posX, posY)) {
                player.receiveHit(-10);

            }

            if (manaPotion.contains(posX, posY)) {
                player.regenMana();
            }
        }
    }
}
