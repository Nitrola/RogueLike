package fr.ul.roguelike.model.items.equipments.armors.heads;

import com.badlogic.gdx.graphics.Texture;
import fr.ul.roguelike.model.items.equipments.Equipement;
import fr.ul.roguelike.model.items.equipments.EquipementStats;

public class Casque extends Equipement {

    /**
     * Casque de protection
     */
    public Casque() {
        super("Casque", 10,"Flemme pour le moment");
        equipement = new EquipementStats(0,0,10f,1f);
        texture = new Texture("images/equipment/casque.png");
        equipementType = EquipementType.HEAD;
    }
}
