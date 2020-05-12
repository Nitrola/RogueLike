package fr.ul.roguelike.model.Items;

import fr.ul.roguelike.model.Items.Equipment.Weapons.BaseBow;
import fr.ul.roguelike.model.Items.Equipment.Weapons.DemonSword;

public class ItemFactory {

    /**
     * Creer un Item
     */
    public static Item create(String itemName){
        switch (itemName){
            case "BaseBow":
                return new BaseBow();
            case"DemonSword":
                return new DemonSword();

        }
        return null;
    }
}
