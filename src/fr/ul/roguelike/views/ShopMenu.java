package fr.ul.roguelike.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import fr.ul.roguelike.model.Items.ButtonItem;
import fr.ul.roguelike.model.Items.Item;
import fr.ul.roguelike.model.Items.ItemWeapon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class ShopMenu extends ScreenAdapter {
    private Stage stage;
    private SpriteBatch spriteBatchPolice;
    private ArrayList<Item> items;
    private ArrayList<Item> shop;
    private ArrayList<ButtonItem> buttons;
    private OrthographicCamera camera;
    private OrthographicCamera cameraPolice;
    private Texture playTexture;
    private ButtonItem playButton;
    private Iterator<ButtonItem> iterator;
    private int money;

    private FreeTypeFontGenerator fontGen;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontCarac;
    private BitmapFont police;

    private Sound sound, soundError;

    private Texture coin;

    private int largeurEcran = Gdx.graphics.getWidth(); //Largeur de l'ecran
    private int hauteurEcran = Gdx.graphics.getHeight(); //Hauteur de l'ecran

    private int taillePolice = Gdx.graphics.getHeight()/23; //Taille de la police d'ecriture

    private int tailleBouton = Gdx.graphics.getHeight()/3; //Taille des boutons


    public ShopMenu(){
        items = new ArrayList<>();
        shop = new ArrayList<>();
        buttons = new ArrayList<>();
        stage = new Stage();
        money = 999;

        fontGen = new FreeTypeFontGenerator(Gdx.files.internal(("fonts/comicSansMS.ttf")));
        fontCarac = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontCarac.size = taillePolice;
        fontCarac.color = new Color(1,0.9607f,0.4274f,0.75f);
        fontCarac.borderColor = Color.BLACK;
        fontCarac.borderWidth = 0.5f;
        fontCarac.shadowColor = new Color(0,0,0,0.50f);
        fontCarac.shadowOffsetX = 2;
        fontCarac.shadowOffsetY = 2;

        police = new BitmapFont();
        police = fontGen.generateFont(fontCarac);
        fontGen.dispose();

        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/coins.mp3"));
        soundError = Gdx.audio.newSound(Gdx.files.internal("sounds/error.mp3"));

        Image fond = new Image(new Texture(Gdx.files.internal("images/shop2.png")));
        fond.setWidth(largeurEcran);
        fond.setHeight(hauteurEcran);
        stage.addActor(fond);
        Gdx.input.setInputProcessor(stage);

        Texture exit = new Texture(Gdx.files.internal("images/exit.png"));
        Drawable drawableExit = new TextureRegionDrawable(new TextureRegion(exit));
        drawableExit.setMinHeight(hauteurEcran/9);
        drawableExit.setMinWidth(largeurEcran/8);
        ImageButton exitBouton = new ImageButton(drawableExit);
        exitBouton.setPosition(0,hauteurEcran-drawableExit.getMinHeight());
        exitBouton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("quitter");
            };
        });
        stage.addActor(exitBouton);
        //exitBouton.addListener(new ClickListener())

        coin = new Texture(Gdx.files.internal("images/coin.png"));

        spriteBatchPolice = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false,largeurEcran,hauteurEcran);
        camera.update();

        cameraPolice = new OrthographicCamera();
        cameraPolice.setToOrtho(false,largeurEcran,hauteurEcran);
        cameraPolice.update();

        items.add(new ItemWeapon("healingPotion",20));
        items.add(new ItemWeapon("strengthPotion",20));
        items.add(new ItemWeapon("sword",75));
        items.add(new ItemWeapon("bow",65));
        items.add(new ItemWeapon("shield",70));
        items.add(new ItemWeapon("spatule",100000));
        items.add(new ItemWeapon("aiguisoir",100));
        items.add(new ItemWeapon("axe",50));
        items.add(new ItemWeapon("book",20));
        items.add(new ItemWeapon("cape",88));
        items.add(new ItemWeapon("boots",30));
        items.add(new ItemWeapon("parchemin",99));
        items.add(new ItemWeapon("pierre de protection",144));
        items.add(new ItemWeapon("pierre",45));
        items.add(new ItemWeapon("plastron",55));
        items.add(new ItemWeapon("talisman",48));
        Random r = new Random();
        for(int i=0;i<6; i++) {
            int rand = r.nextInt(items.size());
            while (shop.contains(items.get(rand))) {
                rand = r.nextInt(items.size());
            }
            shop.add(items.get(rand));
        }
        int index = 0;
        int y = 0;
        for(final Item i : shop){
            if(index%3==0 && index!=0) {
                y++;
            }
            playTexture = new Texture(Gdx.files.internal("images/" +i.getName() +".png"));

            Drawable drawable = new TextureRegionDrawable(new TextureRegion(playTexture));
            drawable.setMinHeight(tailleBouton);
            drawable.setMinWidth(tailleBouton);
            playButton = new ButtonItem(drawable, i.getName());
            buttons.add(playButton);
            playButton.setSize(tailleBouton,tailleBouton);
            playButton.setPosition((int)(largeurEcran/10 + (index%3)*largeurEcran/3.25), (int)(hauteurEcran/10 + y * hauteurEcran/2.30));
            playButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(money - i.getPrice() >=0) {
                        shop.remove(i);
                        for (ButtonItem b : buttons) {
                            if (b.getNomItem().equals(i.getName())) {
                                    b.setClicked(true);
                                    money = money - i.getPrice();
                                    b.addAction(Actions.removeActor());
                                    sound.play(1.0f);
                            }
                        }
                    } else {
                        soundError.play(1.0f);
                    }
                };
            });
            index++;
        }


    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        spriteBatchPolice.setProjectionMatrix(cameraPolice.combined);

        for(ButtonItem b : buttons){
            if(!b.isClicked()) {
                stage.addActor(b);
            }
        }
        String message = " " + money + " ";
        stage.draw();
        stage.act();
        spriteBatchPolice.begin();
        police.draw(spriteBatchPolice,message,largeurEcran-(largeurEcran/10),hauteurEcran-(hauteurEcran/20),1,Align.right,true);
        spriteBatchPolice.draw(coin,largeurEcran-(largeurEcran/10)-taillePolice/2,hauteurEcran-(hauteurEcran/20)-taillePolice,taillePolice,taillePolice);
        for(Item i :shop){
            for(int i2 = 0; i2 < buttons.size(); i2++){
                if(buttons.get(i2).getNomItem().equals(i.getName())){
                    int x = (int)buttons.get(i2).getX()+((int)(tailleBouton * 0.90));
                    int y = (int)buttons.get(i2).getY()-(int)(tailleBouton*0.05);
                    police.draw(spriteBatchPolice,"" + i.getPrice(),x,y,1,Align.right,true );
                    spriteBatchPolice.draw(coin,x,y-taillePolice,taillePolice,taillePolice);
                }
            }
        }
        spriteBatchPolice.end();

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
