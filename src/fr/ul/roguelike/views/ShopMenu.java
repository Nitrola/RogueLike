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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import fr.ul.roguelike.model.Items.ButtonItem;
import fr.ul.roguelike.model.Items.Item;
import fr.ul.roguelike.model.Items.ItemWeapon;
import fr.ul.roguelike.model.Popup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import static fr.ul.roguelike.RogueLike.screenWidth;
import static fr.ul.roguelike.RogueLike.screenHeight;

public class ShopMenu extends ScreenAdapter {
    private Stage stage;
    private SpriteBatch spriteBatchPolice;
    private ArrayList<Item> shop;
    private ArrayList<ButtonItem> buttons;
    private OrthographicCamera camera;
    private OrthographicCamera cameraPolice;
    private int money;
    private Popup popup;

    private BitmapFont police;

    private Sound sound, soundError;

    private Texture coin;
    private int taillePolice = screenHeight/23; //Taille de la police d'ecriture
    private int tailleBouton = screenWidth/3; //Taille des boutons


    /**
     * Représente le magasin d'achat d'objets
     * @param mapInterface la map où revenir après avoir acheté
     */
    ShopMenu(final MapInterface mapInterface){
        ArrayList<ItemWeapon> items = new ArrayList<>();

        items.add(new ItemWeapon("healingPotion",20, "Rends des PV"));
        items.add(new ItemWeapon("strengthPotion",20, "Donne de l'attaque pour un moment"));
        items.add(new ItemWeapon("sword",75, "Arme tranchante de corps à corps"));
        items.add(new ItemWeapon("bow",65, "Il est beau mon bow"));
        items.add(new ItemWeapon("shield",70, "Permet de se protéger"));
        items.add(new ItemWeapon("spatule",100000, "Trouvez en deux pour avoir un personnage en plus !"));
        items.add(new ItemWeapon("aiguisoir",100, "Bah... il aiguise quoi frère"));
        items.add(new ItemWeapon("axe",50, "Coupe du bois ou coupe tes ennemis, ton choix"));
        items.add(new ItemWeapon("book",20, "Qu'est-ce que tu veux foutre d'un bouquin en combat ?"));
        items.add(new ItemWeapon("cape",88, "Rends invi... heu non attenez c'est pas ça"));
        items.add(new ItemWeapon("boots",30, "Parce que marcher pied nu, ça n'est pas très pratique"));
        items.add(new ItemWeapon("parchemin",99, "Un parchemin sur un parchemin"));
        items.add(new ItemWeapon("pierre de protection",144, "Pierre vous protègera à l'avenir"));
        items.add(new ItemWeapon("pierre",45, "Une pierre. Si si, oui je sais, elle coûte cher"));
        items.add(new ItemWeapon("plastron",55, "Afin de garder sa virginité"));
        items.add(new ItemWeapon("talisman",48, "On sait pas trop s'il marche vraiment celui là"));

        shop = new ArrayList<>();
        buttons = new ArrayList<>();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        money = 999;
        popup = new Popup(this);

        FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal(("fonts/comicSansMS.ttf")));
        FreeTypeFontGenerator.FreeTypeFontParameter fontCarac = new FreeTypeFontGenerator.FreeTypeFontParameter();
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
        fond.setWidth(screenWidth);
        fond.setHeight(screenHeight);
        stage.addActor(fond);

        Texture exit = new Texture(Gdx.files.internal("images/exit.png"));
        Drawable drawableExit = new TextureRegionDrawable(new TextureRegion(exit));
        drawableExit.setMinHeight(screenHeight/9f);
        drawableExit.setMinWidth(screenWidth/8f);
        ImageButton exitBouton = new ImageButton(drawableExit);
        exitBouton.setPosition(0, screenHeight -drawableExit.getMinHeight());
        exitBouton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mapInterface.setScreen();
            }
        });
        stage.addActor(exitBouton);


        coin = new Texture(Gdx.files.internal("images/coin.png"));

        spriteBatchPolice = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);
        camera.update();

        cameraPolice = new OrthographicCamera();
        cameraPolice.setToOrtho(false, screenWidth, screenHeight);
        cameraPolice.update();


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
            i.setTexture(new Texture(Gdx.files.internal("images/" +i.getName() +".png")));
            Texture playTexture = i.getTexture();

            Drawable drawable = new TextureRegionDrawable(new TextureRegion(playTexture));
            drawable.setMinHeight(tailleBouton);
            drawable.setMinWidth(tailleBouton);
            ButtonItem playButton = new ButtonItem(drawable, i.getName());
            buttons.add(playButton);
            playButton.setSize(tailleBouton,tailleBouton);
            playButton.setPosition((int)(screenWidth /10 + (index%3)* screenWidth /3.25), (int)(screenHeight /10 + y * screenHeight /2.30));
            playButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                        //Afficher description
                        if(!popup.isPrint()){
                            popup.setItem(i);
                            popup.setPrint(true);
                        }
                        if(i instanceof ItemWeapon) {
                            mapInterface.getInventoryMenu().addItem((ItemWeapon) i);
                        }
                }
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
        police.draw(spriteBatchPolice,message, screenWidth -(screenWidth/10f), screenHeight -(screenHeight /20f),1,Align.right,true);
        spriteBatchPolice.draw(coin, screenWidth -(screenWidth/10f)-taillePolice/2f, screenHeight -(screenHeight/20f)-taillePolice,taillePolice,taillePolice);
        for(Item i :shop){
            for (ButtonItem button : buttons) {
                if (button.getNomItem().equals(i.getName())) {
                    int x = (int) button.getX() + ((int) (tailleBouton * 0.90));
                    int y = (int) button.getY() - (int) (tailleBouton * 0.05);
                    police.draw(spriteBatchPolice, "" + i.getPrice(), x, y, 1, Align.right, true);
                    spriteBatchPolice.draw(coin, x, y - taillePolice, taillePolice, taillePolice);
                }
            }
        }

        spriteBatchPolice.end();

        update();

        camera.update();

    }

    private void update(){
        //Quand le popup est affiché
        if(popup.isPrint()) {
            popup.draw();
            for(ButtonItem b : buttons){
                b.addAction(Actions.removeActor());
            }
        }

        //Verification des boutons cliqués
        Iterator<ButtonItem> iterator = buttons.iterator();
        while (iterator.hasNext()){
            ButtonItem tampon = iterator.next();
            if(tampon.isClicked()){
                iterator.remove();
            }
        }
    }

    //////////////////////////////////
    ///////Getters and Setters///////
    /////////////////////////////////


    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void playSound() {
        sound.play(1.0f);
    }

    public void playSoundError() {
        soundError.play(1.0f);
    }

    public ArrayList<ButtonItem> getButtons() {
        return buttons;
    }

    public ArrayList<Item> getShop() {
        return shop;
    }

    public Stage getStage() {
        return stage;
    }

    @Override
    public void dispose() {
        super.dispose();

    }
}
