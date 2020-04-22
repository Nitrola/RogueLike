package fr.ul.roguelike.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Timer;
import fr.ul.roguelike.controllers.CombatController;
import fr.ul.roguelike.model.Monster.Monster;
import fr.ul.roguelike.model.Monster.MonsterFactory;
import fr.ul.roguelike.model.Player;

import java.util.ArrayList;

public class CombatMenu extends ScreenAdapter {
    private MapInterface mapInterface;

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
    private Texture victoryMessage;
    private Texture deadMessage;
    private Texture lifeBar;
    private Texture heart;
    private Texture mana;
    private ArrayList<Texture> spellsButton;


    private CombatController combatController;


    private enum State{
        WIN,
        LOOSE,
        COMBAT
    }

    private State currentState;

    public CombatMenu(Player p, MapInterface mi) {
        mapInterface = mi;
        player = p;
        monsters = new ArrayList<>();
        monsters.add(MonsterFactory.create("golem"));
        gen_monsters(p.getCurrentLevel());
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
        victoryMessage = new Texture("combat/victoryMessage.png");
        deadMessage = new Texture("combat/deadMessage.png");
        lifeBar = new Texture("combat/lifeBar.png");
        heart = new Texture("combat/heart.png");
        mana = new Texture("combat/manapotion.png");

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
        currentState = State.COMBAT;
    }

    private void gen_monsters(int playerLevel){

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if(currentState == State.COMBAT) {
            drawCombat();
        }

        if(currentState == State.WIN){
            //stop mana regeneration
            Timer.instance().stop();
            drawCombat();
            sb.begin();
            sb.draw(victoryMessage,
                    0,0,
                    Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
            sb.end();
            mapInterface.setScreen();
        }

        if(currentState == State.LOOSE){
            //stop mana regeneration
            Timer.instance().stop();
            drawCombat();
            sb.begin();
            sb.draw(deadMessage,
                    0,0,
                    Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
            sb.end();
        }

        update(delta);
    }

    private void drawCombat(){
            Gdx.gl.glClearColor(0.25f, 0.25f, 0.25f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

            sb.begin();

            sb.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            player.getPlayerCharacter().draw(sb, Gdx.graphics.getWidth() / 6, Gdx.graphics.getHeight() / 3);
            sb.draw(lifeBar,
                0,Gdx.graphics.getHeight() - lifeBar.getHeight()*2,
                lifeBar.getWidth()*2,lifeBar.getHeight()*2);
            sb.draw(lifeBar,
                0,Gdx.graphics.getHeight() - lifeBar.getHeight()*4,
                lifeBar.getWidth()*2,lifeBar.getHeight()*2);


            if (!monsters.isEmpty()) {
                monsters.get(0).draw(sb, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 3);
            }

            attackButton.draw(sb);
            defButton.draw(sb);
            healthPotion.draw(sb);
            manaPotion.draw(sb);
            sb.end();
            drawLifeBars();

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

    private void drawLifeBars(){

        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(.85f,0f,0f,0.1f);

        //player life bar
        float playerHpLeftRate =1.0f * player.getHealthLeft() / player.getPlayerCharacter().getHp();
        sr.rect(
                4.0f,Gdx.graphics.getHeight() + 3f- lifeBar.getHeight()*2,
                (playerHpLeftRate *lifeBar.getWidth()*2)-7.f,(lifeBar.getHeight()*2)-6f );

        //monster health bar
        if(!monsters.isEmpty()) {
            sr.rect(
                    Gdx.graphics.getWidth() / 2, (Gdx.graphics.getHeight() / 3) + monsters.get(0).getAnim().getKeyFrames()[0].getRegionHeight() * 6,
                    monsters.get(0).hpLeftRatio() * monsters.get(0).getAnim().getKeyFrames()[0].getRegionWidth() * 6f, Gdx.graphics.getHeight() / 40);
        }

        //player Mana
        float playerManaLeftRate =1.0f * player.getManaLeft() / player.getPlayerCharacter().getMana();
        sr.setColor(0f,0.4f,1f,1f);
        sr.rect(
                4.0f,Gdx.graphics.getHeight() + 3f- lifeBar.getHeight()*4,
                (playerManaLeftRate *lifeBar.getWidth()*2)-7.f,(lifeBar.getHeight()*2)-6f );
        sr.end();
    }

    private void update(float delta){
        player.updateState();
        if(player.getHealthLeft() <= 0){
            currentState = State.LOOSE;
        }
        if(monsters.isEmpty()){
            currentState = State.WIN;
        }
        for(Monster m : monsters){
            if(m.getTimeSincePreviousAttack() < m.getAttackSpeed()){
                m.updateLastAttackTimer( (m.getTimeSincePreviousAttack() + delta));
            }
            else{
                m.updateLastAttackTimer(0);
                player.receiveHit(m.getPhysicalDmg());
            }
        }
        if(currentState == State.COMBAT)
            combatController.checkInput();
    }

    @Override
    public void dispose() {
        super.dispose();

        background.dispose();
        victoryMessage.dispose();
        deadMessage.dispose();

        attackButton.getTexture().dispose();
        manaPotion.getTexture().dispose();
        healthPotion.getTexture().dispose();
        defButton.getTexture().dispose();
    }
}
