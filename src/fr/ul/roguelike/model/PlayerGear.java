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
     * Permet de changer de stuff
     */
    public void changeGear(Equipement e){
        switch (e.getEquipementType()){
            case "BOW":
                break;
            case "SWORD":
                if(leftHand == null ){
                    leftHand.equals(e);
                }
                else if(leftHand != null && rightHand == null){
                    rightHand.equals(e);
                }
                break;
            case "HELMET":
                helmet.equals(e);
                break;
            case "ARMOR":
                armor.equals(e);
                break;
            case "STAFF":
                rightHand = null;
                leftHand.equals(e);
                break;
            default:
                break;
        }

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
