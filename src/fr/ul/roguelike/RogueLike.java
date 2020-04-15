package fr.ul.roguelike;

import com.badlogic.gdx.Game;
import fr.ul.roguelike.views.ShopMenu;


public class RogueLike extends Game {


	@Override
	public void create () {
		ShopMenu shopMenu = new ShopMenu();
		setScreen(shopMenu);
	}

	@Override
	public void dispose () {

	}
}
