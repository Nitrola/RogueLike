package fr.ul.roguelike.model.stages;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import fr.ul.roguelike.model.GameWorld;

public abstract class Stage {
    private Body body;
    private FixtureDef fixtureDef;
    private BodyDef bodyDef;
    private GameWorld gameWorld;
    private Vector2 position;
    private int rayon = 30;
    protected boolean passed;
    protected Stage nextStage;

    public Stage(GameWorld gameWorld, Vector2 pos){
        this.gameWorld = gameWorld;
        position = pos;
        passed = false;
        bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = gameWorld.getWorld().createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(rayon);

        fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.isSensor = true;

        body.createFixture(fixtureDef);

        circle.dispose();
    }

    public abstract void draw(SpriteBatch spriteBatch);

    public Vector2 getPosition() {
        return position;
    }

    public int getRayon() {
        return rayon;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public void setNextStage(Stage nextStage, OrthographicCamera camera) {
        this.nextStage = nextStage;
    }

    public Stage getNextStage() {
        return nextStage;
    }
}
