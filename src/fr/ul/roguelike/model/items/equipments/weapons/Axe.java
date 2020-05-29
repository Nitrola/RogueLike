package fr.ul.roguelike.model.items.equipments.weapons;

import com.badlogic.gdx.graphics.Texture;
import fr.ul.roguelike.model.items.equipments.Equipement;
import fr.ul.roguelike.model.items.equipments.EquipementStats;

public class Axe extends Equipement {
    /**
     * Hache de combat
     */
    public Axe() {
        super("Axe", 39,"Not for kids");
        equipement = new EquipementStats(5, 0, 0, 0);
        texture = new Texture("images/equipment/axe.png");
        equipementType = EquipementType.WEAPON;
    }
}
