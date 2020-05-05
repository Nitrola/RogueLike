package fr.ul.roguelike.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import fr.ul.roguelike.model.Items.ButtonItem;
import fr.ul.roguelike.model.Items.Equipment.Archer.ArcherPlate;
import fr.ul.roguelike.model.Items.Equipment.Archer.BaseBow;
import fr.ul.roguelike.model.Items.Equipment.Equipement;
import fr.ul.roguelike.model.Items.Equipment.Warrior.DemonSword;
import fr.ul.roguelike.model.Items.ItemWeapon;
import fr.ul.roguelike.model.Player;

import java.util.ArrayList;

import static fr.ul.roguelike.RogueLike.screenHeight;
import static fr.ul.roguelike.RogueLike.screenWidth;


public class InventoryMenu extends ScreenAdapter {

    private Player p;
    private MapInterface mapInterface;
    private Stage stage;
    private Texture background;
    private Sprite headSlot;
    private Sprite leftWepSlot;
    private Sprite rightWepSlot;
    private Sprite armorSlot;
    private ArrayList<Button> itemsButton;
    private ArrayList<Equipement> equipements;
    private ArrayList<ItemWeapon> items;
    private boolean camp;
    private Table tableEquipment, tableItems;

    /**
     * Représente l'inventaire du joueur contenant ses équipements et objets
     */
    InventoryMenu(MapInterface mapInterface) {

        this.p = mapInterface.getPlayer();
        items = new ArrayList<>();
        this.mapInterface = mapInterface;
        headSlot = new Sprite(new Texture("images/inventory/headSlot.png")) ;
        leftWepSlot = new Sprite(new Texture("images/inventory/leftWeaponSlot.png")) ;
        rightWepSlot = new Sprite(new Texture("images/inventory/rightWeaponSlot.png")) ;
        armorSlot = new Sprite(new Texture("images/inventory/armorSlot.png")) ;

        armorSlot.setPosition(screenWidth*0.17f,screenHeight*0.17f);
        rightWepSlot.setPosition(screenWidth*0.3f,screenHeight*0.4f);
        leftWepSlot.setPosition(screenWidth*0.04f,screenHeight*0.4f);
        headSlot.setPosition(screenWidth*0.17f,screenHeight*0.6f);

        itemsButton = new ArrayList<>();
        //inventorySlot.scale(2f);
        tableEquipment = new Table();
        tableItems = new Table();

        equipements = new ArrayList<>();
        equipements.add(new DemonSword());
        equipements.add(new ArcherPlate());
        equipements.add(new BaseBow());
        equipements.add(new BaseBow());
        equipements.add(new BaseBow());
        equipements.add(new DemonSword());

        camp = false;


        //table.setFillParent(true);
        tableEquipment.align(Align.top);
        for(final Equipement e : equipements){

            ButtonItem ib = new ButtonItem(new TextureRegionDrawable(e.getTexture()));
            ib.setSize(screenWidth/80f, screenHeight/45f);
            ib.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(camp){
                        upgrade(e);
                    }
                }
            });


            tableEquipment.add(ib).width(ib.getWidth()*3f).height(ib.getHeight()*3f);
            tableEquipment.add(new Label(
                    "Att: " + e.getPhysicalDamage() + " Mag: " + e.getMagicDamage() + " Armor: " + e.getArmor() + " MagRes: " + e.getMagicResist(),
                    new Label.LabelStyle(new BitmapFont(), Color.WHITE)));
            tableEquipment.row();
            itemsButton.add(ib);
        }
        tableEquipment.align(Align.left);

        tableItems.align(Align.top);
        for(final ItemWeapon e : items){

            ButtonItem ib = new ButtonItem(new TextureRegionDrawable(e.getTexture()));
            ib.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //Afficher description
                }
            });


            tableItems.add(ib).width(ib.getWidth()*3f).height(ib.getHeight()*3f);
            tableItems.add(new Label(e.getDescription(),
                    new Label.LabelStyle(new BitmapFont(), Color.WHITE)));
            tableItems.row();
            itemsButton.add(ib);
        }
        tableItems.align(Align.left);

        background = new Texture("images/inventory/background.png");


        ScrollPane scrollPane = new ScrollPane(tableEquipment);
        scrollPane.setScrollingDisabled(true,false);

        scrollPane.setSize(screenWidth*0.55f,screenHeight*0.9f);
        scrollPane.setPosition(screenWidth*0.43f,0);


        ScrollPane scrollPaneItems = new ScrollPane(tableItems);
        scrollPaneItems.setScrollingDisabled(true,false);
        scrollPaneItems.setSize(screenWidth*0.55f,screenHeight*0.9f);
        scrollPaneItems.setPosition(screenWidth*0.7f, scrollPane.getY());

        Table potionTable = new Table();
        Texture manaPotion, potion;
        manaPotion = new Texture("images/combat/manapotion.png");
        potion = new Texture("images/combat/potion.png");
        potionTable.add(new Image(potion)).width(potion.getWidth()*2f).height(potion.getHeight()*2f);
        potionTable.add(new Label(" : 0",new Label.LabelStyle(new BitmapFont(), Color.WHITE)));


        potionTable.add(new Image(manaPotion)).width(manaPotion.getWidth()*2f).height(manaPotion.getHeight()*2f);
        potionTable.add(new Label(" : 0",new Label.LabelStyle(new BitmapFont(), Color.WHITE)));

        potionTable.setPosition(screenWidth*0.87f,screenHeight*0.96f);

        stage = new Stage();
        stage.addActor(scrollPane);
        stage.addActor(scrollPaneItems);
        stage.addActor(potionTable);
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Pour améliorer un équipement
     * @param equipement à améliorer
     */
    private void upgrade(Equipement equipement){
        equipement.getEquipement().upgrade();
        camp = false;
        p.getPlayerCharacter().setInInventory(false);
        mapInterface.setScreen();
    }

    /**
     * Lance l'inventaire spécialement conçu pour le feu de camp dans le but d'améliorer le premier equipement cliqué
     */
    void toUpgrade(){
        camp = true;
        p.getPlayerCharacter().setInInventory(true);
        for(final Equipement e : equipements) {
            ButtonItem ib = new ButtonItem(new TextureRegionDrawable(e.getTexture()));
            ib.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (camp) {
                        upgrade(e);
                    }
                }
            });
            stage.addActor(ib);
            Gdx.input.setInputProcessor(stage);
        }
        update();
    }


    @Override
    public void render(float delta) {
        //Gdx.gl.glClearColor(0.25f, 0.25f, 0.25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        stage.act(delta);
        stage.getBatch().begin();
        stage.getBatch().draw(background,0,0);
        p.getPlayerCharacter().draw((SpriteBatch) stage.getBatch(), screenWidth*0.05f,screenHeight*0.5f - p.getPlayerCharacter().getHeight()/2 );
        // slots around the character
        armorSlot.draw(stage.getBatch());
        headSlot.draw(stage.getBatch());
        leftWepSlot.draw(stage.getBatch());
        rightWepSlot.draw(stage.getBatch());
        stage.getBatch().end();

        stage.draw();

    }

    void update(){
        Gdx.input.setInputProcessor(stage);
        tableEquipment.clearChildren();
        tableItems.clearChildren();

        tableEquipment.align(Align.top);
        for(final Equipement e : equipements){

            ButtonItem ib = new ButtonItem(new TextureRegionDrawable(e.getTexture()));
            ib.setSize(screenWidth/80f, screenHeight/45f);
            ib.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(camp){
                        upgrade(e);
                    }
                }
            });


            tableEquipment.add(ib).width(ib.getWidth()*3f).height(ib.getHeight()*3f);
            tableEquipment.add(new Label(
                    "Att: " + e.getPhysicalDamage() + " Mag: " + e.getMagicDamage() + " Armor: " + e.getArmor() + " MagRes: " + e.getMagicResist(),
                    new Label.LabelStyle(new BitmapFont(), Color.WHITE)));
            tableEquipment.row();
            itemsButton.add(ib);
        }
        tableEquipment.align(Align.left);

        tableItems.align(Align.top);
        for(final ItemWeapon e : items){

            ButtonItem ib = new ButtonItem(new TextureRegionDrawable(e.getTexture()));
            ib.setSize(screenWidth/80f, screenHeight/45f);
            ib.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //Afficher description
                }
            });


            tableItems.add(ib).width(ib.getWidth()*3f).height(ib.getHeight()*3f);
            tableItems.add(new Label(e.getDescription(),
                    new Label.LabelStyle(new BitmapFont(), Color.WHITE)));
            tableItems.row();
            itemsButton.add(ib);
        }
        tableItems.align(Align.left);
    }

    void addItem(ItemWeapon item){
        items.add(item);
    }

    @Override
    public void dispose() {
    }
}
