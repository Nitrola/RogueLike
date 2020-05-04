package fr.ul.roguelike.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import fr.ul.roguelike.model.Items.ButtonItem;
import fr.ul.roguelike.model.Items.Equipment.Archer.ArcherPlate;
import fr.ul.roguelike.model.Items.Equipment.Archer.BaseBow;
import fr.ul.roguelike.model.Items.Equipment.Equipement;
import fr.ul.roguelike.model.Items.Equipment.Warrior.DemonSword;
import fr.ul.roguelike.model.Items.Item;
import fr.ul.roguelike.model.Player;

import java.util.ArrayList;


public class InventoryMenu extends ScreenAdapter {

    private Player p;
    private MapInterface mapInterface;
    private Stage stage;
    private ScrollPane scrollPane;
    private Texture background;
    private Sprite headSlot;
    private Sprite leftWepSlot;
    private Sprite rightWepSlot;
    private Sprite armorSlot;
    private ArrayList<Button> itemsButton;
    private ArrayList<Equipement> equipements;
    private boolean camp;
    private Table table;
    private SpriteBatch sb;

    private int screenWidth = Gdx.graphics.getWidth();
    private int screenHeight = Gdx.graphics.getHeight();

    public InventoryMenu(MapInterface mapInterface) {

        this.p = mapInterface.getPlayer();
        this.mapInterface = mapInterface;
        headSlot = new Sprite(new Texture("images/inventory/headSlot.png")) ;
        leftWepSlot = new Sprite(new Texture("images/inventory/leftWeaponSlot.png")) ;
        rightWepSlot = new Sprite(new Texture("images/inventory/rightWeaponSlot.png")) ;
        armorSlot = new Sprite(new Texture("images/inventory/armorSlot.png")) ;
        sb = new SpriteBatch();

        armorSlot.setPosition(Gdx.graphics.getWidth()*0.17f,Gdx.graphics.getHeight()*0.17f);
        rightWepSlot.setPosition(Gdx.graphics.getWidth()*0.3f,Gdx.graphics.getHeight()*0.4f);
        leftWepSlot.setPosition(Gdx.graphics.getWidth()*0.04f,Gdx.graphics.getHeight()*0.4f);
        headSlot.setPosition(Gdx.graphics.getWidth()*0.17f,Gdx.graphics.getHeight()*0.6f);

        itemsButton = new ArrayList<>();
        //inventorySlot.scale(2f);
        table = new Table();

        equipements = new ArrayList<>();
        equipements.add(new DemonSword());
        equipements.add(new ArcherPlate());
        equipements.add(new BaseBow());
        equipements.add(new BaseBow());
        equipements.add(new BaseBow());
        equipements.add(new BaseBow());
        equipements.add(new BaseBow());
        equipements.add(new BaseBow());
        equipements.add(new BaseBow());
        equipements.add(new BaseBow());
        equipements.add(new BaseBow());
        equipements.add(new BaseBow());
        equipements.add(new BaseBow());
        equipements.add(new BaseBow());
        equipements.add(new DemonSword());

        camp = false;


        //table.setFillParent(true);
        table.align(Align.top);
        for(final Equipement e : equipements){

            ButtonItem ib = new ButtonItem(new TextureRegionDrawable(e.getTexture()));
            ib.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(camp){
                        upgrade(e);
                    }
                };
            });


            table.add(ib).width(ib.getWidth()*3f).height(ib.getHeight()*3f);
            table.add(new Label(
                    "Att: " + e.getPhysicalDamage() + " Mag: " + e.getMagicDamage() + " Armor: " + e.getArmor() + " MagRes: " + e.getMagicResist(),
                    new Label.LabelStyle(new BitmapFont(), Color.WHITE)));
            table.row();
            itemsButton.add(ib);
        }
        table.align(Align.left);

        background = new Texture("images/inventory/background.png");

        scrollPane = new ScrollPane(table);
        scrollPane.setScrollingDisabled(true,false);

        scrollPane.setSize(Gdx.graphics.getWidth()*0.55f,Gdx.graphics.getHeight()*0.9f);
        scrollPane.setPosition(Gdx.graphics.getWidth()*0.43f,0);

        Table potionTable = new Table();
        Texture manaPotion, potion;
        manaPotion = new Texture("images/combat/manapotion.png");
        potion = new Texture("images/combat/potion.png");
        potionTable.add(new Image(potion)).width(potion.getWidth()*2f).height(potion.getHeight()*2f);
        potionTable.add(new Label(" : 0",new Label.LabelStyle(new BitmapFont(), Color.WHITE)));


        potionTable.add(new Image(manaPotion)).width(manaPotion.getWidth()*2f).height(manaPotion.getHeight()*2f);
        potionTable.add(new Label(" : 0",new Label.LabelStyle(new BitmapFont(), Color.WHITE)));

        potionTable.setPosition(Gdx.graphics.getWidth()*0.87f,Gdx.graphics.getHeight()*0.96f);

        stage = new Stage();
        stage.addActor(scrollPane);
        stage.addActor(potionTable);
        Gdx.input.setInputProcessor(stage);
    }

    public void upgrade(Equipement equipement){
        equipement.getEquipement().upgrade();
        camp = false;
        p.getPlayerCharacter().setInInventory(false);
        mapInterface.setScreen();
    }

    public void toUpgrade (){
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

                ;
            });
            stage.addActor(ib);
            Gdx.input.setInputProcessor(stage);
        }
        table.clearChildren();
        table.align(Align.top);
        for(final Equipement e : equipements){

            ButtonItem ib = new ButtonItem(new TextureRegionDrawable(e.getTexture()));
            ib.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(camp){
                        upgrade(e);
                    }
                };
            });


            table.add(ib).width(ib.getWidth()*3f).height(ib.getHeight()*3f);
            table.add(new Label(
                    "Att: " + e.getPhysicalDamage() + " Mag: " + e.getMagicDamage() + " Armor: " + e.getArmor() + " MagRes: " + e.getMagicResist(),
                    new Label.LabelStyle(new BitmapFont(), Color.WHITE)));
            table.row();
            itemsButton.add(ib);
        }
        table.align(Align.left);
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

    public void update(){
        for(int i = 0; i < itemsButton.size(); i++){
            Button b = itemsButton.get(i);
            if(b.isPressed()){
                //todo
                p.getPlayerGear().changeGear(equipements.get(i));
                break;
            }
        }

    }

    @Override
    public void dispose() {
    }
}
