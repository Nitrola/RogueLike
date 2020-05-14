package fr.ul.roguelike.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.ul.roguelike.RogueLike;
import fr.ul.roguelike.model.Heros.Alchimist;
import fr.ul.roguelike.model.Heros.Hero;
import fr.ul.roguelike.model.Heros.Mage;
import fr.ul.roguelike.model.Heros.Warrior;
import fr.ul.roguelike.model.Player;

import static fr.ul.roguelike.RogueLike.screenWidth;
import static fr.ul.roguelike.RogueLike.screenHeight;

public class ChooseMenu extends ScreenAdapter {
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
        warrior.draw(sb, screenWidth/20f-warrior.getWidth()/12f , screenHeight/5.7f);
        mage.draw(sb, screenWidth/3f-mage.getWidth()/7f , screenHeight/8f);
        alchimist.draw(sb, screenWidth/3f*2f-alchimist.getWidth()/4f, screenHeight/8.2f);
        sb.end();

        camera.update();
        update();
    }

    private void update(){
        if(Gdx.input.getX() < screenWidth/3) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) || Gdx.input.justTouched()) {
                play(new Warrior());
            }
        }

        if(Gdx.input.getX() > screenWidth/3 &&  Gdx.input.getX() < screenWidth/3*2){
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) || Gdx.input.justTouched()) {
                play(new Mage());
            }
        }

        if(Gdx.input.getX() > screenWidth/3*2) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) || Gdx.input.justTouched()) {
                play(new Alchimist());
            }
        }
    }

    private void play(Hero h){
        player.setPlayerCharacter(h);

        MapInterface mapInterface = new MapInterface(rogueLike, player);
        rogueLike.setScreen(mapInterface);
    }
}
