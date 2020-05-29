package fr.ul.roguelike.model.items.equipments.weapons;

import com.badlogic.gdx.graphics.Texture;
import fr.ul.roguelike.model.items.equipments.Equipement;
import fr.ul.roguelike.model.items.equipments.EquipementStats;

public class Shield extends Equipement {
    /**
     * Arc special
     */
    public Shield() {
        super("shield", 75,"Give + 5 physical defense");
        equipement = new EquipementStats(0, 0, 5, 0);
        texture = new Texture("images/equipment/shield.png");
        equipementType = EquipementType.WEAPON;
    }
}
