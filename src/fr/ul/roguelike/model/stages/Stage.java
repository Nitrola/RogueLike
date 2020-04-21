package fr.ul.roguelike.model.stages;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import fr.ul.roguelike.model.GameWorld;

public abstract class Stage {
    protected GameWorld gameWorld;
    protected Sprite sprite;
    protected Texture texture;
    protected Texture texturePassed;
    protected Vector2 position;
    protected int rayon = 30;
    protected boolean passed;
    protected boolean actual;
    protected int coeff = 2;
    protected Stage rightStage;
    protected Stage leftStage;
    protected Stage uniqueStage;


    public Stage(GameWorld gameWorld, Vector2 pos){
        this.gameWorld = gameWorld;
        position = pos;
        sprite = new Sprite();
        passed = false;
        actual = false;
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

    public void setRightStage(Stage nextStage) {
        this.rightStage = nextStage;
    }

    public Stage getRightStage() {
        return rightStage;
    }

    public void setLeftStage(Stage nextStage) {
        this.leftStage = nextStage;
    }

    public Stage getLeftStage() {
        return leftStage;
    }

    public Stage getUniqueStage() {
        return uniqueStage;
    }

    public void setUniqueStage(Stage uniqueStage) {
        this.uniqueStage = uniqueStage;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setActual() {
        if(!actual) {
            this.actual = true;
            sprite.setSize((float) (sprite.getWidth() * 1.5), (float) (sprite.getHeight() * 1.5));
            rayon = (int) (rayon * 1.5);
            sprite.setPosition(this.getPosition().x - getRayon(), this.getPosition().y - getRayon());
        }else{
            this.actual = false;
            sprite.setSize((float) (sprite.getWidth() / 1.5), (float) (sprite.getHeight() / 1.5));
            rayon = (int) (rayon / 1.5);
            sprite.setPosition(this.getPosition().x - getRayon(), this.getPosition().y - getRayon());
        }
    }

    public boolean isActual() {
        return actual;
    }

    public boolean isNext(Stage stage){
        boolean res = false;
        if(stage.equals(uniqueStage) || stage.equals(rightStage) || stage.equals(leftStage)){
            res = true;
        }
        return res;
    }
}
