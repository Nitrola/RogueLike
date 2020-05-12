package fr.ul.roguelike.model.Items.Equipment.Weapons;

import com.badlogic.gdx.graphics.Texture;
import fr.ul.roguelike.model.Items.Equipment.Equipement;
import fr.ul.roguelike.model.Items.Equipment.EquipementStats;

public class WaterStick extends Equipement {

    /**
     * Baton du mage
     */
    public WaterStick() {
        super("Water Stick", "Aqua isn't totally useless");
        equipement = new EquipementStats(1,5,0f,0f);
        texture = new Texture("images/equipment/waterStick.png");
        equipementType = EquipementType.WEAPON;

    }
}
