package fr.ul.roguelike.model.Items.Equipment.Warrior;

import com.badlogic.gdx.graphics.Texture;
import fr.ul.roguelike.model.Items.Equipment.Equipement;
import fr.ul.roguelike.model.Items.Equipment.EquipementStats;

public class DemonSword extends Equipement {

    private EquipementStats equipement;
    private Texture texture;

    public DemonSword() {
        super("Demon Sword", "A sword welded by powerfull demons bathed in blood");
        equipement = new EquipementStats(10,3,0.1f,0.1f);
        texture = new Texture("equipment/demonSword.png");
    }
    @Override
    public float getArmor() {
        return equipement.armor;
    }

    @Override
    public float getMagicResist() {
        return equipement.magicResist;
    }

    @Override
    public int getPhysicalDamage() {
        return equipement.physicalDamage;
    }

    @Override
    public int getMagicDamage() {
        return equipement.magicalDamage;
    }

    @Override
    public Texture getTexture() {
        return texture;

    }
}
