package fr.ul.roguelike.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import fr.ul.roguelike.model.GifDecoder;
import fr.ul.roguelike.model.Items.ButtonItem;
import fr.ul.roguelike.model.Player;


public class CampMenu extends ScreenAdapter {

    private MapInterface mapInterface;

    private Stage stage;
    private Texture texture;
    private Drawable drawable;
    private Button uwButton;


    private Player player;

    private Texture lifeBar, heart, mana;

    private SpriteBatch sb;
    private ShapeRenderer sr;
    private Animation<TextureRegion> animation;
    private float elapsed;
    private Sprite sprite;

    private int largeurEcran = Gdx.graphics.getWidth();
    private int hauteurEcran = Gdx.graphics.getHeight();
    //
    private int largeurBouton = 100;
    private int hauteurBouton = 100;

    public CampMenu (final MapInterface mapInterface){
        this.mapInterface = mapInterface;
        stage = new Stage();
        sr = new ShapeRenderer();
        sb = new SpriteBatch();
        Gdx.input.setInputProcessor(stage);
        Image fond = new Image(new Texture(Gdx.files.internal("images/campfire.png")));
        fond.setWidth(largeurEcran);
        fond.setHeight(hauteurEcran);
        stage.addActor(fond);
        animation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("images/animations/feu.gif").read());
        sprite = new Sprite();
        sprite.setPosition(largeurEcran/2-largeurBouton/2,hauteurEcran/2);
        sprite.setSize(largeurBouton, hauteurBouton);

        player = mapInterface.getPlayer();
        lifeBar = new Texture("combat/lifeBar.png");
        heart = new Texture("combat/heart.png");
        mana = new Texture("combat/manapotion.png");

        texture = new Texture(Gdx.files.internal("images/badlogic.jpg"));
        drawable = new TextureRegionDrawable(new TextureRegion(texture));
        uwButton = new ButtonItem(drawable,"??");
        uwButton.setSize(largeurBouton,hauteurBouton);
        uwButton.setPosition(largeurEcran/2-largeurBouton/2,hauteurEcran/2-150);
        uwButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                uwButton.addAction(Actions.removeActor());
                mapInterface.getInventoryMenu().toUpgrade();
                mapInterface.getRogueLike().setScreen(mapInterface.getInventoryMenu());
            };
        });
        stage.addActor(uwButton);
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
        elapsed += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        stage.draw();
        stage.act();

        //Animation
        sb.begin();
        sprite.setRegion(animation.getKeyFrame(elapsed));
        sb.draw(sprite, sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        sb.end();

        drawLifeBars();

        if(sprite.getBoundingRectangle().contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) || Gdx.input.isTouched()) {
                player.heal(20);
                mapInterface.setScreen();
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        uwButton.addAction(Actions.removeActor());
        sb.dispose();
        sr.dispose();
    }
}
