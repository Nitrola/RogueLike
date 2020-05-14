package fr.ul.roguelike.model.items.equipments.weapons;

import com.badlogic.gdx.graphics.Texture;
import fr.ul.roguelike.model.items.equipments.Equipement;
import fr.ul.roguelike.model.items.equipments.EquipementStats;

public class BowJakshir extends Equipement {
    /**
     * Arc special
     */
    public BowJakshir() {
        super("Bow Jakshir", 75,"a bow abracadabrantesque");
        equipement = new EquipementStats(5, 0, 0, 0);
        texture = new Texture("images/equipment/bowJakshir.png");
        equipementType = EquipementType.WEAPON;
    }
}
