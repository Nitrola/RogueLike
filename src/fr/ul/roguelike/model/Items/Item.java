package fr.ul.roguelike.model.Items;

import com.badlogic.gdx.graphics.Texture;

public abstract class Item {
    private String name;
    private String description;
    private int price;
    private Texture texture;

    /**
     * Creer un Item
     * @param name Nom de l'Item
     * @param price Prix de l'Item
     * @param d Description de l'Item
     */
    public Item(String name, int price, String d){
        this.name = name;
        this.price = price;
        description = d;
    }

    /**
     * Creer un Item
     * @param name Nom de l'Item
     * @param descr Description de l'Item
     */
    public Item(String name, String descr){
        this.name = name;
        this.description = descr;
    }

    //////////////////////////////////
    ///////Getters and Setters///////
    /////////////////////////////////

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}

