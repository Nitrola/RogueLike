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

    public PlayerGear(Equipement helmet, Equipement armor, Equipement leftHand, Equipement rightHand) {
        this.helmet = helmet;
        this.armor = armor;
        this.leftHand = leftHand;
        this.rightHand = rightHand;
    }

    public void changeGear(Equipement e){
        switch (e.getEquipementType()){
            case "BOW":
                break;
            case "SWORD":
                if(leftHand == null ){
                    leftHand = e;
                }
                else if(leftHand != null && rightHand == null){
                    rightHand = e;
                }
                break;
            case "HELMET":
                helmet = e;
                break;
            case "ARMOR":
                armor = e;
                break;
            case "STAFF":
                rightHand = null;
                leftHand = e;
                break;
            default:
                break;
        }

    }

    public void drawEquipment(Equipement e,SpriteBatch sb,int posX,int posY,float scale) {
        sb.draw(e.getTexture(),posX,posY,helmet.getTexture().getWidth() * scale,helmet.getTexture().getHeight() * scale);
    }

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
