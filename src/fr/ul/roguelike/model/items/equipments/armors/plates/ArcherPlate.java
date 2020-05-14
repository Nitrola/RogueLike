package fr.ul.roguelike.model.items.equipments.armors.plates;

import com.badlogic.gdx.graphics.Texture;
import fr.ul.roguelike.model.items.equipments.Equipement;
import fr.ul.roguelike.model.items.equipments.EquipementStats;

public class ArcherPlate extends Equipement {

    /**
     * Armure pour l'archer
     */
    public ArcherPlate() {
        super("Archer plate", 10,"The commun plate worn on the battlefield. Could easly stop any sword not corretly sharped");
        equipement = new EquipementStats(0,0,10f,1f);
        texture = new Texture("images/equipment/archerPlate.png");
        equipementType = EquipementType.PLATE;
    }

}
