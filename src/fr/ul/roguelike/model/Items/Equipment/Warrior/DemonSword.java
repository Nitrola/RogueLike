package fr.ul.roguelike.model.Items.Equipment.Warrior;

import com.badlogic.gdx.graphics.Texture;
import fr.ul.roguelike.model.Items.Equipment.Equipement;
import fr.ul.roguelike.model.Items.Equipment.EquipementStats;

public class DemonSword extends Equipement {

    public DemonSword() {
        super("Demon Sword", "A sword welded by powerfull demons bathed in blood");
        equipement = new EquipementStats(10,3,0.1f,0.1f);
        texture = new Texture("images/equipment/demonSword.png");
        equipementType = EquipementType.SWORD;

    }
}
