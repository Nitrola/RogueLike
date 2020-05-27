package fr.ul.roguelike.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import fr.ul.roguelike.model.heros.Hero;
import fr.ul.roguelike.model.heros.Warrior;
import fr.ul.roguelike.model.monsters.Monster;
import fr.ul.roguelike.model.Player;
import fr.ul.roguelike.views.MainMenu;

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
            if(attack.contains(posX, posY) && !monsters.isEmpty() && player.getPlayerCharacter() instanceof Warrior){
                ((Warrior) player.getPlayerCharacter()).changeAttack();
            }
            if (attack.contains(posX, posY) && !monsters.isEmpty() && player.getPlayerCharacter().getCombatState() != Hero.CombatState.ATTACKING) {
                if(monsters.get(0).getCurrentHp() > 0) {
                    if (!monsters.get(0).isBlocking()) {
                        player.getPlayerCharacter().setHasAttack(false);
                    }else{
                        float res = monsters.get(0).getTimeSincePreviousAttack() - monsters.get(0).getAttackSpeed()/2;
                        if(res <= 0){
                            monsters.get(0).updateLastAttackTimer(0);
                        }else{
                            monsters.get(0).updateLastAttackTimer(res);
                        }
                    }
                }
                player.getPlayerCharacter().setCombatState(Hero.CombatState.ATTACKING);
                player.getPlayerCharacter().setAnimeTime(0.0f);
            }

            //Le joueur bloque
            if (block.contains(posX, posY) && player.getPlayerCharacter().getCombatState() != Hero.CombatState.BLOCKING) {
                if(player.getManaLeft() > player.getPlayerCharacter().getBlockCost()) {
                    player.useMana(MainMenu.player.getPlayerCharacter().getBlockCost());
                    player.parry();
                }
            }

            //Le joueur prend une potion de soin
            if (healthPotion.contains(posX, posY)) {
                if(player.getNbPotionHealth() > 0) {
                    player.heal(50);
                    player.usePotionHealth();
                }
            }

            //Le joueur prend une potion de mana
            if (manaPotion.contains(posX, posY)) {
                if(player.getNbPotionMana() > 0) {
                    player.regenMana(15f);
                    player.usePotionMana();
                }
            }
        }
    }
}
