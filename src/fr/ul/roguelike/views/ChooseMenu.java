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
import fr.ul.roguelike.RogueLike;
import fr.ul.roguelike.model.GifDecoder;
import fr.ul.roguelike.model.Heros.Alchimist;
import fr.ul.roguelike.model.Heros.Hero;
import fr.ul.roguelike.model.Heros.Mage;
import fr.ul.roguelike.model.Heros.Warrior;
import fr.ul.roguelike.model.Player;

public class ChooseMenu extends ScreenAdapter {
    private RogueLike rogueLike;
    private Player player;
    private MapInterface mapInterface;
    private Hero hero;

    private SpriteBatch sb;
    private Texture background;
    private ShapeRenderer sr;
    private Animation<TextureRegion> animationMage;
    private Animation<TextureRegion> animationWarrior;
    private Animation<TextureRegion> animationAlchimist;
    private float elapsed;
    private Sprite spriteMage;
    private Sprite spriteWarrior;
    private Sprite spriteAlchimist;

    private int screenWidth = Gdx.graphics.getWidth();
    private int screenHeight = Gdx.graphics.getHeight();

    public ChooseMenu(RogueLike r, Player p){
        rogueLike = r;
        player = p;
        sb = new SpriteBatch();
        background = new Texture(Gdx.files.internal("images/background_choose.png"));

        //Mage animation
        animationMage = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("images/animations/mage.gif").read());
        spriteMage = new Sprite();
        spriteMage.setPosition(screenWidth/3, 0);
        spriteMage.setSize(screenWidth/3, screenHeight/2);

        //Warrior animation
        animationWarrior = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("images/animations/warrior.gif").read());
        spriteWarrior = new Sprite();
        spriteWarrior.setPosition(0, 0);
        spriteWarrior.setSize(screenWidth/3, screenHeight/2);

        //Alchimist animation
        animationAlchimist = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("images/animations/alchimist.gif").read());
        spriteAlchimist = new Sprite();
        spriteAlchimist.setPosition(screenWidth/3*2, 0);
        spriteAlchimist.setSize(screenWidth/3, screenHeight/2);
    }

    @Override
    public void render(float delta) {
        elapsed += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        //Animations
        sb.begin();
        sb.draw(background, 0, 0, screenWidth, screenHeight);
        spriteMage.setRegion(animationMage.getKeyFrame(elapsed));
        sb.draw(spriteMage, spriteMage.getX(), spriteMage.getY(), spriteMage.getWidth(), spriteMage.getHeight());
        spriteWarrior.setRegion(animationWarrior.getKeyFrame(elapsed));
        sb.draw(spriteWarrior, spriteWarrior.getX(), spriteWarrior.getY(), spriteWarrior.getWidth(), spriteWarrior.getHeight());
        spriteAlchimist.setRegion(animationAlchimist.getKeyFrame(elapsed));
        sb.draw(spriteAlchimist, spriteAlchimist.getX(), spriteAlchimist.getY(), spriteAlchimist.getWidth(), spriteAlchimist.getHeight());
        sb.end();

        update();
    }

    public void update(){
        if(spriteWarrior.getBoundingRectangle().contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) || Gdx.input.isTouched()) {
                play(new Warrior());
            }
        }

        if(spriteMage.getBoundingRectangle().contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) || Gdx.input.isTouched()) {
                play(new Mage());
            }
        }

        if(spriteAlchimist.getBoundingRectangle().contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) || Gdx.input.isTouched()) {
                play(new Alchimist());
            }
        }
    }

    public void play(Hero h){
        player.setPlayerCharacter(h);

        mapInterface = new MapInterface(rogueLike, player);
        rogueLike.setScreen(mapInterface);
    }
}
