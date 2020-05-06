package fr.ul.roguelike.model.Items;

public class ItemWeapon extends Item {

    /**
     * Creer un Item de type Arme
     */
    public ItemWeapon(String name, int price, String descr){
        super(name,price,descr);
    }

    @Override
    public boolean isPotion() {
        return false;
    }

}
