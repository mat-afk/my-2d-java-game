package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity {

    int heal;

    public OBJ_Heart(GamePanel gp) {

        super(gp);

        type = typePickup;
        name = "Heart";
        heal = 2;

        down1 = setup("/objects/heart_full");
        image = setup("/objects/heart_full");
        image2 = setup("/objects/heart_half");
        image3 = setup("/objects/heart_blank");
    }

    public void use(Entity entity) {

        gp.playSoundEffect(2);

        gp.ui.addMessage("Life +" + heal);
        entity.life += heal;
    }
}
