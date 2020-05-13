package fr.ul.roguelike.model.Items.Equipment.Armors;

import com.badlogic.gdx.graphics.Texture;
import fr.ul.roguelike.model.Items.Equipment.Equipement;
import fr.ul.roguelike.model.Items.Equipment.EquipementStats;

public class WariorHeadset extends Equipement {


    /**
     *
     */
    public WariorHeadset() {
        super("Warior Headset",10, "The cheapest helmet in the capital !");
        equipement = new EquipementStats(10,3,10f,1f);
        texture = new Texture("images/equipment/demonSword.png");
        equipementType = EquipementType.HEAD;

    }
}


