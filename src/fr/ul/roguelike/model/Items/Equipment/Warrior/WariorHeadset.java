package fr.ul.roguelike.model.Items.Equipment.Warrior;

import com.badlogic.gdx.graphics.Texture;
import fr.ul.roguelike.model.Items.Equipment.Equipement;
import fr.ul.roguelike.model.Items.Equipment.EquipementStats;

public class WariorHeadset extends Equipement {


    public WariorHeadset() {
        super("Warior Headset", "The cheapest helmet in the capital !");
        equipement = new EquipementStats(10,3,0.1f,0.1f);
        texture = new Texture("equipment/demonSword.png");
    }
}


