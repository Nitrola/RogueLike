package fr.ul.roguelike.model.Items;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class ButtonItem extends ImageButton {
    private boolean isClicked;
    private String nomItem;

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
}
