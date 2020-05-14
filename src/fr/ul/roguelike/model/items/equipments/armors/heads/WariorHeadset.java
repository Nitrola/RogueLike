package fr.ul.roguelike.model.items.equipments.armors.heads;

import com.badlogic.gdx.graphics.Texture;
import fr.ul.roguelike.model.items.equipments.Equipement;
import fr.ul.roguelike.model.items.equipments.EquipementStats;

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


