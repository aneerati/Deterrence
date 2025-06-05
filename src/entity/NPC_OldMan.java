package entity;

import java.util.Random;

import main.GamePanel;

public class NPC_OldMan extends Entity {

    public NPC_OldMan(GamePanel gp) {
        super(gp);

        direction = "right";
        speed = 2;

        loadImages();
        setDialogue();
    }

    // Load old man sprites on startup
    public void loadImages() {

        up1 = setupEntity("/npc/oldman_up_1");
        up2 = setupEntity("/npc/oldman_up_2");

        down1 = setupEntity("/npc/oldman_down_1");
        down2 = setupEntity("/npc/oldman_down_2");

        left1 = setupEntity("/npc/oldman_left_1");
        left2 = setupEntity("/npc/oldman_left_2");

        right1 = setupEntity("/npc/oldman_right_1");
        right2 = setupEntity("/npc/oldman_right_2");
    }

    public void setDialogue() {
        dialogues[0] = "Hello Sir";
    }

    public void setAction() {
        actionLockCounter++;

        if (actionLockCounter == 120) {
            Random random = new Random();

            int i = random.nextInt(100) + 1;

            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }

            actionLockCounter = 0;
        }
    }

    public void speak() {
        gp.ui.currentDialogue = dialogues[0];
    }

}
