package fr.ul.roguelike.model.items.equipments.weapons;

import com.badlogic.gdx.graphics.Texture;
import fr.ul.roguelike.model.items.equipments.Equipement;
import fr.ul.roguelike.model.items.equipments.EquipementStats;

public class BaseBow extends Equipement {

    /**
     * Arc de l'archer
     */
    public BaseBow() {
        super("Base bow", 24,"Every hunter have one from Daddy at 10 !");
        equipement = new EquipementStats(3, 0, 0, 0);
        texture = new Texture("images/equipment/baseBow.png");
        equipementType = EquipementType.WEAPON;
    }
}
