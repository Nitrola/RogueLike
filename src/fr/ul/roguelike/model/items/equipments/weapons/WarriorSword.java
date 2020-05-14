package fr.ul.roguelike.model.items.equipment.weapons;

import com.badlogic.gdx.graphics.Texture;
import fr.ul.roguelike.model.items.equipment.Equipement;
import fr.ul.roguelike.model.items.equipment.EquipementStats;

public class WarriorSword extends Equipement {


    /**
     *
     */
    public WarriorSword() {
        super("Warrior sword",10, "would definitly slay a base slime");
        equipement = new EquipementStats(10,3,0.1f,0.1f);
        texture = new Texture("images/equipment/demonSword.png");
        equipementType = EquipementType.WEAPON;
    }
}
