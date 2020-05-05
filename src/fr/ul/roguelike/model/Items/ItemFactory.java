package fr.ul.roguelike.model.Items;

import fr.ul.roguelike.model.Items.Equipment.Archer.BaseBow;
import fr.ul.roguelike.model.Items.Equipment.Warrior.DemonSword;

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
