package fr.ul.roguelike.model.items.equipment.weapons;

import com.badlogic.gdx.graphics.Texture;
import fr.ul.roguelike.model.items.equipment.Equipement;
import fr.ul.roguelike.model.items.equipment.EquipementStats;

public class WaterStick extends Equipement {

    /**
     * Baton du mage
     */
    public WaterStick() {
        super("Water Stick",10, "Aqua isn't totally useless");
        equipement = new EquipementStats(1,5,0f,0f);
        texture = new Texture("images/equipment/waterStick.png");
        equipementType = EquipementType.WEAPON;

    }
}
