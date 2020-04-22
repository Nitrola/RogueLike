package fr.ul.roguelike.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
        camera.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
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
        intro.draw(texture, 0, 0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        intro.end();
    }

    /**
     * Supprime le SpriteBatch
     */
    public void dispose(){
        intro.dispose();
    }
}
