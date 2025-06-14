package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import object.OBJ_Key;

public class UI {

    GamePanel gp;
    Graphics2D g2;

    // TITLE SCREEN
    public int commandNum = 0;

    // STATIC UI
    Font arial_40;
    Font arial_80B;
    Font maruMonica;
    Font purisaB;
    BufferedImage keyImage;

    // PLAYTIME
    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    // DIALOGUE
    public String currentDialogue = "";

    // NOTIFICATIONS
    public boolean messageOn = false;
    public String message = "";
    int messageTimer = 0;
    int messageDuration = 0;

    public UI(GamePanel gp) {
        this.gp = gp;
        this.arial_40 = new Font("Arial", Font.PLAIN, 40);
        this.arial_80B = new Font("Arial", Font.BOLD, 80);

        try {
            InputStream is = getClass().getResourceAsStream("/fonts/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);

            is = getClass().getResourceAsStream("/fonts/PurisaBold.ttf");
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {

            e.printStackTrace();
        }
        keyImage = new OBJ_Key(gp).image;
    }

    public void showMessage(String text, int duration) {
        message = text;
        messageOn = true;
        messageDuration = duration;
    }

    public void drawTitleState() {

        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // GAME TITLE
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String text = "Deterrence";
        int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - textLength / 2;
        int y = gp.tileSize * 3;

        // TITLE SHADOW
        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 5, y + 5);

        // MAIN COLOR
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        // CHARACTER IMAGE
        x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2;
        y += gp.tileSize * 2;
        g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

        // MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

        text = "NEW GAME";
        textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = gp.screenWidth / 2 - textLength / 2;
        y += gp.tileSize * 4;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "LOAD GAME";
        textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = gp.screenWidth / 2 - textLength / 2;
        y += gp.tileSize * 1;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "QUIT";
        textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = gp.screenWidth / 2 - textLength / 2;
        y += gp.tileSize * 1;
        g2.drawString(text, x, y);
        if (commandNum == 2) {
            g2.drawString(">", x - gp.tileSize, y);
        }

    }

    public void drawPlayState() {
        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);
        g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
        g2.drawString(": " + gp.player.hasKey, 74, 65);

        // PLAYTIME
        playTime += (double) 1 / 60;
        g2.drawString("Time: " + dFormat.format(playTime), gp.tileSize * 11, 65);
        ;

        // NOTIFICATIONS
        if (messageOn == true) {
            g2.setFont(g2.getFont().deriveFont(30F));
            g2.drawString(message, gp.tileSize / 2, gp.tileSize * 5);

            messageTimer++;

            if (messageTimer > messageDuration) {
                messageTimer = 0;
                messageOn = false;
            }
        }
    }

    public void drawPauseScreen() {
        gp.keyH.developerMode = false;

        String text = "PAUSED";
        int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - textLength / 2;
        int y = gp.screenHeight / 2 + (gp.tileSize * 2);

        g2.drawString(text, x, y);
    }

    public void drawWinScreen() {
        gp.keyH.developerMode = false;

        String text;
        int textLength;
        int x;
        int y;

        // Treasure found message
        text = "Treasure Found!";
        textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = gp.screenWidth / 2 - textLength / 2;
        y = gp.screenHeight / 2 - (gp.tileSize * 3);
        g2.drawString(text, x, y);

        text = "Your Time is: " + dFormat.format(playTime);
        textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = gp.screenWidth / 2 - textLength / 2;
        y = gp.screenHeight / 2 + (gp.tileSize * 4);
        g2.drawString(text, x, y);

        g2.setFont(arial_80B);
        g2.setColor(Color.YELLOW);

        // Win message
        text = "YOU WIN!!!";
        textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();

        x = gp.screenWidth / 2 - textLength / 2;
        y = gp.screenHeight / 2 + (gp.tileSize * 2);
        g2.drawString(text, x, y);

        gp.gameThread = null;
    }

    public void drawDialogueScreen() {
        // WINDOW
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;
        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        x += gp.tileSize;
        y += gp.tileSize;

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }

    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);

    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);

        switch (gp.gameState) {
            case GamePanel.TITLE_STATE:
                drawTitleState();
                break;
            case GamePanel.PLAY_STATE:
                drawPlayState();
                break;
            case GamePanel.PAUSE_STATE:
                drawPauseScreen();
                break;
            case GamePanel.WIN_STATE:
                drawWinScreen();
                break;
            case GamePanel.DIALOGUE_STATE:
                drawDialogueScreen();
                break;
            default:
                break;
        }

    }
}
