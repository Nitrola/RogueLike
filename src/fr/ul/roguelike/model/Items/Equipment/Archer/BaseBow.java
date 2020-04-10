package fr.ul.roguelike.model.Items.Equipment.Archer;

import com.badlogic.gdx.graphics.Texture;
import fr.ul.roguelike.model.Items.Equipment.Equipement;
import fr.ul.roguelike.model.Items.Equipment.EquipementStats;

public class BaseBow extends Equipement {

    public BaseBow() {
        super("Base bow", "Every hunter have one from Daddy at 10 !");
        equipement = new EquipementStats(2, 0, 0, 0);
        texture = new Texture("equipment/baseBow.png");
    }
}
