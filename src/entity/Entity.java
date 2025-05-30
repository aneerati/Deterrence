package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {

    GamePanel gp;

    public int worldX;
    public int worldY;
    public int speed;

    public BufferedImage up1;
    public BufferedImage up2;
    public BufferedImage down1;
    public BufferedImage down2;
    public BufferedImage left1;
    public BufferedImage left2;
    public BufferedImage right1;
    public BufferedImage right2;

    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;
    public int standCounter = 0;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);

    public int solidAreaDefaultX;
    public int solidAreaDefaultY;

    public boolean collisionOn = false;

    public int actionLockCounter = 0;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public BufferedImage setupEntity(String imagePath) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    public void setAction() {

    }

    public void update() {
        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkPlayer(this);

        if (collisionOn == false) {
            switch (direction) {
                case "up":
                    worldY = worldY - speed;
                    break;
                case "down":
                    worldY = worldY + speed;
                    break;
                case "left":
                    worldX = worldX - speed;
                    break;
                case "right":
                    worldX = worldX + speed;
                    break;
                default:
                    break;
            }
        }

        spriteCounter++;
        if (spriteCounter > 10) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if ((worldX + gp.tileSize > gp.player.worldX - gp.player.screenX)
                && (worldX - gp.tileSize < gp.player.worldX + gp.player.screenX)
                && (worldY + gp.tileSize > gp.player.worldY - gp.player.screenY)
                && (worldY - gp.tileSize < gp.player.worldY + gp.player.screenY)) {

            switch (direction) {
                case "up":
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                    break;
                case "down":
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                    break;
                case "left":
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                    break;
                case "right":
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                    break;
                default:
                    break;
            }

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}
