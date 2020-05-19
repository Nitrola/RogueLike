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
import fr.ul.roguelike.model.items.ButtonItem;
import fr.ul.roguelike.model.Player;

import static fr.ul.roguelike.RogueLike.screenHeight;
import static fr.ul.roguelike.RogueLike.screenWidth;


public class CampMenu extends ScreenAdapter {

    private MapInterface mapInterface;

    private Stage stage;
    private Button upgradeButton;

    private Player player;
    private Texture lifeBarBackground;
    private Texture heart;
    private Texture mana;
    private TextureRegion lifeBar, manaBar;
    private TextureRegion heartRegion, manaRegion;

    private SpriteBatch sb;
    private ShapeRenderer sr;
    private Animation<Texture> animation;
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
        animation = new Animation<Texture>(0.1f, loadFrames(8,"images/Fire/fire_"));
        sprite = new Sprite();
        int buttonWidth = (int)(screenWidth/8f);
        int buttonHeight = (int)(screenHeight/4.5f);
        sprite.setPosition(RogueLike.screenWidth/2f - buttonWidth /2f ,RogueLike.screenHeight/2f);
        sprite.setSize(buttonWidth, buttonHeight);
        player = mapInterface.getPlayer();
        lifeBarBackground = new Texture("images/combat/lifeBar.png");

        heart = new Texture("images/combat/heart.png");
        mana = new Texture("images/combat/manapotion.png");
        heartRegion = new TextureRegion(heart,screenWidth/50,screenWidth/50);
        manaRegion = new TextureRegion(mana, screenWidth/50,screenWidth/50);

        Texture life = new Texture("images/lifebar.png");
        lifeBar = new TextureRegion(life, screenWidth/2, screenHeight/15);
        Texture manaBa = new Texture("images/manabar.png");
        manaBar = new TextureRegion(manaBa,screenWidth/2, screenHeight/15);

        //Bouton upgrade
        Texture texture = new Texture(Gdx.files.internal("images/badlogic.jpg"));
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(texture));
        upgradeButton = new ButtonItem(drawable,"??");
        upgradeButton.setSize(buttonWidth, buttonHeight);
        upgradeButton.setPosition(RogueLike.screenWidth/2f - buttonWidth /2f,RogueLike.screenHeight/2f - buttonHeight*1.5f);
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

    private Texture[] loadFrames(int nb, String path){
        Texture[] frames = new Texture[nb];
        for(int i = 0; i < nb; i++) {
            frames[i] = new Texture(path + i + ".png");
        }
        return frames;
    }



    @Override
    public void render(float delta) {
        elapsed += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        stage.draw();
        stage.act();

        //Animation
        sb.begin();
        sprite.setRegion(animation.getKeyFrame(elapsed, true));
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

    /**
     * Dessine la barre de vie et de mana
     */
    private void drawLifeBars(){

        sb.begin();
        sb.draw(lifeBarBackground, 0,screenHeight - screenHeight/14, screenWidth/3.125f, screenHeight/14); //Vie du heros
        sb.draw(lifeBarBackground, 0,screenHeight - screenHeight/7, screenWidth/3.125f, screenHeight/14); //Mana du heros
        sb.end();

        //player life bar
        float playerHpLeftRate = 1.0f * player.getHealthLeft() / player.getPlayerCharacter().getHp();

        lifeBar.setRegionWidth((int)((playerHpLeftRate * screenWidth/3.125)- screenWidth/200f));
        sb.begin();
        sb.draw(lifeBar,screenWidth/400f,screenHeight + screenHeight/300f - screenHeight/14.0625f);
        sb.end();

        //player Mana
        float playerManaLeftRate =1.0f * player.getManaLeft() / player.getPlayerCharacter().getMana();
        manaBar.setRegionWidth((int)((playerManaLeftRate * screenWidth/3.125f)-screenWidth/200f));
        sb.begin();
        sb.draw(manaBar,screenWidth/400f,screenHeight + screenHeight/300f - screenHeight/7.03125f);
        sb.end();

        sb.begin();
        sb.draw(heart, screenWidth/3.125f - heartRegion.getRegionWidth(), screenHeight - heartRegion.getRegionHeight()*2, heartRegion.getRegionWidth()*2,heartRegion.getRegionHeight()*2);
        sb.draw(mana, screenWidth/3.125f - manaRegion.getRegionWidth(), screenHeight - manaRegion.getRegionHeight()*4, manaRegion.getRegionWidth()*2,manaRegion.getRegionHeight()*2);
        sb.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        upgradeButton.addAction(Actions.removeActor());
        sb.dispose();
        sr.dispose();
    }
}
