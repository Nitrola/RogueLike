package fr.ul.roguelike.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Timer;
import fr.ul.roguelike.controllers.CombatController;
import fr.ul.roguelike.model.Heros.Hero;
import fr.ul.roguelike.model.Monster.Boss.Boss;
import fr.ul.roguelike.model.Monster.Monster;
import fr.ul.roguelike.model.Monster.MonsterFactory;
import fr.ul.roguelike.model.Player;
import fr.ul.roguelike.model.stages.CombatStage;
import fr.ul.roguelike.model.stages.MiniBossStage;
import fr.ul.roguelike.model.stages.Stage;

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
    private Texture life;
    private TextureRegion lifeBar;
    private Texture manaBa;
    private TextureRegion manaBar;
    private Sprite attackButton;
    private Sprite defButton;
    private Sprite manaPotion;
    private Sprite healthPotion;
    private Texture victoryMessage;
    private Texture deadMessage;
    private Texture lifeBarBackground;
    private Texture heart;
    private Texture mana;
    private boolean ended;

    private Label labelHealthPotion,labelManaPotion;

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
    CombatMenu(Player p, MapInterface mi, Stage stage) {
        ended = false;
        mapInterface = mi;
        player = p;
        player.resetMana();
        monsters = new ArrayList<>();
        if(stage instanceof CombatStage){
            monsters.add(MonsterFactory.create("knight"));
        }else if(stage instanceof MiniBossStage){
            monsters.add(MonsterFactory.create("vampire"));
        }else{
            if(player.cpt == 1){
                monsters.add(MonsterFactory.create("griffin"));
            }if(player.cpt == 2){
                monsters.add(MonsterFactory.create("arachnoide"));
            }

        }
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
        lifeBarBackground = new Texture("images/combat/lifeBar.png");
        heart = new Texture("images/combat/heart.png");
        mana = new Texture("images/combat/manapotion.png");
        life = new Texture("images/lifebar.png");
        lifeBar = new TextureRegion(life, (int)((20 * lifeBarBackground.getWidth()*2)-7.f), (int)((lifeBarBackground.getHeight()*2)-6f ));
        manaBa = new Texture("images/manabar.png");
        manaBar = new TextureRegion(manaBa, (int)((20 * lifeBarBackground.getWidth()*2)-7.f), (int)((lifeBarBackground.getHeight()*2)-6f ));

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

        // Number of potions
        FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal(("fonts/comicSansMS.ttf")));
        FreeTypeFontGenerator.FreeTypeFontParameter fontCarac = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontCarac.size = buttonSize/2;
        fontCarac.color = new Color(0.9490f,0.0862f,0.1568f,1.0f);
        fontCarac.borderColor = Color.BLACK;
        fontCarac.borderWidth = 0.5f;
        fontCarac.shadowColor = new Color(0,0,0,0.50f);
        fontCarac.shadowOffsetX = 2;
        fontCarac.shadowOffsetY = 2;

        BitmapFont police = fontGen.generateFont(fontCarac);


        labelHealthPotion = new Label(Integer.toString(player.getNbPotionHealth()),new Label.LabelStyle(police, fontCarac.color));
        fontCarac.color = new Color(0.0784f,0.4745f,0.9490f,1.0f); // <-- Ceci est un float
        police = fontGen.generateFont(fontCarac);
        labelManaPotion = new Label(Integer.toString(player.getNbPotionMana()),new Label.LabelStyle(police, fontCarac.color));
        labelHealthPotion.setPosition(healthPotion.getX()+3*buttonSize/4f,healthPotion.getY()-screenHeight/36f);
        labelManaPotion.setPosition(manaPotion.getX()+3*buttonSize/4f,manaPotion.getY()-screenHeight/36f);
        fontGen.dispose();
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

        if(!monsters.isEmpty()){
            if(monsters.get(0).getCombatState() == Hero.CombatState.WIN){
                currentState = State.WIN;
            }
        }

        if(currentState == State.WIN){
            //stop mana regeneration
            ended = true;
            drawCombat();
            sb.begin();
            sb.draw(victoryMessage, 0,0, screenWidth,screenHeight);
            sb.end();
            player.giveMoney(20);
            if(mapInterface.isBoss()){
                if(mapInterface.isEnded()){
                    mapInterface.getRogueLike().changeScreen();
                }else {
                    mapInterface.generateMap();
                    player.cpt++;
                    mapInterface.setScreen();
                }
            }else{
                if (!mapInterface.isEnded() || !mapInterface.isBoss()) {
                    mapInterface.setScreen();
                }
            }
        }

        if(currentState == State.LOOSE){
            //stop mana regeneration
            ended = true;
            drawCombat();
            sb.begin();
            sb.draw(deadMessage, 0,0, screenWidth,screenHeight);
            sb.end();
        }

        update(delta);
    }

    private void drawCombat(){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        sb.begin();

        sb.draw(background, 0, 0, screenWidth, screenHeight);
        player.getPlayerCharacter().draw(sb, screenWidth / 20f, screenHeight / 3f);
        sb.draw(lifeBarBackground, 0,screenHeight - lifeBarBackground.getHeight()*2, lifeBarBackground.getWidth()*2, lifeBarBackground.getHeight()*2);
        sb.draw(lifeBarBackground, 0,screenHeight - lifeBarBackground.getHeight()*4, lifeBarBackground.getWidth()*2, lifeBarBackground.getHeight()*2);

        if (!monsters.isEmpty()) {
            monsters.get(0).draw(sb, screenWidth / 2, screenHeight / 3);
        }


        //update les labels
        labelHealthPotion.setText(player.getNbPotionHealth());
        labelManaPotion.setText(player.getNbPotionMana());

        //Dessin des boutons
        attackButton.draw(sb);
        defButton.draw(sb);
        healthPotion.draw(sb);
        manaPotion.draw(sb);

        labelHealthPotion.draw(sb,1);
        labelManaPotion.draw(sb,1);
        sb.end();
        drawLifeBars();

        sb.begin();
        sb.draw(heart, lifeBarBackground.getWidth()*2 - heart.getWidth()*2, screenHeight - heart.getHeight()*4, heart.getHeight()*4,heart.getHeight()*4);
        sb.draw(mana, lifeBarBackground.getWidth()*2 - mana.getWidth(), screenHeight - mana.getHeight()*4, mana.getHeight()*1.9f,mana.getHeight()*1.9f);
        sb.end();
    }

    private void drawLifeBars(){
        //player life bar
        float playerHpLeftRate = 1.0f * player.getHealthLeft() / player.getPlayerCharacter().getHp();

        lifeBar.setRegionWidth((int)((playerHpLeftRate * lifeBarBackground.getWidth()*2)-7.f));
        sb.begin();
        sb.draw(lifeBar,4.0f,screenHeight + 3f- lifeBarBackground.getHeight()*2);
        sb.end();

        //monster health bar
        if(!monsters.isEmpty()) {
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.setColor(Color.RED);
            sr.rect(screenWidth - monsters.get(0).hpLeftRatio() * screenWidth/23f * 6f, screenHeight - screenHeight / 40f, monsters.get(0).hpLeftRatio() * screenWidth/23f * 6f, screenHeight / 40f);
            sr.end();
        }

        //player Mana
        float playerManaLeftRate =1.0f * player.getManaLeft() / player.getPlayerCharacter().getMana();
        manaBar.setRegionWidth((int)((playerManaLeftRate * lifeBarBackground.getWidth()*2)-7.f));
        sb.begin();
        sb.draw(manaBar,4.0f,screenHeight + 3f - lifeBarBackground.getHeight()*4);
        sb.end();
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
            }else{
                m.updateLastAttackTimer(0);
                if(monsters.get(0).getCombatState() != Hero.CombatState.DEAD) {
                    monsters.get(0).setCombatState(Hero.CombatState.ATTACKING);
                    if(monsters.get(0) instanceof Boss){
                        ((Boss) monsters.get(0)).selectRandomAttack();
                    }
                    monsters.get(0).setHasAttack(false);
                }
            }
        }
        if(currentState == State.COMBAT) {
            combatController.checkInput();
        }
        if(!monsters.isEmpty()) {
            if (monsters.get(0).isDegat()) {
                player.receiveHit(monsters.get(0).getPhysicalDmg());
                monsters.get(0).setDegat(false);
            }
        }
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
