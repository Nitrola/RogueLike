package fr.ul.roguelike.model.Items;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class ButtonItem extends ImageButton {
    private boolean isClicked;
    private String nomItem;

    //String deviendra un Item quand Collegue Alexandre aura fini la classe Item
    public ButtonItem(Drawable imageUp, String i) {
        super(imageUp);
        this.isClicked=false;
        this.nomItem = i;
    }

    public ButtonItem(ImageButtonStyle imageButtonStyle, String i) {
        super(imageButtonStyle);
        this.isClicked=false;
        this.nomItem = i;
    }

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
