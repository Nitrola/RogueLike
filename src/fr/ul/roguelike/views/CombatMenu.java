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
import static fr.ul.roguelike.RogueLike.screenWidth;
import static fr.ul.roguelike.RogueLike.screenHeight;

public class CombatMenu extends ScreenAdapter {
    private MapInterface mapInterface;

    //Model
    private Player player;
    private ArrayList<Monster> monsters;

    private SpriteBatch sb;
    private ShapeRenderer sr;

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
    private boolean ended;

    private CombatController combatController;

    private enum State{
        WIN,
        LOOSE,
        COMBAT
    }
    private State currentState;

    /**
     * Représente un combat
     * @param p le joueur
     * @param mi la map à remettre après le combat
     */
    CombatMenu(Player p, MapInterface mi) {
        ended = false;
        mapInterface = mi;
        player = p;
        monsters = new ArrayList<>();
        monsters.add(MonsterFactory.create("golem"));
        gen_monsters(p.getCurrentLevel());

        OrthographicCamera cam = new OrthographicCamera();

        cam.setToOrtho(false, screenWidth, screenHeight);
        cam.update();

        //Sprite batch init
        sb = new SpriteBatch();
        sb.setProjectionMatrix(cam.combined);

        // loading textures
        background = new Texture("images/combat/background.png");
        attackButton = new Sprite(new Texture("images/combat/button_attack.png"));
        manaPotion = new Sprite(new Texture("images/combat/button_manapotion.png"));
        healthPotion = new Sprite(new Texture("images/combat/button_potion.png"));
        defButton = new Sprite(new Texture("images/combat/button_def.png"));
        victoryMessage = new Texture("images/combat/victoryMessage.png");
        deadMessage = new Texture("images/combat/deadMessage.png");
        lifeBar = new Texture("images/combat/lifeBar.png");
        heart = new Texture("images/combat/heart.png");
        mana = new Texture("images/combat/manapotion.png");

        // resizing buttons
        final int buttonSize = screenWidth/15;
        attackButton.setSize(buttonSize,buttonSize);
        defButton.setSize(buttonSize,buttonSize);
        healthPotion.setSize(buttonSize,buttonSize);
        manaPotion.setSize(buttonSize,buttonSize);


        // Positionning buttons
        attackButton.setPosition(screenWidth/9f,screenWidth/15f);
        defButton.setPosition(attackButton.getX() * 3 , attackButton.getY());
        healthPotion.setPosition(attackButton.getX() * 5 , attackButton.getY());
        manaPotion.setPosition(attackButton.getX() * 7 , attackButton.getY());

        sr = new ShapeRenderer();
        sr.setProjectionMatrix(cam.combined);

        //player regenration
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if(!ended)
                player.regenMana();
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
            ended = true;
            drawCombat();
            sb.begin();
            sb.draw(victoryMessage,
                    0,0,
                    screenWidth,screenHeight);
            sb.end();
            mapInterface.setScreen();
        }

        if(currentState == State.LOOSE){
            //stop mana regeneration
            ended = true;
            drawCombat();
            sb.begin();
            sb.draw(deadMessage,
                    0,0,
                    screenWidth,screenHeight);
            sb.end();
        }

        update(delta);
    }

    private void drawCombat(){
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

            sb.begin();

            sb.draw(background, 0, 0, screenWidth, screenHeight);
            player.getPlayerCharacter().draw(sb, screenWidth / 20f, screenHeight / 3f);
            sb.draw(lifeBar,
                0,screenHeight - lifeBar.getHeight()*2,
                lifeBar.getWidth()*2,lifeBar.getHeight()*2);
            sb.draw(lifeBar,
                0,screenHeight - lifeBar.getHeight()*4,
                lifeBar.getWidth()*2,lifeBar.getHeight()*2);


            if (!monsters.isEmpty()) {
                monsters.get(0).draw(sb, screenWidth / 2, screenHeight / 3);
            }

            attackButton.draw(sb);
            defButton.draw(sb);
            healthPotion.draw(sb);
            manaPotion.draw(sb);
            sb.end();
            drawLifeBars();

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

    private void drawLifeBars(){

        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(.85f,0f,0f,0.1f);

        //player life bar
        float playerHpLeftRate =1.0f * player.getHealthLeft() / player.getPlayerCharacter().getHp();
        sr.rect(
                4.0f,screenHeight + 3f- lifeBar.getHeight()*2,
                (playerHpLeftRate *lifeBar.getWidth()*2)-7.f,(lifeBar.getHeight()*2)-6f );

        //monster health bar
        if(!monsters.isEmpty()) {
            sr.rect(
                    screenWidth / 2f, (screenHeight / 3f) + monsters.get(0).getAnim().getKeyFrames()[0].getRegionHeight() * 6,
                    monsters.get(0).hpLeftRatio() * monsters.get(0).getAnim().getKeyFrames()[0].getRegionWidth() * 6f, screenHeight / 40f);
        }

        //player Mana
        float playerManaLeftRate =1.0f * player.getManaLeft() / player.getPlayerCharacter().getMana();
        sr.setColor(0f,0.4f,1f,1f);
        sr.rect(
                4.0f,screenHeight + 3f- lifeBar.getHeight()*4,
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
