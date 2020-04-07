package fr.ul.roguelike.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;
import java.util.Random;

public class ShopMenu extends ScreenAdapter {
    private Stage stage;
    private SpriteBatch spriteBatch;
    private ArrayList<String> items = new ArrayList<>();
    private ArrayList<String> shop = new ArrayList<>();
    private OrthographicCamera camera;
    private Texture playTexture;
    private ImageButton playButton;

    public ShopMenu(){
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        spriteBatch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false,1600,900);
        camera.update();

        items.add("potionSoin");
        items.add("potionForce");
        items.add("potionResistanceMagic");
        items.add("Epee");
        items.add("Arc");
        items.add("Bouclier");
        items.add("Spatule");

        Random r = new Random();
        for(int i=0;i<5; i++) {
            int rand = r.nextInt(items.size());
            while (shop.contains(items.get(rand))) {
                rand = r.nextInt(items.size());
            }
            shop.add(items.get(rand));
            System.out.println(items.get(rand));
        }
        int intex = 0;
        for(String i : shop){
            
            intex++;
        }
        playTexture = new Texture(Gdx.files.internal("badlogic.jpg"));
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(playTexture));
        playButton = new ImageButton(drawable);
        playButton.setSize(playTexture.getWidth(),playTexture.getHeight());
        playButton.setPosition(200,200);
        playButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Ja cliké bro");
            };
        });


        stage.addActor(playButton);

    }

    @Override
    public void render(float delta) {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        spriteBatch.draw(new Texture(Gdx.files.internal("shop.png")),0,0);
        spriteBatch.draw(playTexture,100,100);
        stage.draw();
        stage.act();
        spriteBatch.end();

    }


}
