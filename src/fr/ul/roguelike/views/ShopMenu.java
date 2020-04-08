package fr.ul.roguelike.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import fr.ul.roguelike.model.Items.ButtonItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class ShopMenu extends ScreenAdapter {
    private Stage stage;
    private SpriteBatch spriteBatch;
    private ArrayList<String> items; //Bientot = à Item
    private ArrayList<String> shop; //Bientot = à Item
    private ArrayList<ButtonItem> buttons;
    private OrthographicCamera camera;
    private Texture playTexture;
    private ButtonItem playButton;
    private Iterator<ButtonItem> iterator;
    private int largeurEcran = Gdx.graphics.getWidth();
    private int hauteurEcran = Gdx.graphics.getHeight();

    private int tailleBouton = Gdx.graphics.getHeight()/3;


    public ShopMenu(){
        System.out.println("l " + largeurEcran + " h :" + hauteurEcran);
        items = new ArrayList<>();
        shop = new ArrayList<>();
        buttons = new ArrayList<>();
        stage = new Stage();
        Image fond = new Image(new Texture(Gdx.files.internal("shop.png")));
        fond.setWidth(largeurEcran);
        fond.setHeight(hauteurEcran);
        stage.addActor(fond);
        Gdx.input.setInputProcessor(stage);
        spriteBatch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false,largeurEcran,hauteurEcran);
        camera.update();

        items.add("healingPotion");
        items.add("strengthPotion");
        items.add("sword");
        items.add("bow");
        items.add("shield");
        items.add("spatule");

        //Charger l'image potion.jpg

        Random r = new Random();
        for(int i=0;i<6; i++) {
            int rand = r.nextInt(items.size());
            while (shop.contains(items.get(rand))) {
                rand = r.nextInt(items.size());
            }
            shop.add(items.get(rand));
            System.out.println(items.get(rand));
        }
        int index = 0;
        int y = 0;
        for(final String i : shop){
            if(index%3==0 && index!=0) {
                y++;
            }
            //
            if(i.equals("sword")|| i.equals("shield") || i.equals("healingPotion") || i.equals("strengthPotion") || i.equals("bow") || i.equals("spatule")){
                playTexture = new Texture(Gdx.files.internal(i +".png"));
            } else {
                playTexture = new Texture(Gdx.files.internal("badlogic.jpg"));
            }
            Drawable drawable = new TextureRegionDrawable(new TextureRegion(playTexture));
            drawable.setMinHeight(tailleBouton);
            drawable.setMinWidth(tailleBouton);
            playButton = new ButtonItem(drawable, i);
            buttons.add(playButton);
            playButton.setSize(tailleBouton,tailleBouton);
            playButton.setPosition((int)(largeurEcran/10 + (index%3)*largeurEcran/3.25), (int)(hauteurEcran/10 + y * hauteurEcran/2.25));
            final int finalIndex = index;
            playButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("Ja cliké sur " + finalIndex + " et ça a detruit " + i);
                    shop.remove(i);
                    for(ButtonItem b : buttons){
                        if(b.getNomItem().equals(i)){
                            b.setClicked(true);
                            b.addAction(Actions.removeActor());
                        }
                    }
                };
            });
            //
            index++;
        }


    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        for(ButtonItem b : buttons){
            if(!b.isClicked()) {
                stage.addActor(b);
            }
        }
        stage.draw();
        stage.act();
        spriteBatch.end();
        //Verification des boutons cliqués
        iterator = buttons.iterator();
        while (iterator.hasNext()){
            ButtonItem tampon = iterator.next();
            if(tampon.isClicked()){
                iterator.remove();
            }
        }
        camera.update();

    }


}
