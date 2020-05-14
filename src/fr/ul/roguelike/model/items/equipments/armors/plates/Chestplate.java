package fr.ul.roguelike.model.items.equipments.armors.plates;

import com.badlogic.gdx.graphics.Texture;
import fr.ul.roguelike.model.items.equipments.Equipement;
import fr.ul.roguelike.model.items.equipments.EquipementStats;

public class Chestplate extends Equipement {

    /**
     * Plastron
     */
    public Chestplate() {
        super("Chestplate", 40,"???");
        equipement = new EquipementStats(0,0,15f,1f);
        texture = new Texture("images/equipment/chestplate.png");
        equipementType = EquipementType.PLATE;
    }
}