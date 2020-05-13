package fr.ul.roguelike.model.Items.Equipment.Armors;

import com.badlogic.gdx.graphics.Texture;
import fr.ul.roguelike.model.Items.Equipment.Equipement;
import fr.ul.roguelike.model.Items.Equipment.EquipementStats;

public class HighMageMantel extends Equipement {

    /**
     * Armure du mage
     */
    public HighMageMantel() {
        super("High Mage Mantel", 10,"A mantel worn by the best mage in the world");
        equipement = new EquipementStats(0,0,5f,15f);
        texture = new Texture("images/equipment/HighMageMantel.png");
        equipementType = EquipementType.PLATE;
    }
}
