package fr.ul.roguelike.model.Items.Equipment.Mage;

import com.badlogic.gdx.graphics.Texture;
import fr.ul.roguelike.model.Items.Equipment.Equipement;
import fr.ul.roguelike.model.Items.Equipment.EquipementStats;

public class HighMageMantel extends Equipement {

    public HighMageMantel() {
        super("High Mage Mantel", "A mantel worn by the best mage in the world");
        equipement = new EquipementStats(0,0,0.1f,0.1f);
        texture = new Texture("images/equipment/HighMageMantel.png");
        equipementType = EquipementType.ARMOR;

    }

}
