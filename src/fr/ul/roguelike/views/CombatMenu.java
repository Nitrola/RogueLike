package fr.ul.roguelike.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Timer;
import fr.ul.roguelike.model.Monster.Golem;
import fr.ul.roguelike.model.Monster.Monster;
import fr.ul.roguelike.model.Player;

import java.util.ArrayList;

public class CombatMenu extends ScreenAdapter {

    //Model
    private Player player;
    private ArrayList<Monster> monsters;

    private OrthographicCamera cam;
    private SpriteBatch sb;
    private ShapeRenderer sr;

    // Assets
    private Music combat_music;

    private Texture background;
    private Texture playerCharacterTex;
    private Texture attackButton;
    private Texture fleeButton;
    private Texture manaPotion;
    private Texture lifePotion;
    private ArrayList<Texture> monstersTexture;
    private ArrayList<TextureRegion> monstersSprite;
    private ArrayList<Texture> spellsButton;

    private Timer manaTimer;
    private Timer.Task regenMana;

    private TextureRegion playerTextureRegion;


    public CombatMenu(Player p) {
        player = p;
        monsters = new ArrayList<>();
        monsters.add(new Golem(100, 0, 5, 0.0f, 5, 0, 0.2f, 0.5f));
        gen_monsters(p.getCurrent_level());
        monstersTexture = new ArrayList<>();
        monstersTexture.add(new Texture("combat/golem.png"));
        monstersSprite = new ArrayList<>();
        monstersSprite.add(new TextureRegion(monstersTexture.get(0),
                3 * monstersTexture.get(0).getWidth() / 7, monstersTexture.get(0).getHeight() / 4,
                monstersTexture.get(0).getWidth() / 7,
                monstersTexture.get(0).getHeight() / 4));
        spellsButton = new ArrayList<>();


        //loading assets
        background = new Texture("combat/background.png");
        playerCharacterTex = new Texture("combat/" + player.getPlayerCharacter().getClass().getSimpleName().toString() + ".png");

        this.playerTextureRegion = new TextureRegion(playerCharacterTex,
                3 * playerCharacterTex.getWidth() / 9, 3 * playerCharacterTex.getHeight() / 9,
                playerCharacterTex.getWidth() / 9,
                playerCharacterTex.getHeight() / 6
        );
        playerTextureRegion.flip(true, false);

        cam = new OrthographicCamera();

        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.update();
        //Sprite batch init
        sb = new SpriteBatch();
        sb.setProjectionMatrix(cam.combined);

        sr = new ShapeRenderer();
        sr.setProjectionMatrix(cam.combined);
        //player regenration
        /*manaTimer = new Timer();
        regenMana = new Timer.Task() {
            @Override
            public void run() {
                player.regenMana();
            }
        };
        manaTimer.scheduleTask(regenMana, 1.0f,player.getPlayerCharacter().getManaRegenTime());*/
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                regenMana();
            }
        },0.0f,player.getPlayerCharacter().getManaRegenTime());

    }

    public void regenMana(){
        player.regenMana();
    }
    private void gen_monsters(int playerLevel){

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0.25f, 0.25f, 0.25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        sb.begin();
        sb.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        sb.draw(
                playerTextureRegion,
                 Gdx.graphics.getWidth()/6 ,Gdx.graphics.getHeight()/3,
                playerTextureRegion.getRegionWidth() * 3, playerTextureRegion.getRegionHeight() * 3);
        sb.draw(
                monstersSprite.get(0),
                Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/3,
                monstersSprite.get(0).getRegionWidth() * 6, monstersSprite.get(0).getRegionHeight() * 6);

        sb.end();
        Gdx.gl.glClearColor(1f, 0.25f, 0.25f, 1);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(1.f,0f,0f,1f);
        //playerHP
        float playerHpLeftRate =1.0f * player.getHealthLeft() / player.getPlayerCharacter().getHp();
        sr.rect(
                0.0f,Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/20,
                playerHpLeftRate *Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/20)
        ;
        //monster health bar
        sr.rect(
                Gdx.graphics.getWidth()/2 ,(Gdx.graphics.getHeight()/3) + monstersSprite.get(0).getRegionHeight()*6,
                monstersSprite.get(0).getRegionWidth() * 6, Gdx.graphics.getHeight()/40
        );

        //player Mana
        float playerManaLeftRate =1.0f * player.getManaLeft() / player.getPlayerCharacter().getMana();
        sr.setColor(0f,0.3f,1f,1f);
        sr.rect(
                0.0f,(float)Gdx.graphics.getHeight() - 2* Gdx.graphics.getHeight()/20,
                playerManaLeftRate * Gdx.graphics.getWidth()/2.f,Gdx.graphics.getHeight()/20
        );
        sr.end();
        manageCombat(delta);
    }

    private void manageCombat(float delta){

        for(Monster m : monsters){
            if(m.getTimeSincePreviousAttack() < m.getAttackSpeed()){

                m.updateLastAttackTimer( (m.getTimeSincePreviousAttack() + delta));
            }
            else{
                m.updateLastAttackTimer(0);
                player.receiveHit(m.getPhysicalDmg());
            }
        }

    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
