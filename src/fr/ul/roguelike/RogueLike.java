package fr.ul.roguelike;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import fr.ul.roguelike.views.MainMenu;
import fr.ul.roguelike.views.SplashScreen;

import com.badlogic.gdx.utils.Timer;



public class RogueLike extends Game {
	private SplashScreen splashScreen;
    public static int screenWidth;
    public static int screenHeight;

	@Override
	public void create () {
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
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

    public void changeScreen(){
        MainMenu mainMenu = new MainMenu(this);
        setScreen(mainMenu);
    }

	@Override
	public void dispose () {
	}
}
