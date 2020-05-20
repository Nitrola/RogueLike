package fr.ul.roguelike.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.ul.roguelike.RogueLike;
import fr.ul.roguelike.model.heros.Alchimist;
import fr.ul.roguelike.model.heros.Hero;
import fr.ul.roguelike.model.heros.Mage;
import fr.ul.roguelike.model.heros.Warrior;
import fr.ul.roguelike.model.Player;

import static fr.ul.roguelike.RogueLike.screenWidth;
import static fr.ul.roguelike.RogueLike.screenHeight;

public class ChooseMenu extends ScreenAdapter implements InputProcessor {
    private RogueLike rogueLike;
    private Player player;

    private SpriteBatch sb;
    private Texture background;

    private Mage mage;
    private Warrior warrior;
    private Alchimist alchimist;

    private OrthographicCamera camera;

    /**
     * Représente le menu de choix de personnage
     * @param r le modèle
     * @param p le joueur
     */
    ChooseMenu(RogueLike r, Player p){
        Gdx.input.setInputProcessor(this);
        rogueLike = r;
        player = p;
        sb = new SpriteBatch();
        background = new Texture(Gdx.files.internal("images/background_choose.png"));

        mage = new Mage();
        warrior = new Warrior();
        alchimist = new Alchimist();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,screenWidth, screenHeight);
        camera.update();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        sb.setProjectionMatrix(camera.combined);

        //Animations
        sb.begin();
        sb.draw(background, 0 ,0, screenWidth, screenHeight);
        warrior.draw(sb, screenWidth/20f-warrior.getWidth()/7.5f , screenHeight/5.7f);
        mage.draw(sb, screenWidth/3f-mage.getWidth()/7f , screenHeight/8f);
        alchimist.draw(sb, screenWidth/3f*2f-alchimist.getWidth()/4f, screenHeight/8.2f);
        sb.end();

        camera.update();
    }


    private void play(Hero h){
        player.setPlayerCharacter(h);

        MapInterface mapInterface = new MapInterface(rogueLike, player);
        rogueLike.setScreen(mapInterface);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return  false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(screenX < screenWidth/3) {
            play(new Warrior());
        }

        if(screenX > screenWidth/3 &&  screenX < screenWidth/3*2){
            play(new Mage());
        }

        if(screenX > screenWidth/3*2) {
            play(new Alchimist());
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
