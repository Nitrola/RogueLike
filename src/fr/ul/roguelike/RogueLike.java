package fr.ul.roguelike;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.ul.roguelike.views.MainMenu;
import fr.ul.roguelike.views.MapInterface;
import fr.ul.roguelike.views.SplashScreen;

import com.badlogic.gdx.utils.Timer;

public class RogueLike extends Game {
	private SplashScreen splashScreen;
	private MainMenu mainMenu;

	@Override
	public void create () {
        splashScreen = new SplashScreen();
        setScreen(splashScreen);
        Timer timer = new Timer();
        com.badlogic.gdx.utils.Timer.Task task = new com.badlogic.gdx.utils.Timer.Task() {
            @Override
            public void run() {
                changeScreen();
            }
        };
        timer.scheduleTask(task, 3);
	}

    private void changeScreen(){
        splashScreen.dispose();
        mainMenu = new MainMenu(this);
        setScreen(mainMenu);
    }

	@Override
	public void dispose () {
	}
}
