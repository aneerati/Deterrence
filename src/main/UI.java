package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class UI {

    GamePanel gp;
    Font textFont;

    public UI(GamePanel gp) {
        this.gp = gp;
        this.textFont = new Font("Arial", Font.PLAIN, 40);
    }

    public void draw(Graphics2D g2) {

        g2.setFont(textFont);
        g2.setColor(Color.WHITE);
        g2.drawString("Key = " + gp.player.hasKey, 50, 50);
    }
}
