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
import fr.ul.roguelike.model.heros.Hero;
import fr.ul.roguelike.model.monsters.bosses.Boss;
import fr.ul.roguelike.model.monsters.Monster;
import fr.ul.roguelike.model.monsters.MonsterFactory;
import fr.ul.roguelike.model.Player;
import fr.ul.roguelike.model.stages.CombatStage;
import fr.ul.roguelike.model.stages.MiniBossStage;
import fr.ul.roguelike.model.stages.Stage;

import java.util.ArrayList;
import java.util.Random;

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
    private Texture monsterLifeBa;
    private TextureRegion monsterLifeBar;
    private Sprite attackButton;
    private Sprite defButton;
    private Sprite manaPotion;
    private Sprite healthPotion;
    private Texture lifeBarBackground;
    private Texture lifeBarMonsterBackground;
    private Texture heart, mana;
    private TextureRegion heartRegion, manaRegion;
    private boolean ended;

    private float physicalDamage,magicalDamage;

    private Label labelHealthPotion,labelManaPotion;

    private CombatController combatController;
    private Stage stage;
    private boolean isTimer = false;

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
    CombatMenu(Player p, MapInterface mi, Stage s) {
        ended = false;
        mapInterface = mi;
        player = p;
        player.resetMana();
        stage = s;
        createMonster();

        gen_monsters(p.getCurrentLevel());
        textureInit();
    }

    private void gen_monsters(int playerLevel){

    }

    private void createMonster(){
        monsters = new ArrayList<>();
        Random r = new Random();
        int random = r.nextInt(7);
        if(stage instanceof CombatStage){
            switch (random) {
                case 0 :
                    monsters.add(MonsterFactory.create("knight"));
                    break;
                case 1:
                    monsters.add(MonsterFactory.create("dragon"));
                    break;
                case 2:
                    monsters.add(MonsterFactory.create("skeleton"));
                    break;
                case 3:
                    monsters.add(MonsterFactory.create("stag_knight"));
                    break;
                case 4:
                    monsters.add(MonsterFactory.create("inquisitor"));
                    break;
                case 5:
                    monsters.add(MonsterFactory.create("cyclops"));
                    break;
                case 6:
                    monsters.add(MonsterFactory.create("assassin"));
                    break;
            }
        }else if(stage instanceof MiniBossStage){
            monsters.add(MonsterFactory.create("vampire"));
        }else{
            if(player.getCpt() == 1){
                monsters.add(MonsterFactory.create("griffin"));
            }if(player.getCpt() == 2){
                monsters.add(MonsterFactory.create("arachnoide"));
            }
        }
    }

    private void textureInit(){
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
        lifeBarBackground = new Texture("images/combat/lifeBar.png");
        lifeBarMonsterBackground = new Texture("images/combat/lifeBar.png");
        heart = new Texture("images/combat/heart.png");
        mana = new Texture("images/combat/manapotion.png");
        heartRegion = new TextureRegion(heart,screenWidth/50,screenWidth/50);
        manaRegion = new TextureRegion(mana, screenWidth/50,screenWidth/50);

        life = new Texture("images/lifebar.png");
        lifeBar = new TextureRegion(life,screenWidth/2, screenHeight/15);
        manaBa = new Texture("images/manabar.png");
        manaBar = new TextureRegion(manaBa,screenWidth/2, screenHeight/15);

        //Bar pour le monstre
        monsterLifeBa = new Texture("images/monsterLifebar.png");
        monsterLifeBar = new TextureRegion(monsterLifeBa,screenWidth/2, screenHeight/15);

        // resizing buttons
        final int buttonSize = screenWidth/10;
        attackButton.setSize(buttonSize,buttonSize);
        defButton.setSize(buttonSize,buttonSize);
        healthPotion.setSize(buttonSize,buttonSize);
        manaPotion.setSize(buttonSize,buttonSize);


        // Positionning buttons
        attackButton.setPosition(screenWidth/15f,screenWidth/15f);
        defButton.setPosition(attackButton.getX() * 3 , attackButton.getY());
        healthPotion.setPosition(screenWidth - screenWidth/15f - healthPotion.getWidth() , attackButton.getY());
        manaPotion.setPosition(screenWidth - 3 * screenWidth /15f - manaPotion.getWidth() , attackButton.getY());

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
            player.giveMoney(20);
            if(mapInterface.isBoss()){
                if(mapInterface.isEnded()){
                    mapInterface.getRogueLike().changeScreen();
                }else {
                    mapInterface.generateMap();
                    player.increaseCpt();
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
            if(!isTimer){
                isTimer = true;
                Timer timer = new Timer();
                com.badlogic.gdx.utils.Timer.Task task = new com.badlogic.gdx.utils.Timer.Task() {
                    @Override
                    public void run() {
                        mapInterface.getRogueLike().setScreen(new MainMenu(mapInterface.getRogueLike()));;
                    }
                };
                timer.scheduleTask(task, 5);
            }

        }

        update(delta);
    }

    private void drawCombat(){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        sb.begin();

        sb.draw(background, 0, 0, screenWidth, screenHeight);
        player.getPlayerCharacter().draw(sb, screenWidth / 20f, screenHeight / 3f);
        sb.draw(lifeBarBackground, 0,screenHeight - screenHeight/14, screenWidth/3.125f, screenHeight/14); //Vie du heros
        sb.draw(lifeBarBackground, 0,screenHeight - screenHeight/7, screenWidth/3.125f, screenHeight/14); //Mana du heros
        sb.draw(lifeBarBackground, screenWidth - monsters.get(0).getHp()*screenWidth/400f - screenWidth/800f, screenHeight - screenHeight/14,monsters.get(0).getHp()*screenWidth/400f + screenWidth/800f , screenHeight/14); //Vie du monstre

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
        sb.draw(heart, screenWidth/3.125f - heartRegion.getRegionWidth(), screenHeight - heartRegion.getRegionHeight()*2, heartRegion.getRegionWidth()*2,heartRegion.getRegionHeight()*2);
        sb.draw(mana, screenWidth/3.125f - manaRegion.getRegionWidth(), screenHeight - manaRegion.getRegionHeight()*4, manaRegion.getRegionWidth()*2,manaRegion.getRegionHeight()*2);
        sb.end();
    }

    private void drawLifeBars(){
        //player life bar
        float playerHpLeftRate = 1.0f * player.getHealthLeft() / player.getPlayerCharacter().getHp();

        lifeBar.setRegionWidth((int)((playerHpLeftRate * screenWidth/3.125)- screenWidth/200f));
        sb.begin();
        sb.draw(lifeBar,screenWidth/400f,screenHeight + screenHeight/300f - screenHeight/14.0625f);
        sb.end();

        //monster health bar
        if(!monsters.isEmpty()) {
            sb.begin();
            sb.draw(monsterLifeBar,screenWidth - monsters.get(0).getCurrentHp()*screenWidth/400f, screenHeight+screenHeight/200 - screenHeight/14);
            sb.end();
        }

        //player Mana
        float playerManaLeftRate =1.0f * player.getManaLeft() / player.getPlayerCharacter().getMana();
        manaBar.setRegionWidth((int)((playerManaLeftRate * screenWidth/3.125f)-screenWidth/200f));
        sb.begin();
        sb.draw(manaBar,screenWidth/400f,screenHeight + screenHeight/300f - screenHeight/7.03125f);
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
                player.receiveHit((int) (monsters.get(0).getDamage(player)));
                player.getPlayerCharacter().setCombatState(Hero.CombatState.HIT);
                monsters.get(0).setDegat(false);
            }
        }
        if(player.getPlayerCharacter().isDegat()){
            monsters.get(0).receiveHit((int)(player.getDamage(monsters.get(0))));
            player.getPlayerCharacter().setDegat(false);
            if (monsters.get(0).getCurrentHp() <= 0 && monsters.get(0).getCombatState() != Hero.CombatState.DEAD) {
                monsters.get(0).setCombatState(Hero.CombatState.DEAD);
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();

        background.dispose();

        attackButton.getTexture().dispose();
        manaPotion.getTexture().dispose();
        healthPotion.getTexture().dispose();
        defButton.getTexture().dispose();
    }
}
