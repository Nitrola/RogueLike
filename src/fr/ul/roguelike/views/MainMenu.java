package fr.ul.roguelike.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import fr.ul.roguelike.RogueLike;
import fr.ul.roguelike.model.Items.ButtonItem;

public class MainMenu extends ScreenAdapter {

    private MapInterface mapInterface;
    private SpriteBatch spriteBatch;
    private Stage stage;
    private OrthographicCamera camera;
    private RogueLike rogueLike;

    private Button playButton,exitButton;
    private Texture texture;

    private int largeurEcran = Gdx.graphics.getWidth();
    private int hauteurEcran = Gdx.graphics.getHeight();
    private int largeurBouton = Gdx.graphics.getWidth()/5;
    private int hauteurBouton = Gdx.graphics.getWidth()/9;

    public MainMenu(final RogueLike rogueLike){
        stage = new Stage();
        this.rogueLike = rogueLike;
        mapInterface = new MapInterface(rogueLike);
        Gdx.input.setInputProcessor(stage);
        spriteBatch = new SpriteBatch();
        Image fond = new Image(new Texture(Gdx.files.internal("images/menu.png")));
        fond.setWidth(largeurEcran);
        fond.setHeight(hauteurEcran);
        stage.addActor(fond);

        texture = new Texture(Gdx.files.internal("images/woodTexture.png"));
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(texture));
        drawable.setMinHeight(hauteurBouton);
        drawable.setMinWidth(largeurBouton);
        playButton = new ButtonItem(drawable);
        playButton.setSize(largeurBouton,hauteurBouton);
        playButton.setPosition(largeurEcran/2-largeurBouton/2,hauteurEcran/2);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                play();
            };
        });

        texture = new Texture(Gdx.files.internal("images/woodTexture.png"));
        Drawable drawable2 = new TextureRegionDrawable(new TextureRegion(texture));
        drawable2.setMinHeight(hauteurBouton);
        drawable2.setMinWidth(largeurBouton);
        exitButton = new ButtonItem(drawable2);
        exitButton.setSize(largeurBouton,hauteurBouton);
        exitButton.setPosition(largeurEcran/2-largeurBouton/2,hauteurEcran/2-hauteurEcran/4);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            };
        });


        stage.addActor(playButton);
        stage.addActor(exitButton);

        camera = new OrthographicCamera();
        camera.setToOrtho(false,largeurEcran,hauteurEcran);
        camera.update();
    }

    public void play (){
        mapInterface = new MapInterface(rogueLike);
        rogueLike.setScreen(mapInterface);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        spriteBatch.setProjectionMatrix(camera.combined);
        stage.draw();
        stage.act();
        camera.update();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    public MapInterface getMap() {
        return mapInterface;
    }
}