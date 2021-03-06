package fr.ul.roguelike.model.items.equipments.armors.plates;

import com.badlogic.gdx.graphics.Texture;
import fr.ul.roguelike.model.items.equipments.Equipement;
import fr.ul.roguelike.model.items.equipments.EquipementStats;

public class HighMageMantel extends Equipement {

    /**
     * Armure du mage
     */
    public HighMageMantel() {
        super("High Mage Mantel", 87,"A mantel worn by the best mage in the world");
        equipement = new EquipementStats(0,0,1f,9f);
        texture = new Texture("images/equipment/HighMageMantel.png");
        equipementType = EquipementType.PLATE;
    }
}
