package fr.ul.roguelike.model.items.equipments.weapons;

import com.badlogic.gdx.graphics.Texture;
import fr.ul.roguelike.model.items.equipments.Equipement;
import fr.ul.roguelike.model.items.equipments.EquipementStats;

public class Book extends Equipement {
    /**
     * Arc special
     */
    public Book() {
        super("book", 30,"Give +1 physical damage and +6 magical damage");
        equipement = new EquipementStats(1, 6, 0, 0);
        texture = new Texture("images/equipment/book.png");
        equipementType = EquipementType.WEAPON;
    }
}