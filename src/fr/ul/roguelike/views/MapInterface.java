package fr.ul.roguelike.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import fr.ul.roguelike.controllers.KeyboardListener;
import fr.ul.roguelike.model.GameWorld;
import fr.ul.roguelike.model.stages.*;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.Random;

public class MapInterface extends ScreenAdapter {
    private SpriteBatch spriteBatch;
    private GameWorld gameWorld;
    private ArrayList<Stage> listeStages;
    private KeyboardListener keyboardListener;
    private OrthographicCamera camera = new OrthographicCamera();
    private ShapeRenderer shapeRenderer;
    static int i = 0;
    static int j = -1;
    static boolean bool = false;
    static Stage stageD = null, stageG;

    public MapInterface(){
        spriteBatch = new SpriteBatch();
        gameWorld = new GameWorld();
        keyboardListener = new KeyboardListener();
        Gdx.input.setInputProcessor(keyboardListener);
        camera.setToOrtho(false, 1600,900);
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
        spriteBatch.draw(new Texture(Gdx.files.internal("images/map.png")), -200, -100);
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
        }

        //Dessin des icônes de stage
        spriteBatch.begin();
        for (Stage stage: listeStages) {
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

    private void generateMap(){
        int x = 200;
        int y = 450;
        Stage stage, tampon;
        tampon = new CombatStage(gameWorld, new Vector2(x, y));
        listeStages.add(tampon);
        buildMap(tampon);
        stage = new BossStage(gameWorld, new Vector2(x + 1050 , y));
        listeStages.add(stage);
        stageD.setRightStage(stage, camera);
        stageG.setRightStage(stage, camera);

        Stage s;
        for(int i = 0 ; i < listeStages.size() ; i++){
            s = listeStages.get(i);
            if(s.getRightStage() == null && s.getLeftStage() == null){
                if(i+2 < listeStages.size()) {
                   s.setRightStage(listeStages.get(i + 2), camera);
                }
            }
        }
    }

    private void buildMap(Stage stage){
        //Fils droit
        stageD = stageAleatoire(new Vector2(stage.getPosition().x + 80, stage.getPosition().y - 80));
        listeStages.add(stageD);
        stage.setRightStage(stageD, camera);


        //Fils gauche
        stageG = stageAleatoire(new Vector2(stage.getPosition().x  + 80, stage.getPosition().y + 80));
        listeStages.add(stageG);
        stage.setLeftStage(stageG, camera);
        if(j == -1) {
            j++;
            buildMap(stageD);
        }
        if(j < 10) {
            j++;
            if(j%2 == 0) {
                buildMap(stageD);
            }else{
                buildMap(stageG);
            }
        }
    }

    private Stage stageAleatoire(Vector2 position){
        int max = 6;
        int min = 0;
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
}
