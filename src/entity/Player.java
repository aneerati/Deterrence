package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {

    // GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public int hasKey = 0;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;

        // player position on screen
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        // player collision box
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = gp.tileSize - 16;
        solidArea.height = gp.tileSize - 16;

        setDefaultValues();
        loadPlayerImages();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
    }

    // Load player sprites on startup
    public void loadPlayerImages() {

        up1 = setupEntity("/player/player_up_1");
        up2 = setupEntity("/player/player_up_2");

        down1 = setupEntity("/player/player_down_1");
        down2 = setupEntity("/player/player_down_2");

        left1 = setupEntity("/player/player_left_1");
        left2 = setupEntity("/player/player_left_2");

        right1 = setupEntity("/player/player_right_1");
        right2 = setupEntity("/player/player_right_2");
    }

    // update player's position and sprite
    public void update() {
        if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true
                || keyH.rightPressed == true) {
            if (keyH.upPressed == true) {
                direction = "up";
            } else if (keyH.downPressed == true) {
                direction = "down";
            } else if (keyH.leftPressed == true) {
                direction = "left";
            } else if (keyH.rightPressed == true) {
                direction = "right";
            }

            // CHECK COLLISION WITH TILE
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // CHECK COLLISION WITH OBJECT
            int objIndex = gp.cChecker.checkObject(this, true);
            pickupObject(objIndex);

            // CHECK COLLISION WITH NPC
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

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
        } else {
            standCounter++;

            if (standCounter == 20) {
                spriteNum = 1;
                standCounter = 0;
            }
        }
    }

    public void pickupObject(int i) {

        if (i != 999) {
            String objName = gp.obj[i].name;

            switch (objName) {
                case "key":
                    gp.playSoundEffect(1);
                    hasKey++;
                    gp.obj[i] = null;
                    gp.ui.showMessage("Key Picked Up", 120);
                    break;
                case "door":
                    if (hasKey > 0) {
                        gp.playSoundEffect(3);
                        gp.obj[i] = null;
                        hasKey--;
                    }
                    if (hasKey <= 0) {
                        gp.ui.showMessage("Find a Key", 180);
                    }
                    break;
                case "boots":
                    gp.playSoundEffect(2);
                    speed += 2;
                    gp.obj[i] = null;
                    gp.ui.showMessage("Speed Increased", 120);
                    break;
                case "chest":
                    gp.gameState = GamePanel.WIN_STATE;
                    gp.stopMusic();
                    gp.playSoundEffect(4);
                    break;
                default:
                    break;
            }
        }
    }

    public void interactNPC(int i) {
        if (i != 999) {
            gp.gameState = GamePanel.DIALOGUE_STATE;
            gp.npc[i].speak();
        }
    }

    // draw updates player sprite
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

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
        g2.drawImage(image, screenX, screenY, null);

        // GAME CHANGES FOR DEVELOPER MODE
        if (keyH.developerMode == true) {
            g2.setColor(Color.RED);
            g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
        }
    }
}
