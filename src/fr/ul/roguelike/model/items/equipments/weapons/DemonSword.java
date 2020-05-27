package fr.ul.roguelike.model.items.equipments.weapons;

import com.badlogic.gdx.graphics.Texture;
import fr.ul.roguelike.model.items.equipments.Equipement;
import fr.ul.roguelike.model.items.equipments.EquipementStats;

public class DemonSword extends Equipement {

    /**
     * Epee du Warrior
     */
    public DemonSword() {
        super("Demon Sword",666, "A sword welded by powerfull demons bathed in blood");
        equipement = new EquipementStats(666,666,666,666);
        texture = new Texture("images/equipment/demonSword.png");
        equipementType = EquipementType.WEAPON;

    }
}
