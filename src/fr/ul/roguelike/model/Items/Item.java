package fr.ul.roguelike.model.Items;

import com.badlogic.gdx.graphics.Texture;

public abstract class Item {
    private String name;
    private String description;
    private int price;
    private Texture texture;

    public Item(String name, int price, String d){
        this.name = name;
        this.price = price;
        description = d;
    }

    public Item(String name, String descr){
        this.name = name;
        this.description = descr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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

