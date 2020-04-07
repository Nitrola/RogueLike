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
import fr.ul.roguelike.model.stages.CampStage;
import fr.ul.roguelike.model.stages.CombatStage;
import fr.ul.roguelike.model.stages.ShopStage;
import fr.ul.roguelike.model.stages.Stage;

import java.util.ArrayList;
import java.util.EventListener;

public class MapInterface extends ScreenAdapter {
    private SpriteBatch spriteBatch;
    private GameWorld gameWorld;
    private ArrayList<Stage> listeStages;
    private KeyboardListener keyboardListener;
    private OrthographicCamera camera = new OrthographicCamera();

    public MapInterface(){
        spriteBatch = new SpriteBatch();
        gameWorld = new GameWorld();
        keyboardListener = new KeyboardListener();
        Gdx.input.setInputProcessor(keyboardListener);
        camera.setToOrtho(false, 1600,900);
        camera.update();

        listeStages = new ArrayList<Stage>();
        CampStage campStage = new CampStage(gameWorld, new Vector2(1000, 600));
        CombatStage combatStage = new CombatStage(gameWorld, new Vector2(700, 500));
        ShopStage shopStage = new ShopStage(gameWorld, new Vector2(600, 300));
        listeStages.add(campStage);
        listeStages.add(combatStage);
        listeStages.add(shopStage);
        campStage.setNextStage(combatStage, camera);
        combatStage.setNextStage(shopStage, camera);
    }

    /**
     * Affiche la map
     */
    @Override
    public void render (float delta) {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        spriteBatch.draw(new Texture(Gdx.files.internal("map.png")), -200, -100);
        spriteBatch.end();

        for(Stage stage : listeStages){
            if(stage.getNextStage() != null) {
                ShapeRenderer shapeRenderer = new ShapeRenderer();
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(0, 0, 0, 1);
                shapeRenderer.line(stage.getPosition(), stage.getNextStage().getPosition());
                shapeRenderer.end();
            }
        }

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

    /**
     * Supprime le SpriteBatch
     */
    public void dispose(){
        spriteBatch.dispose();
    }
}
