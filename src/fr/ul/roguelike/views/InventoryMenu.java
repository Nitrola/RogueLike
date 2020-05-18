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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.Align;
import fr.ul.roguelike.model.heros.Hero;
import fr.ul.roguelike.model.items.ButtonItem;
import fr.ul.roguelike.model.items.equipments.Equipement;
import fr.ul.roguelike.model.items.equipments.armors.heads.Casque;
import fr.ul.roguelike.model.items.equipments.armors.plates.ArcherPlate;
import fr.ul.roguelike.model.items.equipments.armors.plates.HighMageMantel;
import fr.ul.roguelike.model.items.equipments.weapons.BaseBow;
import fr.ul.roguelike.model.items.equipments.weapons.DemonSword;
import fr.ul.roguelike.model.items.equipments.weapons.WaterStick;
import fr.ul.roguelike.model.items.runes.Rune;
import fr.ul.roguelike.model.Player;

import java.util.ArrayList;

import static fr.ul.roguelike.RogueLike.screenHeight;
import static fr.ul.roguelike.RogueLike.screenWidth;


public class InventoryMenu extends ScreenAdapter {

    private Player player;
    private MapInterface mapInterface;
    private Stage stage;
    private Texture background;
    private Sprite headSlot;
    private Sprite leftWepSlot;
    private Sprite rightWepSlot;
    private Sprite armorSlot;
    private Sprite textureAttack, textureDefense, textureDefenseMagic, textureAttackMagic, textureHealth, textureMana;
    private ScrollPane scrollPane;
    private SpriteBatch spriteBatch;
    private ArrayList<ButtonItem> itemsButton, runesButton;
    private ArrayList<Equipement> equipements;
    private ArrayList<Rune> runes;
    private ImageButton exitBouton;
    private boolean camp;
    private Table tableEquipment, tableItems;
    private Label labelHealthPotion,labelManaPotion, labelAttack, labelAttackMagic, labelDefense, labelDefenseMagic, labelHealth, labelMana;
    private float buttonWidth = screenWidth/20f, buttonHeight = screenHeight/10f;

    /**
     * Représente l'inventaire du joueur contenant ses équipements et objets
     */
    InventoryMenu(final MapInterface mapInterface) {

        this.player = mapInterface.getPlayer();
        this.mapInterface = mapInterface;
        camp = false;
        spriteBatch = new SpriteBatch();

        fillInventary();
        initStage();
        moveEquipment();
    }

    /**
     * Remplit l'inventaire
     */
    private void fillInventary() {
        headSlot = new Sprite(new Texture("images/inventory/headSlot.png"));
        leftWepSlot = new Sprite(new Texture("images/inventory/leftWeaponSlot.png"));
        rightWepSlot = new Sprite(new Texture("images/inventory/rightWeaponSlot.png"));
        armorSlot = new Sprite(new Texture("images/inventory/armorSlot.png"));

        textureAttack = new Sprite(new Texture("images/inventory/attack.png"));
        textureAttackMagic = new Sprite(new Texture("images/inventory/attack_magic.png"));
        textureHealth = new Sprite(new Texture("images/combat/heart.png"));
        textureDefense = new Sprite(new Texture("images/inventory/defense.png"));
        textureDefenseMagic = new Sprite(new Texture("images/inventory/defense_magic.png"));
        textureMana = new Sprite(new Texture("images/inventory/mana.png"));

        armorSlot.setPosition(screenWidth*0.17f,screenHeight*0.17f+screenHeight/5f);
        rightWepSlot.setPosition(screenWidth*0.3f,screenHeight*0.4f+screenHeight/5f);
        leftWepSlot.setPosition(screenWidth*0.04f,screenHeight*0.4f+screenHeight/5f);
        headSlot.setPosition(screenWidth*0.17f,screenHeight*0.6f+screenHeight/5f);

        itemsButton = new ArrayList<>();
        tableEquipment = new Table();
        tableItems = new Table();

        equipements = new ArrayList<>();
        equipements.add(new DemonSword());
        equipements.add(new ArcherPlate());
        equipements.add(new BaseBow());
        equipements.add(new HighMageMantel());
        equipements.add(new WaterStick());
        equipements.add(new Casque());
    }

    /**
     * Initialisation des boutons
     */
    private void initStage() {
        runes = new ArrayList<>();
        runesButton = new ArrayList<>();
        //table.setFillParent(true);
        tableEquipment.align(Align.top);
        tableEquipment.align(Align.left);
        tableItems.align(Align.top);
        tableItems.align(Align.left);

        background = new Texture("images/inventory/background.png");


        scrollPane = new ScrollPane(tableEquipment);
        scrollPane.setScrollingDisabled(true,false);

        scrollPane.setSize(screenWidth*0.55f,screenHeight*0.9f);
        scrollPane.setPosition(screenWidth*0.43f,0);
        scrollPane.toFront();


        ScrollPane scrollPaneItems = new ScrollPane(tableItems);
        scrollPaneItems.setScrollingDisabled(true,false);
        scrollPaneItems.setSize(screenWidth*0.55f,screenHeight*0.9f);
        scrollPaneItems.setPosition(screenWidth*0.7f, scrollPane.getY());
        scrollPaneItems.toFront();

        Table potionTable = new Table();
        Texture manaPotion, potion;
        manaPotion = new Texture("images/combat/manapotion.png");
        potion = new Texture("images/combat/potion.png");
        potionTable.add(new Image(potion)).width(potion.getWidth()*2f).height(potion.getHeight()*2f);
        potionTable.add(labelHealthPotion = new Label(Integer.toString(player.getNbPotionHealth()),new Label.LabelStyle(new BitmapFont(), Color.WHITE)));


        potionTable.add(new Image(manaPotion)).width(manaPotion.getWidth()*2f).height(manaPotion.getHeight()*2f);
        potionTable.add(labelManaPotion = new Label(Integer.toString(player.getNbPotionMana()),new Label.LabelStyle(new BitmapFont(), Color.WHITE)));

        potionTable.setPosition(screenWidth*0.87f,screenHeight*0.96f);


        //Stats
        Table statsTable = new Table();
        statsTable.setPosition(screenWidth*0.2f, screenHeight*0.2f);
        //Attaque
        statsTable.add(new Image(textureAttack)).width(screenWidth/18f).height(screenHeight/10f).pad(screenWidth/60f);
        statsTable.add(labelAttack = new Label(Integer.toString((int) player.getPlayerCharacter().getPhysicalDmg(player)), new Label.LabelStyle(new BitmapFont(), Color.RED))).width(screenWidth/20f);
        //Attaque magique
        statsTable.add(new Image(textureAttackMagic)).width(screenWidth/18f).height(screenHeight/10f).pad(screenWidth/60f);
        statsTable.add(labelAttackMagic = new Label(Integer.toString((int) player.getPlayerCharacter().getMagicalDmg(player)), new Label.LabelStyle(new BitmapFont(), Color.RED))).width(screenWidth/20f);
        //Points de vie
        statsTable.add(new Image(textureHealth)).width(screenWidth/18f).height(screenHeight/10f).pad(screenWidth/60f);
        statsTable.add(labelHealth = new Label(Integer.toString((int) player.getPlayerCharacter().getHp()), new Label.LabelStyle(new BitmapFont(), Color.RED))).width(screenWidth/20f).width(screenWidth/20f);

        statsTable.row().height(screenHeight/20f);
        //Defense
        statsTable.add(new Image(textureDefense)).width(screenWidth/18f).height(screenHeight/7f).pad(screenWidth/60f);
        statsTable.add(labelDefense = new Label(Integer.toString((int) player.getPlayerCharacter().getPhysicalDef(player)), new Label.LabelStyle(new BitmapFont(), Color.RED))).width(screenWidth/20f);
        //Defense magique
        statsTable.add(new Image(textureDefenseMagic)).width(screenWidth/18f).height(screenHeight/7f).pad(screenWidth/60f);
        statsTable.add(labelDefenseMagic = new Label(Integer.toString((int) player.getPlayerCharacter().getMagicalDef(player)), new Label.LabelStyle(new BitmapFont(), Color.RED))).width(screenWidth/20f);
        //Mana
        statsTable.add(new Image(textureMana)).width(screenWidth/18f).height(screenHeight/6f).pad(screenWidth/60f);
        statsTable.add(labelMana = new Label(Integer.toString((int) player.getPlayerCharacter().getMana()), new Label.LabelStyle(new BitmapFont(), Color.RED))).width(screenWidth/20f).width(screenWidth/20f);

        //Bouton Exit
        Texture exit = new Texture(Gdx.files.internal("images/exit.png"));
        Drawable drawableExit = new TextureRegionDrawable(new TextureRegion(exit));
        drawableExit.setMinHeight(screenHeight/9f);
        drawableExit.setMinWidth(screenWidth/8f);
        exitBouton = new ImageButton(drawableExit);
        exitBouton.setPosition(0,screenHeight-drawableExit.getMinHeight());
        exitBouton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mapInterface.getRogueLike().setScreen(mapInterface);
                player.getPlayerCharacter().setInInventory(false);
                mapInterface.setInputProcessor();
            }
        });

        stage = new Stage();
        stage.addActor(scrollPane);
        stage.addActor(scrollPaneItems);
        stage.addActor(potionTable);
        stage.addActor(statsTable);
        stage.addActor(exitBouton);
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Pour améliorer un équipement
     * @param equipement à améliorer
     */
    private void upgrade(Equipement equipement){
        equipement.getEquipement().upgrade();
        camp = false;
        player.getPlayerCharacter().setInInventory(false);
        mapInterface.setScreen();
    }

    /**
     * Lance l'inventaire spécialement conçu pour le feu de camp dans le but d'améliorer le premier equipement cliqué
     */
    void toUpgrade(){
        camp = true;
        exitBouton.addAction(Actions.removeActor());
        player.getPlayerCharacter().setInInventory(true);
        for(final Equipement e : equipements) {
            ButtonItem ib = new ButtonItem(new TextureRegionDrawable(e.getTexture()), e ,buttonWidth, buttonHeight);
            ib.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (camp) {
                        upgrade(e);
                    }
                }
            });
            Gdx.input.setInputProcessor(stage);
        }
        update();
    }


    @Override
    public void render(float delta) {
        //Gdx.gl.glClearColor(0.25f, 0.25f, 0.25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        stage.act(delta);

        spriteBatch.begin();
        spriteBatch.draw(background,0,0, screenWidth, screenHeight);
        player.getPlayerCharacter().draw(spriteBatch, screenWidth*0.05f,screenHeight*0.5f - player.getPlayerCharacter().getHeight()/2+screenHeight/5f);

        // slots around the character
        armorSlot.draw(spriteBatch);
        headSlot.draw(spriteBatch);
        leftWepSlot.draw(spriteBatch);
        rightWepSlot.draw(spriteBatch);

        labelHealthPotion.setText(player.getNbPotionHealth());
        labelManaPotion.setText(player.getNbPotionMana());
        labelAttack.setText(Integer.toString((int) player.getPlayerCharacter().getPhysicalDmgWithoutCrit(player)));
        labelAttackMagic.setText(Integer.toString((int) player.getPlayerCharacter().getMagicalDmgWithoutCrit(player)));
        labelDefense.setText(Integer.toString((int) player.getPlayerCharacter().getPhysicalDef(player)));
        labelDefenseMagic.setText(Integer.toString((int) player.getPlayerCharacter().getMagicalDef(player)));
        labelHealth.setText(Integer.toString((int) player.getPlayerCharacter().getHp()));
        labelMana.setText(Integer.toString((int) player.getPlayerCharacter().getMana()));
        spriteBatch.end();

        stage.draw();

        if(camp) {
            upgradeInventaryItem();
        }
    }

    private void upgradeInventaryItem() {
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) || Gdx.input.isTouched()){
            //HEAD
            if (headSlot.getBoundingRectangle().contains(Gdx.input.getX(), screenHeight - Gdx.input.getY())) {

                if(isSlotFilled(headSlot)){
                    upgrade(player.getEquipement(0));
                }
                //RIGHT ARM
            } else if (rightWepSlot.getBoundingRectangle().contains(Gdx.input.getX(), screenHeight - Gdx.input.getY())) {
                if(isSlotFilled(rightWepSlot)){
                    upgrade(player.getEquipement(1));
                }

                //LEFT ARM
            } else if (leftWepSlot.getBoundingRectangle().contains(Gdx.input.getX(), screenHeight - Gdx.input.getY())) {
                if(isSlotFilled(leftWepSlot)){
                    upgrade(player.getEquipement(2));
                }

                //ARMOR PLATE
            } else if (armorSlot.getBoundingRectangle().contains(Gdx.input.getX(), screenHeight - Gdx.input.getY())) {
                if(isSlotFilled(armorSlot)){
                    upgrade(player.getEquipement(3));
                }
            }
        }
    }

    private void moveEquipment() {
        int position = 0;
        for(final ButtonItem button : itemsButton) {
            position++;
            final int finalPosition = position;
            button.addListener(new DragListener() {
                @Override
                public void dragStart(InputEvent event, float x, float y, int pointer) {
                    super.dragStart(event, x, y, pointer);
                    equipements.remove(button.getEquipement());
                    button.setPosition(screenWidth+200, screenHeight+200);
                    stage.addActor(button);
                }

                public void drag(InputEvent event, float x, float y, int pointer) {
                    button.moveBy(x - button.getWidth() / 2, y - button.getHeight() / 2);
                }

                @Override
                public void dragStop(InputEvent event, float x, float y, int pointer) {
                    super.dragStart(event, x, y, pointer);
                    scrollPane.setFlickScroll(true);
                    stage.addAction(Actions.removeActor(button));

                    ///////////////////////////////////////////
                    //Placement des equipements sur les slots//
                    ///////////////////////////////////////////

                    //HEAD
                    if(headSlot.getBoundingRectangle().contains(Gdx.input.getX(), screenHeight - Gdx.input.getY())){
                        if(button.getEquipement().getEquipementType() == Equipement.EquipementType.HEAD){
                            replace(headSlot, button.getEquipement(), 0);
                        }else{
                            equipements.add(finalPosition-1, button.getEquipement());
                        }

                    //RIGHT ARM
                    }else if(rightWepSlot.getBoundingRectangle().contains(Gdx.input.getX(), screenHeight - Gdx.input.getY())){
                        if(button.getEquipement().getEquipementType() == Equipement.EquipementType.WEAPON){
                            if(isSlotFilled(rightWepSlot) && !isSlotFilled(leftWepSlot)) {
                                replace(leftWepSlot, button.getEquipement(), 2);
                            }else{
                                replace(rightWepSlot, button.getEquipement(), 1);
                            }
                        }else{
                            equipements.add(finalPosition-1, button.getEquipement());
                        }

                    //LEFT ARM
                    }else if(leftWepSlot.getBoundingRectangle().contains(Gdx.input.getX(), screenHeight - Gdx.input.getY())) {
                        if (button.getEquipement().getEquipementType() == Equipement.EquipementType.WEAPON) {
                            if(!isSlotFilled(rightWepSlot) && isSlotFilled(leftWepSlot)) {
                                replace(rightWepSlot, button.getEquipement(), 1);
                            }else{
                                replace(leftWepSlot, button.getEquipement(), 2);
                            }
                        } else {
                            equipements.add(finalPosition - 1, button.getEquipement());
                        }


                    //ARMOR PLATE
                    }else if(armorSlot.getBoundingRectangle().contains(Gdx.input.getX(), screenHeight - Gdx.input.getY())){
                        if(button.getEquipement().getEquipementType() == Equipement.EquipementType.PLATE){
                            replace(armorSlot, button.getEquipement(), 3);
                        }else{
                            equipements.add(finalPosition-1, button.getEquipement());
                        }

                    //Si l'item n'a été placé dans aucun slot
                    }else{
                        equipements.add(finalPosition-1, button.getEquipement());
                    }

                    update();
                }
            });
        }
    }

    private void replace(Sprite slot, Equipement equipement, int slotPosition) {
        if(isSlotFilled(slot)) {
            equipements.add(player.getEquipement(slotPosition));
            player.fillInventary(slotPosition, equipement);
        }else{
            player.fillInventary(slotPosition, equipement);
        }
        slot.setTexture(equipement.getTexture());
    }

    private boolean isSlotFilled(Sprite sprite){
        boolean res = true;
        if(sprite.getTexture().toString().equals("images/inventory/headSlot.png")
                || sprite.getTexture().toString().equals("images/inventory/leftWeaponSlot.png")
                || sprite.getTexture().toString().equals("images/inventory/rightWeaponSlot.png")
                || sprite.getTexture().toString().equals("images/inventory/armorSlot.png")){
            res = false;
        }

        return res;
    }

    /**
     * Redessine l'inventaire lorsqu'il est rechargé
     */
    void update(){
        if(!camp){
            stage.addActor(exitBouton);
        }
        Gdx.input.setInputProcessor(stage);
        itemsButton.clear();
        runesButton.clear();
        player.getPlayerCharacter().setCombatState(Hero.CombatState.IDLE); //Pour réinitialiser l'animation
        tableEquipment.clearChildren();
        tableItems.clearChildren();

        tableEquipment.align(Align.top);
        for(final Equipement e : equipements){
            ButtonItem ib = new ButtonItem(new TextureRegionDrawable(e.getTexture()), e, buttonWidth, buttonHeight);
            ib.setSize(buttonWidth/2f, buttonHeight/2f);
            ib.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(camp){
                        upgrade(e);
                    }else{
                        scrollPane.setFlickScroll(false);
                    }
                    return super.touchDown(event, x, y, pointer, button);
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
        for(final Rune e : runes){

            ButtonItem ib = new ButtonItem(new TextureRegionDrawable(e.getTexture()), e, buttonWidth, buttonHeight);
            ib.setSize(buttonWidth/2f, buttonHeight/2f);
            ib.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //Afficher description
                }
            });


            tableItems.add(ib).width(ib.getWidth()*3f).height(ib.getHeight()*3f);
            tableItems.add(new Label(e.getDescription(), new Label.LabelStyle(new BitmapFont(), Color.WHITE)));
            tableItems.row();
            runesButton.add(ib);
        }
        tableItems.align(Align.left);
        moveEquipment();
    }

    public void addItem(Rune item){
        runes.add(item);
    }

    public void addEquipement(Equipement equipement){
        equipements.add(equipement);
    }

    @Override
    public void dispose() {
    }
}
