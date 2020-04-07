package fr.ul.roguelike.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class GameWorld {
    World world;

    public GameWorld(){
        world = new World(new Vector2(0, 0), true);;
    }

    public World getWorld() {
        return world;
    }
}
