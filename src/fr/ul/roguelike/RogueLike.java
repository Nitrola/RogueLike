package fr.ul.roguelike;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.ul.roguelike.model.Player;
import fr.ul.roguelike.views.CombatMenu;

public class RogueLike extends Game {
	Player p;
	CombatMenu cm;
	
	@Override
	public void create () {
		p = new Player();
		cm = new CombatMenu(p);
		setScreen(cm);
	}
	
	@Override
	public void dispose () {
	}
}
