package fr.ul.roguelike.views;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import fr.ul.roguelike.RogueLike;
import fr.ul.roguelike.model.Player;
import fr.ul.roguelike.model.stages.*;

import java.util.ArrayList;
import java.util.Random;
import static fr.ul.roguelike.RogueLike.screenWidth;
import static fr.ul.roguelike.RogueLike.screenHeight;


public class MapInterface extends ScreenAdapter implements InputProcessor {
    private RogueLike rogueLike;
    private InventoryMenu inventoryMenu;
    private Player player;

    private SpriteBatch spriteBatch;
    private Texture map;
    private ArrayList<Stage> listeStages;
    private OrthographicCamera camera = new OrthographicCamera();
    private ShapeRenderer shapeRenderer;
    private Stage actualStage;

    private com.badlogic.gdx.scenes.scene2d.Stage stage;
    private boolean isClicking;
    private int coeff = screenWidth/16;
    private ArrayList<Integer> mapGenerated;

    /**
     * Représente la vue de la map, propose le choix du prochain stage
     * @param rogueLike le modèle
     * @param p le joueur
     */
    MapInterface(final RogueLike rogueLike, Player p){
        this.rogueLike = rogueLike;
        player = p;
        inventoryMenu = new InventoryMenu(this);
        player.setInventaryMenu(inventoryMenu);
        stage = new com.badlogic.gdx.scenes.scene2d.Stage();

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(inputMultiplexer);
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        isClicking = false;
        map = new Texture(Gdx.files.internal("images/map.png"));
        camera.setToOrtho(false, screenWidth, screenHeight);
        camera.update();

        listeStages = new ArrayList<>();
        mapGenerated = new ArrayList<>();
        for(int i=0; i<3; i++){
            mapGenerated.add(i);
        }
        generateMap();

        initStage();
        actualStage.setActual();
        actualStage.setPassed();
    }

    /**
     * Initialise les boutons
     */
    private void initStage() {
        //Bouton Exit
        Texture exit = new Texture(Gdx.files.internal("images/exit.png"));
        Drawable drawableExit = new TextureRegionDrawable(new TextureRegion(exit));
        drawableExit.setMinHeight(screenHeight/9f);
        drawableExit.setMinWidth(screenWidth/8f);
        ImageButton exitBouton = new ImageButton(drawableExit);
        exitBouton.setPosition(0,screenHeight-drawableExit.getMinHeight());
        exitBouton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainMenu mainMenu = new MainMenu(rogueLike);
                rogueLike.setScreen(mainMenu);
            }
        });
        stage.addActor(exitBouton);

        //Bouton Inventaire
        final Texture inventory = new Texture(Gdx.files.internal("images/inventory/bag.png"));
        Drawable drawableInventory = new TextureRegionDrawable(new TextureRegion(inventory));
        drawableExit.setMinHeight(screenHeight/9f);
        drawableExit.setMinWidth(screenWidth/8f);
        ImageButton inventoryButton = new ImageButton(drawableInventory);
        inventoryButton.setWidth(screenWidth/10f);
        inventoryButton.setHeight(screenWidth/10f);
        inventoryButton.setPosition(screenWidth - inventoryButton.getWidth(),screenHeight - inventoryButton.getHeight());
        inventoryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.getPlayerCharacter().setInInventory(true);
                inventoryMenu.update();
                rogueLike.setScreen(inventoryMenu);
            }
        });
        stage.addActor(inventoryButton);
    }


    @Override
    public void render (float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        spriteBatch.setProjectionMatrix(camera.combined);

        spriteBatch.begin();
        spriteBatch.draw(map, 0, 0, screenWidth, screenHeight);
        spriteBatch.end();
        drawLines();
        drawStages();
        stage.draw();
        stage.act();
    }

    /**
     * Dessin des traits qui relient les stages
     */
    private void drawLines(){
        for(Stage stage : listeStages){
            if(stage.getRightStage() != null) {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(0, 0, 0, 1);
                shapeRenderer.line(stage.getPosition(), stage.getRightStage().getPosition());
                shapeRenderer.end();
            }
            if(stage.getLeftStage() != null) {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(0, 0, 0, 1);
                shapeRenderer.line(stage.getPosition(), stage.getLeftStage().getPosition());
                shapeRenderer.end();
            }
            if(stage.getUniqueStage() != null) {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(0, 0, 0, 1);
                shapeRenderer.line(stage.getPosition(), stage.getUniqueStage().getPosition());
                shapeRenderer.end();
            }
        }
    }

    /**
     * Dessin des icônes de stage et change le stage actuel
     */
    private void drawStages(){
        spriteBatch.begin();
        for (Stage stage: listeStages) {
            stage.draw(spriteBatch);
        }
        spriteBatch.end();
    }

    /**
     * Appelle les fonctions qui génère l'arbre
     */
    void generateMap(){
        listeStages.clear();
        int x = screenWidth / 8;
        int y = screenHeight / 2;
        Stage stage, tampon;
        tampon = new CombatStage(new Vector2(x, y));
        actualStage = tampon;
        listeStages.add(tampon);
        buildMap(tampon);

        //Fin de la map par un boss
        stage = new BossStage(new Vector2(x + screenWidth / 1.5f, y));
        listeStages.add(stage);
        Stage s;
        for (Stage listeStage : listeStages) {
            s = listeStage;
            if (s.getRightStage() == null && s.getLeftStage() == null && s.getUniqueStage() == null && !(s instanceof BossStage)) {
                s.setUniqueStage(stage);
            }
        }
    }


    /**
     * Construit l'arbre à partir de la racine
     * @param stage racine
     */
    private void buildMap(Stage stage){
        Stage tamponD, tamponG, tampon;
        Random r = new Random();
        int rand = r.nextInt(mapGenerated.size());
        createRightStage(stage);
        createLeftStage(stage);
        switch (mapGenerated.get(rand)) {
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
            case 2:
                createUniqueStage(stage);
                createRightStage(stage.getUniqueStage());
                createUniqueStage(stage.getUniqueStage());
                createLeftStage(stage.getUniqueStage());
                createUniqueStage(stage.getUniqueStage().getRightStage());
                createUniqueStage(stage.getUniqueStage().getUniqueStage());
                createUniqueStage(stage.getUniqueStage().getLeftStage());
                createUniqueStage(stage.getUniqueStage().getRightStage().getUniqueStage());
                createUniqueStage(stage.getUniqueStage().getUniqueStage().getUniqueStage());
                createUniqueStage(stage.getUniqueStage().getLeftStage().getUniqueStage());
                createUniqueStage(stage.getUniqueStage().getUniqueStage().getUniqueStage().getUniqueStage());
                tampon = stage.getUniqueStage().getUniqueStage().getUniqueStage().getUniqueStage().getUniqueStage();
                createLeftStage(stage.getLeftStage());
                createRightStage(stage.getRightStage());
                createLeftStage(stage.getLeftStage().getLeftStage());
                stage.getRightStage().getRightStage().setLeftStage(stage.getUniqueStage().getRightStage().getUniqueStage());
                createRightStage(stage.getRightStage().getRightStage());
                createUniqueStage(stage.getLeftStage().getLeftStage().getLeftStage());
                createUniqueStage(stage.getRightStage().getRightStage().getRightStage());
                stage.getRightStage().getRightStage().getRightStage().getUniqueStage().setUniqueStage(tampon);
                stage.getLeftStage().getLeftStage().getLeftStage().getUniqueStage().setUniqueStage(tampon);
                stage.getRightStage().getRightStage().getRightStage().getUniqueStage().setUniqueStage(tampon);
                stage.getUniqueStage().getLeftStage().getUniqueStage().getUniqueStage().setUniqueStage(tampon);
                stage.getUniqueStage().getRightStage().getUniqueStage().getUniqueStage().setUniqueStage(tampon);
                createUniqueStage(tampon);
                createUniqueStage(tampon.getUniqueStage());
                stage.getLeftStage().getLeftStage().getLeftStage().setRightStage(stage.getUniqueStage().getLeftStage().getUniqueStage().getUniqueStage());
                stage.getUniqueStage().getLeftStage().getUniqueStage().setRightStage(stage.getUniqueStage().getUniqueStage().getUniqueStage().getUniqueStage());
                break;

        }
        mapGenerated.remove(rand);
    }

    /**
     * Créée un stage aléatoire à droite du stage
     */
    private void createRightStage(Stage stage){
        //Fils droit
        Stage stageD = stageAleatoire(new Vector2(stage.getPosition().x + coeff, stage.getPosition().y - coeff));
        listeStages.add(stageD);
        stage.setRightStage(stageD);
    }

    /**
     * Créée un stage aléatoire en face du stage
     */
    private void createUniqueStage(Stage stage){
        //Fils droit
        Stage stageU = stageAleatoire(new Vector2(stage.getPosition().x + coeff + screenWidth/80f, stage.getPosition().y));
        listeStages.add(stageU);
        stage.setUniqueStage(stageU);
    }

    /**
     * Créée un stage aléatoire à gauche du stage
     */
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
                stage = new CombatStage(position);
                break;
            case 1:
                stage = new ShopStage(position);
                break;
            case 2:
                stage = new CampStage(position);
                break;
            case 3:
                stage = new MiniBossStage(position);
                break;
        }
        return stage;
    }

    /**
     * Supprime le SpriteBatch
     */
    public void dispose(){
        spriteBatch.dispose();
        shapeRenderer.dispose();
        map.dispose();
    }

    /**
     * Change l'écran de jeu pour mettre la map
     */
    void setScreen() {
        MainMenu.music.stop();
        rogueLike.setScreen(this);
        isClicking = false;
        Texture exit = new Texture(Gdx.files.internal("images/exit.png"));
        Drawable drawableExit = new TextureRegionDrawable(new TextureRegion(exit));
        drawableExit.setMinHeight(screenHeight/9f);
        drawableExit.setMinWidth(screenWidth/8f);
        ImageButton exitBouton = new ImageButton(drawableExit);
        exitBouton.setPosition(0,screenHeight - drawableExit.getMinHeight());
        exitBouton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainMenu mainMenu = new MainMenu(rogueLike);
                rogueLike.setScreen(mainMenu);
            }
        });
        stage.addActor(exitBouton);
        setInputProcessor();
    }

    //////////////////////////////////
    ///////Getters and Setters///////
    /////////////////////////////////

    RogueLike getRogueLike() {
        return rogueLike;
    }

    public Player getPlayer() {
        return player;
    }

    public InventoryMenu getInventoryMenu() {
        return inventoryMenu;
    }

    public boolean isBoss(){
        return actualStage instanceof BossStage;
    }

    boolean isEnded(){
        boolean res = false;
        if(mapGenerated.isEmpty()){
            res = true;
        }
        return res;
    }

    void setInputProcessor() {
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        for (Stage stage: listeStages) {
            if(stage.getSprite().getBoundingRectangle().contains(Gdx.input.getX(), screenHeight - Gdx.input.getY()) && actualStage.isNext(stage)){
                if(!isClicking) {
                    isClicking = true;
                    if(stage instanceof ShopStage){
                        rogueLike.setScreen(new ShopMenu(this));
                    }else if(stage instanceof CombatStage){
                        rogueLike.setScreen(new CombatMenu(player, this, stage));
                    }else if(stage instanceof CampStage){
                        rogueLike.setScreen(new CampMenu(this));
                    }else if(stage instanceof MiniBossStage){
                        rogueLike.setScreen(new CombatMenu(player, this, stage));
                    }else if(stage instanceof BossStage){
                        rogueLike.setScreen(new CombatMenu(player, this, stage));
                    }
                    actualStage.setActual();
                    player.increaseCptStage();
                    actualStage = stage;
                    actualStage.setPassed();
                }
            }
            if(stage.equals(actualStage) && !stage.isActual()){
                stage.setActual();
            }
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
