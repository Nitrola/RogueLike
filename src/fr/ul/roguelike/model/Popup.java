package fr.ul.roguelike.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import fr.ul.roguelike.model.Items.ButtonItem;
import fr.ul.roguelike.model.Items.Item;
import fr.ul.roguelike.views.ShopMenu;
import static fr.ul.roguelike.RogueLike.screenWidth;
import static fr.ul.roguelike.RogueLike.screenHeight;

public class Popup {
    private ShopMenu shopMenu;
    private boolean isPrint;
    private Stage stage;
    private Item item;
    private Texture textureBackground;
    private Texture textureButtonYes;
    private Texture textureButtonNo;

    private ButtonItem buttonYes;
    private ButtonItem buttonNo;

    private Image image;
    private TextArea textArea;
    private BitmapFont police;
    private TextField.TextFieldStyle textField;


    /**
     * ATTENTION CONSTRUCTEUR UNIQUEMENT VALABLE POUR SHOPMENU
     */
    public Popup(final ShopMenu shopMenu){
        isPrint = false;
        stage = new Stage();
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(shopMenu.getStage());
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);

        this.shopMenu = shopMenu;
        textureBackground = new Texture(Gdx.files.internal("images/parchemin_popup.png"));

        image = new Image(textureBackground);
        stage.addActor(image);
        image.setHeight(screenHeight /1.25f);
        image.setWidth(screenWidth /2.5f);
        image.setPosition(screenWidth /2-image.getWidth()/2, screenHeight /10);

        textureButtonYes = new Texture(Gdx.files.internal("images/sceau_yes.png"));
        textureButtonNo = new Texture(Gdx.files.internal("images/sceau_no.png"));
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(textureButtonYes));
        drawable.setMinHeight(image.getHeight()/4);
        drawable.setMinWidth(image.getWidth()/4);
        buttonYes = new ButtonItem(drawable, "popup");
        buttonYes.setPosition(image.getX(),image.getY());

        drawable = new TextureRegionDrawable(new TextureRegion(textureButtonNo));
        drawable.setMinHeight(image.getHeight()/4);
        drawable.setMinWidth(image.getWidth()/4);
        buttonNo = new ButtonItem(drawable, "popup");
        buttonNo.setPosition(image.getX()+image.getWidth()-buttonNo.getWidth(),image.getY());


        buttonYes.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(shopMenu.getMoney() - item.getPrice() >=0) {
                    shopMenu.setMoney(shopMenu.getMoney() - item.getPrice());
                    shopMenu.getShop().remove(item);
                    for (ButtonItem b : shopMenu.getButtons()) {
                        if (b.getNomItem().equals(item.getName())) {
                            b.addAction(Actions.removeActor());
                            b.setClicked(true);
                            //ajouter l'item à l'inventaire
                            //TODO Ajouter dans la liste des items
                            //shopMenu.getMapInterface().getPlayer().getItems().add(item);
                        }
                    }
                    shopMenu.playSound();
                }else{
                    shopMenu.playSoundError();
                }

                //On supprime le popup
                dispose();
            }
        });

        buttonNo.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
            }
        });

        //Police
        FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal(("fonts/calliPixel.ttf")));
        FreeTypeFontGenerator.FreeTypeFontParameter fontCarac = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontCarac.size = 50;
        fontCarac.color = new Color(1,0.9607f,0.4274f,0.75f);
        fontCarac.borderColor = Color.BLACK;
        fontCarac.borderWidth = 0.5f;
        fontCarac.shadowColor = new Color(0,0,0,0.50f);
        fontCarac.shadowOffsetX = 2;
        fontCarac.shadowOffsetY = 2;

        police = fontGen.generateFont(fontCarac);
        fontGen.dispose();

        textField = new TextField.TextFieldStyle(police, Color.BLACK, null, null, null);
        textArea = new TextArea("", textField);
        textArea.setDisabled(true);
    }

    public void draw(){
        stage.addActor(image);
        stage.addActor(buttonYes);
        stage.addActor(buttonNo);
        stage.addActor(textArea);

        stage.act();
        stage.draw();
    }

    public void dispose(){
        buttonYes.addAction(Actions.removeActor());
        buttonNo.addAction(Actions.removeActor());
        image.addAction(Actions.removeActor());
        textArea.addAction(Actions.removeActor());
        setPrint(false);
    }

    public boolean isPrint() {
        return isPrint;
    }

    public void setPrint(boolean print) {
        isPrint = print;
    }

    public void setItem(Item item) {
        this.item = item;

        textArea.setText(item.getDescription());
        textArea.setX(image.getX()+image.getWidth()/13);
        textArea.setY(image.getY()+buttonYes.getHeight());
        textArea.setWidth(image.getWidth()-image.getWidth()/10);
        textArea.setHeight(image.getHeight()-buttonYes.getHeight()-image.getHeight()/6);
        stage.addActor(textArea);
    }
}
