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
    protected Body body;
    protected FixtureDef fixtureDef;
    protected BodyDef bodyDef;
    protected GameWorld gameWorld;
    protected Vector2 position;
    protected int rayon = 30;
    protected boolean passed;
    protected boolean actual;
    protected int coeff = 2;
    protected Stage rightStage;
    protected Stage leftStage;


    public Stage(GameWorld gameWorld, Vector2 pos){
        this.gameWorld = gameWorld;
        position = pos;
        passed = false;
        actual = false;
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

    public void setRightStage(Stage nextStage, OrthographicCamera camera) {
        this.rightStage = nextStage;
    }

    public Stage getRightStage() {
        return rightStage;
    }

    public void setLeftStage(Stage nextStage, OrthographicCamera camera) {
        this.leftStage = nextStage;
    }

    public Stage getLeftStage() {
        return leftStage;
    }
}
