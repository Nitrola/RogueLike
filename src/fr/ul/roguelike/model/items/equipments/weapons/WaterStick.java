package fr.ul.roguelike.model.items.equipments.weapons;

import com.badlogic.gdx.graphics.Texture;
import fr.ul.roguelike.model.items.equipments.Equipement;
import fr.ul.roguelike.model.items.equipments.EquipementStats;

public class WaterStick extends Equipement {

    /**
     * Baton du mage
     */
    public WaterStick() {
        super("Water Stick",56, "Aqua isn't totally useless");
        equipement = new EquipementStats(1,9,0f,0f);
        texture = new Texture("images/equipment/waterStick.png");
        equipementType = EquipementType.WEAPON;

    }
}
