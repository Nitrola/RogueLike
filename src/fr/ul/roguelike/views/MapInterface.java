package fr.ul.roguelike.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import fr.ul.roguelike.RogueLike;
import fr.ul.roguelike.controllers.KeyboardListener;
import fr.ul.roguelike.model.GameWorld;
import fr.ul.roguelike.model.Player;
import fr.ul.roguelike.model.stages.*;

import java.util.ArrayList;
import java.util.Random;

public class MapInterface extends ScreenAdapter {
    private RogueLike rogueLike;
    private InventoryMenu inventoryMenu;
    private Player player;

    private SpriteBatch spriteBatch;
    private GameWorld gameWorld;
    private Texture map;
    private ArrayList<Stage> listeStages;
    private KeyboardListener keyboardListener;
    private OrthographicCamera camera = new OrthographicCamera();
    private ShapeRenderer shapeRenderer;
    private Stage actualStage;
    private boolean isClicking;

    static Stage tamponD, tamponG, tampon;
    private int coeff = Gdx.graphics.getWidth()/16;

    public MapInterface(RogueLike rogueLike, Player p){
        this.rogueLike = rogueLike;
        player = p;
        inventoryMenu = new InventoryMenu(this);
        spriteBatch = new SpriteBatch();
        gameWorld = new GameWorld();
        isClicking = false;
        map = new Texture(Gdx.files.internal("images/map.png"));
        keyboardListener = new KeyboardListener();
        Gdx.input.setInputProcessor(keyboardListener);
        camera.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camera.update();

        listeStages = new ArrayList<Stage>();
        generateMap();
    }

    /**
     * Affiche la map
     */
    @Override
    public void render (float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        spriteBatch.draw(map, 0, 0,Gdx.graphics.getWidth(),       Gdx.graphics.getHeight());
        spriteBatch.end();

        //Dessin des traits qui relient les stages
        for(Stage stage : listeStages){
            if(stage.getRightStage() != null) {
                shapeRenderer = new ShapeRenderer();
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(0, 0, 0, 1);
                shapeRenderer.line(stage.getPosition(), stage.getRightStage().getPosition());

                shapeRenderer.end();
            }
            if(stage.getLeftStage() != null) {
                shapeRenderer = new ShapeRenderer();
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(0, 0, 0, 1);
                shapeRenderer.line(stage.getPosition(), stage.getLeftStage().getPosition());
                shapeRenderer.end();
            }
            if(stage.getUniqueStage() != null) {
                shapeRenderer = new ShapeRenderer();
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(0, 0, 0, 1);
                shapeRenderer.line(stage.getPosition(), stage.getUniqueStage().getPosition());
                shapeRenderer.end();
            }
        }

        //Dessin des icônes de stage
        spriteBatch.begin();
        for (Stage stage: listeStages) {
            if(stage.getSprite().getBoundingRectangle().contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY()) && actualStage.isNext(stage)){
                if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) || Gdx.input.isTouched() && !isClicking) {
                    isClicking = true;
                    if(stage instanceof ShopStage){
                        rogueLike.setScreen(new ShopMenu(this));
                    }else if(stage instanceof CombatStage){
                        rogueLike.setScreen(new CombatMenu(player, this));
                    }else if(stage instanceof CampStage){
                        rogueLike.setScreen(new CampMenu(this));
                    }else if(stage instanceof MiniBossStage){
                        //TODO faire combat plus dur
                        rogueLike.setScreen(new CombatMenu(player, this));
                    }
                    actualStage.setActual();
                    actualStage = stage;
                }

            }
            if(stage.equals(actualStage) && !stage.isActual()){
                stage.setActual();
            }
            stage.draw(spriteBatch);
        }

        //DEBUGGER
        if(keyboardListener.isDebug()) {
            Gdx.gl.glClearColor(0.20f, 0.20f, 0.20f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            Box2DDebugRenderer box2DDebugRenderer = new Box2DDebugRenderer();
            box2DDebugRenderer.render(gameWorld.getWorld(), camera.combined);
        }
        spriteBatch.end();

    }

    /**
     * Appelle les fonctions qui génère l'arbre
     */
    private void generateMap(){
        int x = Gdx.graphics.getWidth()/8;
        int y = Gdx.graphics.getHeight()/2;
        Stage stage, tampon;
        tampon = new CombatStage(gameWorld, new Vector2(x, y));
        actualStage = tampon;
        listeStages.add(tampon);
        buildMap(tampon);

        //Fin de la map par un boss
        stage = new BossStage(gameWorld, new Vector2(x + Gdx.graphics.getWidth()/1.5f , y));
        listeStages.add(stage);
        Stage s;
        for(int i = 0 ; i < listeStages.size() ; i++){
            s = listeStages.get(i);
            if(s.getRightStage() == null && s.getLeftStage() == null && s.getUniqueStage() == null){
                s.setUniqueStage(stage);
            }
        }
    }


    /**
     * Construit l'arbre à partir de la racine
     * @param stage racine
     */
    private void buildMap(Stage stage){
        Random r = new Random();
        int rand = r.nextInt(2);
        createRightStage(stage);
        createLeftStage(stage);
        switch(rand){
            //Première map
            case 0:
                tamponD = stage.getRightStage();
                tamponG = stage.getLeftStage();
                createRightStage(tamponD);
                createLeftStage(tamponD);

                tamponG.setRightStage(tamponD.getLeftStage());
                createLeftStage(tamponG);
                createUniqueStage(tamponG.getRightStage());
                createUniqueStage(tamponG.getRightStage().getUniqueStage());
                tamponG = tamponG.getRightStage().getUniqueStage().getUniqueStage();
                tamponD = tamponD.getRightStage();
                createUniqueStage(tamponD);
                createUniqueStage(tamponD.getUniqueStage());
                createUniqueStage(tamponD.getUniqueStage().getUniqueStage());
                tampon = tamponD.getUniqueStage().getUniqueStage();
                tamponD = tampon.getUniqueStage();
                createUniqueStage(stage.getLeftStage().getLeftStage());
                createUniqueStage(stage.getLeftStage().getLeftStage().getUniqueStage());
                tampon = stage.getLeftStage().getLeftStage().getUniqueStage().getUniqueStage();
                createRightStage(tampon);
                tamponG.setLeftStage(tampon.getRightStage());
                createRightStage(tampon.getRightStage());
                createLeftStage(tampon.getRightStage().getRightStage());
                createRightStage(tampon.getRightStage().getRightStage());
                createUniqueStage(tampon.getRightStage().getRightStage().getRightStage());
                tamponD.setLeftStage(tampon.getRightStage().getRightStage());
                createUniqueStage(tampon.getRightStage().getRightStage().getLeftStage());
                break;
            case 1:
                createUniqueStage(stage);
                createUniqueStage(stage.getUniqueStage());
                tamponD = stage.getRightStage();
                tamponG = stage.getLeftStage();
                createRightStage(tamponD);
                createLeftStage(tamponG);
                tampon = stage.getUniqueStage().getUniqueStage();

                createUniqueStage(tamponD.getRightStage());
                createUniqueStage(tamponG.getLeftStage());
                createUniqueStage(tampon);
                createUniqueStage(tampon.getUniqueStage());
                tampon = tampon.getUniqueStage().getUniqueStage();
                tamponD.getRightStage().getUniqueStage().setLeftStage(tampon);
                tamponG.getLeftStage().getUniqueStage().setRightStage(tampon);

                createUniqueStage(tampon);
                createLeftStage(tampon);
                createRightStage(tampon);

                createUniqueStage(tampon.getUniqueStage());
                createUniqueStage(tampon.getRightStage());
                createUniqueStage(tampon.getLeftStage());

                tamponD = tampon.getUniqueStage().getUniqueStage();
                createUniqueStage(tamponD);
                tampon.getRightStage().getUniqueStage().setLeftStage(tamponD.getUniqueStage());
                tampon.getLeftStage().getUniqueStage().setRightStage(tamponD.getUniqueStage());

                createUniqueStage(tamponD.getUniqueStage());
                break;

        }

    }

    private void createRightStage(Stage stage){
        //Fils droit
        Stage stageD = stageAleatoire(new Vector2(stage.getPosition().x + coeff, stage.getPosition().y - coeff));
        listeStages.add(stageD);
        stage.setRightStage(stageD);
    }

    private void createUniqueStage(Stage stage){
        //Fils droit
        Stage stageU = stageAleatoire(new Vector2(stage.getPosition().x + coeff+Gdx.graphics.getWidth()/80, stage.getPosition().y));
        listeStages.add(stageU);
        stage.setUniqueStage(stageU);
    }

    private void createLeftStage(Stage stage){
        //Fils gauche
        Stage stageG = stageAleatoire(new Vector2(stage.getPosition().x + coeff, stage.getPosition().y + coeff));
        listeStages.add(stageG);
        stage.setLeftStage(stageG);
    }

    /**
     * Crée un stage aléatoirement parmi les 4 existants
     * @param position du stage que l'on veut créer
     * @return le nouveau stage
     */
    private Stage stageAleatoire(Vector2 position){
        int max = 6;
        Random r = new Random();
        int rand = r.nextInt(max);
        Stage stage = null;
        switch(rand){
            case 0:
            case 4:
            case 5:
                stage = new CombatStage(gameWorld, position);
                break;
            case 1:
                stage = new ShopStage(gameWorld, position);
                break;
            case 2:
                stage = new CampStage(gameWorld, position);
                break;
            case 3:
                stage = new MiniBossStage(gameWorld, position);
                break;
        }
        return stage;
    }

    /**
     * Supprime le SpriteBatch
     */
    public void dispose(){
        spriteBatch.dispose();
    }

    public void setScreen() {
        rogueLike.setScreen(this);
        isClicking = false;
    }

    public RogueLike getRogueLike() {
        return rogueLike;
    }

    public Player getPlayer() {
        return player;
    }

    public InventoryMenu getInventoryMenu() {
        return inventoryMenu;
    }
}
