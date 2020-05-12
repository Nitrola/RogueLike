package fr.ul.roguelike.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.ul.roguelike.model.Items.Equipment.Equipement;

public class PlayerGear {
    private Equipement helmet;
    private Equipement armor;
    private Equipement leftHand;
    private Equipement rightHand;


    public PlayerGear() {
        helmet = null;
        armor = null;
        leftHand = null;
        rightHand = null;
    }

    /**
     * Equipements actuellement portés par le personnage
     */
    public PlayerGear(Equipement helmet, Equipement armor, Equipement leftHand, Equipement rightHand) {
        this.helmet = helmet;
        this.armor = armor;
        this.leftHand = leftHand;
        this.rightHand = rightHand;
    }

    /**
     * Affiche un equipement
     */
    public void drawEquipment(Equipement e,SpriteBatch sb,int posX,int posY,float scale) {
        sb.draw(e.getTexture(),posX,posY,helmet.getTexture().getWidth() * scale,helmet.getTexture().getHeight() * scale);
    }

    //////////////////////////////////
    ///////Getters and Setters///////
    /////////////////////////////////

    public Equipement getHelmet() {
        return helmet;
    }

    public Equipement getArmor() {
        return armor;
    }

    public Equipement getLeftHand() {
        return leftHand;
    }

    public Equipement getRightHand() {
        return rightHand;
    }
}
