package fr.ul.roguelike.model.items.equipment.armors.plates;

import com.badlogic.gdx.graphics.Texture;
import fr.ul.roguelike.model.items.equipment.Equipement;
import fr.ul.roguelike.model.items.equipment.EquipementStats;

public class Mantle extends Equipement {

    public Mantle() {
        super("Mantle", 10,"You're a wizard with that");
        equipement = new EquipementStats(0,0,1f,10f);
        texture = new Texture("images/equipment/mantle.png");
        equipementType = EquipementType.PLATE;
    }
}
