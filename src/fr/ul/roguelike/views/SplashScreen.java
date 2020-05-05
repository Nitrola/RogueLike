package fr.ul.roguelike.views;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static fr.ul.roguelike.RogueLike.screenWidth;
import static fr.ul.roguelike.RogueLike.screenHeight;

public class SplashScreen extends ScreenAdapter {
    private SpriteBatch intro;
    private OrthographicCamera camera;
    private Texture texture;

    /**
     * Ecran d'acceuil de notre jeu
     */
    public SplashScreen(){
        intro = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);
        camera.update();
        texture = new Texture("images/intro.png");
    }

    /**
     * Affiche le SpriteBatch de l'intro
     */
    @Override
    public void render (float delta) {
        intro.setProjectionMatrix(camera.combined);
        intro.begin();
        intro.draw(texture, 0, 0, screenWidth, screenHeight);
        intro.end();
    }

    /**
     * Supprime le SpriteBatch
     */
    public void dispose(){
        intro.dispose();
    }
}
