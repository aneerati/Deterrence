package entity;

import main.GamePanel;

public class NPC_OldMan extends Entity {

    public NPC_OldMan(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 2;
    }

    // Load player sprites on startup
    public void loadPlayerImages() {

        up1 = setupEntity("player_up_1");
        up2 = setupEntity("player_up_2");

        down1 = setupEntity("player_down_1");
        down2 = setupEntity("player_down_2");

        left1 = setupEntity("player_left_1");
        left2 = setupEntity("player_left_2");

        right1 = setupEntity("player_right_1");
        right2 = setupEntity("player_right_2");
    }

}
