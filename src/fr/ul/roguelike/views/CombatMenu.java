package fr.ul.roguelike.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import fr.ul.roguelike.controllers.CombatController;
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
    private Sprite attackButton;
    private Sprite defButton;
    private Sprite manaPotion;
    private Sprite healthPotion;
    private ArrayList<Texture> spellsButton;

    private CombatController combatController;



    private TextureRegion playerTextureRegion;


    public CombatMenu(Player p) {
        player = p;
        monsters = new ArrayList<>();
        monsters.add(new Golem(100, 0, 1, 0.0f, 5, 0, 0.2f, 0.5f));
        gen_monsters(p.getCurrent_level());
        spellsButton = new ArrayList<>();

        cam = new OrthographicCamera();

        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.update();
        //Sprite batch init
        sb = new SpriteBatch();
        sb.setProjectionMatrix(cam.combined);

        // loading textures
        background = new Texture("combat/background.png");
        attackButton = new Sprite(new Texture("combat/button_attack.png"));
        manaPotion = new Sprite(new Texture("combat/button_manapotion.png"));
        healthPotion = new Sprite(new Texture("combat/button_potion.png"));
        defButton = new Sprite(new Texture("combat/button_def.png"));

        // resizing buttons
        final int buttonSize = Gdx.graphics.getWidth()/15;
        attackButton.setSize(buttonSize,buttonSize);
        defButton.setSize(buttonSize,buttonSize);
        healthPotion.setSize(buttonSize,buttonSize);
        manaPotion.setSize(buttonSize,buttonSize);


        // Positionning buttons
        attackButton.setPosition(Gdx.graphics.getWidth()/9,Gdx.graphics.getWidth()/15);
        defButton.setPosition(attackButton.getX() * 3 , attackButton.getY());
        healthPotion.setPosition(attackButton.getX() * 5 , attackButton.getY());
        manaPotion.setPosition(attackButton.getX() * 7 , attackButton.getY());

        sr = new ShapeRenderer();
        sr.setProjectionMatrix(cam.combined);

        //player regenration
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                player.regenMana();;
            }
        },0.0f,player.getPlayerCharacter().getManaRegenTime());

        combatController = new CombatController(attackButton.getBoundingRectangle(),defButton.getBoundingRectangle(),healthPotion.getBoundingRectangle(),manaPotion.getBoundingRectangle(),player,monsters);

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
        player.getPlayerCharacter().draw(sb,Gdx.graphics.getWidth()/6 ,Gdx.graphics.getHeight()/3);

        if(!monsters.isEmpty()) {
            monsters.get(0).draw(sb,Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 3);
        }

        attackButton.draw(sb);
        defButton.draw(sb);
        healthPotion.draw(sb);
        manaPotion.draw(sb);

        sb.end();

        drawLifeBars();

        update(delta);
    }

    private void drawLifeBars(){
        Gdx.gl.glClearColor(1f, 0.25f, 0.25f, 1);

        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(1.f,0f,0f,1f);

        //player life bar
        float playerHpLeftRate =1.0f * player.getHealthLeft() / player.getPlayerCharacter().getHp();
        sr.rect(
                0.0f,Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/20,
                playerHpLeftRate *Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/20);

        //monster health bar
        if(!monsters.isEmpty()) {
            sr.rect(
                    Gdx.graphics.getWidth() / 2, (Gdx.graphics.getHeight() / 3) + monsters.get(0).getAnim().getKeyFrames()[0].getRegionHeight() * 6,
                    monsters.get(0).hpLeftRatio() * monsters.get(0).getAnim().getKeyFrames()[0].getRegionWidth() * 6f, Gdx.graphics.getHeight() / 40);
        }

        //player Mana
        float playerManaLeftRate =1.0f * player.getManaLeft() / player.getPlayerCharacter().getMana();
        sr.setColor(0f,0.3f,1f,1f);
        sr.rect(
                0.0f,(float)Gdx.graphics.getHeight() - 2* Gdx.graphics.getHeight()/20,
                playerManaLeftRate * Gdx.graphics.getWidth()/2.f,Gdx.graphics.getHeight()/20);



        sr.end();
    }

    private void update(float delta){
        player.updateState();
        for(Monster m : monsters){
            if(m.getTimeSincePreviousAttack() < m.getAttackSpeed()){
                m.updateLastAttackTimer( (m.getTimeSincePreviousAttack() + delta));
            }
            else{
                m.updateLastAttackTimer(0);
                player.receiveHit(m.getPhysicalDmg());
            }
        }
        combatController.checkInput();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
