package fr.ul.roguelike.model.Items;

public abstract class Item {
    private String name;
    private String description;

    public Item(String itemName, String itemDescription){
        this.name = itemName;
        this.description = itemDescription;
    }
}
