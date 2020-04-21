package fr.ul.roguelike.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import fr.ul.roguelike.model.Items.ButtonItem;
import fr.ul.roguelike.model.Player;

import java.util.Random;

public class CampMenu extends ScreenAdapter {

    private Stage stage;
    private Texture texture;
    private Button cfButton;

    private Player player;

    private Texture lifeBar, heart, mana;

    private SpriteBatch sb;
    private ShapeRenderer sr;

    private int largeurEcran = Gdx.graphics.getWidth();
    private int hauteurEcran = Gdx.graphics.getHeight();
    //
    private int largeurBouton = 300;
    private int hauteurBouton = 100;

    public CampMenu (){
        stage = new Stage();
        sr = new ShapeRenderer();
        sb = new SpriteBatch();
        Gdx.input.setInputProcessor(stage);
        Image fond = new Image(new Texture(Gdx.files.internal("images/campfire.png")));
        fond.setWidth(largeurEcran);
        fond.setHeight(hauteurEcran);
        stage.addActor(fond);
        texture = new Texture(Gdx.files.internal("images/badlogic.jpg"));

        player = new Player();
        lifeBar = new Texture("combat/lifeBar.png");
        heart = new Texture("combat/heart.png");
        mana = new Texture("combat/manapotion.png");


        Drawable drawable = new TextureRegionDrawable(new TextureRegion(texture));
        drawable.setMinHeight(hauteurBouton);
        drawable.setMinWidth(largeurBouton);
        cfButton = new ButtonItem(drawable,"??");
        cfButton.setSize(largeurBouton,hauteurBouton);
        cfButton.setPosition(largeurEcran/2-largeurBouton/2,hauteurEcran/2);
        cfButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.heal(20);
                cfButton.addAction(Actions.removeActor());
                System.out.println(player.getHealthLeft());
            };
        });
        stage.addActor(cfButton);
    }

    private void drawLifeBars(){

        sb.begin();

        sb.draw(lifeBar,
                0,Gdx.graphics.getHeight() - lifeBar.getHeight()*2,
                lifeBar.getWidth()*2,lifeBar.getHeight()*2);
        sb.draw(lifeBar,
                0,Gdx.graphics.getHeight() - lifeBar.getHeight()*4,
                lifeBar.getWidth()*2,lifeBar.getHeight()*2);
        sb.end();

        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(.85f,0f,0f,0.1f);

        //player life bar
        float playerHpLeftRate =1.0f * player.getHealthLeft() / player.getPlayerCharacter().getHp();
        sr.rect(
                4.0f,Gdx.graphics.getHeight() + 3f- lifeBar.getHeight()*2,
                (playerHpLeftRate *lifeBar.getWidth()*2)-7.f,(lifeBar.getHeight()*2)-6f );


        //player Mana
        float playerManaLeftRate =1.0f * player.getManaLeft() / player.getPlayerCharacter().getMana();
        sr.setColor(0f,0.4f,1f,1f);
        sr.rect(
                4.0f,Gdx.graphics.getHeight() + 3f- lifeBar.getHeight()*4,
                (playerManaLeftRate *lifeBar.getWidth()*2)-7.f,(lifeBar.getHeight()*2)-6f );


        sr.end();

        sb.begin();
        sb.draw(heart,
                lifeBar.getWidth()*2 - heart.getWidth()*2, Gdx.graphics.getHeight() - heart.getHeight()*4,
                heart.getHeight()*4,heart.getHeight()*4
        );
        sb.draw(mana,
                lifeBar.getWidth()*2 - mana.getWidth(), Gdx.graphics.getHeight() - mana.getHeight()*4,
                mana.getHeight()*1.9f,mana.getHeight()*1.9f);
        sb.end();
    }




    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        stage.draw();
        stage.act();
        drawLifeBars();
    }

    @Override
    public void dispose() {
        super.dispose();
        cfButton.addAction(Actions.removeActor());
        sb.dispose();
        sr.dispose();
    }
}
