package fr.ul.roguelike.model.Items.Equipment.Armors;

import com.badlogic.gdx.graphics.Texture;
import fr.ul.roguelike.model.Items.Equipment.Equipement;
import fr.ul.roguelike.model.Items.Equipment.EquipementStats;

public class Casque extends Equipement {

    public Casque() {
        super("Casque", "Flemme pour le moment");
        equipement = new EquipementStats(0,0,0.1f,0.1f);
        texture = new Texture("images/equipment/casque.png");
        equipementType = EquipementType.HEAD;
    }
}
