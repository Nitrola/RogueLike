package fr.ul.roguelike.model.items;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import fr.ul.roguelike.model.items.equipment.Equipement;
import fr.ul.roguelike.model.items.runes.Rune;

public class ButtonItem extends ImageButton {
    private boolean isClicked;
    private String nomItem;
    private Equipement equipement;

    /**
     * Creer un bouton avec un Item comme image
     * @param imageUp Drawable de l'image que l'on veut avoir
     * @param i Nom de l'Item
     */
    //String deviendra un Item quand Collegue Alexandre aura fini la classe Item
    public ButtonItem(Drawable imageUp, String i) {
        super(imageUp);
        this.isClicked=false;
        this.nomItem = i;
    }


    /**
     * Creer un bouton avec une image voulu
     */
    public ButtonItem(Drawable imageUp, Equipement e, float width, float height) {
        super(imageUp);
        imageUp.setMinWidth(width);
        imageUp.setMinHeight(height);
        equipement = e;
        this.isClicked=false;
    }

    /**
     * Creer un bouton avec une image voulu
     */
    public ButtonItem(Drawable imageUp, Rune ir, float width, float height) {
        super(imageUp);
        imageUp.setMinWidth(width);
        imageUp.setMinHeight(height);
        this.isClicked=false;
    }

    public ButtonItem(Drawable imageUp) {
        super(imageUp);
        this.isClicked=false;
    }


    //////////////////////////////////
    ///////Getters and Setters///////
    /////////////////////////////////

    public String getNomItem() {
        return nomItem;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public Equipement getEquipement() {
        return equipement;
    }
}
