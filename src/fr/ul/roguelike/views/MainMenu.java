package fr.ul.roguelike.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import fr.ul.roguelike.RogueLike;
import fr.ul.roguelike.model.Items.ButtonItem;
import fr.ul.roguelike.model.Player;

import static fr.ul.roguelike.RogueLike.screenWidth;
import static fr.ul.roguelike.RogueLike.screenHeight;

public class MainMenu extends ScreenAdapter {

    private SpriteBatch spriteBatch;
    private Stage stage;
    private OrthographicCamera camera;
    private RogueLike rogueLike;

    private boolean clicked;

    /**
     * Représente le menu principal
     * @param rogueLike modèle
     */
    public MainMenu(final RogueLike rogueLike){
        stage = new Stage();
        this.rogueLike = rogueLike;
        Gdx.input.setInputProcessor(stage);
        clicked = false;
        spriteBatch = new SpriteBatch();
        Image fond = new Image(new Texture(Gdx.files.internal("images/menu.png")));
        fond.setWidth(screenWidth);
        fond.setHeight(screenHeight);
        stage.addActor(fond);

        //Bouton pour jouer
        Texture texture = new Texture(Gdx.files.internal("images/jouer.png"));
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(texture));
        int buttonHeight = screenWidth / 9;
        drawable.setMinHeight(buttonHeight);
        int buttonWidth = screenWidth / 5;
        drawable.setMinWidth(buttonWidth);
        Button playButton = new ButtonItem(drawable);
        playButton.setSize(buttonWidth, buttonHeight);
        playButton.setPosition(screenWidth /2f- buttonWidth /2f, screenHeight /2f);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                play();
            }
        });

        //Bouton quitter
        texture = new Texture(Gdx.files.internal("images/quitter.png"));
        Drawable drawable2 = new TextureRegionDrawable(new TextureRegion(texture));
        drawable2.setMinHeight(buttonHeight);
        drawable2.setMinWidth(buttonWidth);
        Button exitButton = new ButtonItem(drawable2);
        exitButton.setSize(buttonWidth, buttonHeight);
        exitButton.setPosition(screenWidth /2f- buttonWidth /2f, screenHeight /2f- screenHeight /4f);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });


        stage.addActor(playButton);
        stage.addActor(exitButton);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);
        camera.update();
    }

    /**
     * Lance le menu de choix du personnage
     */
    private void play(){
        if(!clicked) {
            ChooseMenu chooseMenu = new ChooseMenu(rogueLike, new Player());
            rogueLike.setScreen(chooseMenu);
            clicked = true;
        }
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
}