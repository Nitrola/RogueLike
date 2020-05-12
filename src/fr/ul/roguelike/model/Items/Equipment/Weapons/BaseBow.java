package fr.ul.roguelike.model.Items.Equipment.Weapons;

import com.badlogic.gdx.graphics.Texture;
import fr.ul.roguelike.model.Items.Equipment.Equipement;
import fr.ul.roguelike.model.Items.Equipment.EquipementStats;

public class BaseBow extends Equipement {

    /**
     * Arc de l'archer
     */
    public BaseBow() {
        super("Base bow", "Every hunter have one from Daddy at 10 !");
        equipement = new EquipementStats(2, 0, 0, 0);
        texture = new Texture("images/equipment/baseBow.png");
        equipementType = EquipementType.WEAPON;
    }
}
