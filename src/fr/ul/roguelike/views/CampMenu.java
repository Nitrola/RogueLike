package fr.ul.roguelike.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
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
import fr.ul.roguelike.RogueLike;
import fr.ul.roguelike.model.GifDecoder;
import fr.ul.roguelike.model.Items.ButtonItem;
import fr.ul.roguelike.model.Player;
import static fr.ul.roguelike.RogueLike.screenWidth;
import static fr.ul.roguelike.RogueLike.screenHeight;


public class CampMenu extends ScreenAdapter {

    private MapInterface mapInterface;

    private Stage stage;
    private Button upgradeButton;

    private Player player;
    private Texture lifeBar, heart, mana;

    private SpriteBatch sb;
    private ShapeRenderer sr;
    private Animation<TextureRegion> animation;
    private float elapsed;
    private Sprite sprite;

    /**
     * Représente le Menu de feu de camp
     * @param mapInterface la map à afficher après avoir passé le stage
     */
    CampMenu(final MapInterface mapInterface){
        this.mapInterface = mapInterface;
        stage = new Stage();
        sr = new ShapeRenderer();
        sb = new SpriteBatch();
        Gdx.input.setInputProcessor(stage);
        Image fond = new Image(new Texture(Gdx.files.internal("images/campfire.png")));
        fond.setWidth(RogueLike.screenWidth);
        fond.setHeight(RogueLike.screenHeight);
        stage.addActor(fond);
        animation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("images/animations/feu.gif").read());
        sprite = new Sprite();
        int buttonWidth = 100;
        sprite.setPosition(RogueLike.screenWidth/2f - buttonWidth /2f ,RogueLike.screenHeight/2f);
        int buttonHeight = 100;
        sprite.setSize(buttonWidth, buttonHeight);

        player = mapInterface.getPlayer();
        lifeBar = new Texture("images/combat/lifeBar.png");
        heart = new Texture("images/combat/heart.png");
        mana = new Texture("images/combat/manapotion.png");

        //Bouton upgrade
        Texture texture = new Texture(Gdx.files.internal("images/badlogic.jpg"));
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(texture));
        upgradeButton = new ButtonItem(drawable,"??");
        upgradeButton.setSize(buttonWidth, buttonHeight);
        upgradeButton.setPosition(RogueLike.screenWidth/2f - buttonWidth /2f,RogueLike.screenHeight/2f -150);
        upgradeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                upgradeButton.addAction(Actions.removeActor());
                mapInterface.getInventoryMenu().toUpgrade();
                mapInterface.getRogueLike().setScreen(mapInterface.getInventoryMenu());
            }
        });
        stage.addActor(upgradeButton);
    }

    /**
     * Dessine la barre de vie et de mana
     */
    private void drawLifeBars(){

        sb.begin();

        sb.draw(lifeBar,
                0,screenHeight - lifeBar.getHeight()*2,
                lifeBar.getWidth()*2,lifeBar.getHeight()*2);
        sb.draw(lifeBar,
                0,screenHeight - lifeBar.getHeight()*4,
                lifeBar.getWidth()*2,lifeBar.getHeight()*2);
        sb.end();

        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(.85f,0f,0f,0.1f);

        //player life bar
        float playerHpLeftRate =1.0f * player.getHealthLeft() / player.getPlayerCharacter().getHp();
        sr.rect(
                4.0f,screenHeight + 3f- lifeBar.getHeight()*2,
                (playerHpLeftRate *lifeBar.getWidth()*2)-7.f,(lifeBar.getHeight()*2)-6f );


        //player Mana
        float playerManaLeftRate =1.0f * player.getManaLeft() / player.getPlayerCharacter().getMana();
        sr.setColor(0f,0.4f,1f,1f);
        sr.rect(
                4.0f,screenHeight + 3f- lifeBar.getHeight()*4,
                (playerManaLeftRate *lifeBar.getWidth()*2)-7.f,(lifeBar.getHeight()*2)-6f );


        sr.end();

        sb.begin();
        sb.draw(heart,
                lifeBar.getWidth()*2 - heart.getWidth()*2, screenHeight - heart.getHeight()*4,
                heart.getHeight()*4,heart.getHeight()*4
        );
        sb.draw(mana,
                lifeBar.getWidth()*2 - mana.getWidth(), screenHeight - mana.getHeight()*4,
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

        if(sprite.getBoundingRectangle().contains(Gdx.input.getX(), screenHeight - Gdx.input.getY())) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) || Gdx.input.isTouched()) {
                player.heal(20);
                mapInterface.setScreen();
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        upgradeButton.addAction(Actions.removeActor());
        sb.dispose();
        sr.dispose();
    }
}
