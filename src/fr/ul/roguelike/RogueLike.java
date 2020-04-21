package fr.ul.roguelike;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.ul.roguelike.views.MapInterface;

public class RogueLike extends Game {
	SpriteBatch batch;
	MapInterface map;

	@Override
	public void create () {
		batch = new SpriteBatch();
		map = new MapInterface(this);
		setScreen(map);
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
