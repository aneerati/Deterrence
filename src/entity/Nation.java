package entity;

import main.GamePanel;

public class Nation {

    GamePanel gp;

    public String name;

    public Nation(GamePanel gp, String nationName) {
        this.gp = gp;
        this.name = nationName;
    }
}
