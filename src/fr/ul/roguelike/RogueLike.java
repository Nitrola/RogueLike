package fr.ul.roguelike;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.ul.roguelike.views.MapInterface;
import fr.ul.roguelike.views.SplashScreen;

import com.badlogic.gdx.utils.Timer;

public class RogueLike extends Game {
	SpriteBatch batch;
	SplashScreen splashScreen;
	MapInterface map;

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
        map = new MapInterface(this);
        setScreen(map);
    }

	@Override
	public void dispose () {
		batch.dispose();
	}
}
