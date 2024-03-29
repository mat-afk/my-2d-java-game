package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Chest extends Entity {

    public final static String OBJ_NAME = "Chest";

    public OBJ_Chest(GamePanel gp) {
        super(gp);

        name = OBJ_NAME;
        image = setup("/objects/chest");
        image2 = setup("/objects/chest_opened");
        down1 = image;
        type = typeObstacle;

        collision = true;

        solidArea.x = 4;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    @Override
    public void setLoot(Entity loot) {
        this.loot = loot;
        setDialogue();
    }

    public void setDialogue() {

        dialogues[0][0] = "You opened the chest and found a " + loot.name + "!\n" +
                "...But you cannot carry more items";
        dialogues[1][0] = "You opened the chest and found a " + loot.name + "!\n" +
                "You got a " + loot.name + "!";
        dialogues[2][0] = "It's empty.";

    }

    @Override
    public void interact() {

        if(!opened) {
            gp.playSoundEffect(3);

            if(!gp.player.canObtainItem(loot)) {
                startDialogue(this, 0);
            } else {
                startDialogue(this, 1);
                down1 = image2;
                opened = true;
            }

        } else {
            startDialogue(this, 2);
        }
    }
}
