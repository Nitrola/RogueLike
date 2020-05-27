package fr.ul.roguelike.model.items.equipments.armors.plates;

import com.badlogic.gdx.graphics.Texture;
import fr.ul.roguelike.model.items.equipments.Equipement;
import fr.ul.roguelike.model.items.equipments.EquipementStats;

public class Mantle extends Equipement {

    public Mantle() {
        super("Mantle", 37,"You're a wizard with that");
        equipement = new EquipementStats(0,0,1f,4f);
        texture = new Texture("images/equipment/mantle.png");
        equipementType = EquipementType.PLATE;
    }
}
