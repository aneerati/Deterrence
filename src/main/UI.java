package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import object.OBJ_Key;

public class UI {

    GamePanel gp;

    // STATIC UI
    Font textFont;
    BufferedImage keyImage;

    // NOTIFICATIONS
    public boolean messageOn = false;
    public String message = "";
    int messageTimer = 0;
    int messageDuration = 0;

    public UI(GamePanel gp) {
        this.gp = gp;
        this.textFont = new Font("Arial", Font.PLAIN, 40);
        OBJ_Key key = new OBJ_Key();
        keyImage = key.image;
    }

    public void showMessage(String text, int duration) {
        message = text;
        messageOn = true;
        messageDuration = duration;
    }

    public void draw(Graphics2D g2) {

        // STATIC UI
        g2.setFont(textFont);
        g2.setColor(Color.WHITE);
        g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
        g2.drawString(": " + gp.player.hasKey, 74, 65);

        // NOTIFICATIONS
        if (messageOn == true) {
            g2.setFont(g2.getFont().deriveFont(30F));
            g2.drawString(message, gp.tileSize / 2, gp.tileSize * 5);

            messageTimer++;

            // waits 120 frames
            if (messageTimer > messageDuration) {
                messageTimer = 0;
                messageOn = false;
            }
        }
    }
}
