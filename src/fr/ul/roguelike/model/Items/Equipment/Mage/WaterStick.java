package fr.ul.roguelike.model.Items.Equipment.Mage;

import com.badlogic.gdx.graphics.Texture;
import fr.ul.roguelike.model.Items.Equipment.Equipement;
import fr.ul.roguelike.model.Items.Equipment.EquipementStats;

public class WaterStick extends Equipement {

    public WaterStick(String itemName, String itemDescription) {
        super("Water Stick", "Aqua isn't totally useless");
        equipement = new EquipementStats(1,5,0f,0f);
        texture = new Texture("equipment/waterStick.png");
        equipementType = EquipementType.STAFF;

    }
}
